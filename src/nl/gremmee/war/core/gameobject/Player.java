package nl.gremmee.war.core.gameobject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.gremmee.war.ai.IAi;
import nl.gremmee.war.core.BattleAction;

public class Player extends GameObject {
    private boolean alive = true;
    private Color color;
    private Color contrastColor;
    private IAi ai;
    private int connectedTileIndex = -1;
    private int tileIndex = -1;
    private List<Tile> connections;
    private Random randomGenerator;

    public Player(String aName) {
        randomGenerator = new Random();
        connections = new ArrayList<>();
        this.setName("P" + aName);
        this.color = initColor();
        this.contrastColor = initContrastColor(this.color);
    }

    public BattleAction determineAction() {
        Tile tile = getCurrentTile();
        Tile connectedTile = getCurrentConnectedTile(tile);
        return this.ai.determineAction(tile, connectedTile);
    }

    public int determineCountrySize() {
        Integer size = 0;
        int total = 0;
        for (Tile tile : connections) {
            size = 1;
            size = Integer.valueOf(connected(new Integer(size), tile, new ArrayList<Tile>()).intValue());
            if (size != null) {
                total = Math.max(total, size.intValue());
            }
        }
        return Math.round(total);
    }

    public void dies() {
        if (isAlive()) {
            System.out.println(this.getName() + " dies!");
            this.alive = false;
        }
    }

    public void endTurn() {
        resetIndices();
        this.getAi().resupply(this);
    }

    public IAi getAi() {
        return ai;
    }

    public Color getColor() {
        return this.color;
    }

    public List<Tile> getConnections() {
        return this.connections;
    }

    public Color getContrastColor() {
        return this.contrastColor;
    }

    public Tile getCurrentConnectedTile(Tile aTile) {
        return aTile.getConnected().get(connectedTileIndex);
    }

    public Tile getCurrentTile() {
        return this.connections.get(tileIndex);
    }

    public Tile getNextConnectedTile(Tile aTile) {
        connectedTileIndex++;
        Tile connectedTile = null;
        if (connectedTileIndex <= aTile.getConnected().size()) {
            connectedTile = getCurrentConnectedTile(aTile);
        } else {
            connectedTileIndex = 0;
        }
        return connectedTile;
    }

    public Tile getNextTile() {
        tileIndex++;
        Tile tile = null;
        if (tileIndex <= this.connections.size()) {
            tile = getCurrentTile();
        } else {
            tileIndex = 0;
        }
        return tile;
    }

    public boolean hasNextConnectedTile(Tile aTile) {
        return (connectedTileIndex < (aTile.getConnected().size() - 1));
    }

    public boolean hasNextTile() {
        return (tileIndex < (this.connections.size() - 1));
    }

    public Color initColor() {
        int r = randomGenerator.nextInt(255);
        int g = randomGenerator.nextInt(255);
        int b = randomGenerator.nextInt(255);
        return new Color(r, g, b);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void owned(Tile aTile) {
        this.connections.remove(aTile);
    }

    public void owns(Tile aTile) {
        this.connections.add(aTile);
    }

    public void printConnection() {
        for (Tile connectedTile : connections) {
            System.out
                    .println("   ***** " + connectedTile.getName() + ", index : " + connections.indexOf(connectedTile));
        }
    }

    public void printCountrySize() {
        System.out.println("CountrySize " + determineCountrySize());
    }

    public void resetConnectedIndex() {
        connectedTileIndex = -1;
    }

    public List<Integer> roll(Tile aTile) {
        List<Integer> result = new ArrayList<>();
        System.out.print("Roll " + aTile.getNumDice() + " dice :");
        for (int i = 0; i < aTile.getNumDice(); i++) {
            int roll = D6.roll();
            System.out.print(" " + roll);
            result.add(Integer.valueOf(roll));
        }
        System.out.println();
        return result;
    }

    public void setAi(IAi aAI) {
        this.ai = aAI;
    }

    private Integer connected(Integer aSize, Tile aTile, List<Tile> aCounted) {
        Integer result = new Integer(aSize.intValue());
        int size = aSize.intValue();
        aCounted.add(aTile);
        for (Tile conTile : aTile.getConnected()) {
            if (!aCounted.contains(conTile)) {
                if (!aTile.equals(conTile)) {
                    if (conTile.getOwner().equals(this)) {
                        result = Integer.valueOf(++size);
                        result = Integer.valueOf(connected(result, conTile, aCounted).intValue());
                    }
                }
            }
        }
        return result;
    }

    private Color initContrastColor(Color aColor) {
        double y = ((299 * aColor.getRed()) + (587 * aColor.getGreen()) + (114 * aColor.getBlue())) / 1000;
        return (y >= 128) ? Color.black : Color.white;
    }

    private void resetIndices() {
        this.connectedTileIndex = -1;
        this.tileIndex = -1;
    }
}
