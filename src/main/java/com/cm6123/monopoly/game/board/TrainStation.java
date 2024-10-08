package com.cm6123.monopoly.game.board;

import java.util.Random;

public class TrainStation extends Property {
    /** The associated unit price of the train station. */
    private int unitPrice;
    /** The sum of the last roll. */
    private int lastRoll;

    /**
     * Constructs a TrainStation object with a specified name and position.
     *
     * @param name The name of the train station.
     * @param position The position of the train station.
     */
    public TrainStation(final String name, final int position) {
        super(name, position);
        Random random = new Random();
        int randomUnitPrice = 100 + random.nextInt(101);
        this.unitPrice = (int) (Math.floor(randomUnitPrice / 10.0) * 10);
        this.lastRoll = 0;
    }

    /**
     * Retrieves the purchase price of the train station.
     *
     * @return The purchase price of the train station.
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * Retrieves the rent cost of the train station.
     *
     * @return The rent cost of the train station.
     */
    public int getTicketCost() {
        return (int) (unitPrice * 0.1 * lastRoll);
    }

    /**
     * Sets the sum of the last roll.
     *
     * @param rollSum The sum of the last roll.
     */
    public void setLastRoll(final int rollSum) {
        this.lastRoll = rollSum;
    }
}
