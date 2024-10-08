package com.cm6123.monopoly.game.player;

import com.cm6123.monopoly.game.board.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Monopoly game.
 */
public class Player {
    /**
     * The name of the player.
     */
    private String name;

    /**
     * The position of the player on the game board.
     */
    private int position;
    /**
     * The balance of the player.
     */
    private int balance;

    /**
     * Static counter to keep track of the number of players created.
     */
    private static int playerCount = 0;

    /**
     * The properties owned by the player.
     */
    private List<Property> ownedProperties;

    /**
     * Constructs a Player object with the specified name.
     *
     * @param playerName The name of the player.
     */
    public Player(final String playerName) {
        this.name = playerName;
        this.position = 1;
        this.balance = 1000;
        this.ownedProperties = new ArrayList<>();
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the position of the player on the game board.
     *
     * @return The position of the player.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the player on the game board.
     *
     * @param newPosition The new position of the player.
     */
    public void setPosition(final int newPosition) {
        this.position = newPosition;
    }
    /**
     * Sets the balance of the player.
     *
     * @param newBalance The new balance of the player.
     */
    public void setBalance(final int newBalance) {
        this.balance = newBalance;
    }
    /**
     * Retrieves the balance of the player.
     *
     * @return The balance of the player.
     */

    public int getBalance() {
        return balance;
    }
    /**
     * Retrieves the total assets of the player.
     *
     * @return The total assets of the player.
     */
    public int getTotalAssets() {
        int totalPropertyPrice = ownedProperties.stream()
                .mapToInt(Property::getPrice)
                .sum();
        return balance + totalPropertyPrice;
    }
    /**
     * Sets the balance of the player.
     *
     * @param amount The new balance of the player.
     */
    public void receiveMoney(final int amount) {
        balance += amount;
    }
    /**
     * Deducts the specified amount from the player's balance.
     *
     * @param amount The amount to deduct.
     */
    public void payMoney(final int amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        balance -= amount;
    }
    /**
     * Retrieves the properties owned by the player.
     *
     * @return The properties owned by the player.
     */
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }
    /**
     * Adds a property to the list of properties owned by the player.
     *
     * @param property The property to add.
     */
    public void removeProperty(final Property property) {
        ownedProperties.remove(property);
    }
}


