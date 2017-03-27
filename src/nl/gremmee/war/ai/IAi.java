package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;

public interface IAi {
    public BattleAction determineAction(Tile aAttackTile, Tile aDefenseTile);

    public String getName();

    public void resupply(Player aPlayer);
}
