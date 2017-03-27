package nl.gremmee.war.core;

import java.util.List;
import java.util.Random;

import nl.gremmee.war.ai.IAi;
import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;
import nl.gremmee.war.gui.SplashScreen;

public class Initialize {
    private static Initialize instance;
    private boolean initialized;
    private Random random;
    private SplashScreen splashScreen;

    private Initialize() {
        this.initialized = false;
        random = new Random();
    }

    public static Initialize getInstance() {
        if (instance == null) {
            instance = new Initialize();
        }
        return instance;
    }

    public SplashScreen getSplashScreen() {
        return this.splashScreen;
    }

    public void initializeAI(int aNumAi) {
        System.out.println("Initializing AI");
        AIController aic = AIController.getInstance();
        for (int i = 0; i < aic.getAis().size(); i++) {
            IAi ai = aic.getAI(i);
            String msg = "Creating AI '" + ai.getName() + "...";
            System.out.print(msg);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializeAIToPlayers(int aNumAiToPlayers) {
        System.out.println("Initializing AI to Players");
        AIController aic = AIController.getInstance();
        PlayerController pc = PlayerController.getInstance();
        for (Player player : pc.getPlayers()) {
            IAi ai = aic.getRandomAI();
            String msg = "Assigning AI '" + ai.getName() + "' to player '" + player.getName() + "'...";
            System.out.print(msg);
            player.setAi(ai);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializeConnections(int aNumConnections) {
        System.out.println("Initializing Connections");
        TileController tc = TileController.getInstance();
        List<Tile> tiles = tc.getTiles();

        int side = (int) Math.round(Math.sqrt(tiles.size()));
        int index = 0;
        for (int y = 0; y < side; y++) {
            for (int x = 0; x < side; x++) {
                if ((y == 0) && (x == 0)) {
                    connect(2, tiles, side, index, true, true, false, false);
                } else if ((y == (side - 1)) && (x == (side - 1))) {
                    connect(2, tiles, side, index, false, false, true, true);
                } else if ((x == (side - 1)) && (y == 0)) {
                    connect(2, tiles, side, index, false, true, true, false);
                } else if ((x == 0) && (y == (side - 1))) {
                    connect(2, tiles, side, index, true, false, false, true);
                } else if (y == 0) {
                    connect(3, tiles, side, index, true, true, true, false);
                } else if (x == 0) {
                    connect(3, tiles, side, index, true, true, false, true);
                } else if (y == (side - 1)) {
                    connect(3, tiles, side, index, true, false, true, true);
                } else if (x == (side - 1)) {
                    connect(3, tiles, side, index, false, true, true, true);
                } else {
                    connect(4, tiles, side, index, true, true, true, true);
                }

                index++;
            }
        }
    }

    public void initializeDice(int aNumDice) {
        System.out.println("Initializing Dice");
        DiceController dc = DiceController.getInstance();
        for (int i = 0; i < aNumDice; i++) {
            String msg = "Creating Die '" + (i + 1) + "'...";
            System.out.print(msg);
            dc.initDice(i);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializeNumDiceToTiles(int aNumDiceToTile) {
        System.out.println("Initializing Dice To Tiles");
        TileController tc = TileController.getInstance();
        for (Tile tile : tc.getTiles()) {
            tile.setNumDice(random.nextInt(GameController.MAX_NUM_DICE) + 1);
            String msg = "Assigning '" + tile.getNumDice() + "' to tile '" + tile.getName() + "'...";
            System.out.print(msg);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializePlayers(int aNumPlayers) {
        System.out.println("Initializing Players");
        PlayerController pc = PlayerController.getInstance();
        for (int i = 0; i < aNumPlayers; i++) {
            Player player = pc.initPlayer(i + 1);
            String msg = "Creating Player '" + player.getName() + "'...";
            System.out.print(msg);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializePlayersToTiles(int aNumPlayersToTile) {
        System.out.println("Initializing Players to Tiles");
        PlayerController pc = PlayerController.getInstance();
        for (Tile tile : TileController.getInstance().getTiles()) {
            Player player = pc.getNextPlayer();
            String msg = "Assigning Player '" + player.getName() + "' to Tile '" + tile.getName() + "'...";
            System.out.print(msg);
            tile.setOwner(player);
            player.owns(tile);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public void initializeTiles(int aNumTiles) {
        System.out.println("Initializing Dice");
        TileController tc = TileController.getInstance();
        for (int i = 0; i < aNumTiles; i++) {
            Tile tile = tc.initTile(i + 1);
            String msg = "Creating Tile '" + tile.getName() + "'...";
            System.out.print(msg);
            repaint(msg);
            System.out.println("[OK]");
        }
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setInitialized() {
        this.initialized = true;
    }

    public void setView(SplashScreen aSplashScreen) {
        this.splashScreen = aSplashScreen;
    }

    private void connect(int aConnections, List<Tile> aTiles, int aSide, int aIndex, boolean aRight, boolean aDown,
            boolean aLeft, boolean aUp) {
        Tile tile = aTiles.get(aIndex);
        Tile nextTile;
        String msg = "Connect " + aConnections + " connection(s) for tile '" + tile.getName() + "'...";
        System.out.print(msg);
        if (aRight) {
            nextTile = aTiles.get(aIndex + 1);
            tile.addConnectedTile(nextTile);
            nextTile.addConnectedTile(tile);
        }
        if (aDown) {
            nextTile = aTiles.get(aIndex + aSide);
            tile.addConnectedTile(nextTile);
            nextTile.addConnectedTile(tile);
        }
        if (aLeft) {
            nextTile = aTiles.get(aIndex - 1);
            tile.addConnectedTile(nextTile);
            nextTile.addConnectedTile(tile);
        }
        if (aUp) {
            nextTile = aTiles.get(aIndex - aSide);
            tile.addConnectedTile(nextTile);
            nextTile.addConnectedTile(tile);
        }
        System.out.println("[OK]");
    }

    private void repaint(String aMessage) {
        if (splashScreen != null) {
            splashScreen.setProgress(aMessage, splashScreen.getProgress() + 1);
            if (!isInitialized()) {
                GameController.sleep();
            }
        }
    }
}
