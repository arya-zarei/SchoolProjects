package UI;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the UI components and interactions of the Inventory class.
 */
public class InventoryUITest {
    private Inventory inventory;
    private JButton inventoryButton;

    /**
     * Sets up the test environment before each test.
     * Initializes the Inventory instance with mock data.
     */
    @BeforeEach
    void setUp() {
        GameMenu mockGameMenu = new GameMenu(TestUtils.createTestPet());
        Image mockImage = new ImageIcon(new byte[100]).getImage();
        statistics mockStats = new statistics("test.csv", createTestStats(),
                mockImage, mockImage, mockImage, mockImage, mockImage, mockGameMenu);
        inventoryButton = new JButton();
        Map<String, String> testData = createTestStats();

        inventory = new Inventory("test.csv", mockGameMenu, mockStats, mockImage,
                inventoryButton, testData);
    }

    /**
     * Tests the toggle functionality of the inventory display.
     * Verifies that the inventory button is disabled when the inventory is shown.
     */
    @Test
    void testToggleInventoryDisplay() {
        inventory.toggleInventoryDisplay();
        assertFalse(inventoryButton.isEnabled(),
                "Inventory button should be disabled when inventory is shown");
    }

    /**
     * Creates a map of test statistics for the inventory items.
     * 
     * @return a map containing item names and their corresponding counts.
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