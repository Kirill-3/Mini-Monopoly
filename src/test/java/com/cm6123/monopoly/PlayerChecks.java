import com.cm6123.monopoly.game.player.Player;
import com.cm6123.monopoly.game.player.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerChecks {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Test Player");
    }

    @Test
    public void payMoneyReducesBalance() throws InsufficientBalanceException {
        player.setBalance(1000);
        player.payMoney(200);
        assertEquals(800, player.getBalance());
    }

    @Test
    public void payMoneyThrowsExceptionWhenBalanceIsInsufficient() {
        player.setBalance(100);
        assertThrows(InsufficientBalanceException.class, () -> player.payMoney(200));
    }

    @Test
    public void receiveMoneyIncreasesBalance() {
        player.setBalance(1000);
        player.receiveMoney(200);
        assertEquals(1200, player.getBalance());
    }
}