package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.game.player.Player;

import java.util.Random;

public class Property extends Space implements PurchasableInterface {
    /** The price of the property. */
    private int price;
    /** The owner of the property. */
    private Player owner;

    /**
     * Constructs a Property object with a specified name and position.
     *
     * @param name The name of the property.
     * @param position The position of the property.
     */
    public Property(final String name, final int position) {
        super(name, position);
        Random random = new Random();
        int randomPrice = 100 + random.nextInt(401);
        this.price = (int) (Math.floor(randomPrice / 10.0) * 10);
        this.owner = null;
    }

    /**
     * Retrieves the price of the property.
     *
     * @return The price of the property.
     */
    public int getPrice() {
        return price;
    }
    /**
     * Retrieves the rent cost of the property.
     *
     * @return The rent cost of the property.
     */
    public int getRentCost() {
        // Assuming the rent cost is 10% of the purchase price
        return (int) (price * 0.1);
    }

    /**
     * Retrieves the owner of the property.
     *
     * @return The owner of the property.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the property.
     *
     * @param propertyOwner The owner of the property.
     */
    public void setOwner(final Player propertyOwner) {
        this.owner = propertyOwner;
    }

    /**
     * Checks if the property is owned.
     *
     * @return True if the property is owned, false otherwise.
     */
    public boolean isOwned() {
        return owner != null;
    }
}
