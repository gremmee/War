package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.gameobject.Tile;

public class AISuicide extends BaseAI {
    @Override
    public String getName() {
        return "Suicide";
    }

    @Override
    protected BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile) {
        return BattleAction.BA_ATTACK;
    }
}
