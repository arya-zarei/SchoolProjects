package UI;

import Animation.Animation;
import Animation.MockAnimation;
import Game.DataManager;
import Pets.Dog;
import Pets.Pet;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the initialization and basic setup of the GameMenu class.
 */
public class GameMenuInitializationTest {
    private Pet testPet;
    private Animation testAnimation;
    private MockAnimation mockAnimation;
    private Map<String, String> testData;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes test data, saves it, and creates a test pet with animation.
     */
    @BeforeEach
    void setUp() {
        // Create test data
        testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Save test data
        DataManager.saveState(TEST_SAVE_FILE, testData);

        // Create test animation
        testAnimation = new MockAnimation();

        // Create test pet with animation
        testPet = new Dog(testAnimation);
    }

    /**
     * Tests the initialization of the pet and its animation.
     * Asserts that the pet and its animation are properly initialized.
     */
    @Test
    void testPetInitialization() {
        assertNotNull(testPet, "Pet should be initialized");
        assertNotNull(testAnimation, "Animation should be initialized");
        assertNotNull(testPet.getAnimationPanel(), "Pet's animation panel should be initialized");
    }

    /**
     * Tests the initialization of the data loaded from the save file.
     * Asserts that the loaded data matches the expected values.
     */
    @Test
    void testDataInitialization() {
        Map<String, String> loadedData = DataManager.loadState("", TEST_SAVE_FILE);
        assertEquals("100", loadedData.get("health"), "Health should be initialized to 100");
        assertEquals("100", loadedData.get("happiness"), "Happiness should be initialized to 100");
        assertEquals("100", loadedData.get("hunger"), "Hunger should be initialized to 100");
        assertEquals("100", loadedData.get("sleep"), "Sleep should be initialized to 100");
    }

    /**
     * Tests the initialization of the mock GameMenu.
     * Asserts that the mock GameMenu is properly initialized.
     */
    @Test
    void testMockGameMenu() {
        MockGameMenu mockMenu = new MockGameMenu(testPet);
        assertNotNull(mockMenu, "Mock GameMenu should be initialized");
        // Add more specific tests for GameMenu behavior
    }
}