package UI;

import Game.DataManager;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

/**
 * Represents the inventory system for managing items in the game.
 */
public class Inventory{
    /** Dialog window for displaying the inventory interface */
    private JDialog inventoryDialog;
    
    /** Array storing the quantity of each item type. Indices match item types */
    private final int[] itemCounts = {0,0,0,0,0,0};
    
    /** Persistent storage map for inventory state */
    private final Map<String, String> data;
    
    /** Background image for the inventory dialog */
    private final Image inventoryImage;
    
    /** Button that toggles inventory visibility */
    private final JButton inventoryButton;
    
    /** Reference to the main game menu */
    private final GameMenu gameMenu;
    
    /** Reference to the game's statistics tracking system */
    private final statistics gameStats;
    
    /** Name of the file where inventory state is saved */
    private String saveFileName;

    /** 
     * Maps items to their corresponding stat indices
     * Values: 2 = hunger stat, 1 = happiness stat
     */
    private final int[] statIndices = {2, 2, 2, 2, 1, 1};

    /**
     * Constructs a new Inventory instance and initializes the inventory system.
     * This constructor sets up the inventory dialog, loads existing item counts from saved data,
     * and establishes connections with the game's statistical tracking system.
     * 
     * @param saveFileName The name of the save file where inventory data is persisted
     * @param gameMenu The parent GameMenu instance that contains this inventory
     * @param gameStats The statistics tracker that monitors game metrics like hunger and happiness
     * @param inventoryImage The background image displayed in the inventory dialog window
     * @param inventoryButton The UI button that players click to access the inventory
     * @param data A map containing the current state of the inventory, including item counts
     */
    public Inventory(String saveFileName, GameMenu gameMenu, statistics gameStats, Image inventoryImage, JButton inventoryButton, Map<String, String> data) {
        this.gameMenu = gameMenu;
        this.gameStats = gameStats; // Initialize the statistics reference
        this.inventoryImage = inventoryImage;
        this.inventoryButton = inventoryButton;
        this.saveFileName = saveFileName;
        setupInventoryDialog();

        // Interact with csv
        this.data = data;
        itemCounts[0] = Integer.parseInt(data.get("apple"));
        itemCounts[1] = Integer.parseInt(data.get("orange"));
        itemCounts[2] = Integer.parseInt(data.get("strawberry"));
        itemCounts[3] = Integer.parseInt(data.get("banana"));
        itemCounts[4] = Integer.parseInt(data.get("ybone"));
        itemCounts[5] = Integer.parseInt(data.get("bbone"));

    }

    /**
     * Sets up the inventory dialog window with its basic properties.
     * Creates a non-modal dialog with fixed dimensions and positioning relative to the game menu.
     * Initializes the dialog with:
     * - Size: 525x225 pixels
     * - Non-resizable window
     * - Centered position relative to game menu
     * - Custom inventory panel
     * - Window listener for button state management
     */
    private void setupInventoryDialog() {
        inventoryDialog = new JDialog(gameMenu, "Inventory", false);
        inventoryDialog.setSize(525, 225);
        inventoryDialog.setLocationRelativeTo(gameMenu);
        inventoryDialog.setResizable(false);
        JPanel inventoryPanel = createInventoryPanel();
        inventoryDialog.add(inventoryPanel);
        inventoryDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                inventoryButton.setEnabled(true); // Re-enable the inventory button
            }
        });
    }

    /**
     * Creates and returns the main panel for the inventory dialog.
     * The panel includes:
     * - Custom background image
     * - Item count displays using Arial Bold 20pt font
     * - Interactive buttons for each inventory slot
     * - Custom paint component for visual elements
     * 
     * @return JPanel A configured panel with all inventory UI elements
     */
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (inventoryImage != null) {
                    g.drawImage(inventoryImage, 0, 0, getWidth(), getHeight(), this);
                }
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.BLACK);
                for (int i = 0; i < itemCounts.length; i++) {
                    g.drawString(String.valueOf(itemCounts[i]), 60 + 86 * i, 120);
                }
            }
        };
        panel.setLayout(null);
        addButton(panel);
        return panel;
    }

    /**
     * Adds interactive buttons for each inventory item to the panel.
     * Each button:
     * - Is invisible but clickable (transparent)
     * - Handles item usage and count decrementation
     * - Updates persistent storage when clicked
     * - Shows warning when item count is zero
     * - Updates corresponding game statistics on use
     * 
     * @param panel The panel to which the buttons will be added
     */
    private void addButton(JPanel panel) {
        for (int i = 0; i < itemCounts.length; i++) {
            int index = i; // Capture the index for use in the lambda
            JButton button = new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setBounds(85 * i, 90, 85, 100);
            button.addActionListener(e -> {
                if (itemCounts[index] > 0) {
                    itemCounts[index]--; // Decrement the item count

                    switch(index) {
                        case 0:
                            data.put("apple", String.valueOf(Integer.parseInt(data.get("apple")) - 1));
                            break;
                        case 1:
                            data.put("orange", String.valueOf(Integer.parseInt(data.get("orange")) - 1));
                            break;
                        case 2:
                            data.put("strawberry", String.valueOf(Integer.parseInt(data.get("strawberry")) - 1));
                            break;
                        case 3:
                            data.put("banana", String.valueOf(Integer.parseInt(data.get("banana")) - 1));
                            break;
                        case 4:
                            data.put("ybone", String.valueOf(Integer.parseInt(data.get("ybone")) - 1));
                            break;
                        case 5:
                            data.put("bbone", String.valueOf(Integer.parseInt(data.get("bbone")) - 1));
                            break;
                    }

                    DataManager.saveState(this.saveFileName, this.data);
                    increaseStat(statIndices[index], 10); // Call to increaseStat

                } else {
                    JOptionPane.showMessageDialog(inventoryDialog, "No more items left!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            });
            panel.add(button);
        }
    }

    /**
     * Increases a specific stat when an item is used.
     * Delegates the stat update to the statistics system.
     * Currently configured for:
     * - Food items: +10 to hunger stat
     * - Bones: +10 to happiness stat
     * 
     * @param statIndex The index of the stat to increase (1=happiness, 2=hunger)
     * @param increment The amount to increase the stat by (typically 10)
     */
    private void increaseStat(int statIndex, int increment) {
        // Call the updateStat method on the statistics instance
        gameStats.updateState(statIndex, increment);
    }

    /**
     * Toggles the visibility of the inventory dialog.
     * When opened:
     * - Makes the inventory dialog visible
     * - Disables the inventory button to prevent multiple windows
     * When closed:
     * - The button is re-enabled via the window listener
     */
    public void toggleInventoryDisplay() {
        if (!inventoryDialog.isVisible()) {
            inventoryDialog.setVisible(true);
            inventoryButton.setEnabled(false);
        }
    }

    /**
     * Updates the count of a specific item in the inventory.
     * Handles both increasing and decreasing item quantities.
     * After updating:
     * - Refreshes the inventory display
     * - Updates the persistent storage map
     * - Saves the new state to file
     * 
     * @param index The index of the item to update (0-5)
     * @param count The amount to change the item count by (positive for increase, negative for decrease)
     */
    public void updateItemCount(int index, int count) {
        if (index >= 0 && index < itemCounts.length) {
            itemCounts[index] += count;
            inventoryDialog.repaint(); // Refresh the dialog to show updated counts
            switch(index) {
                case 0:
                    data.put("apple", String.valueOf(Integer.parseInt(data.get("apple")) + 1));
                    break;
                case 1:
                    data.put("orange", String.valueOf(Integer.parseInt(data.get("orange")) + 1));
                    break;
                case 2:
                    data.put("strawberry", String.valueOf(Integer.parseInt(data.get("strawberry")) + 1));
                    break;
                case 3:
                    data.put("banana", String.valueOf(Integer.parseInt(data.get("banana")) + 1));
                    break;
                case 4:
                    data.put("ybone", String.valueOf(Integer.parseInt(data.get("ybone")) + 1));
                    break;
                case 5:
                    data.put("bbone", String.valueOf(Integer.parseInt(data.get("bbone")) + 1));
                    break;

            }

            DataManager.saveState(saveFileName, data); // Save the updated state
        }
    }
}

