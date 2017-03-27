package nl.gremmee.war.ai;

import nl.gremmee.war.core.BattleAction;
import nl.gremmee.war.core.TileController;
import nl.gremmee.war.core.gameobject.Tile;

public class AIDefender extends BaseAI {
    @Override
    public String getName() {
        return "Defender";
    }

    @Override
    protected BattleAction doDetermineAction(Tile aAttackTile, Tile aDefenseTile) {
        if (aAttackTile.getNumDice() > aDefenseTile.getNumDice()) {
            return BattleAction.BA_ATTACK;
        } else {
            if (TileController.getInstance().areTilesAtMax(aAttackTile.getOwner())) {
                return BattleAction.BA_ATTACK;
            } else {
                return BattleAction.BA_DEFEND;
            }
        }
    }
}
