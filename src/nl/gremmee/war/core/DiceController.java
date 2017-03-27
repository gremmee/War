package nl.gremmee.war.core;

import nl.gremmee.war.core.gameobject.D6;
import nl.gremmee.war.core.gameobject.IDice;

public class DiceController {
    private static DiceController instance;

    private DiceController() {
    }

    public static DiceController getInstance() {
        if (instance == null) {
            instance = new DiceController();
        }
        return instance;
    }

    public IDice initDice(int aDiceNumber) {
        return new D6();
    }
}
