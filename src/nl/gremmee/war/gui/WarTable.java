package nl.gremmee.war.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import nl.gremmee.war.War;
import nl.gremmee.war.core.GameController;
import nl.gremmee.war.core.gameobject.D6;
import nl.gremmee.war.core.gameobject.GameMessage;
import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;
import nl.gremmee.war.gui.gameobjectview.DieView;
import nl.gremmee.war.gui.gameobjectview.MessageView;
import nl.gremmee.war.gui.gameobjectview.PlayerView;
import nl.gremmee.war.gui.gameobjectview.TileView;

public class WarTable extends JComponent {
    private static final long serialVersionUID = -4703459118098184857L;
    private static final Color BACKGROUND_COLOR = new Color(171, 153, 117);
    public static final int TABLE_WIDTH = 1024; // pixels
    public static final int TABLE_HEIGHT = TABLE_WIDTH / 12 * 9; // pixels
    private List<Integer> attackDiceList;
    private List<Integer> defenseDiceList;

    public WarTable(List<Integer> aAttackDiceList, List<Integer> aDefenseDiceList) {
        this.attackDiceList = aAttackDiceList;
        this.defenseDiceList = aDefenseDiceList;
        setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        setBackground(Color.WHITE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintImmediately(int aXPos, int aYPos, int aWidth, int aHeight) {
        super.paintImmediately(aXPos, aYPos, aWidth, aHeight);
    }

    @Override
    protected void paintComponent(Graphics aGraphics) {
        super.paintComponent(aGraphics);
        int width = getWidth();
        int height = getHeight();
        aGraphics.setColor(BACKGROUND_COLOR);
        aGraphics.fillRect(0, 0, width, height);

        // Players
        Tile attacker = War.getGameController().getTileController().getAttackTile();
        if (attacker != null) {
            PlayerView playerview = new PlayerView(attacker.getOwner(), 10, 10);
            playerview.draw(aGraphics, this);
            // AttackDice
            attackDiceList = War.getGameController().getAttackDice();
            for (int i = 0; i < attackDiceList.size(); i++) {
                Integer roll = attackDiceList.get(i);
                DieView dieView = new DieView(new D6(), roll.intValue(), 10, (i * 60) + 70);
                dieView.draw(aGraphics, this);
            }
        }
        Tile defender = War.getGameController().getTileController().getDefenseTile();
        if (defender != null) {
            PlayerView playerview = new PlayerView(defender.getOwner(), 970, 10);
            playerview.draw(aGraphics, this);
            // DefenceDice
            defenseDiceList = War.getGameController().getDefenseDice();
            for (int i = 0; i < defenseDiceList.size(); i++) {
                Integer roll = defenseDiceList.get(i);
                DieView dieView = new DieView(new D6(), roll.intValue(), 970, (i * 60) + 70);
                dieView.draw(aGraphics, this);
            }
        }

        int defenderX = 0;
        int defenderY = 0;
        int attackerX = 0;
        int attackerY = 0;

        List<Tile> tiles = War.getGameController().getTileController().getTiles();
        // System.out.println("--------------------------------");
        // System.out.println("- TILES " + tiles.size());
        // System.out.println("--------------------------------");
        int index = 0;
        for (int y = 0; y < Math.round(Math.sqrt(tiles.size())); y++) {
            for (int x = 0; x < Math.round(Math.sqrt(tiles.size())); x++) {
                Tile tile = tiles.get(index);
                // System.out.println(tile.getName());
                // System.out.println("x = " + x);
                // System.out.println("y = " + y);
                int xpos = ((x + 1) * 70) + 70;
                int ypos = ((y) * 70) + 10;
                // System.out.println("xpos tile = " + xpos);
                // System.out.println("ypos tile = " + ypos);
                if (attacker != null) {
                    if (tile.equals(attacker)) {
                        aGraphics.setColor(Color.RED);
                        aGraphics.fillRoundRect(xpos - 3, ypos - 3, 56, 56, 2, 2);
                        attackerX = xpos;
                        attackerY = ypos;
                    }
                }
                if (defender != null) {
                    if (tile.equals(defender)) {
                        aGraphics.setColor(Color.GREEN);
                        aGraphics.fillRoundRect(xpos - 3, ypos - 3, 56, 56, 2, 2);
                        defenderX = xpos;
                        defenderY = ypos;
                    }
                }
                TileView tileView = new TileView(tile, xpos, ypos);
                tileView.draw(aGraphics, this);
                // War.getGameController().sleep();
                index++;
            }

            if (defender != null) {
                aGraphics.setColor(Color.PINK);
                aGraphics.drawLine(defenderX + 25, defenderY + 25, attackerX + 25, attackerY + 25);
            }

            // Players
            Player currentPlayer = War.getGameController().getPlayerController().getCurrentPlayer();
            List<Player> players = War.getGameController().getPlayerController().getPlayers();
            int numPlayers = players.size();
            int space = width - 120;
            int playerseparator = space / numPlayers;
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                int xPos = ((playerseparator * (i + 1)));
                int yPos = height - 70;
                if (player.equals(currentPlayer)) {
                    if (currentPlayer.equals(player)) {
                        aGraphics.setColor(Color.YELLOW);
                        aGraphics.fillRoundRect(xPos - 3, yPos - 3, 56, 56, 2, 2);
                    }
                }
                PlayerView playerView = new PlayerView(player, xPos, yPos);
                playerView.draw(aGraphics, this);
                if (!player.isAlive()) {
                    aGraphics.setColor(Color.RED);
                    aGraphics.drawLine(xPos - 3, yPos - 3, xPos + 56, yPos + 56);
                    aGraphics.drawLine(xPos + 56, yPos - 3, xPos - 3, yPos + 56);
                }
            }
        }

        if (War.isDone()) {
            aGraphics.setColor(Color.RED);
            GameMessage msg = new GameMessage("Winner " + GameController.getInstance().getWinner().getName());
            MessageView msgView = new MessageView(msg, 0, 0);
            msgView.draw(aGraphics, this);
        }
    }

}
