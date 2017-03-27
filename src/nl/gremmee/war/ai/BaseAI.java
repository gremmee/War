package nl.gremmee.war.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.GameController;
import nl.gremmee.war.core.TileController;
import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;

public abstract class BaseAI implements IAi {
    @Override
    public BattleAction determineAction(Tile aAttackTile, Tile aDefenseTile) {
        if (aAttackTile.getOwner().equals(aDefenseTile.getOwner())) {
            return BattleAction.BA_SKIPP;
        }
        if ((aAttackTile.getNumDice() == 1)) {
            return BattleAction.BA_DEFEND;
        }

        return doDetermineAction(aAttackTile, aDefenseTile);
    }

    @Override
    public void resupply(Player aPlayer) {
        int size = aPlayer.determineCountrySize();
        System.out.println("+++++ Resupply Player " + aPlayer.getName() + " with " + size);

        if (!TileController.getInstance().areTilesAtMax(aPlayer)) {
            List<Tile> tmpTiles = new ArrayList<>();
            for (Tile tile : aPlayer.getConnections()) {
                if (tile.getNumDice() < GameController.MAX_NUM_DICE) {
                    // System.out.println("Tile " + tile.getName() + " ADDED :
                    // numDice " + tile.getNumDice());
                    tmpTiles.add(tile);
                } else {
                    // System.out.println("Tile " + tile.getName() + " skipped :
                    // numDice " + tile.getNumDice());
                }
            }
            Random randomGenerator = new Random();
            for (int i = 0; i < size; i++) {
                if (!tmpTiles.isEmpty()) {
                    int index;
                    int numDice;
                    index = (randomGenerator.nextInt(tmpTiles.size()));
                    Tile tmpTile = tmpTiles.get(index);
                    numDice = (tmpTile.getNumDice());
                    tmpTile.setNumDice(++numDice);
                    int playerindex = aPlayer.getConnections().indexOf(tmpTile);
                    Tile playerTile = aPlayer.getConnections().get(playerindex);
                    playerTile.setNumDice(numDice);
                    if (numDice >= GameController.MAX_NUM_DICE) {
                        tmpTiles.remove(tmpTile);
                    }
                }
            }
        }
        // TODO surplus
    }

    protected abstract BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile);
}
