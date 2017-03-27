package nl.gremmee.war.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;

public class TileController {
    private static TileController instance;
    private List<Tile> tiles = new ArrayList<>();
    private Random randomGenerator;
    private Tile attackTile;
    private Tile defenseTile;

    private TileController() {
        randomGenerator = new Random();
    }

    public static TileController getInstance() {
        if (instance == null) {
            instance = new TileController();
        }
        return instance;
    }

    public boolean areTilesAtMax(Player aPlayer) {
        boolean result = true;
        List<Tile> tempConnections = aPlayer.getConnections();
        for (Tile tile : tempConnections) {
            if (tile.getNumDice() < GameController.MAX_NUM_DICE) {
                return false;
            }
        }
        return result;
    }

    public Tile getAttackTile() {
        return this.attackTile;
    }

    public Tile getDefenseTile() {
        return this.defenseTile;
    }

    public Tile getRandomTile() {
        Tile randomTile;
        int randomIndex = randomGenerator.nextInt(tiles.size());
        randomTile = tiles.get(randomIndex);
        return randomTile;
    }

    public Tile getRandomTile(Tile aCurrentTile) {
        Tile randomTile;
        boolean validTile = true;
        do {
            randomTile = getRandomTile();
            boolean self = aCurrentTile.equals(randomTile);
            boolean connectedExists = determineConnectedExists(aCurrentTile, randomTile);
            validTile = !self && !connectedExists;
        } while (!validTile);
        return randomTile;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    public Tile initTile(int aTileNumber) {
        Tile tile = new Tile(Integer.toString(aTileNumber));
        tiles.add(tile);
        return tile;
    }

    public void printTileList() {
        System.out.println("Tiles...");
        for (Tile tile : tiles) {
            System.out.println("+++++ " + tile.getName() + ", index : " + tiles.indexOf(tile) + " owner "
                    + tile.getOwner().getName() + " dice " + tile.getNumDice());
            tile.printConnectedTiles();
        }
    }

    public void setAttackTile(Tile aTile) {
        this.attackTile = aTile;
    }

    public void setDefenseTile(Tile aTile) {
        this.defenseTile = aTile;
    }

    private boolean determineConnectedExists(Tile aCurrentTile, Tile aConnectedTile) {
        boolean connectedFound = false;
        for (Tile tile : aCurrentTile.getConnected()) {
            if (tile.equals(aConnectedTile)) {
                connectedFound = true;
                break;
            }
        }
        return connectedFound;
    }
}
