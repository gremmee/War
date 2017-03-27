package nl.gremmee.war.gui.gameobjectview;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import nl.gremmee.war.core.gameobject.IGameObject;

public abstract class GameObjectView extends JComponent {
    private static final long serialVersionUID = -2916202084516390739L;
    private IGameObject gameObject;
    private int xOffset;
    private int yOffset;

    public GameObjectView(IGameObject aGameObject, int aXOffset, int aYOffset) {
        this.gameObject = aGameObject;
        this.xOffset = aXOffset;
        this.yOffset = aYOffset;
    }

    /**
     * Draw the object
     *
     * @param aGraphics The graphics.
     * @param aComponent The canvas.
     */
    public abstract void draw(Graphics aGraphics, Component aComponent);

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param aGraphics The Graphics instance.
     * @param aText The String to draw.
     * @param aRectangle The Rectangle to center the text in.
     * @param aFont The font to use.
     */
    protected void drawCenteredString(Graphics aGraphics, String aText, Rectangle aRectangle, Font aFont) {
        // Get the FontMetrics
        FontMetrics metrics = aGraphics.getFontMetrics(aFont);
        // Determine the X coordinate for the text
        int x = (aRectangle.width - metrics.stringWidth(aText)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as
        // in java 2d 0 is top of the screen)
        int y = ((aRectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        aGraphics.setFont(aFont);
        // Draw the String
        aGraphics.drawString(aText, x + aRectangle.x, y + aRectangle.y);
    }

    protected IGameObject getGameObject() {
        return this.gameObject;
    }

    protected int getXOffset() {
        return this.xOffset;
    }

    protected int getYOffset() {
        return this.yOffset;
    }
}
