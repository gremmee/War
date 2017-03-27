package nl.gremmee.war.core.gameobject;

import java.util.Random;

public class Dice extends GameObject {
    private static int sides;
    private static Random randomGenerator;

    public Dice(int aSides) {
        randomGenerator = new Random();
        setSides(aSides);
    }

    public static int roll() {
        return randomGenerator.nextInt(sides) + 1;
    }

    @Override
    public String getName() {
        return "D" + sides;
    }

    public int getSides() {
        return sides;
    }

    private void setSides(int aSides) {
        sides = aSides;
    }
}
