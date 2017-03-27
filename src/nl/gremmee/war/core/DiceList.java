package nl.gremmee.war.core;

import java.util.ArrayList;

import nl.gremmee.war.core.gameobject.D6;

public class DiceList extends ArrayList<Integer> {
    private static final long serialVersionUID = -1706098170660245532L;
    private static final int NUM_DICE_START = 1;
    private int numDice = NUM_DICE_START;

    public DiceList() {
        // nothing to initialize
    }

    public void addDice() {
        this.numDice++;
        createList(NUM_DICE_START);
    }

    public int getResult() {
        int result = 0;
        for (Integer roll : this) {
            result += roll.intValue();
        }
        return result;
    }

    public void removeDice() {
        this.numDice--;
        createList(NUM_DICE_START);
    }

    public void resetDice() {
        this.numDice = NUM_DICE_START;
        createList(this.numDice);
    }

    public void roll() {
        int size = this.size();
        clearDiceList();
        for (int i = 0; i < size; i++) {
            int roll = D6.roll();
            add(Integer.valueOf(roll));
        }
    }

    public void setDice(int aNumDice) {
        this.numDice = aNumDice;
        createList(aNumDice);
    }

    private void clearDiceList() {
        this.numDice = 0;
        createList(this.numDice);
    }

    private void createList(int aNumDice) {
        clear();
        for (int i = 0; i < aNumDice; i++) {
            this.add(Integer.valueOf(1));
        }
    }
}
