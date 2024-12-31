import Game.DataManager;
import Game.GameManager;
import Game.GameTimer;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * The main entry point for the Kids Pet Game application.
 * <p>
 * This class initializes and starts the game by managing game states and timers.
 * Opens the csv to interpret the game states.
 * @author arya, mark, Raghav, Karl all contributed to code, author in classes represent who was in charge of that class
 */
public class Main {
    /**
     * The main method that starts the Kids Pet Game.
     * <p>
     * It creates an instance of the {@link GameManager} to initialize and manage the game's state.
     * A {@link GameTimer} is also initialized to handle time-based events in the game.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        GameManager manager = new GameManager();
        manager.start(); // Start the game manager.
        new GameTimer(); // Initialize the game timer.
    }
}