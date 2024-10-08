package com.cm6123.monopoly.game.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddPlayerChecks {
    private AddPlayer addPlayer;

    @BeforeEach
    public void setUp() {
        addPlayer = new AddPlayer();
    }

    @Test
    public void addPlayerSuccessfullyAddsPlayers() {
        addPlayer.addPlayer(3);
        List<Player> players = addPlayer.getPlayers();
        assertEquals(3, players.size());
        for (int i = 0; i < players.size(); i++) {
            assertEquals("Player " + (i + 1), players.get(i).getName());
        }
    }

    @Test
    public void GetNumPlayersReturnsCorrectNumberOfPlayers() {
        addPlayer.addPlayer(5);
        assertEquals(5, addPlayer.getNumPlayers());
    }
    @Test
    public void getPlayersReturnsCorrectNumberOfPlayers() {
        addPlayer.addPlayer(2);
        assertEquals(2, addPlayer.getPlayers().size());
    }

}

