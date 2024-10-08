package com.cm6123.monopoly.game.board;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.game.player.AddPlayer;
import com.cm6123.monopoly.game.player.Player;
import com.cm6123.monopoly.game.player.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Scanner;

public class TurnManager {
    /**
     * The AddPlayer object to manage players in the game.
     */
    private AddPlayer addPlayer;
    /**
     * The Board object to manage the game board.
     */
    private Board board;
    /**
     * The Scanner object to read user input.
     */
    private Scanner scanner;
    /**
     * Constructs a TurnManager object with the specified AddPlayer and Board instances.
     *
     * @param addPlayerInstance The AddPlayer instance.
     * @param boardInstance     The Board instance.
     */
    public TurnManager(final AddPlayer addPlayerInstance, final Board boardInstance) {
        this.addPlayer = addPlayerInstance;
        this.board = boardInstance;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Plays a specified number of turns in the game.
     *
     * @param numTurns  The number of turns to play.
     * @param isTestRun A flag indicating whether the game is a test run.
     */
    public void playTurns(final int numTurns, final boolean isTestRun) {
        if (numTurns <= 0) {
            throw new IllegalArgumentException("Number of turns must be greater than zero");
        }

        Dice dice = new Dice(6);
        int numPlayers = addPlayer.getNumPlayers();

        if (!isTestRun) {
            for (int turn = 1; turn <= numTurns; turn++) {
                if (addPlayer.getNumPlayers() == 1) {
                    System.out.println("Game over. " + addPlayer.getPlayers().get(0).getName() + " is the winner.");
                    break;
                }
                System.out.println("Turn " + turn + ":");
                playSingleTurn(dice, turn, false);
            }
        } else {
            for (int turn = 1; turn <= numTurns; turn++) {
                if (addPlayer.getNumPlayers() == 1) {
                    System.out.println("Game over. " + addPlayer.getPlayers().get(0).getName() + " is the winner.");
                    break;
                }
                System.out.println("Turn " + turn + ":");
                playSingleTurn(dice, turn, true);
            }
        }

        List<Player> winners = determineWinners();
        for (Player winner : winners) {
            System.out.println(winner.getName() + " is a winner with a total asset value of £" + winner.getTotalAssets() + ".");
        }
        System.out.println("\n" + board.displayBoard());
    }
    /**
     * Plays a single turn in the game.
     *
     * @param dice      The Dice object to roll the dice.
     * @param turn      The current turn number.
     * @param isTestRun A flag indicating whether the game is a test run.
     */
    public void playSingleTurn(final Dice dice, final int turn, final boolean isTestRun) {
        int numPlayers = addPlayer.getNumPlayers();
        int currentPlayerIndex = (turn - 1) % numPlayers;
        Player currentPlayer = addPlayer.getPlayers().get(currentPlayerIndex);

        if (!isTestRun) {
            String response = "";
            while (!response.equalsIgnoreCase("y")) {
                System.out.println(currentPlayer.getName() + ", are you ready to play? (y/n)");
                response = scanner.nextLine();
            }
        }
        int diceResult1 = dice.roll();
        int diceResult2 = dice.roll();
        int totalRoll = diceResult1 + diceResult2;

        int oldPosition = currentPlayer.getPosition();
        int newPosition = ((oldPosition - 1 + totalRoll) % board.getSpaces().size()) + 1;

        currentPlayer.setPosition(newPosition);
        System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
        System.out.println(currentPlayer.getName() + " rolled " + diceResult1 + " and " + diceResult2 + ". Moved to position " + currentPlayer.getPosition());
        if (oldPosition + totalRoll > board.getSpaces().size()) {
            int totalPropertyPrice = currentPlayer.getOwnedProperties().stream()
                    .mapToInt(Property::getPrice)
                    .sum();
            int bonus = totalPropertyPrice / 100; // 1% of total property price
            currentPlayer.receiveMoney(bonus);
            currentPlayer.receiveMoney(200);
            System.out.println(currentPlayer.getName() + " passed Home square and received £200 from the banker.");
            System.out.println(currentPlayer.getName() + " also receives 1% of the purchase price of all of their properties as a bonus from the banker: £" + bonus + ".");
            System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
        }


        Space landedSpace = board.getSpaces().get(currentPlayer.getPosition() - 1);
        int rentCost = 0;
        try {
            if (landedSpace instanceof TrainStation) {
                TrainStation landedStation = (TrainStation) landedSpace;
                landedStation.setLastRoll(totalRoll);
                rentCost = landedStation.getRentCost();
                currentPlayer.payMoney(rentCost);
                System.out.println(currentPlayer.getName() + " landed on a train station and paid £" + rentCost + " to the banker for their journey.");
                System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
            } else if (landedSpace instanceof Property) {
                Property landedProperty = (Property) landedSpace;
                if (!landedProperty.isOwned()) {
                    System.out.println("You landed on a property. Price: " + landedProperty.getPrice() + ". Rental Value: " + landedProperty.getPrice() / 10 + ".");
                    if (!isTestRun) {
                    String response = "";
                    while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                        System.out.println("Do you want to purchase it? (y/n)");
                        response = scanner.nextLine();
                    }
                        if (response.equalsIgnoreCase("y")) {
                            if (currentPlayer.getBalance() >= landedProperty.getPrice()) {
                                currentPlayer.payMoney(landedProperty.getPrice());
                                landedProperty.setOwner(currentPlayer);
                                currentPlayer.getOwnedProperties().add(landedProperty);
                                System.out.println(currentPlayer.getName() + " purchased the property and sent £" + landedProperty.getPrice() + " to the banker.");
                                System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
                            } else {
                                System.out.println("You do not have enough balance to purchase this property.");
                                System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
                            }
                        }
                    }
                } else if (landedProperty.isOwned() && landedProperty.getOwner() != currentPlayer) {
                    rentCost = landedProperty.getRentCost();
                    currentPlayer.payMoney(rentCost);
                    landedProperty.getOwner().receiveMoney(rentCost);
                    System.out.println(currentPlayer.getName() + " paid £" + rentCost + " in rent to " + landedProperty.getOwner().getName() + ".");
                    System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
                }
            } else if (landedSpace instanceof TaxOffice) {
                int taxPercentage = (diceResult1 == diceResult2) ? diceResult1 : totalRoll;
                int taxAmount = currentPlayer.getBalance() * taxPercentage / 100;
                currentPlayer.payMoney(taxAmount);
                System.out.println(currentPlayer.getName() + " landed on a tax office and paid £" + taxAmount + " in tax to the banker.");
                System.out.println(currentPlayer.getName() + "'s balance: £" + currentPlayer.getBalance());
            }
        } catch (InsufficientBalanceException e) {
            handleBankruptcy(currentPlayer, rentCost);
        }
        System.out.println(board.displayBoard() + "\n");
    }
    /**
     * Handles bankruptcy of a player.
     *
     * @param currentPlayer The player who is bankrupt.
     * @param rentCost      The rent cost to pay.
     * CheckStyle generates an error here since rentCost should be final.
     * A final variable is one which cannot be changed after it is assigned a value.
     * However, it is modified in the method.
     * In the line "rentCost -= payableRent;", rentCost is modified.
     * With my implementation, this is unavoidable.
     */
    private void handleBankruptcy(final Player currentPlayer, int rentCost) {
        if (currentPlayer.getBalance() > 0) {
            int payableRent = Math.min(currentPlayer.getBalance(), rentCost);
            try {
                currentPlayer.payMoney(payableRent);
                rentCost -= payableRent;
            } catch (InsufficientBalanceException e) {
            }
        }
        while (currentPlayer.getBalance() < rentCost && !currentPlayer.getOwnedProperties().isEmpty()) {
            Property cheapestProperty = currentPlayer.getOwnedProperties().stream()
                    .min(Comparator.comparing(Property::getPrice))
                    .orElseThrow(NoSuchElementException::new);
            int sellPrice = cheapestProperty.getPrice() / 2;
            currentPlayer.receiveMoney(sellPrice);
            cheapestProperty.setOwner(null);
            currentPlayer.removeProperty(cheapestProperty);

            System.out.println(currentPlayer.getName() + " sold " + cheapestProperty.getName() + " for £" + sellPrice + " to the banker.");
        }
        if (currentPlayer.getBalance() >= rentCost) {
            try {
                currentPlayer.payMoney(rentCost);
                System.out.println(currentPlayer.getName() + " paid £" + rentCost + " in rent.");
            } catch (InsufficientBalanceException e) {
                System.out.println(currentPlayer.getName() + " is declared bankrupt and is out of the game.");
                addPlayer.getPlayers().remove(currentPlayer);
            }
        } else {
            System.out.println(currentPlayer.getName() + " Has no means of paying their remaining rent.");
            System.out.println(currentPlayer.getName() + " is declared bankrupt and is out of the game.");
            addPlayer.getPlayers().remove(currentPlayer);
        }
    }
    /**
     * Determines the winners of the game.
     *
     * @return The list of winners.
     */
    public List<Player> determineWinners() {
        int highestAssets = 0;
        List<Player> winners = new ArrayList<>();

        for (Player player : addPlayer.getPlayers()) {
            int playerAssets = player.getTotalAssets();

            if (playerAssets > highestAssets) {
                highestAssets = playerAssets;
                winners.clear();
                winners.add(player);
            } else if (playerAssets == highestAssets) {
                winners.add(player);
            }
        }

        return winners;
    }
}
