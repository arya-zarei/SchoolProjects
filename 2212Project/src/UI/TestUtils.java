package UI;

import Animation.DogAnimation;
import Pets.Dog;
import Pets.Pet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Utility class for creating mock objects and data for testing.
 * This class provides methods to create mock images, pets, statistics,
 * game menus, frames, mouse events, key events, and to safely dispose of windows.
 */
public class TestUtils {

    /**
     * Creates a mock Image for testing purposes.
     * Uses a minimal 1x1 pixel transparent image to avoid loading actual image files.
     *
     * @return Image A simple test image
     */
    public static Image createMockImage() {
        // Create a 1x1 pixel transparent image
        return new ImageIcon(new byte[100]).getImage();
    }

    /**
     * Creates a mock Pet object for testing purposes.
     *
     * @return Pet A test pet instance (defaults to Dog)
     */
    public static Pet createTestPet() {
        return new Dog(new DogAnimation());
    }

    /**
     * Creates a mock statistics data map for testing.
     *
     * @return Map<String, String> containing default test statistics
     */
    public static Map<String, String> createTestStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("health", "100");
        stats.put("happiness", "100");
        stats.put("hunger", "100");
        stats.put("sleep", "100");
        stats.put("apple", "5");
        stats.put("orange", "5");
        stats.put("strawberry", "5");
        stats.put("banana", "5");
        stats.put("ybone", "5");
        stats.put("bbone", "5");
        return stats;
    }

    /**
     * Creates a mock GameMenu for testing purposes.
     *
     * @return GameMenu A test game menu instance
     */
    public static GameMenu createTestGameMenu() {
        return new GameMenu(createTestPet());
    }

    /**
     * Creates a mock JFrame for testing UI components.
     *
     * @return JFrame A test frame
     */
    public static JFrame createTestFrame() {
        JFrame frame = new JFrame("Test Frame");
        frame.setSize(800, 600);
        return frame;
    }

    /**
     * Waits for all pending Swing events to be processed.
     * Useful for testing UI updates.
     */
    public static void waitForSwingThread() {
        try {
            SwingUtilities.invokeAndWait(() -> {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a test file path string.
     *
     * @param fileName the name of the test file
     * @return String the complete test file path
     */
    public static String getTestFilePath(String fileName) {
        return "test_files/" + fileName;
    }

    /**
     * Creates a mock MouseEvent for testing UI interactions.
     *
     * @param source the source component
     * @param x the x coordinate
     * @param y the y coordinate
     * @return MouseEvent a test mouse event
     */
    public static java.awt.event.MouseEvent createTestMouseEvent(Component source, int x, int y) {
        return new java.awt.event.MouseEvent(
                source,
                java.awt.event.MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(),
                0,
                x, y,
                1,
                false
        );
    }

    /**
     * Creates a mock KeyEvent for testing keyboard interactions.
     *
     * @param source the source component
     * @param keyCode the key code
     * @return KeyEvent a test key event
     */
    public static java.awt.event.KeyEvent createTestKeyEvent(Component source, int keyCode) {
        return new java.awt.event.KeyEvent(
                source,
                java.awt.event.KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED
        );
    }

    /**
     * Safely disposes of a window on the EDT.
     *
     * @param window the window to dispose
     */
    public static void safeWindowDispose(Window window) {
        if (window != null) {
            SwingUtilities.invokeLater(window::dispose);
        }
    }

    /**
     * Gets the value of a private field using reflection.
     *
     * @param object The object containing the field
     * @param fieldName The name of the field
     * @return Object The value of the field
     */
    public static Object getFieldValue(Object object, String fieldName) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field value: " + fieldName, e);
        }
    }
}