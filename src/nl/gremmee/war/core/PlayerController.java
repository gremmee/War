package nl.gremmee.war.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.gremmee.war.core.gameobject.Player;
import nl.gremmee.war.core.gameobject.Tile;

public class PlayerController {
    private static PlayerController instance;
    private DiceList attackDice;
    private DiceList defenseDice;
    private int playerIndex = 0;
    private List<Player> players = new ArrayList<>();
    private Player player;
    private Random randomGenerator;

    private PlayerController() {
        randomGenerator = new Random();
        attackDice = new DiceList();
        defenseDice = new DiceList();
    }

    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public BattleAction battle(Player aAttacker) {
        // System.out.println("++++++++++++++++++++++++++++++++++++ BATTLE");

        // System.out.println(aAttacker.getAi().getName());
        // aAttacker.printConnection();
        BattleAction action = BattleAction.BA_NONE;
        Tile tile = aAttacker.getCurrentTile();
        if (aAttacker.hasNextConnectedTile(tile)) {
            aAttacker.getNextConnectedTile(tile);
            action = BattleAction.BA_DETERMINE;
        } else if (aAttacker.hasNextTile()) {
            tile = aAttacker.getNextTile();
            action = BattleAction.BA_DETERMINE;
            aAttacker.resetConnectedIndex();
            if (aAttacker.hasNextConnectedTile(tile)) {
                aAttacker.getNextConnectedTile(tile);
            }
        }
        if (!BattleAction.BA_NONE.equals(action)) {
            // System.out.println("##########");
            action = aAttacker.determineAction();
            // System.out.println(action.toString());

            if (BattleAction.BA_ATTACK.equals(action)) {
                Tile currentTile = aAttacker.getCurrentTile();
                Tile currentConnectedTile = aAttacker.getCurrentConnectedTile(currentTile);
                // System.out.println(aAttacker.getName() + " attackes " +
                // currentConnectedTile.getOwner().getName());
                attackDice.setDice(currentTile.getNumDice());
                attackDice.roll();
                defenseDice.setDice(currentConnectedTile.getNumDice());
                defenseDice.roll();

                int attackRoll = attackDice.getResult();

                int defenseRoll = defenseDice.getResult();

                // System.out.println("Attack : " + attackRoll);
                // System.out.println("Defend : " + defenseRoll);

                Tile losTile;
                Tile winTile;
                Player looser;
                Player winner;

                TileController.getInstance().setAttackTile(currentTile);
                TileController.getInstance().setDefenseTile(currentConnectedTile);

                // Attacker looses
                if (attackRoll <= defenseRoll) {
                    losTile = currentTile;
                    winTile = currentConnectedTile;
                    looser = losTile.getOwner();
                    winner = winTile.getOwner();
                    // System.out.println("Winner battle : " + winner.getName()
                    // + " with tile " + winTile.getName());
                    // System.out.println("Looser battle : " + looser.getName()
                    // + " with tile " + losTile.getName());
                    losTile.setNumDice(1);
                } else {
                    winTile = currentTile;
                    losTile = currentConnectedTile;
                    looser = losTile.getOwner();
                    winner = winTile.getOwner();
                    // System.out.println("Winner battle : " + winner.getName()
                    // + " with tile " + winTile.getName());
                    // System.out.println("Looser battle : " + looser.getName()
                    // + " with tile " + losTile.getName());
                    looser.owned(losTile);
                    winner.owns(losTile);
                    losTile.setOwner(winner);
                    losTile.setNumDice(winTile.getNumDice() - 1);
                    winTile.setNumDice(1);
                    if (looser.determineCountrySize() == 0) {
                        looser.dies();
                    }
                }
            } else if (BattleAction.BA_DEFEND.equals(action)) {
            } else if (BattleAction.BA_SKIPP.equals(action)) {
                TileController.getInstance().setDefenseTile(null);
            } else if (BattleAction.BA_NONE.equals(action)) {
                TileController.getInstance().setDefenseTile(null);
            } else {
                assert (false) : "Unknown action!";
            }
        }
        return action;
    }

    public Player determineWinner() {
        if (isSoleSurvivor()) {
            return getWinner();
        }

        return null;
    }

    public void distributeTiles(TileController aTileController) {
        System.out.println("Distribute tiles to players");

        for (Tile tile : aTileController.getTiles()) {
            Player randomPlayer = getNextPlayer();
            tile.setOwner(randomPlayer);
            randomPlayer.owns(tile);
        }

        System.out.println("Done distributing tiles!");
    }

    public void distributeTilesRandomly(TileController aTileController) {
        System.out.println("Distribute tiles to players");

        for (Tile tile : aTileController.getTiles()) {
            Player randomPlayer = getRandomPlayer();
            tile.setOwner(randomPlayer);
            randomPlayer.owns(tile);
        }

        System.out.println("Done distributing tiles!");
    }

    public List<Integer> getAttackDice() {
        return attackDice;
    }

    public Player getCurrentPlayer() {
        return this.player;
    }

    public List<Integer> getDefenseDice() {
        return defenseDice;
    }

    public Player getNextPlayer() {
        Player player = null;
        do {
            playerIndex = determinePlayerIndex();
            player = players.get(playerIndex);
            if (!player.isAlive()) {
                playerIndex++;
            }
        } while (!player.isAlive());
        playerIndex++;
        setCurrentPlayer(player);
        return player;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getRandomPlayer() {
        Player randomPlayer;
        do {
            int randomIndex = randomGenerator.nextInt(players.size());
            randomPlayer = players.get(randomIndex);
        } while (!randomPlayer.isAlive());
        return randomPlayer;
    }

    public Player getRandomPlayer(Player aCurrentPlayer) {
        Player randomPlayer;
        do {
            randomPlayer = getRandomPlayer();
        } while (aCurrentPlayer.equals(randomPlayer) || !randomPlayer.isAlive());
        return randomPlayer;
    }

    public Player initPlayer(int aPlayerNumber) {
        Player player = new Player(Integer.toString(aPlayerNumber));
        players.add(player);
        return player;
    }

    public void printPlayerList() {
        System.out.println("Players...");
        for (Player player : players) {
            if (player.isAlive()) {
                System.out.println("***** " + player.getName() + ", " + player.getAi().getName() + ", index : "
                        + players.indexOf(player) + " alive? " + player.isAlive());
            } else {
                System.out.println("***** " + player.getName() + ", " + player.getAi().getName() + ", index : "
                        + players.indexOf(player));
            }
            player.printConnection();
            player.printCountrySize();
        }
    }

    private int determinePlayerIndex() {
        int index = (this.playerIndex) % players.size();
        return index;
    }

    private Player getWinner() {
        for (Player player : players) {
            if (player.isAlive()) {
                return player;
            }
        }
        // Should not happen
        assert (false) : "This should not happen!";
        return null;
    }

    /**
     * Is there only one player alive?
     *
     * @return true is only one player alive.
     */
    private boolean isSoleSurvivor() {
        int countAlive = players.size();
        for (Player player : players) {
            if (!player.isAlive()) {
                countAlive--;
            }
        }
        return (countAlive <= 1);
    }

    /**
     * Sets the current player
     *
     * @param aPlayer
     *            the current player
     */
    private void setCurrentPlayer(Player aPlayer) {
        this.player = aPlayer;
    }
}
