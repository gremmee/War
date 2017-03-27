package nl.gremmee.war.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import nl.gremmee.war.War;

public class WarForm extends JFrame {
    private static final long serialVersionUID = 1493750598132918495L;
    private static WarTable warTable;
    private SplashScreen screen;

    public WarForm() {
        splashScreenInit();
        initGame();
        initForm();
        splashScreenDestruct();
        setVisible(true);
        War.getGameController().game(warTable);
    }

    public static void main(final String[] aArgs) {
        new WarForm();
    }

    private void initForm() {
        setTitle("War");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        warTable = new WarTable(War.getGameController().getAttackDice(), War.getGameController().getDefenseDice());
        setContentPane(warTable);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGame() {
        War.initialize(screen);
    }

    private void splashScreenDestruct() {
        screen.setScreenVisible(false);
    }

    private void splashScreenInit() {
        ImageIcon myImage = new ImageIcon("D:\\Dropbox\\Dropbox\\workspace\\War\\src\\resources\\war.jpg");

        screen = new SplashScreen(myImage);
        screen.setSize(748, 498);
        screen.setLocationRelativeTo(null);
        screen.setScreenVisible(true);
    }
}
