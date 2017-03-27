package nl.gremmee.war.core.gameobject;

import java.util.ArrayList;
import java.util.List;

public class Tile extends GameObject {
    private int numDice;
    private List<Tile> connections;
    private Player owner;

    public Tile(String aName) {
        this.setName(aName);
        connections = new ArrayList<>();
    }

    public void addConnectedTile(Tile aTile) {
        if (!connections.contains(aTile)) {
            connections.add(aTile);
        }
    }

    @Override
    public boolean equals(Object aOtherObject) {
        Tile otherObject = (Tile) aOtherObject;
        return this.getName().equals(otherObject.getName());
    }

    public List<Tile> getConnected() {
        return this.connections;
    }

    public int getNumDice() {
        return this.numDice;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    public void printConnectedTiles() {
        for (Tile connectedTile : connections) {
            System.out
                    .println("   +++++ " + connectedTile.getName() + ", index : " + connections.indexOf(connectedTile));
        }
    }

    public void setNumDice(int aNumDice) {
        this.numDice = aNumDice;
    }

    public void setOwner(Player aOwner) {
        this.owner = aOwner;
    }
}
