package UI;

import Game.DataManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * The statistics class manages the health, happiness, hunger, and sleep of a pet.
 * It provides methods to update these statistics and draw them on the screen.
 * @author aryaz
 */
public class statistics {
    private int health;
    private int happiness;
    private int hunger;
    private int sleep;
    private final Image statBarImage;
    private final Image healthIcon;
    private final Image foodIcon;
    private final Image happyIcon;
    private final Image sleepIcon;
    private boolean notifiedHealth = false;
    private boolean notifiedSleep = false;
    private boolean notifiedHunger = false;
    private boolean notifiedHappiness = false;
    private final GameMenu gameMenu;
    private Map<String, String> data;
    private String saveFileName;

    /**
     * Constructs a statistics object with the given parameters.
     *
     * @param saveFileName the name of the file to save the statistics
     * @param data a map containing the initial statistics values
     * @param statBarImage the image used for the stat bar
     * @param healthIcon the image representing health
     * @param happyIcon the image representing happiness
     * @param foodIcon the image representing hunger
     * @param sleepIcon the image representing sleep
     * @param gameMenu the game menu associated with this statistics object
     */
    public statistics(String saveFileName, Map<String, String> data, Image statBarImage, Image healthIcon, Image happyIcon, Image foodIcon, Image sleepIcon, GameMenu gameMenu) {
        this.data = data;
        this.saveFileName = saveFileName;
        this.health = Integer.parseInt(data.get("health"));
        this.happiness = Integer.parseInt(data.get("happiness"));
        this.hunger = Integer.parseInt(data.get("hunger"));
        this.sleep = Integer.parseInt(data.get("sleep"));
        this.statBarImage = statBarImage;
        this.healthIcon = healthIcon;
        this.foodIcon = foodIcon;
        this.happyIcon = happyIcon;
        this.sleepIcon = sleepIcon;
        this.gameMenu = gameMenu;
    }

    /**
     * Updates the specified statistic by a given increment.
     *
     * @param statIndex the index of the statistic to update (0: health, 1: happiness, 2: hunger, 3: sleep)
     * @param increment the amount to increment the statistic by
     * @throws IllegalArgumentException if the statIndex is invalid
     */
    public void updateState(int statIndex, int increment) {
        switch (statIndex) {
            case 0: // Health
                setHealth(Math.min(100, Math.max(0, health + increment)));
                this.data.put("health", String.valueOf(Math.min(100, Math.max(0, Integer.parseInt(data.get("health")) + increment))));
                gameMenu.repaint();
                if (getHealth() <= 25) {
                    if (!notifiedHealth) {
                        this.notifiedHealth = true;
                        SwingUtilities.invokeLater(() -> {
                            // Create a non-modal dialog to show the message
                            final JDialog dialog = new JDialog();
                            dialog.setTitle("Alert");
                            dialog.setModal(false); // Make it non-modal
                            dialog.setSize(300, 100);
                            dialog.setLayout(new FlowLayout());
                            dialog.add(new JLabel("Your pet is hurt! Health is very low"));
                            dialog.setLocationRelativeTo(null); // Center on screen

                            // Set a timer to close the dialog automatically after 2 seconds
                            new Timer(2000, new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    dialog.dispose();
                                }
                            }).start();

                            dialog.setVisible(true);
                        });

                    }
                }
                break;
            case 1: // Happiness
                setHappiness(Math.min(100, Math.max(0, happiness + increment)));
                this.data.put("happiness", String.valueOf(Math.min(100, Math.max(0, Integer.parseInt(data.get("happiness")) + increment))));
                gameMenu.repaint();
                if (getHappiness() <= 25) {
                    if (!notifiedHappiness) {
                        notifiedHappiness = true;
                        SwingUtilities.invokeLater(() -> {
                            // Create a non-modal dialog to show the message
                            final JDialog dialog = new JDialog();
                            dialog.setTitle("Alert");
                            dialog.setModal(false); // Make it non-modal
                            dialog.setSize(300, 100);
                            dialog.setLayout(new FlowLayout());
                            dialog.add(new JLabel("Your pet is not happy! Happiness is very low"));
                            dialog.setLocationRelativeTo(null); // Center on screen

                            // Set a timer to close the dialog automatically after 2 seconds
                            new Timer(2000, new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    dialog.dispose();
                                }
                            }).start();

                            dialog.setVisible(true);
                        });

                    }
                }
                break;
            case 2: // Hunger
                setHunger(Math.min(100, Math.max(0, hunger + increment)));
                this.data.put("hunger", String.valueOf(Math.min(100, Math.max(0, Integer.parseInt(data.get("hunger")) + increment))));
                gameMenu.repaint();
                if(getHunger() == 0){
                    updateState(1, -25);
                }
                if (getHunger() <= 25) {
                        if (!notifiedHunger) {
                            this.notifiedHunger = true;
                            SwingUtilities.invokeLater(() -> {
                                // Create a non-modal dialog to show the message
                                final JDialog dialog = new JDialog();
                                dialog.setTitle("Alert");
                                dialog.setModal(false); // Make it non-modal
                                dialog.setSize(300, 100);
                                dialog.setLayout(new FlowLayout());
                                dialog.add(new JLabel("Your pet is hungry! Hunger is very low"));
                                dialog.setLocationRelativeTo(null); // Center on screen

                                // Set a timer to close the dialog automatically after 2 seconds
                                new Timer(2000, new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        dialog.dispose();
                                    }
                                }).start();

                                dialog.setVisible(true);
                            });
                        }

                }
                break;
            case 3: // Sleep
                setSleep(Math.min(100, Math.max(0, sleep + increment)));
                this.data.put("sleep", String.valueOf(Math.min(100, Math.max(0, Integer.parseInt(data.get("sleep")) + increment))));
                gameMenu.repaint();
                if(getSleep() == 0){
                    updateState(0, -25);
                }
                if (getSleep() <= 25) {
                    if (!notifiedSleep) {
                        this.notifiedSleep = true;
                        SwingUtilities.invokeLater(() -> {
                            // Create a non-modal dialog to show the message
                            final JDialog dialog = new JDialog();
                            dialog.setTitle("Alert");
                            dialog.setModal(false); // Make it non-modal
                            dialog.setSize(300, 100);
                            dialog.setLayout(new FlowLayout());
                            dialog.add(new JLabel("Your pet is tired! Sleep is very low"));
                            dialog.setLocationRelativeTo(null); // Center on screen

                            // Set a timer to close the dialog automatically after 2 seconds
                            new Timer(2000, new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    dialog.dispose();
                                }
                            }).start();

                            dialog.setVisible(true);
                        });

                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid stat index: " + statIndex);
        }
        DataManager.saveState(this.saveFileName, this.data);
    }

    /**
     * Draws the current statistics on the provided graphics context.
     *
     * @param g the graphics context to draw on
     * @param width the width of the area to draw in
     * @param height the height of the area to draw in
     */
    public void drawStats(Graphics g, int width, int height) {
        int statBarWidth = statBarImage.getWidth(null);
        int statBarHeight = statBarImage.getHeight(null);
        int statBarX = width - statBarWidth - 60;
        int statBarY = 10;

        drawStatBar(g, statBarX, statBarY, getHealth(), Color.RED);
        g.drawImage(healthIcon, statBarX + statBarWidth + 5, statBarY, null);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getHappiness(), Color.RED);
        g.drawImage(happyIcon, statBarX + statBarWidth + 5, statBarY, null);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getHunger(), Color.RED);
        g.drawImage(foodIcon, statBarX + statBarWidth + 5, statBarY, null);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getSleep(), Color.RED);
        g.drawImage(sleepIcon, statBarX + statBarWidth + 5, statBarY, null);
    }

    /**
     * Calculates the total score based on the current statistics.
     *
     * @return the total score, which is the sum of health, happiness, hunger, and sleep
     */
    public int getScore(){
        return getSleep() + getHappiness() + getHealth() + getHunger();
    }

    /**
     * Sets the health of the pet.
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health =  health;
    }

    /**
     * Gets the current health of the pet.
     *
     * @return the current health value
     */
    public int getHealth(){
        return health;
    }

    /**
     * Sets the happiness of the pet.
     *
     * @param happiness the new happiness value
     */
    public void setHappiness(int happiness) {
        this.happiness = happiness; // Ensure health is within bounds
    }

    /**
     * Gets the current happiness of the pet.
     *
     * @return the current happiness value
     */
    public int getHappiness(){
        return happiness;
    }

    /**
     * Sets the hunger of the pet.
     *
     * @param hunger the new hunger value
     */
    public void setHunger(int hunger) {
        this.hunger = hunger; // Ensure health is within bounds
    }

    /**
     * Gets the current hunger of the pet.
     *
     * @return the current hunger value
     */
    public int getHunger(){
        return hunger;
    }

    /**
     * Sets the sleep of the pet.
     *
     * @param sleep the new sleep value
     */
    public void setSleep(int sleep) {
        this.sleep = sleep; // Ensure health is within bounds
    }

    /**
     * Gets the current sleep of the pet.
     *
     * @return the current sleep value
     */
    public int getSleep(){
        return sleep;
    }

    /**
     * Draws a stat bar for a given statistic.
     *
     * @param g the graphics context to draw on
     * @param x the x-coordinate to start drawing
     * @param y the y-coordinate to start drawing
     * @param percentage the percentage of the stat to display
     * @param color the color of the stat bar
     */
    private void drawStatBar(Graphics g, int x, int y, int percentage, Color color) {
        int width = (int)(statBarImage.getWidth(null) * (percentage / 100.0)) - 4;
        g.drawImage(statBarImage, x, y, null);
        g.setColor(color);
        g.fillRect(x + 2, y + 2, width, statBarImage.getHeight(null) - 4);
    }
}
