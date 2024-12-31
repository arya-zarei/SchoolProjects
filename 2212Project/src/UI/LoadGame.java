package UI;

import Animation.CatAnimation;
import Animation.DogAnimation;
import Animation.FoxAnimation;
import Animation.RatAnimation;
import Game.DataManager;
import Pets.Cat;
import Pets.Dog;
import Pets.Fox;
import Pets.Pet;
import Pets.Rat;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A graphical user interface class that handles the loading of saved game states.
 * This class presents a window with up to four save slots where players can load their
 * previously saved pet games.
 * 
 * <p>The class provides a visual interface with:
 * <ul>
 *     <li>Up to four save slot buttons</li>
 *     <li>A background image</li>
 *     <li>A back button to return to the main screen</li>
 * </ul>
 * </p>
 */
public class LoadGame extends JFrame {
    /** Maximum number of save slots available in the game */
    private static final int MAX_SAVES = 4;

    /**
     * Constructs a new LoadGame window.
     * Initializes the GUI components including the background image, save slot buttons,
     * and back button. The window is set to full screen mode.
     */
    public LoadGame() {
        // Set up basic window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);  // Default size before maximizing
        setLocationRelativeTo(null);  // Center window on screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make window fullscreen

        // Create custom JPanel that paints the background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Load and scale background image to fill entire panel
                    Image backgroundImage = ImageIO.read(new File("Assets/GameImages/LoadGame.png"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mainPanel.setLayout(null);  // Use absolute positioning for components

        // Retrieve list of existing save files from DataManager
        String[] saveFiles = DataManager.getSaveFileNames();

        // Create buttons for each save slot, whether empty or filled
        for (int i = 0; i < MAX_SAVES; i++) {
            // Pass null for saveFileName if index exceeds available saves
            JButton saveSlotButton = createSaveSlotButton(i + 1, (i < saveFiles.length) ? saveFiles[i] : null);
            // Position buttons horizontally with 350px spacing, starting at x=110
            saveSlotButton.setBounds(110 + (i * 350), 390, 320, 240);
            mainPanel.add(saveSlotButton);
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, 100, 40);
        backButton.addActionListener(e -> {
            new MainScreen();
            dispose();
        });
        mainPanel.add(backButton);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates a button for a save slot with specific formatting and functionality.
     *
     * @param slotNumber The number of the save slot (1-4)
     * @param saveFileName The name of the save file associated with this slot, or null if empty
     * @return A configured JButton representing the save slot
     */
    private JButton createSaveSlotButton(int slotNumber, String saveFileName) {
        String buttonText = (saveFileName == null) ? "Slot " + slotNumber : saveFileName;
        JButton button = new JButton(buttonText);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        if (saveFileName == null) {
            button.setEnabled(false); // Disable button if no save exists
        } else {
            button.addActionListener(e -> loadSaveFile(saveFileName));
        }

        return button;
    }

    /**
     * Loads a saved game state from a file and initializes the appropriate pet object.
     * This method handles the process of:
     * <ul>
     *     <li>Loading the save file data</li>
     *     <li>Creating the appropriate pet object based on the save file name</li>
     *     <li>Setting the pet's attributes from the saved values</li>
     *     <li>Launching the game menu with the loaded pet</li>
     * </ul>
     *
     * @param saveFileName The name of the save file to load
     * @throws NumberFormatException if the saved attribute values cannot be parsed as integers
     */
    private void loadSaveFile(String saveFileName) {
        // Load saved attributes from file using DataManager
        Map<String, String> attributes = DataManager.loadState("", saveFileName);

        if (!attributes.isEmpty()) {
            Pet loadedPet;

            // Create appropriate pet object based on save file name
            // Each pet type has its own animation class
            switch (saveFileName.toLowerCase()) {
                case "fox":
                    loadedPet = new Fox(new FoxAnimation());
                    break;
                case "dog":
                    loadedPet = new Dog(new DogAnimation());
                    break;
                case "cat":
                    loadedPet = new Cat(new CatAnimation());
                    break;
                case "rat":
                    loadedPet = new Rat(new RatAnimation());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown pet type.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Convert saved string values back to integers and set pet attributes
            loadedPet.setHealth(Integer.parseInt(attributes.get("health")));
            loadedPet.setHungerLevel(Integer.parseInt(attributes.get("hunger")));
            loadedPet.setSleepLevel(Integer.parseInt(attributes.get("sleep")));

            // Create new game menu with loaded pet and close load screen
            new GameMenu(loadedPet);
            dispose();
            
            // Alternative approach using SwingUtilities (currently commented out)
            //SwingUtilities.invokeLater(() -> new GameMenu(loadedPet).setVisible(true));
        } else {
            // Show error if load fails
            JOptionPane.showMessageDialog(this, "Failed to load save file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Add these methods to support testing */
    public JPanel getMainPanel() {
        return (JPanel) getContentPane().getComponent(0);
    }

    public void setTestSaveDirectory(String directory) {
        // Method to set a different save directory for testing
        // Implementation depends on how DataManager handles save directories
    }
}
