package nl.gremmee.war.gui.gameobjectview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import nl.gremmee.war.core.gameobject.GameMessage;
import nl.gremmee.war.core.gameobject.IGameObject;

public class MessageView extends GameObjectView {
    private static final long serialVersionUID = 7405629557335955394L;

    public MessageView(IGameObject aGameObject, int aXOffset, int aYOffset) {
        super(aGameObject, aXOffset, aYOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics aGraphics, Component aComponent) {
        Font oldFont = getFont();
        Color oldColor = getForeground();
        GameMessage msg = (GameMessage) getGameObject();
        int x = getXOffset();
        int y = getYOffset();
        Font font = new Font("Arial", Font.BOLD, 25);
        drawCenteredString(aGraphics, msg.getName(), new Rectangle(x, y, aComponent.getWidth(), aComponent.getHeight()),
                font);
        aGraphics.setColor(oldColor);
        setFont(oldFont);
    }
}
