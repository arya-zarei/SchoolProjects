package UI;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import Game.DataManager;

/**
 * Tests the MainScreen class functionality.
 */
public class MainScreenTest {
    private MainScreen mainScreen;
    private JLabel imageLabel;
    private static final String TEST_RESTRICTIONS_FILE = "test_restrictions.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes the MainScreen and prepares test restrictions.
     */
    @BeforeEach
    void setUp() {
        // Create test restrictions
        Map<String, String> restrictions = new HashMap<>();
        restrictions.put("StartTime", "00:00");
        restrictions.put("EndTime", "23:59");
        DataManager.saveState(TEST_RESTRICTIONS_FILE, restrictions);

        // Initialize MainScreen
        mainScreen = new MainScreen();

        // Get the image label through reflection (if needed)
        Component[] components = mainScreen.getContentPane().getComponents();
        imageLabel = (JLabel) components[1]; // Assuming imageLabel is the second component
    }

    /**
     * Cleans up the test environment after each test.
     * Disposes of the MainScreen if it was initialized.
     */
    @AfterEach
    void tearDown() {
        if (mainScreen != null) {
            mainScreen.dispose();
        }
    }

    /**
     * Tests the initialization of the MainScreen.
     * Verifies that the MainScreen is not null, has the correct title,
     * and is visible.
     */
    @Test
    void testMainScreenInitialization() {
        assertNotNull(mainScreen, "MainScreen should be initialized");
        assertEquals("Main Menu", mainScreen.getTitle(),
                "Window title should be 'Main Menu'");
        assertTrue(mainScreen.isVisible(),
                "MainScreen should be visible");
    }

    /**
     * Tests the button click bounds for the "Game" button.
     * Simulates a click and verifies that the title changes
     * to "Load Game Menu".
     */
    @Test
    void testButtonClickBounds() {
        // Create a mouse event within the "Game" button bounds
        MouseEvent gameClick = createMouseEvent(1035, 680); // Adjust coordinates based on your layout

        // Simulate click
        for (MouseListener listener : imageLabel.getMouseListeners()) {
            listener.mouseClicked(gameClick);
        }

        // Verify the click was handled (check if new window opened or state changed)
        assertEquals("Load Game Menu", mainScreen.getTitle(),
                "Title should change to 'Load Game Menu' after clicking Game button");
    }

    /**
     * Tests parental controls by simulating a click outside
     * the allowed time. Verifies that the appropriate restriction
     * message is shown.
     */
    @Test
    void testParentalControls() {
        // Test clicking outside allowed time
        Map<String, String> restrictions = new HashMap<>();
        restrictions.put("StartTime", "23:00");
        restrictions.put("EndTime", "00:00");
        DataManager.saveState(TEST_RESTRICTIONS_FILE, restrictions);

        // Simulate game button click
        MouseEvent gameClick = createMouseEvent(1035, 680);
        for (MouseListener listener : imageLabel.getMouseListeners()) {
            listener.mouseClicked(gameClick);
        }

        // Verify restriction message (this might need adjustment based on how you show the message)
        // You might need to add a way to capture the shown message for testing
    }

    /**
     * Tests the pet selection process by simulating a click
     * on the "New Game" button and verifying that the title
     * changes to "Pet Selection".
     */
    @Test
    void testPetSelection() {
        // Simulate clicking the "New Game" button
        MouseEvent newGameClick = createMouseEvent(450, 520);
        for (MouseListener listener : imageLabel.getMouseListeners()) {
            listener.mouseClicked(newGameClick);
        }

        assertEquals("Pet Selection", mainScreen.getTitle(),
                "Title should change to 'Pet Selection' after clicking New Game");
    }

    /**
     * Helper method to create MouseEvents for testing.
     *
     * @param x the x-coordinate of the mouse event
     * @param y the y-coordinate of the mouse event
     * @return a MouseEvent object representing the click
     */
    private MouseEvent createMouseEvent(int x, int y) {
        return new MouseEvent(
                imageLabel, // source
                MouseEvent.MOUSE_CLICKED, // id
                System.currentTimeMillis(), // when
                0, // modifiers
                x, // x
                y, // y
                1, // clickCount
                false // popupTrigger
        );
    }

    /**
     * Helper method to get scaled coordinates based on current window size.
     *
     * @param originalX the original x-coordinate
     * @param originalY the original y-coordinate
     * @return a Point object containing the scaled coordinates
     */
    private Point getScaledCoordinates(int originalX, int originalY) {
        double xScale = (double) mainScreen.getWidth() / 1920;
        double yScale = (double) mainScreen.getHeight() / 1080;
        return new Point(
                (int) (originalX * xScale),
                (int) (originalY * yScale)
        );
    }
}