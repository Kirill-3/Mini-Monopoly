package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyChecks {
    private Property property;
    private Player player;

    @BeforeEach
    void setUp() {
        property = new Property("Property 1", 1);
        player = new Player("Player 1");
    }

    @Test
    void constructorSetsValuesCorrectly() {
        assertEquals("Property 1", property.getName());
        assertEquals(1, property.getPosition());
        assertNull(property.getOwner());
        assertTrue(property.getPrice() >= 100 && property.getPrice() <= 500);
        assertEquals(0, property.getPrice() % 10);
    }

    @Test
    void setOwnerChangesOwner() {
        property.setOwner(player);
        assertEquals(player, property.getOwner());
    }

    @Test
    void isOwnedReturnsFalseWhenNoOwner() {
        assertFalse(property.isOwned());
    }

    @Test
    void isOwnedReturnsTrueWhenOwnerIsSet() {
        property.setOwner(player);
        assertTrue(property.isOwned());
    }
}