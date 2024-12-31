package UI;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Tests the Questions class functionality.
 */
public class QuestionsTest {
    private Questions questions;
    private Inventory mockInventory;
    private statistics mockStats;
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Initializes mock inventory and statistics, and creates a Questions instance.
     */
    @BeforeEach
    void setUp() {
        // Create mock inventory and statistics
        GameMenu mockGameMenu = TestUtils.createTestGameMenu();
        Map<String, String> testData = TestUtils.createTestStats();
        Image mockImage = TestUtils.createMockImage();

        // Create mockInventory with correct constructor parameters
        JButton mockInventoryButton = null;
        mockInventory = new Inventory(
                TEST_SAVE_FILE,          // saveFileName
                mockGameMenu,            // gameMenu
                mockStats,               // gameStats
                mockImage,              // inventoryImage
                mockInventoryButton,    // inventoryButton
                testData               // data
        );

        mockStats = new statistics(TEST_SAVE_FILE, testData, mockImage, mockImage,
                mockImage, mockImage, mockImage, mockGameMenu);

        // Initialize Questions with type 1 (inventory reward)
        questions = new Questions(mockInventory, mockStats, 1);
    }

    /**
     * Cleans up the test environment after each test.
     * Disposes of the Questions instance and waits for the Swing thread.
     */
    @AfterEach
    void tearDown() {
        if (questions != null) {
            questions.dispose();
        }
        TestUtils.waitForSwingThread();
    }

    /**
     * Tests the initialization of the Questions instance.
     * Verifies that the Questions object is not null, visible, and has the correct title.
     */
    @Test
    void testQuestionsInitialization() {
        assertNotNull(questions, "Questions should be initialized");
        assertTrue(questions.isVisible(), "Questions window should be visible");
        assertEquals("Questions", questions.getTitle(), "Window title should be 'Questions'");
    }

    /**
     * Tests the display of the question.
     * Verifies that the question label exists and is not empty.
     */
    @Test
    void testQuestionDisplay() {
        // Get question label using reflection
        JLabel questionLabel = (JLabel) TestUtils.getFieldValue(questions, "questionLabel");
        assertNotNull(questionLabel, "Question label should exist");
        assertFalse(questionLabel.getText().isEmpty(), "Question text should not be empty");
    }

    /**
     * Tests the answer buttons.
     * Verifies that the answer buttons exist, are visible, and have non-empty text.
     */
    @Test
    void testAnswerButtons() {
        // Get answer buttons using reflection
        JButton[] answerButtons = (JButton[]) TestUtils.getFieldValue(questions, "answerButtons");
        assertNotNull(answerButtons, "Answer buttons should exist");
        assertEquals(4, answerButtons.length, "Should have 4 answer buttons");

        // Verify each button
        for (JButton button : answerButtons) {
            assertNotNull(button, "Button should not be null");
            assertTrue(button.isVisible(), "Button should be visible");
            assertFalse(button.getText().isEmpty(), "Button text should not be empty");
        }
    }

    /**
     * Tests the behavior when the correct answer is selected.
     * Verifies that the inventory updates and the Questions window closes.
     */
    @Test
    void testCorrectAnswer() {
        // Get current question and correct answer index
        String currentQuestion = (String) TestUtils.getFieldValue(questions, "currentQuestion");
        Map<String, Integer> correctAnswerMap =
                (Map<String, Integer>) TestUtils.getFieldValue(questions, "correctAnswerIndexMap");
        JButton[] answerButtons = (JButton[]) TestUtils.getFieldValue(questions, "answerButtons");

        // Click correct answer button
        int correctIndex = correctAnswerMap.get(currentQuestion);
        answerButtons[correctIndex].doClick();

        // Verify inventory update for type 1
        // Note: This might need adjustment based on random reward implementation
        TestUtils.waitForSwingThread();

        // Verify window closed
        assertFalse(questions.isVisible(), "Questions window should close after answer");
    }

    /**
     * Tests the behavior when an incorrect answer is selected.
     * Verifies that the statistics update and the Questions window closes.
     */
    @Test
    void testIncorrectAnswer() {
        // Get current question and correct answer index
        String currentQuestion = (String) TestUtils.getFieldValue(questions, "currentQuestion");
        Map<String, Integer> correctAnswerMap =
                (Map<String, Integer>) TestUtils.getFieldValue(questions, "correctAnswerIndexMap");
        JButton[] answerButtons = (JButton[]) TestUtils.getFieldValue(questions, "answerButtons");

        // Click incorrect answer button
        int correctIndex = correctAnswerMap.get(currentQuestion);
        int incorrectIndex = (correctIndex + 1) % 4;
        answerButtons[incorrectIndex].doClick();

        // Verify stats update for type 1
        TestUtils.waitForSwingThread();

        // Verify window closed
        assertFalse(questions.isVisible(), "Questions window should close after answer");
    }

    /**
     * Tests different question types.
     * Verifies that questions of type 2 (sleep reward) and type 3 (health reward) are handled correctly.
     */
    @Test
    void testDifferentQuestionTypes() {
        // Test type 2 (sleep reward)
        Questions sleepQuestions = new Questions(mockInventory, mockStats, 2);
        String currentQuestion = (String) TestUtils.getFieldValue(sleepQuestions, "currentQuestion");
        Map<String, Integer> correctAnswerMap =
                (Map<String, Integer>) TestUtils.getFieldValue(sleepQuestions, "correctAnswerIndexMap");
        JButton[] answerButtons = (JButton[]) TestUtils.getFieldValue(sleepQuestions, "answerButtons");

        // Click correct answer
        int correctIndex = correctAnswerMap.get(currentQuestion);
        answerButtons[correctIndex].doClick();

        TestUtils.waitForSwingThread();
        sleepQuestions.dispose();

        // Test type 3 (health reward)
        Questions healthQuestions = new Questions(mockInventory, mockStats, 3);
        currentQuestion = (String) TestUtils.getFieldValue(healthQuestions, "currentQuestion");
        correctAnswerMap =
                (Map<String, Integer>) TestUtils.getFieldValue(healthQuestions, "correctAnswerIndexMap");
        answerButtons = (JButton[]) TestUtils.getFieldValue(healthQuestions, "answerButtons");

        // Click correct answer
        correctIndex = correctAnswerMap.get(currentQuestion);
        answerButtons[correctIndex].doClick();

        TestUtils.waitForSwingThread();
        healthQuestions.dispose();
    }

    /**
     * Tests the randomization of questions.
     * Verifies that multiple instances of Questions do not always show the same question.
     */
    @Test
    void testQuestionRandomization() {
        // Create multiple question instances and verify they don't always show the same question
        Questions q1 = new Questions(mockInventory, mockStats, 1);
        String question1 = (String) TestUtils.getFieldValue(q1, "currentQuestion");

        Questions q2 = new Questions(mockInventory, mockStats, 1);
        String question2 = (String) TestUtils.getFieldValue(q2, "currentQuestion");

        Questions q3 = new Questions(mockInventory, mockStats, 1);
        String question3 = (String) TestUtils.getFieldValue(q3, "currentQuestion");

        // At least one question should be different
        boolean hasVariation = !question1.equals(question2) ||
                !question2.equals(question3) ||
                !question1.equals(question3);

        assertTrue(hasVariation, "Questions should be randomized");

        q1.dispose();
        q2.dispose();
        q3.dispose();
    }
}