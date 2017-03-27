package nl.gremmee.war;

import nl.gremmee.war.core.GameController;
import nl.gremmee.war.gui.SplashScreen;

public class War {
    private static boolean done = false;

    static GameController gameController;

    public static GameController getGameController() {
        return gameController;
    }

    public static void initialize(SplashScreen aSplashScreen) {
        gameController = GameController.getInstance();
        gameController.setSplash(aSplashScreen);
        gameController.initialize();
    }

    public static final boolean isDone() {
        return done;
    }

    public static void main(String[] aArguments) {
        long beginTime = System.currentTimeMillis();
        System.out.println("Starting application " + War.class.getSimpleName() + "...");
        System.out.println("Initializing...");
        initialize(null);
        System.out.println("Running...");
        gameController.game(null);
        System.out.println("Stopping application " + War.class.getSimpleName() + "...");
        setDone();
        long endTime = System.currentTimeMillis() - beginTime;
        System.out.println("Time (in ms): " + endTime);
    }

    public static void setDone() {
        done = true;
    }
}
