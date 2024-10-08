package com.cm6123.monopoly.game.board;

public class Space {
    /** The name of the space. */
    private String spaceName;
    /** The position of the space. */
    private int position;

    /**
     * Constructs a Space object with a specified name and position.
     *
     * @param name The name of the space.
     * @param spacePosition The position of the space.
     */
    Space(final String name, final int spacePosition) {
        this.spaceName = name;
        this.position = spacePosition;
    }

    /**
     * Retrieves the name of the space.
     *
     * @return The name of the space.
     */
    public String getName() {
        return spaceName;
    }

    /**
     * Retrieves the position of the space.
     *
     * @return The position of the space.
     */
    public int getPosition() {
        return position;
    }
}
