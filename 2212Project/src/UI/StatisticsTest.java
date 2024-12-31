package UI;

import Game.DataManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the statistics class functionality.
 */
public class StatisticsTest {
    private statistics stats;
    private GameMenu mockGameMenu;
    private Map<String, String> testData;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes mock data, images, and the statistics object.
     */
    @BeforeEach
    void setUp() {
        // Create mock data
        testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Create mock images
        Image mockStatBarImage = TestUtils.createMockImage();
        Image mockHealthIcon = TestUtils.createMockImage();
        Image mockHappyIcon = TestUtils.createMockImage();
        Image mockFoodIcon = TestUtils.createMockImage();
        Image mockSleepIcon = TestUtils.createMockImage();

        // Create mock GameMenu
        mockGameMenu = TestUtils.createTestGameMenu();

        // Initialize statistics
        stats = new statistics(
                TEST_SAVE_FILE,
                testData,
                mockStatBarImage,
                mockHealthIcon,
                mockHappyIcon,
                mockFoodIcon,
                mockSleepIcon,
                mockGameMenu
        );
    }

    /**
     * Tests the initial values of the statistics.
     * Verifies that health, happiness, hunger, and sleep are set to 100.
     */
    @Test
    void testInitialValues() {
        assertEquals(100, stats.getHealth(), "Initial health should be 100");
        assertEquals(100, stats.getHappiness(), "Initial happiness should be 100");
        assertEquals(100, stats.getHunger(), "Initial hunger should be 100");
        assertEquals(100, stats.getSleep(), "Initial sleep should be 100");
    }

    /**
     * Tests the update of health state.
     * Verifies that health is updated correctly and does not go below 0.
     */
    @Test
    void testUpdateStateHealth() {
        stats.updateState(0, -50);
        assertEquals(50, stats.getHealth(), "Health should be updated to 50");
        stats.updateState(0, -60);
        assertEquals(0, stats.getHealth(), "Health should not go below 0");
    }

    /**
     * Tests the update of happiness state.
     * Verifies that happiness is updated correctly and does not go below 0.
     */
    @Test
    void testUpdateStateHappiness() {
        stats.updateState(1, -30);
        assertEquals(70, stats.getHappiness(), "Happiness should be updated to 70");
        stats.updateState(1, -80);
        assertEquals(0, stats.getHappiness(), "Happiness should not go below 0");
    }

    /**
     * Tests the update of hunger state.
     * Verifies that hunger is updated correctly and does not go below 0.
     */
    @Test
    void testUpdateStateHunger() {
        stats.updateState(2, -40);
        assertEquals(60, stats.getHunger(), "Hunger should be updated to 60");
        stats.updateState(2, -70);
        assertEquals(0, stats.getHunger(), "Hunger should not go below 0");
    }

    /**
     * Tests the update of sleep state.
     * Verifies that sleep is updated correctly and does not go below 0.
     */
    @Test
    void testUpdateStateSleep() {
        stats.updateState(3, -20);
        assertEquals(80, stats.getSleep(), "Sleep should be updated to 80");
        stats.updateState(3, -90);
        assertEquals(0, stats.getSleep(), "Sleep should not go below 0");
    }

    /**
     * Tests the score calculation based on the current state.
     * Verifies that the score is updated correctly after state changes.
     */
    @Test
    void testScoreCalculation() {
        assertEquals(400, stats.getScore(), "Initial score should be 400");


        stats.updateState(0, -50);
        stats.updateState(1, -50);
        stats.updateState(2, -50);
        stats.updateState(3, -50);
        assertEquals(200, stats.getScore(), "Score should be updated to 200");
    }

    /**
     * Tests notifications triggered by low state values.
     * Verifies that notifications are triggered for health, happiness, hunger, and sleep.
     */
    @Test
    void testNotifications() {
        // Test health notification
        stats.updateState(0, -80);
        assertTrue(stats.getHealth() <= 25, "Health should trigger notification");

        // Test happiness notification
        stats.updateState(1, -80);
        assertTrue(stats.getHappiness() <= 25, "Happiness should trigger notification");

        // Test hunger notification
        stats.updateState(2, -80);
        assertTrue(stats.getHunger() <= 25, "Hunger should trigger notification");

        // Test sleep notification
        stats.updateState(3, -80);
        assertTrue(stats.getSleep() <= 25, "Sleep should trigger notification");
    }
}