package UI;

import Game.DataManager;
import Pets.*;
import Animation.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

/**
 * Tests the LoadGame class functionality.
 */
public class LoadGameTest {
    private LoadGame loadGame;
    private static final String TEST_SAVE_DIR = "test_saves/";
    private GameMenu gameMenu;

    /**
     * Sets up the test environment before each test.
     * Creates a test save directory, initializes a test pet,
     * and simulates a save action in the GameMenu.
     */
    @BeforeEach
    void setUp() {
        // Create test save directory
        new File(TEST_SAVE_DIR).mkdirs();

        // Create a test pet with mock animation
        MockAnimation mockAnimation = new MockAnimation();
        Pet testPet = new Dog(mockAnimation);

        // Create and initialize GameMenu
        gameMenu = new GameMenu(testPet);

        // Find and click the save button in GameMenu
        Component[] components = gameMenu.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals("Save")) {
                    button.doClick();
                    break;
                }
            }
        }

        // Create test save files with mock data
        createTestSaveFile("dog", createTestStats());
        createTestSaveFile("cat", createTestStats());
    }

    /**
     * Cleans up the test environment after each test.
     * Disposes of the LoadGame and GameMenu instances,
     * and deletes any test save files created during the tests.
     */
    @AfterEach
    void tearDown() {
        if (loadGame != null) {
            loadGame.dispose();
        }
        if (gameMenu != null) {
            gameMenu.dispose();
        }
        // Clean up test save files
        deleteTestSaveFiles();
    }

    /**
     * Tests the initialization of the LoadGame class.
     * Asserts that no exceptions are thrown during initialization.
     */
    @Test
    void testLoadGameInitialization() {
        assertDoesNotThrow(() -> {
            loadGame = new LoadGame();
        }, "LoadGame should initialize without throwing exceptions");
    }

    /**
     * Tests the functionality of the back button in the LoadGame interface.
     * Asserts that the back button exists, is enabled, and can be clicked without exceptions.
     */
    @Test
    void testBackButton() {
        loadGame = new LoadGame();

        // Find back button
        Component[] components = loadGame.getContentPane().getComponents();
        JPanel mainPanel = (JPanel) components[0];
        JButton backButton = (JButton) java.util.Arrays.stream(mainPanel.getComponents())
                .filter(c -> c instanceof JButton)
                .filter(c -> ((JButton) c).getText().equals("Back"))
                .findFirst()
                .orElse(null);

        assertNotNull(backButton, "Back button should exist");
        assertTrue(backButton.isEnabled(), "Back button should be enabled");

        // Test clicking back button
        assertDoesNotThrow(() -> {
            backButton.doClick();
        }, "Clicking back button should not throw exceptions");
    }

    /**
     * Creates a test save file for a given pet type with specified stats.
     *
     * @param petType the type of pet (e.g., "dog", "cat")
     * @param stats   a map containing the pet's stats
     */
    private void createTestSaveFile(String petType, Map<String, String> stats) {
        DataManager.saveState(TEST_SAVE_DIR + petType + ".csv", stats);
    }

    private void deleteTestSaveFiles() {
        File testDir = new File(TEST_SAVE_DIR);
        if (testDir.exists()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            testDir.delete();
        }
    }

    private Map<String, String> createTestStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("health", "100");
        stats.put("happiness", "100");
        stats.put("hunger", "100");
        stats.put("sleep", "100");
        return stats;
    }
}