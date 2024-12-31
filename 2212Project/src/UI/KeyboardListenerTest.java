package UI;

import Pets.Pet;
import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;

/**
 * Tests the KeyboardListener class functionality for pet movement controls.
 */
public class KeyboardListenerTest {
    private KeyboardListener keyListener;
    private Pet testPet;
    private int initialX;
    private int initialY;

    /**
     * Sets up the test environment before each test.
     * Initializes a test pet and stores its initial position.
     */
    @BeforeEach
    void setUp() {
        // Create a test pet using the TestUtils
        testPet = TestUtils.createTestPet();
        keyListener = new KeyboardListener(testPet);

        // Store initial position
        initialX = testPet.getX();
        initialY = testPet.getY();
    }

    /**
     * Tests the movement keys (A and D) for the pet.
     * Verifies that pressing A moves the pet left and pressing D returns it to the initial position.
     */
    @Test
    void testMovementKeys() {
        // Test A key (left)
        KeyEvent aPress = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_A, 'A');
        keyListener.keyPressed(aPress);
        assertEquals(initialX - 5, testPet.getX(), "Pet should move left by 5 units");

        // Test D key (right)
        KeyEvent dPress = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_D, 'D');
        keyListener.keyPressed(dPress);
        assertEquals(initialX, testPet.getX(), "Pet should move back to initial X position");

        // Test key release
        KeyEvent keyRelease = new KeyEvent(new JPanel(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
                0, KeyEvent.VK_D, 'D');
        keyListener.keyReleased(keyRelease);
        assertTrue(testPet.isMoving(), "Pet should move slightly walking after key release");
    }

    /**
     * Tests the behavior when a non-movement key is pressed.
     * Verifies that pressing a non-movement key does not change the pet's Y position.
     */
    @Test
    void testNonMovementKey() {
        // Test pressing a non-movement key
        KeyEvent otherPress = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
                0, KeyEvent.VK_F, 'F');
        keyListener.keyPressed(otherPress);

        assertEquals(initialY, testPet.getY(), "Y never changes, pet only moves horizontal"); // Position should not change
    }

    /**
     * Tests the keyTyped method of the KeyboardListener.
     * Verifies that keyTyped does not throw any exceptions and does not change the pet's position.
     */
    @Test
    void testKeyTyped() {
        // Test that keyTyped does nothing
        KeyEvent typedEvent = new KeyEvent(new JPanel(), KeyEvent.KEY_TYPED, System.currentTimeMillis(),
                0, KeyEvent.VK_UNDEFINED, 'w');
        assertDoesNotThrow(() -> keyListener.keyTyped(typedEvent),
                "keyTyped should not throw any exceptions");

        // Position should not change
        assertEquals(initialX, testPet.getX(), "Pet should not move on key typed");
        assertEquals(initialY, testPet.getY(), "Pet should not move on key typed");
    }
}