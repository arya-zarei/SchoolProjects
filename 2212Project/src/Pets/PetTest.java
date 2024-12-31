package Pets;

import Animation.MockAnimation;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the Pet class functionality using Dog as a concrete implementation.
 * This class contains unit tests to verify the behavior of the Pet class,
 * including initialization, attribute management, inventory handling, and movement.
 */
public class PetTest {
    private Pet pet;
    private static final String TEST_PET_NAME = "TestDog";

    /**
     * Sets up the test environment before each test case.
     * Initializes a Dog instance with a mock animation and sets its name.
     */
    @BeforeEach
    void setUp() {
        pet = new Dog(new MockAnimation());
        pet.setName(TEST_PET_NAME);
    }

    /**
     * Tests the initialization of the Pet object.
     * Verifies that the pet's name, health, max health, hunger level, and sleep level
     * are set correctly upon initialization.
     */
    @Test
    void testPetInitialization() {
        assertEquals(TEST_PET_NAME, pet.getName(), "Pet name should match");
        assertEquals(100, pet.getHealth(), "Initial health should be 100");
        assertEquals(100, pet.getMaxHealth(), "Max health should be 100");
        assertEquals(50, pet.getHungerLevel(), "Initial hunger should be 50");
        assertEquals(50, pet.getSleepLevel(), "Initial sleep should be 50");
    }

    /**
     * Tests the retrieval of pet attributes.
     * Verifies that the attributes returned match the expected values.
     */
    @Test
    void testPetAttributes() {
        // Get attributes
        Map<String, String> attributes = pet.getAttributes();

        // Verify attributes
        assertEquals(TEST_PET_NAME, attributes.get("name"));
        assertEquals("100", attributes.get("health"));
        assertEquals("50", attributes.get("hunger"));
        assertEquals("50", attributes.get("sleep"));
    }

    /**
     * Tests setting new attributes for the Pet object.
     * Verifies that the pet's attributes are updated correctly.
     */
    @Test
    void testSetAttributes() {
        // Create test attributes
        Map<String, String> testAttributes = new HashMap<>();
        testAttributes.put("name", "NewName");
        testAttributes.put("health", "80");
        testAttributes.put("hunger", "70");
        testAttributes.put("sleep", "60");

        // Set attributes
        pet.setAttributes(testAttributes);

        // Verify new values
        assertEquals("NewName", pet.getName());
        assertEquals(80, pet.getHealth());
        assertEquals(70, pet.getHungerLevel());
        assertEquals(60, pet.getSleepLevel());
    }

    /**
     * Tests the inventory management of the Pet object.
     * Verifies that items can be added to the pet's inventory and retrieved correctly.
     */
    @Test
    void testInventory() {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("bone", 5);
        inventory.put("food", 3);

        pet.setInventory(inventory);

        assertEquals(5, pet.getInventory().get("bone"));
        assertEquals(3, pet.getInventory().get("food"));
    }

    /**
     * Tests the movement functionality of the Pet object.
     * Verifies that the pet's position updates correctly when moved.
     */
    @Test
    void testMovement() {
        int initialX = pet.getX();
        int initialY = pet.getY();

        pet.move(10, 0);  // Move right
        assertEquals(initialX + 10, pet.getX());
        assertEquals(initialY, pet.getY());
    }
}