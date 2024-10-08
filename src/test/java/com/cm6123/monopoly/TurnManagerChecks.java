import com.cm6123.monopoly.game.board.*;
import com.cm6123.monopoly.game.player.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.game.player.AddPlayer;
import com.cm6123.monopoly.game.player.Player;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerChecks {

    private AddPlayer addPlayer;
    private Board board;
    private TurnManager turnManager;

    private Dice dice;

    @BeforeEach
    public void setUp() {
        addPlayer = new AddPlayer();
        addPlayer.addPlayer(1);
        board = new Board(10, addPlayer.getPlayers());
        turnManager = new TurnManager(addPlayer, board);
        dice = new Dice(1);
    }

    @Test
    public void playSingleTurnUpdatesPlayerPositionCorrectly() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(1);
        turnManager.playSingleTurn(new Dice(1), 1, true);
        assertEquals(3, player.getPosition());
    }

    @Test
    public void playSingleTurnUpdatesPlayerBalanceWhenPassingGo() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(9);
        int initialBalance = player.getBalance();
        turnManager.playSingleTurn(new Dice(1), 1, true);
        assertEquals(initialBalance + 200, player.getBalance());
    }

    @Test
    public void playTurnsThrowsExceptionForZeroTurns() {
        assertThrows(IllegalArgumentException.class, () -> turnManager.playTurns(0, true));
    }

    @Test
    public void playSingleTurnDoesNotThrowExceptionForPositiveNumberOfTurns() {
        turnManager.playSingleTurn(new Dice(1), 1, true);
    }

    @Test
    public void playSingleTurnDoesNotChangePlayerBalanceWhenLandingOnOwnedProperty() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(1);
        Space space = board.getSpaces().get(1);
        if (space instanceof Property) {
            Property property = (Property) space;
            property.setOwner(player);
            int initialBalance = player.getBalance();
            turnManager.playSingleTurn(new Dice(1), 1, true);
            assertEquals(initialBalance, player.getBalance());
        }
    }

    @Test
    public void determineWinnersReturnsSingleWinner() {
        Player player1 = addPlayer.getPlayers().get(0);
        Player player2 = new Player("Player 2");
        addPlayer.getPlayers().add(player2);
        player1.receiveMoney(100);
        List<Player> winners = turnManager.determineWinners();
        assertEquals(1, winners.size());
        assertTrue(winners.contains(player1));
    }

    @Test
    public void determineWinnersReturnsMultipleWinners() {
        Player player1 = addPlayer.getPlayers().get(0);
        Player player2 = new Player("Player 2");
        addPlayer.getPlayers().add(player2);
        player1.receiveMoney(100);
        player2.receiveMoney(100);
        List<Player> winners = turnManager.determineWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(player1));
        assertTrue(winners.contains(player2));
    }

    @Test
    public void playSingleTurnDoesNotChangePlayerBalanceWhenNotPurchasingPropertyOrTrainStation() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(1);
        Space space = board.getSpaces().get(1);
        if (!(space instanceof Property) && !(space instanceof TrainStation)) {
            int initialBalance = player.getBalance();
            turnManager.playSingleTurn(new Dice(1), 1, true);
            assertEquals(initialBalance, player.getBalance());
        }
    }
    @Test
    public void playTurnsWithNegativeTurns() {
        assertThrows(IllegalArgumentException.class, () -> turnManager.playTurns(-1, true));
    }

    @Test
    public void playTurnsUpdatesPlayerPositionsCorrectlyForMaximumPlayers() {
        for (int i = 0; i < 10; i++) {
            addPlayer.addPlayer(1);
        }

        int numberOfTurns = 5;
        turnManager.playTurns(numberOfTurns, true);
        for (Player player : addPlayer.getPlayers()) {
            assertTrue(player.getPosition() <= 10);
        }
    }

    @Test
    public void playSingleTurnHandlesBankruptcyWhenPlayerCannotPayTrainStationRent() {
        Player player1 = addPlayer.getPlayers().get(0);
        Player player2 = new Player("Player 2");
        addPlayer.getPlayers().add(player2);

        TrainStation trainStation = new TrainStation("Train Station", 3);
        trainStation.setOwner(player1);
        player1.getOwnedProperties().add(trainStation);

        player2.setBalance(10);

        player2.setPosition(2);
        board.getSpaces().set(3, trainStation);

        turnManager.playSingleTurn(new Dice(1), 2, true);
        assertTrue(player2.getOwnedProperties().isEmpty());
        assertEquals(0, player2.getBalance());
        assertFalse(addPlayer.getPlayers().contains(player2));
    }
    @Test
    public void playSingleTurnGivesBonusWhenPassingPosition1() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(10);
        player.setBalance(1000);

        Property property = new Property("Property", 4);
        player.getOwnedProperties().add(property);

        turnManager.playSingleTurn(new Dice(1), 1, true);
        assertEquals(1200 + property.getPrice()/100 , player.getBalance()); // Player received £200 for passing position 1 and £22 as a bonus
    }
    @Test
    public void playSingleTurnHandlesTaxWhenPlayerLandsOnTaxOffice() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(2);
        player.setBalance(1000);

        TaxOffice taxOffice = new TaxOffice("Tax Office", 3);
        board.getSpaces().set(3, taxOffice);

        turnManager.playSingleTurn(new Dice(1), 1, true);
        assertEquals(990, player.getBalance());
    }
    @Test
    public void playSingleTurnHandlesTicketPriceWhenPlayerLandsOnTrainStation() {
        Player player = addPlayer.getPlayers().get(0);
        player.setPosition(2);
        player.setBalance(1000);

        TrainStation trainstation = new TrainStation("Train Station", 3);
        board.getSpaces().set(3, trainstation);

        turnManager.playSingleTurn(new Dice(1), 1, true);
        assertTrue(player.getBalance() < 1000);
    }
}
