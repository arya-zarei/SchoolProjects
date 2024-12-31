package UI;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the item management functionality of the Inventory class.
 */
public class InventoryItemManagementTest {
    private Inventory inventory;
    private Map<String, String> testData;

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
        JButton inventoryButton = new JButton();
        testData = createTestStats();

        inventory = new Inventory("test.csv", mockGameMenu, mockStats, mockImage,
                inventoryButton, testData);
    }

    /**
     * Tests the update of item count in the inventory.
     * Verifies that adding an item increases the count correctly.
     */
    @Test
    void testUpdateItemCount() {
        inventory.updateItemCount(0, 1); // Add an apple
        assertEquals("6", testData.get("apple"),
                "Apple count should increase by 1");
    }

    /**
     * Tests the behavior of updating item count with a negative value.
     * Verifies that the count does not incorrectly decrease.
     */
    @Test
    void testNegativeItemUpdate() {
        inventory.updateItemCount(0, -1); // Remove an apple
        assertNotEquals("4", testData.get("apple"),
                "Apple count should decrease by 1");
    }

    /**
     * Tests the handling of invalid item indices.
     * Verifies that no exceptions are thrown for invalid indices.
     */
    @Test
    void testInvalidItemIndex() {
        assertDoesNotThrow(() -> {
            inventory.updateItemCount(-1, 1);
            inventory.updateItemCount(6, 1);
        }, "Invalid indices should be handled gracefully");
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