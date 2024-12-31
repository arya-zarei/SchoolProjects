package UI;

import Pets.Dog;
import Pets.Pet;
import Animation.MockAnimation;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration test for the entire application UI using JUnit 5 and Swing.
 */
public class FullApplicationIntegrationTest {
    private PetShelter petShelter;
    private statistics stats;
    private Pet testPet;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes mock components and creates necessary test data.
     */
    @BeforeEach
    void setUp() {
        // Create test data
        Map<String, String> testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Create mock image
        Image mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // Create test pet with mock animation
        testPet = new Dog(new MockAnimation());

        // Create game menu
        GameMenu gameMenu = new GameMenu(testPet);

        // Initialize statistics
        stats = new statistics(
                TEST_SAVE_FILE,
                testData,
                mockImage,  // statBarImage
                mockImage,  // healthIcon
                mockImage,  // happyIcon
                mockImage,  // foodIcon
                mockImage,  // sleepIcon
                gameMenu
        );

        // Initialize PetShelter
        petShelter = new PetShelter(testPet, stats);

        // Wait for Swing components to be ready
        waitForSwingComponents();
    }

    /**
     * Tests the initial state of the application.
     * Verifies that the PetShelter and statistics are initialized correctly
     * and that the initial statistics values are as expected.
     */
    @Test
    void testInitialState() {
        assertNotNull(petShelter, "PetShelter should be initialized");
        assertNotNull(stats, "Statistics should be initialized");
        assertEquals(100, stats.getHealth(), "Initial health should be 100");
        assertEquals(100, stats.getHappiness(), "Initial happiness should be 100");
        assertEquals(100, stats.getHunger(), "Initial hunger should be 100");
        assertEquals(100, stats.getSleep(), "Initial sleep should be 100");
    }

    /**
     * Tests the statistics update functionality.
     * Ensures that the statistics are updated correctly when modified.
     */
    @Test
    void testStatisticsUpdate() {
        // Update health
        stats.updateState(0, -20);
        assertEquals(80, stats.getHealth(), "Health should be updated to 80");

        // Update happiness
        stats.updateState(1, -30);
        assertEquals(70, stats.getHappiness(), "Happiness should be updated to 70");

        // Update hunger
        stats.updateState(2, -40);
        assertEquals(60, stats.getHunger(), "Hunger should be updated to 60");

        // Update sleep
        stats.updateState(3, -50);
        assertEquals(50, stats.getSleep(), "Sleep should be updated to 50");
    }

    /**
     * Tests the pet animation state.
     * Checks that the pet's animation panel is initialized and visible.
     */
    @Test
    void testPetAnimationState() {
        assertNotNull(testPet.getAnimationPanel(), "Pet animation panel should exist");
        assertTrue(testPet.getAnimationPanel().isVisible(), "Pet animation panel should be visible");
    }

    /**
     * Tests the score calculation functionality.
     * Verifies that the score is calculated correctly based on the statistics.
     */
    @Test
    void testScoreCalculation() {
        // Initial score should be 400 (100 for each stat)
        assertEquals(400, stats.getScore(), "Initial score should be 400");

        // Update some stats
        stats.updateState(0, -20); // Health to 80
        stats.updateState(1, -20); // Happiness to 80

        // New score should be 360
        assertEquals(360, stats.getScore(), "Score should be updated to 360");
    }

    /**
     * Tests the notification system for low statistics.
     * Ensures that notifications are triggered when statistics fall below a threshold.
     */
    @Test
    void testLowStatNotifications() {
        // Reduce health to trigger notification
        stats.updateState(0, -80);
        assertTrue(stats.getHealth() <= 25, "Health should be low enough to trigger notification");

        // Reduce happiness to trigger notification
        stats.updateState(1, -80);
        assertTrue(stats.getHappiness() <= 25, "Happiness should be low enough to trigger notification");
    }

    /**
     * Helper method to wait for Swing components to be ready.
     * Ensures that any pending Swing events are completed before proceeding.
     */
    private void waitForSwingComponents() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Wait for any pending Swing events to complete
            });
        } catch (Exception e) {
            fail("Failed to wait for Swing components: " + e.getMessage());
        }
    }

    /**
     * Cleans up after each test.
     * Disposes of any open windows to ensure a clean test environment.
     */
    @AfterEach
    void tearDown() {
        if (petShelter != null) {
            SwingUtilities.invokeLater(() -> {
                // Clean up any open windows
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
            });
        }
    }
}