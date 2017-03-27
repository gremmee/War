package nl.gremmee.war.gui.gameobjectview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import nl.gremmee.war.core.gameobject.IGameObject;
import nl.gremmee.war.core.gameobject.Tile;

public class TileView extends GameObjectView {
    private static final long serialVersionUID = -3649735194317571308L;
    private static final int DIE_WIDTH = 50;
    private static final int DIE_HEIGHT = 50;
    private static final int DIE_ARC = 10;

    public TileView(IGameObject aGameObject, int aXOffset, int aYOffset) {
        super(aGameObject, aXOffset, aYOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics aGraphics, Component aComponent) {
        Tile tile = (Tile) getGameObject();
        aGraphics.setColor(tile.getOwner().getColor());
        int x = getXOffset();
        int y = getYOffset();
        aGraphics.fillRoundRect(x, y, DIE_WIDTH, DIE_HEIGHT, DIE_ARC, DIE_ARC);
        Font font = new Font("Arial", Font.BOLD, 25);
        aGraphics.setColor(tile.getOwner().getContrastColor());
        drawCenteredString(aGraphics, "" + tile.getName(), new Rectangle(x, y, DIE_WIDTH, DIE_HEIGHT / 2), font);
        drawCenteredString(aGraphics, "" + tile.getNumDice(),
                new Rectangle(x, y + (DIE_HEIGHT / 2), DIE_WIDTH, DIE_HEIGHT / 2), font);
        aGraphics.setColor(Color.BLACK);
    }
}
