package UI;

import Pets.Pet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * The PetShelter class represents a graphical user interface for a pet shelter application.
 * It displays a background panel with various interactive components, including buttons for 
 * user actions and a character animation.
 * 
 * <p>This class initializes the main frame of the application, sets up the background panel, 
 * and manages user interactions through buttons and event listeners.</p>
 * @author aryaz
 */
public class PetShelter {
    private final JPanel backgroundPanel; // Background panel
    private statistics stats; // Statistics instance
    GameMenu gameMenu;
    private Inventory inventory;

    /**
     * Constructs a PetShelter instance with the specified pet and statistics.
     * 
     * <p>This constructor initializes the main JFrame, sets up the background panel with 
     * a background image, and adds interactive components such as question and exit buttons. 
     * It also handles the display of statistics and character animations.</p>
     * 
     * @param animal The pet object to be displayed in the shelter.
     * @param stats An instance of statistics that holds and manages the game's statistics.
     */
    public PetShelter(Pet animal, statistics stats) {
        JFrame frame = new JFrame("Pet Shelter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gameMenu = gameMenu;
        this.inventory = inventory;
        this.stats = stats;

        backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Assets/GameImages/Petshelter.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        // Set the layout manager to null for absolute positioning of components
        backgroundPanel.setLayout(null);

        ImageIcon question = new ImageIcon("Assets/Images/question.png");
        JButton questionButton = new JButton(question);
        questionButton.setBounds(10, 100, question.getIconWidth(), question.getIconHeight()); // Adjust positioning as needed
        questionButton.setBorderPainted(false); // Removes the border around the button
        questionButton.setContentAreaFilled(false); // Ensures the background inside the button is not filled
        questionButton.setFocusPainted(false); // Removes the focus indicator when the button is clicked
        questionButton.setOpaque(false); // Makes sure the button is transparent
        // Position the button at the top-right corner
        backgroundPanel.add(questionButton);

        questionButton.addActionListener(e -> {
            questionButton.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 2); // Open the Questions window !! TYPE 2 = SLEEP
            backgroundPanel.repaint();
            // Timer to re-enable the button after 15 seconds (15000 milliseconds)
            Timer enableButtonTimer = new Timer(15000, event -> {
                questionButton.setEnabled(true);  // Re-enable the button after 30 seconds
            });
            enableButtonTimer.setRepeats(false);  // Ensure the timer only runs once
            enableButtonTimer.start();  // Start the timer
            questionsWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    backgroundPanel.repaint();
                }
            });
        });
        
        
        // Create an exit button
        ImageIcon exit = new ImageIcon("Assets/Images/goBack.png");
        JButton exitButton = new JButton(exit);
        exitButton.setBorderPainted(false); // Removes the border around the button
        exitButton.setContentAreaFilled(false); // Ensures the background inside the button is not filled
        exitButton.setFocusPainted(false); // Removes the focus indicator when the button is clicked
        exitButton.setOpaque(false); // Makes sure the button is transparent
        // Position the button at the top-right corner
        exitButton.setBounds(10, 10, exit.getIconWidth(), exit.getIconHeight()); // Adjusted positioning
        // Add the exit button directly to the background panel
        backgroundPanel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame when the exit button is clicked
            new GameMenu(animal); // Open the GameMenu with the current animal
        });

        JPanel character = animal.getAnimationPanel(); // Get the animation panel for the animal
        animal.sleep(); // Put the animal into a sleep state
        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height); // Set the position and size of the character panel
        backgroundPanel.addKeyListener(new KeyboardListener(animal)); // Add a key listener to the background panel for animal controls
        backgroundPanel.add(character); // Add the character panel to the background panel

        backgroundPanel.setFocusable(true); // Make the background panel focusable to receive key events
        backgroundPanel.requestFocusInWindow(); // Request focus for the background panel
        frame.setVisible(true); // Make the frame visible
        
        frame.getContentPane().add(backgroundPanel); // Add the background panel to the frame's content pane
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
