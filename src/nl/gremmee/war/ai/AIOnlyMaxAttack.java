package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.GameController;
import nl.gremmee.war.core.gameobject.Tile;

public class AIOnlyMaxAttack extends BaseAI {
    @Override
    public String getName() {
        return "OnlyMaxAttack";
    }

    @Override
    protected BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile) {
        if (aAttackTile.getNumDice() >= GameController.MAX_NUM_DICE) {
            return BattleAction.BA_ATTACK;
        }
        return BattleAction.BA_DEFEND;
    }
}
