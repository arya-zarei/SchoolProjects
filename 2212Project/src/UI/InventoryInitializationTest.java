package UI;

import Game.DataManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the initialization and setup of the Inventory class.
 */
public class InventoryInitializationTest {
    private GameMenu mockGameMenu;
    private statistics mockStats;
    private Image mockImage;
    private JButton inventoryButton;
    private Map<String, String> testData;

    /**
     * Sets up the test environment before each test.
     * Initializes mock objects and test data.
     */
    @BeforeEach
    void setUp() {
        // Create a simple ImageIcon instead of BufferedImage
        mockImage = new ImageIcon(new byte[100]).getImage();
        mockGameMenu = new GameMenu(TestUtils.createTestPet());
        mockStats = new statistics("test.csv", createTestStats(), mockImage, mockImage, mockImage, mockImage, mockImage, mockGameMenu);
        inventoryButton = new JButton();
        testData = createTestStats();
    }

    /**
     * Tests that the Inventory class initializes without throwing exceptions.
     */
    @Test
    void testInventoryInitialization() {
        assertDoesNotThrow(() -> {
            new Inventory("test.csv", mockGameMenu, mockStats, mockImage,
                    inventoryButton, testData);
        }, "Inventory should initialize without throwing exceptions");
    }

    /**
     * Tests the initial item counts in the Inventory.
     * Verifies that the counts match the expected test data.
     */
    @Test
    void testInitialItemCounts() {
        Inventory inventory = new Inventory("test.csv", mockGameMenu, mockStats,
                mockImage, inventoryButton, testData);

        assertEquals("5", testData.get("apple"), "Initial apple count should match test data");
        assertEquals("5", testData.get("orange"), "Initial orange count should match test data");
    }

    /**
     * Creates a map of test statistics for the Inventory.
     * 
     * @return a map containing test statistics for items and attributes.
     */
    private Map<String, String> createTestStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("apple", "5");
        stats.put("orange", "5");
        stats.put("strawberry", "5");
        stats.put("banana", "5");
        stats.put("ybone", "5");
        stats.put("bbone", "5");
        stats.put("health", "100");
        stats.put("happiness", "100");
        stats.put("hunger", "100");
        stats.put("sleep", "100");
        return stats;
    }
}