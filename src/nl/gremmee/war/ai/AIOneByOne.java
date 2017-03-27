package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.gameobject.Tile;

public class AIOneByOne extends BaseAI {
    int numAction = 0;

    @Override
    public String getName() {
        return "OneByOne";
    }

    @Override
    protected BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile) {
        if (numAction >= 1) {
            this.numAction++;
            return BattleAction.BA_NONE;
        } else {
            if (aAttackTile.getNumDice() >= aDefenseTile.getNumDice()) {
                this.numAction++;
                return BattleAction.BA_ATTACK;
            } else {
                return BattleAction.BA_DEFEND;
            }
        }
    }
}
