package nl.gremmee.war.gui.gameobjectview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import nl.gremmee.war.core.gameobject.IGameObject;
import nl.gremmee.war.core.gameobject.Player;

public class PlayerView extends GameObjectView {
    private static final long serialVersionUID = 4511139845483004953L;
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 50;
    private static final int PLAYER_ARC = 10;

    public PlayerView(IGameObject aPlayer, int aXOffset, int aYOffset) {
        super(aPlayer, aXOffset, aYOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics aGraphics, Component aComponent) {
        Font oldFont = getFont();
        Player player = (Player) getGameObject();
        aGraphics.setColor(player.getColor());
        int x = getXOffset();
        int y = getYOffset();
        aGraphics.fillRoundRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_ARC, PLAYER_ARC);
        aGraphics.setColor(player.getContrastColor());
        Font font = new Font("Arial", Font.BOLD, 15);
        drawCenteredString(aGraphics, player.getName(), new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT / 3), font);
        drawCenteredString(aGraphics, "" + player.determineCountrySize(),
                new Rectangle(x, y + (PLAYER_HEIGHT / 3), PLAYER_WIDTH, PLAYER_HEIGHT / 2), font);
        drawCenteredString(aGraphics, "" + player.getAi().getName(),
                new Rectangle(x, y + (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT - (PLAYER_HEIGHT / 3)), font);
        aGraphics.setColor(Color.BLACK);
        setFont(oldFont);
    }
}
