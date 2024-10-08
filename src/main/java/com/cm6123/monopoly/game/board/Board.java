
package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.game.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Board {
    /** The list of spaces on the board. */
    private List<Space> spaces;
    /** The list of players on the board. */
    private List<Player> players;

    /**
     * Constructs a Board object with a specified number of spaces and players.
     *
     * @param numSpaces The number of spaces on the board.
     * @param playersList The list of players on the board.
     */
    public Board(final int numSpaces, final List<Player> playersList) {
        if (numSpaces <= 0) {
            throw new IllegalArgumentException("Number of spaces must be greater than zero");
        }
        this.spaces = createSpaces(numSpaces);
        this.players = playersList;
    }

    /**
     * Displays the current state of the board.
     *
     * @return The current state of the board.
     */
    public String displayBoard() {
        StringBuilder boardDisplay = new StringBuilder();
        List<List<String>> playerPositions = new ArrayList<>();
        for (int i = 0; i < spaces.size(); i++) {
            playerPositions.add(new ArrayList<>());
        }
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            playerPositions.get(player.getPosition() - 1).add("U" + (i + 1));
        }
        for (int i = 0; i < spaces.size(); i++) {
            if (playerPositions.get(i).isEmpty()) {
                if (spaces.get(i) instanceof TrainStation && ((TrainStation) spaces.get(i)).isOwned()) {
                    boardDisplay.append(String.format("%-3s", "TS*"));
                } else if (spaces.get(i) instanceof Property && ((Property) spaces.get(i)).isOwned()) {
                    boardDisplay.append(String.format("%-3s", "PR*"));
                } else {
                    boardDisplay.append(String.format("%-3s", spaces.get(i).getName()));
                }
            } else {
                boardDisplay.append(String.format("%-3s", String.join("/", playerPositions.get(i))));
            }
            boardDisplay.append(" ");
        }

        return boardDisplay.toString();
    }

    /**
     * Creates a list of spaces for the board.
     *
     * @param numSpaces The number of spaces to create.
     * @return The list of spaces.
     */
    private List<Space> createSpaces(final int numSpaces) {
        List<Space> spaceList = new ArrayList<>();
        Random random = new Random();
        int numProperties = (int) (numSpaces * (random.nextDouble() * 0.2 + 0.3));
        int numTrainStations = (int) (numSpaces * (random.nextDouble() * 0.1 + 0.1));
        int numTaxOffices = numSpaces / 10;

        for (int i = 0; i < numSpaces; i++) {
            if (i == 0) {
                spaceList.add(new Space("H", i));
            } else if (i < numProperties + 1) {
                spaceList.add(new Property("PR", i));
            } else if (i < numProperties + numTrainStations + 1) {
                spaceList.add(new TrainStation("TS", i));
            } else if (i < numProperties + numTrainStations + numTaxOffices + 1) {
                spaceList.add(new TaxOffice("TO", i));
            } else {
                spaceList.add(new Space("R", i));
            }
        }

        // Shuffle the spaces to distribute the properties, train stations, and tax offices randomly, excluding the home square
        List<Space> spacesToShuffle = spaceList.subList(1, spaceList.size());
        Collections.shuffle(spacesToShuffle);
        spaceList = new ArrayList<>();
        spaceList.add(new Space("H", 0));
        spaceList.addAll(spacesToShuffle);

        return spaceList;
    }

    /**
     * Retrieves the list of spaces on the board.
     *
     * @return The list of spaces.
     */
    public List<Space> getSpaces() {
        return spaces;
    }

    /**
     * Retrieves the position of the space at the specified index.
     *
     * @param index The index of the space.
     * @return The position of the space.
     */
    public int getPosition(final int index) {
        return spaces.get(index).getPosition();
    }
}
