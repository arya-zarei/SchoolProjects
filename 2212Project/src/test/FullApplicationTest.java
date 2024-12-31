package test;

import Animation.*;
import Game.*;
import Pets.*;
import UI.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Comprehensive test suite for the entire application.
 * This class tests the integration and functionality of all major components
 * including Pet management, Animation, Statistics tracking, and the Pet Shelter system.
 */
public class FullApplicationTest {
    private PetShelter petShelter;
    private statistics stats;
    private Pet testPet;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test execution.
     * This method:
     * - Creates test data with default values
     * - Initializes mock images for UI components
     * - Creates a test pet instance
     * - Sets up the game menu and statistics system
     * - Initializes the pet shelter
     * All components are initialized on the Event Dispatch Thread (EDT) to ensure thread safety.
     */
    @BeforeEach
    void setUp() {
        // Create test data
        Map<String, String> testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Create mock image for testing
        Image mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // Initialize test components
        SwingUtilities.invokeLater(() -> {
            try {
                // Create test pet
                testPet = new Dog(new MockAnimation());

                // Create game menu
                GameMenu gameMenu = new GameMenu(testPet);

                // Initialize statistics
                stats = new statistics(
                        TEST_SAVE_FILE,
                        testData,
                        mockImage,
                        mockImage,
                        mockImage,
                        mockImage,
                        mockImage,
                        gameMenu
                );

                // Initialize pet shelter
                petShelter = new PetShelter(testPet, stats);

            } catch (Exception e) {
                fail("Setup failed: " + e.getMessage());
            }
        });

        waitForSwingComponents();
    }

    /**
     * Tests the complete application functionality.
     */
    @Test
    void testFullApplication() {
        // Test Pet and Animation
        testPetAndAnimation();

        // Test Statistics System
        testStatisticsSystem();

        // Test Pet Shelter
        testPetShelter();
    }

    /**
     * Tests pet and animation functionality.
     */
    private void testPetAndAnimation() {
        assertNotNull(testPet, "Pet should be initialized");
        assertTrue(testPet instanceof Dog, "Test pet should be a Dog");
        assertNotNull(testPet.getAnimationPanel(), "Animation panel should exist");
    }

    /**
     * Tests the statistics system.
     */
    private void testStatisticsSystem() {
        assertNotNull(stats, "Statistics should be initialized");

        // Test initial values
        assertEquals(100, stats.getHealth(), "Initial health should be 100");
        assertEquals(100, stats.getHappiness(), "Initial happiness should be 100");
        assertEquals(100, stats.getHunger(), "Initial hunger should be 100");
        assertEquals(100, stats.getSleep(), "Initial sleep should be 100");

        // Test statistics updates
        stats.updateState(0, -20); // Update health
        assertEquals(80, stats.getHealth(), "Health should be updated to 80");

        stats.updateState(1, -30); // Update happiness
        assertEquals(70, stats.getHappiness(), "Happiness should be updated to 70");

        // Test score calculation
        assertEquals(350, stats.getScore(), "Score should be sum of all stats");
    }

    /**
     * Tests pet shelter functionality.
     */
    private void testPetShelter() {
        assertNotNull(petShelter, "Pet shelter should be initialized");

        // Test question button functionality
        SwingUtilities.invokeLater(() -> {
            try {
                // Simulate statistics update through pet shelter
                stats.updateState(2, -10); // Update hunger
                assertEquals(90, stats.getHunger(), "Hunger should be updated to 90");
            } catch (Exception e) {
                fail("Pet shelter test failed: " + e.getMessage());
            }
        });
    }

    /**
     * Helper method to wait for Swing components to be ready.
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
     */
    @AfterEach
    void tearDown() {
        SwingUtilities.invokeLater(() -> {
            // Clean up all windows
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
        });

        // Clean up test save file
        File testFile = new File(TEST_SAVE_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}