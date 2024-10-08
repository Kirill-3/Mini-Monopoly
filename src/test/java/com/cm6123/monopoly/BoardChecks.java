package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.game.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardChecks {

    @Test
    void constructorGeneratesBoardSuccessfully() {
        List<Player> players = new ArrayList<>();
        Board board = new Board(10, players);
        List<Space> spaces = board.getSpaces();
        assertEquals(10, spaces.size());
    }

    @Test
    void spacesAreCreatedSuccessfully() {
        List<Player> players = new ArrayList<>();
        Board board1 = new Board(5, players);
        assertEquals(5, board1.getSpaces().size());

        Board board2 = new Board(15, players);
        assertEquals(15, board2.getSpaces().size());
    }

    @Test
    void getSpacesReturnsCorrectNumberOfSquares() {
        List<Player> players = new ArrayList<>();
        Board board = new Board(8, players);
        List<Space> spaces = board.getSpaces();
        assertNotNull(spaces);
        assertEquals(8, spaces.size());
    }

    @Test
    void getNameReturnsCorrectNameOfSpace() {
        List<Player> players = new ArrayList<>();
        Board board = new Board(3, players);
        Space space = new Space("Space 2",2);
        assertEquals("Space 2", space.getName());
    }
}