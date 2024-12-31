package UI;


import Game.DataManager;
import Pets.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A graphical user interface class representing the main game menu for the virtual pet game.
 * This class extends JFrame and provides the primary game interface where players can:
 * <ul>
 *     <li>View and interact with their pet</li>
 *     <li>Monitor pet statistics (health, happiness, sleep, hunger)</li>
 *     <li>Access the inventory system</li>
 *     <li>Save game progress</li>
 *     <li>Visit different locations (Vet, Pet Shelter)</li>
 *     <li>Answer questions for rewards</li>
 * </ul>
 * <p>
 * The menu includes real-time stat tracking, automatic stat decreases over time,
 * and various interactive elements through buttons and keyboard controls.
 * @author aryaz
 * @version 1.0
 * @see Pet
 * @see statistics
 * @see Inventory
 */
public class GameMenu extends JFrame {
    /** Background image for the game menu */
    private Image backgroundImage;
    
    /** Status bar image displaying pet statistics */
    private Image statBarImage;
    
    /** Icons for different pet statistics */
    private Image healthIcon, happyIcon, foodIcon, sleepIcon;
    
    /** Icons for inventory and related features */
    private Image inventoryIcon, inventoryImage;
    
    /** Game state data stored in key-value pairs */
    private Map<String,String> data;
    
    /** Icons for various game features */
    private Image questionImage, saveImage, vetImage, petImage;
    
    /** Pet statistics values */
    private int health, happiness, sleep, hunger;
    
    /** Reference to the statistics tracking system */
    private statistics stats;
    
    /** Reference to the inventory management system */
    private Inventory inventory;
    
    /** Interactive buttons for questions */
    private final JButton question1Button, question2Button;
    
    /** The pet instance being managed in the game */
    private final Pet petToSpawn;
    
    /** Name of the save file for this pet */
    private String saveFileName;

    /**
     * Constructs a new GameMenu instance with the specified pet.
     * <p>
     * Initializes the game interface with:
     * <ul>
     *     <li>Loading necessary images and resources</li>
     *     <li>Setting up the main game panel</li>
     *     <li>Initializing pet statistics</li>
     *     <li>Creating interactive buttons</li>
     *     <li>Setting up timers for stat decreases</li>
     *     <li>Configuring keyboard controls</li>
     * </ul>
     *
     * @param petToSpawn The pet instance to be displayed and managed in the game
     * @throws RuntimeException if required image resources cannot be loaded
     */
    public GameMenu(Pet petToSpawn) {

        this.petToSpawn = petToSpawn; // Save the pet instance for save functionality

        Map<String,String> data = this.loadData();
        this.initializeStatistics(data);

        this.data = this.loadData();
        this.initializeStatistics(this.data);

        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen


        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            healthIcon = ImageIO.read(new File("Assets/Images/healthicon.png")); // Load the health icon image
            happyIcon = ImageIO.read(new File("Assets/Images/happyicon.png")); // Load the health icon image
            foodIcon = ImageIO.read(new File("Assets/Images/foodicon.png")); // Load the health icon image
            sleepIcon = ImageIO.read(new File("Assets/Images/sleepicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image
            stats = new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this); // Initialize statistics
            questionImage = ImageIO.read(new File("Assets/Images/question.png")); // Load the inventory image
            saveImage = ImageIO.read(new File("Assets/Images/SaveGame.png")); // Load the inventory image
            petImage = ImageIO.read(new File("Assets/Images/PetShelterButton.png")); // Load the inventory image
            vetImage = ImageIO.read(new File("Assets/Images/VetShelterButton.png")); // Load the inventory image
//            stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this); // Initialize statistics


        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        Timer decreaseAll = new Timer(60000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stats.updateState(1,-5);
                stats.updateState(2,-3);
                stats.updateState(3,-4);

            }
        });

        decreaseAll.setRepeats(true); // Ensure the timer continues to repeat
        decreaseAll.start(); // Start the timer

        mainPanel.setLayout(null);

        ImageIcon saveIcon = new ImageIcon(saveImage); // Wrap the image in an ImageIcon
        JButton saveButton = new JButton(saveIcon); // Create the button with the image icon
        saveButton.setBounds(50, 50, saveIcon.getIconWidth(), saveIcon.getIconHeight()); // Adjust button size to image size
        saveButton.setBorderPainted(false); // Do not paint the border
        saveButton.setContentAreaFilled(false); // Do not fill the content area
        saveButton.setFocusPainted(false); // Do not paint the focus indicator
        saveButton.setOpaque(false); // Set the button to be transparent

        saveButton.addActionListener(e -> {
            DataManager.saveState(petToSpawn.getClass().getSimpleName().toLowerCase(), petToSpawn.getAttributes());
            JOptionPane.showMessageDialog(this, "Game saved!");
        });

        mainPanel.add(saveButton); // Add the button to your panel


        //pet spawn
        JPanel character = petToSpawn.getAnimationPanel();
        if(stats.getHealth() == 0){
            petToSpawn.sleep();
            SwingUtilities.invokeLater(() -> {
                // Create a non-modal dialog to show the message
                final JDialog dialog = new JDialog();
                dialog.setTitle("Alert");
                dialog.setModal(false); // Make it non-modal
                dialog.setSize(600, 100);
                dialog.setLayout(new FlowLayout());

                // Create the label with a custom font
                JLabel messageLabel = new JLabel("Your pet has died! Health is empty...");
                messageLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Set the font size to 24 and make it bold
                dialog.add(messageLabel);

                dialog.setLocationRelativeTo(null); // Center on screen

                // Set a timer to close the dialog automatically after 2 seconds
                new Timer(2000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        dispose();
                    }
                }).start();

                dialog.setVisible(true);
            });

        }
        else{
            petToSpawn.unlock();
            petToSpawn.stopWalking();
        }

        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height);
        mainPanel.add(character);

        add(mainPanel);

        mainPanel.setFocusable(true);

        // Define JLabel to display the score
        JLabel scoreLabel = new JLabel();
        scoreLabel.setHorizontalAlignment(JLabel.CENTER); // Center align text
        scoreLabel.setVerticalAlignment(JLabel.TOP);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 30)); // Set font size and style
        scoreLabel.setForeground(Color.BLACK); // Set text color to white
        // Set the initial score
        int initialScore = stats.getScore(); // Assuming stats has a method getScore()
        scoreLabel.setText("Score: " + initialScore);
        // Position the score label at the middle right of the panel
        scoreLabel.setBounds(680, 95, 300, 300);
        mainPanel.add(scoreLabel); // Add the score label to your panel
        // Timer to update the score label every second (1000 milliseconds)
        Timer scoreUpdater = new Timer(1000, e -> {
            int currentScore = stats.getScore();
            scoreLabel.setText("Score: " + currentScore);
        });
        scoreUpdater.start();

        // Setup inventory button and inventory class
        JButton inventoryButton = new JButton(new ImageIcon(inventoryIcon));
        inventoryButton.setContentAreaFilled(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setFocusPainted(false);
        mainPanel.add(inventoryButton);
        updateInventoryButtonPosition(inventoryButton);

        // Initialize the Inventory with the statistics instance
        inventory = new Inventory(this.saveFileName, this, stats, inventoryImage, inventoryButton, this.data);
        mainPanel.repaint();
        inventoryButton.addActionListener(e -> {
            inventory.toggleInventoryDisplay();
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        mainPanel.repaint();

        // Initialize the sleep button
        // New variable for the sleep button
        ImageIcon petIcon = new ImageIcon(petImage); // Wrap the image in an ImageIcon
        JButton sleepButton = new JButton(petIcon); // Initialize the petShelter button
        sleepButton.setVisible(true); // Initially hidden
        sleepButton.setVisible(true);
        sleepButton.setBounds(550, 300, petIcon.getIconWidth(), petIcon.getIconHeight()); // position and size
        sleepButton.setBorderPainted(false); // Do not paint the border
        sleepButton.setContentAreaFilled(false); // Do not fill the content area
        sleepButton.setFocusPainted(false); // Do not paint the focus indicator
        sleepButton.setOpaque(false); // Set the button to be transparent
        mainPanel.add(sleepButton);

        // Action listener for the sleep button
        sleepButton.addActionListener(e -> {
            try {
                if(stats.getHappiness() > 0){
                    this.dispose();
                    new PetShelter(this.petToSpawn, new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
                }
            }
                catch(Exception error) { error.printStackTrace();}
                //mainPanel.requestFocusInWindow();  // regain focus after interaction
            });

        ImageIcon vet = new ImageIcon(vetImage);
        JButton vetButton = new JButton(vet);
        vetButton.setVisible(true);
        vetButton.setBounds(150, 300, vet.getIconWidth(), vet.getIconHeight());
        vetButton.setBorderPainted(false); // Do not paint the border
        vetButton.setContentAreaFilled(false); // Do not fill the content area
        vetButton.setFocusPainted(false); // Do not paint the focus indicator
        vetButton.setOpaque(false); // Set the button to be transparent
        mainPanel.add(vetButton);

        vetButton.addActionListener(e -> {
            try {
                if(stats.getHappiness() > 0) {
                    this.dispose();
                    new VetShelter(this.petToSpawn, new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
                }
            } catch(Exception error) { error.printStackTrace();}
//            mainPanel.repaint();
        });

        ImageIcon questionIcon1 = new ImageIcon(questionImage); // Wrap the image in an ImageIcon
        question1Button = new JButton(questionIcon1); // Initialize the petShelter button
        question1Button.setVisible(true);
        question1Button.setBounds(750, 350, questionIcon1.getIconWidth(), questionIcon1.getIconHeight()); // position and size
        question1Button.setBorderPainted(false); // Do not paint the border
        question1Button.setContentAreaFilled(false); // Do not fill the content area
        question1Button.setFocusPainted(false); // Do not paint the focus indicator
        question1Button.setOpaque(false); // Set the button to be transparent
        mainPanel.add(question1Button);

        // Action listener for Question 1 button
        question1Button.addActionListener(e -> {
            question1Button.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 1); // Open the Questions window !! TYPE 1 = GAMEMENU
            mainPanel.requestFocusInWindow();  // Regain focus after interaction
            mainPanel.repaint();

            // Timer to re-enable the button after 15 seconds (15000 milliseconds)
            Timer enableButtonTimer = new Timer(15000, event -> {
                question1Button.setEnabled(true);  // Re-enable the button after 30 seconds
            });
            enableButtonTimer.setRepeats(false);  // Ensure the timer only runs once
            enableButtonTimer.start();  // Start the timer
        });

        // Initialize the Question 2 button
        ImageIcon questionIcon2 = new ImageIcon(questionImage);
        question2Button = new JButton(questionIcon2);
        question2Button.setVisible(true);
        question2Button.setBounds(getWidth() - 3000, (getHeight() - 30) / 2, questionIcon2.getIconWidth(), questionIcon2.getIconHeight()); //position/size
        question2Button.setBorderPainted(false); // Do not paint the border
        question2Button.setContentAreaFilled(false); // Do not fill the content area
        question2Button.setFocusPainted(false); // Do not paint the focus indicator
        question2Button.setOpaque(false); // Set the button to be transparent
        mainPanel.add(question2Button);
        // Set the position of the Question 2 button (right side)

        // Action listener for Question 1 button
        question2Button.addActionListener(e -> {
            question2Button.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 1); // Open the Questions window !! TYPE 1 = GAMEMENU
            mainPanel.requestFocusInWindow();  // Regain focus after interaction
            mainPanel.repaint();
            // Timer to re-enable the button after 15 seconds (15000 milliseconds)
            Timer enableButtonTimer = new Timer(15000, event -> {
                question2Button.setEnabled(true);  // Re-enable the button after 30 seconds
            });
            enableButtonTimer.setRepeats(false);  // Ensure the timer only runs once
            enableButtonTimer.start();  // Start the timer
        });

        // Component listeners for resizing and button updates
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateInventoryButtonPosition(inventoryButton);
                // Update positions of question buttons on resize
                question1Button.setBounds(220, (getHeight() - 30) / 2 + 150, questionIcon1.getIconWidth(), questionIcon1.getIconHeight());
                question2Button.setBounds(getWidth() - 330, (getHeight() - 30) / 2 + 150, questionIcon2.getIconWidth(), questionIcon2.getIconHeight());
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        KeyboardListener animalControls = new KeyboardListener(petToSpawn);
        mainPanel.addKeyListener(animalControls);

        setVisible(true);
    }

    /**
     * Loads the game state data from the appropriate save file based on pet type.
     * Each pet type has its own save slot (slot1.csv for Dog, slot2.csv for Cat, etc.).
     *
     * @return A Map containing the loaded game state data
     */
    private Map<String, String> loadData() {
        if (this.petToSpawn instanceof Dog) {
            this.saveFileName = "slot1.csv";
        }
        else if (this.petToSpawn instanceof Cat) {
            this.saveFileName = "slot2.csv";
        }
        else if (this.petToSpawn instanceof Fox) {
            this.saveFileName = "slot3.csv";
        }
        else if (this.petToSpawn instanceof Rat) {
            this.saveFileName = "slot4.csv";
        }
        else {
            return new HashMap<>();
        }

        return DataManager.loadState("", this.saveFileName);
    }

    /**
     * Initializes the pet statistics from the loaded game data.
     * Sets up health, happiness, hunger, and sleep values.
     *
     * @param data Map containing the game state data
     */
    private void initializeStatistics(Map<String,String> data) {
        this.health = Integer.parseInt(data.get("health"));
        this.happiness = Integer.parseInt(data.get("happiness"));
        this.hunger = Integer.parseInt(data.get("hunger"));
        this.sleep = Integer.parseInt(data.get("sleep"));
    }

    /**
     * Updates the position of the inventory button based on the current window size.
     * Ensures the button remains in the correct position when the window is resized.
     *
     * @param inventoryButton The JButton instance to be repositioned
     */
    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }
}
