package com.cm6123.monopoly.game.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the addition of players to the game.
 */
public class AddPlayer {
    /** List of players currently participating in the game. */
    private List<Player> players;

    /** Counter to keep track of the number of players added. */
    private int playerCount;

    /**
     * Constructs an AddPlayer object.
     * Initializes an empty list to store players.
     */
    public AddPlayer() {
        this.players = new ArrayList<>();
    }

    /**
     * Adds a specified number of players to the game.
     *
     * @param numPlayers The number of players to add.
     */
    public void addPlayer(final int numPlayers) {
        for (int i = 1; i <= numPlayers; i++) {
            Player player = new Player("Player " + i);
            players.add(player);
        }
    }

    /**
     * Retrieves the list of players currently in the game.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the number of players currently in the game.
     *
     * @return The number of players.
     */
    public int getNumPlayers() {
        return players.size();
    }
}
