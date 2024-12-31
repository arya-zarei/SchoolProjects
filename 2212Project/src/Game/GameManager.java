package Game;

import UI.MainScreen;
import java.util.Map;
import javax.swing.*;

/**
 * The GameManager class is responsible for managing the overall flow of the game,
 * including data retrieval, UI interaction, and screen management.
 * <p>
 * This class initializes the game, manages the main user interface, and allows for
 * screen transitions within the game. It serves as the central controller for the game
 * logic and user interactions.
 * </p>
 * @author Mark
 */
public class GameManager {

    /**
     * Constructs a new GameManager instance and retrieves data for the pet with ID "1".
     * This is primarily for initialization and testing purposes.
     * <p>
     * The constructor fetches the pet attributes from the data manager and prints them
     * to the console. This is useful for verifying that the data retrieval process is
     * functioning correctly during the initial setup of the game.
     * </p>
     */
    public GameManager() {
        Map<String, String> petData = DataManager.getPetAttributes("1");
        System.out.println(petData.values());
    }

    /**
     * Starts the game by initializing the main UI screen.
     * <p>
     * This method serves as the entry point for launching the game interface. It creates
     * an instance of the MainScreen class, which is responsible for displaying the main
     * user interface of the game. This method should be called to begin the game.
     * </p>
     */
    public void start() {
        new MainScreen();
    }

    /**
     * Changes the currently displayed screen in the game UI.
     * <p>
     * This method updates the visible content in the main window by replacing the current
     * JPanel with the new panel provided as an argument. It allows for dynamic screen
     * transitions within the game, enabling different game states or menus to be displayed
     * to the user.
     * </p>
     *
     * @param panel The new JPanel to be displayed. This panel replaces the current
     *              content on the main screen.
     */
    public void changeScreen(JPanel panel) {
        // Implementation for changing screens will go here.
        // This method can be used to update the visible content in the main window.
    }
}
