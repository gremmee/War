package nl.gremmee.war.core;

import java.util.List;

import nl.gremmee.war.War;
import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.gui.SplashScreen;
import nl.gremmee.war.gui.WarTable;

public class GameController {
    private static final int NUM_DICE = 1;
    public static final int MAX_NUM_DICE = 8;
    private static final int NUM_PLAYERS = 4;
    private static final int NUM_AI = 2;
    private static final int NUM_TILES = 36;
    private static final int NUM_CONNECTIONS_PER_TILE = 1;
    private static final int NUM_AI_TO_PLAYERS = NUM_PLAYERS;
    private static final int NUM_PLAYERS_TO_TILE = NUM_TILES;
    private static final int NUM_DICE_TO_TILE = NUM_TILES;
    private static GameController instance;
    private static final long SLEEP = 150;
    private AIController aiController;
    private DiceController diceController;
    private Player winner;
    private PlayerController playerController;
    private TileController tileController;

    public GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public static void sleep() {
        sleep(SLEEP);
    }

    public static void sleep(long aMiliseconds) {
        try {
            Thread.sleep(aMiliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void game(WarTable aWarTable) {
        // printAll();

        System.out.println();
        System.out.println("### Game starts ###");

        Player currentPlayer = null;
        Player winner = null;

        do {
            System.out.println();
            currentPlayer = playerController.getNextPlayer();
            System.out.println("-----CurrentPlayer = " + currentPlayer.getName());
            if (currentPlayer.hasNextTile()) {
                currentPlayer.getNextTile();
            }
            BattleAction action = BattleAction.BA_NONE;
            do {
                action = playerController.battle(currentPlayer);
                repaint(aWarTable);
            } while (!BattleAction.BA_NONE.equals(action));
            currentPlayer.endTurn();
            // printAll();
        } while ((winner = playerController.determineWinner()) == null);
        setWinner(winner);
        War.setDone();
        repaint(aWarTable);

        System.out.println("***************");
        System.out.println("Winner " + winner.getName());
        System.out.println("***************");
    }

    public AIController getAIController() {
        return this.aiController;
    }

    public List<Integer> getAttackDice() {
        return getPlayerController().getAttackDice();
    }

    public List<Integer> getDefenseDice() {
        return getPlayerController().getDefenseDice();
    }

    public DiceController getDiceController() {
        return this.diceController;
    }

    public PlayerController getPlayerController() {
        return this.playerController;
    }

    public TileController getTileController() {
        return this.tileController;
    }

    public Player getWinner() {
        return winner;
    }

    public void initialize() {
        aiController = AIController.getInstance();
        diceController = DiceController.getInstance();
        playerController = PlayerController.getInstance();
        tileController = TileController.getInstance();
        int max = 0;
        max += NUM_DICE;
        max += NUM_PLAYERS;
        max += NUM_AI;
        max += NUM_TILES;
        max += NUM_CONNECTIONS_PER_TILE;
        max += NUM_AI_TO_PLAYERS;
        max += NUM_PLAYERS_TO_TILE;
        max += NUM_DICE_TO_TILE;
        Initialize init = Initialize.getInstance();
        if (init.getSplashScreen() != null) {
            init.getSplashScreen().setProgressMax(max);
        }
        // components
        init.initializeDice(NUM_DICE);
        init.initializePlayers(NUM_PLAYERS);
        init.initializeAI(NUM_AI);
        init.initializeTiles(NUM_TILES);
        // connections
        init.initializeConnections(NUM_CONNECTIONS_PER_TILE);
        init.initializeAIToPlayers(NUM_AI_TO_PLAYERS);
        init.initializePlayersToTiles(NUM_PLAYERS_TO_TILE);
        init.initializeNumDiceToTiles(NUM_DICE_TO_TILE);
        init.setInitialized();
        System.out.println();
        printAll();
    }

    public void setSplash(SplashScreen aSplashScreen) {
        Initialize.getInstance().setView(aSplashScreen);
    }

    public void setWinner(Player aWinner) {
        this.winner = aWinner;
    }

    private static void repaint(WarTable aWarTable) {
        if (aWarTable != null) {
            aWarTable.paintImmediately(0, 0, aWarTable.TABLE_WIDTH, aWarTable.TABLE_HEIGHT);
            sleep(10);
        }
    }

    private void printAll() {
        System.out.println();
        tileController.printTileList();
        System.out.println();
        playerController.printPlayerList();
    }
}
