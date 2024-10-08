package com.cm6123.monopoly.app;

import java.util.Scanner;

public class GameSetup {
    /**
     * Scanner object for reading user input.
     */
    private Scanner sc;
    /**
     * Constructs a GameSetup object.
     */
    public GameSetup() {
        this.sc = new Scanner(System.in);
    }
    /**
     * Gets the size of the board from the user.
     *
     * @return The size of the board.
     */
    public int getBoardSize() {
        int boardSize = 0;
        while (boardSize < 10 || boardSize > 50) {
            System.out.println("Enter the size of the board (10-50): ");
            boardSize = sc.nextInt();
            if (boardSize < 10 || boardSize > 50) {
                System.out.println("Invalid board size. Please enter a number between 10 and 50.");
            }
        }
        return boardSize;
    }
    /**
     * Gets the number of players from the user.
     *
     * @return The number of players.
     */
    public int getNumPlayers() {
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 10) {
            System.out.println("Enter the number of players (2-10): ");
            numPlayers = sc.nextInt();
            if (numPlayers < 2 || numPlayers > 10) {
                System.out.println("Invalid number of players. Please enter a number between 2 and 10.");
            }
        }
        return numPlayers;
    }
    /**
     * Gets the number of rounds from the user.
     *
     * @return The number of rounds.
     */
    public int getNumRounds() {
        int numRounds = 0;
        while (numRounds <= 0) {
            System.out.println("Enter the number of rounds (greater than 0): ");
            numRounds = sc.nextInt();
            if (numRounds <= 0) {
                System.out.println("Invalid number of rounds. Please enter a number greater than 0.");
            }
        }
        return numRounds;
    }
}
