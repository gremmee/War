package nl.gremmee.war.gui.gameobjectview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nl.gremmee.war.core.gameobject.IGameObject;

public class DieView extends GameObjectView {
    private static final long serialVersionUID = -7743223991674071713L;
    private static final int DIE_WIDTH = 50;
    private static final int DIE_HEIGHT = 50;
    private static final int DIE_ARC = 10;

    private int dieRoll;

    public DieView(IGameObject aGameObject, int aDieRoll, int aXOffset, int aYOffset) {
        super(aGameObject, aXOffset, aYOffset);
        this.dieRoll = aDieRoll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics aGraphics, Component aComponent) {
        aGraphics.setColor(Color.WHITE);
        int x = getXOffset();
        int y = getYOffset();
        aGraphics.fillRoundRect(x, y, DIE_WIDTH, DIE_HEIGHT, DIE_ARC, DIE_ARC);
        aGraphics.setColor(Color.BLACK);
        BufferedImage img = null;
        try {
            img = ImageIO.read(
                    new File("D:\\Dropbox\\Dropbox\\workspace\\War\\src\\resources\\" + this.dieRoll + "dice.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        aGraphics.drawImage(img, x, y, x + DIE_WIDTH, y + DIE_WIDTH, 0, 0, 105, 105, null);
    }
}
