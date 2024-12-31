package Game;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the DataManager class functionality.
 */
public class DataManagerTest {
    private static final String TEST_DATA_DIR = "Data/";
    private static final String TEST_STATE_FILE = "State.csv";
    private static final String TEST_SAVE_FILE = "test_save.csv";

    /**
     * Sets up the test environment before each test.
     * Creates a test directory if it doesn't exist.
     */
    @BeforeEach
    void setUp() {
        // Create test directory if it doesn't exist
        new File(TEST_DATA_DIR).mkdirs();
    }

    /**
     * Cleans up the test environment after each test.
     * Deletes test files and the test directory.
     */
    @AfterEach
    void tearDown() {
        // Clean up test files
        new File(TEST_DATA_DIR + TEST_SAVE_FILE).delete();
        new File(TEST_DATA_DIR + TEST_STATE_FILE).delete();
        new File(TEST_DATA_DIR).delete();
    }

    /**
     * Tests the initialization of save files.
     * Verifies that the state file is created.
     */
    @Test
    void testInitializeSaveFiles() {
        DataManager.initializeSaveFiles();
        assertTrue(new File(TEST_DATA_DIR + TEST_STATE_FILE).exists(),
                "State file should be created");
    }

    /**
     * Tests saving and loading the game state.
     * Verifies that the loaded data matches the saved data.
     */
    @Test
    void testSaveAndLoadState() {
        // Create test data
        Map<String, String> testData = new HashMap<>();
        testData.put("health", "100");
        testData.put("happiness", "100");
        testData.put("hunger", "100");
        testData.put("sleep", "100");

        // Save state
        DataManager.saveState(TEST_SAVE_FILE, testData);

        // Load state
        Map<String, String> loadedData = DataManager.loadState("", TEST_SAVE_FILE);

        // Verify data
        assertEquals(testData.get("health"), loadedData.get("health"));
        assertEquals(testData.get("happiness"), loadedData.get("happiness"));
        assertEquals(testData.get("hunger"), loadedData.get("hunger"));
        assertEquals(testData.get("sleep"), loadedData.get("sleep"));
    }

    /**
     * Tests retrieving pet attributes.
     * Verifies that the attributes for a specific pet ID are correct.
     */
    @Test
    void testGetPetAttributes() {
        DataManager.initializeSaveFiles();
        Map<String, String> dogAttributes = DataManager.getPetAttributes("2");

        assertNotNull(dogAttributes);
        assertEquals("70", dogAttributes.get("health"));
        assertEquals("70", dogAttributes.get("happiness"));
        assertEquals("70", dogAttributes.get("hunger"));
        assertEquals("70", dogAttributes.get("sleep"));
    }

    /**
     * Tests retrieving pet IDs based on pet names.
     * Verifies that the correct pet ID is returned for each name.
     */
    @Test
    void testGetPetID() {
        assertEquals("1", DataManager.getPetID("fox"));
        assertEquals("2", DataManager.getPetID("dog"));
        assertEquals("3", DataManager.getPetID("cat"));
        assertEquals("4", DataManager.getPetID("rat"));
        assertNull(DataManager.getPetID("invalid"));
    }

    /**
     * Tests resetting the game state.
     * Verifies that the state is reset to default values.
     */
    @Test
    void testResetState() {
        DataManager.resetState("slot1.csv");
        Map<String, String> resetData = DataManager.loadState("", "slot1.csv");

        assertEquals("100", resetData.get("happiness"));
        assertEquals("50", resetData.get("sleep"));
        assertEquals("50", resetData.get("health"));
        assertEquals("50", resetData.get("hunger"));
    }

    /**
     * Tests retrieving save file names.
     * Verifies that at least one save file is found.
     */
    @Test
    void testGetSaveFileNames() {
        // Create a test save file
        Map<String, String> testData = new HashMap<>();
        testData.put("test", "value");
        DataManager.saveState(TEST_SAVE_FILE, testData);

        String[] saveFiles = DataManager.getSaveFileNames();
        assertTrue(saveFiles.length > 0, "Should find at least one save file");
    }
}