package com.cm6123.monopoly.game.player;

public class InsufficientBalanceException extends Exception {
    /**
     * Constructs an InsufficientBalanceException object with the specified message.
     *
     * @param message The message to display when the exception is thrown.
     */
    public InsufficientBalanceException(final String message) {
        super(message);
    }
}
