package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.gameobject.Tile;

public class AIAttackLowFirst extends BaseAI {
    @Override
    public String getName() {
        return "AttackLowFirst";
    }

    @Override
    protected BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile) {
        return BattleAction.BA_ATTACK;
    }
}
