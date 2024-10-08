import com.cm6123.monopoly.app.GameSetup;
import com.cm6123.monopoly.game.board.Board;
import com.cm6123.monopoly.game.board.TurnManager;
import com.cm6123.monopoly.game.player.AddPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Application {
    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    /**
     * Main entry point.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {
        logger.info("Application Started with args:{}", String.join(",", args));

        System.out.println("Hello. Welcome to Monopoly.");

        GameSetup gameSetup = new GameSetup();
        int boardSize = gameSetup.getBoardSize();

        int numPlayers = gameSetup.getNumPlayers();
        AddPlayer addPlayer = new AddPlayer();
        addPlayer.addPlayer(numPlayers);
        Board board = new Board(boardSize, addPlayer.getPlayers());

        int numRounds = gameSetup.getNumRounds();
        int numTurns = numRounds * numPlayers;

        TurnManager turnManager = new TurnManager(addPlayer, board);
        System.out.print("\n" + board.displayBoard() + "\n");
        System.out.print("\n");
        turnManager.playTurns(numTurns, false);
        logger.info("Application closing");
    }
}
