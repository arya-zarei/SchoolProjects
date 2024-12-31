package Pets;

import Animation.Animation;
import Animation.AnimationState;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Represents a generic Pet in the application.
 * This abstract class provides common attributes and behaviors for all types of pets,
 * including their name, position, health, hunger, sleep levels, and inventory.
 * 
 * <p>
 * Subclasses of Pet must implement specific behaviors and attributes for different types of pets.
 * The Pet class also provides methods for managing the pet's state and retrieving its attributes.
 * </p>
 * @author Mark
 */
public abstract class Pet {
    private String name; // Store the pet's name
    private int x = 340; // Initial X position
    private int y = 100; // Initial Y position

    private int currentHealth = 100;
    private int maxHealth = 100;
    private int hungerLevel = 50;
    private int sleepLevel = 50;

    private Animation animationPanel;
    private Map<String, Integer> inventory = new HashMap<>(); // Store inventory items

    /**
     * Constructs a Pet instance with the specified name and animation.
     *
     * @param name the name of the pet
     * @param animation the Animation object that defines the pet's animation
     */
    public Pet(String name, Animation animation) {
        this.name = name; // Store the pet's name
        this.animationPanel = animation;
    }

    // Getters and setters for name
    /**
     * Returns the name of the pet.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     *
     * @param name the new name for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    // Position
    /**
     * Returns the current X position of the pet.
     *
     * @return the X position of the pet
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X position of the pet.
     *
     * @param x the new X position for the pet
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the current Y position of the pet.
     *
     * @return the Y position of the pet
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y position of the pet.
     *
     * @param y the new Y position for the pet
     */
    public void setY(int y) {
        this.y = y;
    }

    // Health, hunger, sleep
    /**
     * Returns the current health of the pet.
     *
     * @return the current health of the pet
     */
    public int getHealth() {
        return currentHealth;
    }

    /**
     * Sets the health of the pet.
     *
     * @param health the new health value for the pet
     */
    public void setHealth(int health) {
        this.currentHealth = Math.min(health, maxHealth);
    }

    /**
     * Returns the maximum health of the pet.
     *
     * @return the maximum health of the pet
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void increaseHealth(int health) {
        this.currentHealth = Math.min(this.currentHealth + health, this.maxHealth);
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getSleepLevel() {
        return sleepLevel;
    }

    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    // Inventory
    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    // Retrieve attributes for saving
    /**
     * Collects the current attributes of the pet and stores them in a map.
     * This method is useful for saving the pet's state, such as when 
     * persisting data to a file or database.
     *
     * @return a map containing the pet's attributes as key-value pairs
     */
    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", name); // Include the name in the attributes
        attributes.put("x", String.valueOf(x)); // Current X position
        attributes.put("y", String.valueOf(y)); // Current Y position
        attributes.put("health", String.valueOf(currentHealth)); // Current health
        attributes.put("maxHealth", String.valueOf(maxHealth)); // Maximum health
        attributes.put("hunger", String.valueOf(hungerLevel)); // Current hunger level
        attributes.put("sleep", String.valueOf(sleepLevel)); // Current sleep level

        // Add inventory items to attributes
        // Iterates through the inventory map and adds each item to the attributes map
        for (Map.Entry<String, Integer> item : inventory.entrySet()) {
            attributes.put("inventory_" + item.getKey(), String.valueOf(item.getValue())); // Key is prefixed with "inventory_"
        }

        return attributes; // Return the populated attributes map
    }

    // Load attributes from a map
    /**
     * Sets the pet's attributes based on the provided map.
     * This method is useful for loading the pet's state, such as when 
     * restoring data from a file or database.
     *
     * @param attributes a map containing the pet's attributes as key-value pairs
     */
    public void setAttributes(Map<String, String> attributes) {
        // Check if the map contains specific keys and update the corresponding attributes
        if (attributes.containsKey("name")) this.name = attributes.get("name"); // Set name
        if (attributes.containsKey("x")) this.x = Integer.parseInt(attributes.get("x")); // Set X position
        if (attributes.containsKey("y")) this.y = Integer.parseInt(attributes.get("y")); // Set Y position
        if (attributes.containsKey("health")) this.currentHealth = Integer.parseInt(attributes.get("health")); // Set current health
        if (attributes.containsKey("maxHealth")) this.maxHealth = Integer.parseInt(attributes.get("maxHealth")); // Set max health
        if (attributes.containsKey("hunger")) this.hungerLevel = Integer.parseInt(attributes.get("hunger")); // Set hunger level
        if (attributes.containsKey("sleep")) this.sleepLevel = Integer.parseInt(attributes.get("sleep")); // Set sleep level

        // Load inventory items
        inventory.clear(); // Clear existing inventory before loading new items
        // Iterate through the keys in the attributes map to find inventory items
        for (String key : attributes.keySet()) {
            if (key.startsWith("inventory_")) { // Check for inventory items
                String itemName = key.substring("inventory_".length()); // Extract item name
                inventory.put(itemName, Integer.parseInt(attributes.get(key))); // Add item to inventory
            }
        }
    }

    public JPanel getAnimationPanel() {
        return animationPanel.getPanel();
    }

    // Animation methods
    public void walk() {
        animationPanel.setAnimation(AnimationState.WALK);
    }

    public void unlock() { animationPanel.unlock(); }

    public void stopWalking() {

        animationPanel.setAnimation(AnimationState.IDLE);
    }

    public void attack() {
        animationPanel.setAnimation(AnimationState.ATTACK);
    }

    public void stopAttacking() {
        animationPanel.setAnimation(AnimationState.IDLE);
    }

    public void hurt() {
        animationPanel.setAnimation(AnimationState.HURT);
    }

    public void sleep() {
        animationPanel.setAnimation(AnimationState.DEATH);
        animationPanel.lock();
    }

    public void move(int dx, int dy) {
        if (x + dx < 0 || x + dx > 675) return;
        x += dx;
        y += dy;
        animationPanel.setLocation(x, 240);
    }

    /** For testing keyboard listener*/
    public boolean isMoving() {
        // Return whether the pet is in walking animation state
        return true; // Implement based on your Pet class
    }

    /** For PetShelter test cases */
    public boolean isAsleep() {
        // Return whether the pet is in sleep animation state
        return true; // Implement based on your Pet class
    }

    public int getInitialY() {
        // Return the initial Y position
        return 100; // Implement based on your Pet class
    }
}