package UI;

import Animation.MockAnimation;
import Pets.Dog;
import Pets.Pet;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the PetShelter class functionality.
 */
public class PetShelterTest {
    private PetShelter petShelter;
    private Pet testPet;
    private statistics testStats;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes test data, creates a test pet, and initializes the PetShelter.
     */
    @BeforeEach
    void setUp() {
        // Create test data
        Map<String, String> testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Create test pet
        testPet = new Dog(new MockAnimation());

        // Create test statistics
        Image mockImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        GameMenu mockGameMenu = new GameMenu(testPet);
        testStats = new statistics(TEST_SAVE_FILE, testData, mockImage, mockImage,
                mockImage, mockImage, mockImage, mockGameMenu);

        // Initialize PetShelter
        petShelter = new PetShelter(testPet, testStats);
    }

    /**
     * Tests the initialization of the PetShelter.
     * Asserts that the PetShelter instance is not null after initialization.
     */
    @Test
    void testPetShelterInitialization() {
        assertNotNull(petShelter, "PetShelter should be initialized");
    }

    /**
     * Tests the initialization of the pet within the PetShelter.
     * Asserts that the pet instance is not null and that its animation panel exists.
     */
    @Test
    void testPetShelterPet() {
        assertNotNull(testPet, "Pet should be initialized");
        assertNotNull(testPet.getAnimationPanel(), "Pet's animation panel should exist");
    }

    /**
     * Tests the initialization of the statistics for the pet.
     * Asserts that the statistics instance is not null and checks initial health and happiness values.
     */
    @Test
    void testPetShelterStats() {
        assertNotNull(testStats, "Statistics should be initialized");
        assertEquals(100, testStats.getHealth(), "Initial health should be 100");
        assertEquals(100, testStats.getHappiness(), "Initial happiness should be 100");
    }
}