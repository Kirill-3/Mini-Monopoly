package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.game.player.Player;

public interface PurchasableInterface {
    /**
     * Retrieves the price of the property.
     *
     * @return The price of the property.
     */
    int getPrice();
    /**
     * Retrieves the owner of the property.
     *
     * @return The owner of the property.
     */
    Player getOwner();
    /**
     * Sets the owner of the property.
     *
     * @param owner The owner of the property.
     */
    void setOwner(Player owner);

    /**
     * Boolean to check if the property is owned.
     * @return True if the property is owned, false otherwise.
     */
    boolean isOwned();
}
