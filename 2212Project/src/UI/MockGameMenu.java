package UI;

import Pets.Pet;

/**
 * MockGameMenu is a subclass of GameMenu that simulates a game menu
 * for testing purposes without loading actual images or UI components.
 */
public class MockGameMenu extends GameMenu {
    /**
     * Constructs a MockGameMenu with the specified pet.
     *
     * @param pet the pet associated with this game menu
     */
    public MockGameMenu(Pet pet) {
        super(pet);
    }

    /**
     * Loads images for the game menu. This method is overridden to prevent
     * loading actual images during testing.
     */
    protected void loadImages() {
        // Override to prevent loading actual images
    }

    /**
     * Initializes the user interface components for the game menu. This method
     * is overridden to prevent creating actual UI components during testing.
     */
    protected void initializeUI() {
        // Override to prevent creating actual UI components
    }
}