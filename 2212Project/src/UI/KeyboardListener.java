package UI;

import Pets.Pet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A keyboard input handler that translates key events into pet movement commands.
 * This class implements the KeyListener interface to process keyboard inputs
 * for controlling a virtual pet's movement in a 2D space.
 * 
 * <p>The class supports the following controls:
 * <ul>
 *   <li>W - Move pet upward</li>
 *   <li>A - Move pet left</li>
 *   <li>S - Move pet downward</li>
 *   <li>D - Move pet right</li>
 * </ul>
 * 
 * @author Mark
 * @version 
 */
public class KeyboardListener implements KeyListener {
    private Pet pet;

    /**
     * Constructs a new KeyboardListener that controls the specified pet.
     * 
     * @param pet the Pet instance that will be controlled by keyboard input
     * @throws NullPointerException if the provided pet is null
     */
    public KeyboardListener(Pet pet) {
        this.pet = pet;
    }

    /**
     * Handles key typed events. Currently not implemented as movement
     * is handled through keyPressed events instead.
     * 
     * @param e the KeyEvent containing information about the key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Currently, no actions are performed on keyTyped.
    }

    /**
     * Handles key pressed events by moving the pet in the corresponding direction.
     * Movement is controlled using the WASD keys, with each key press moving
     * the pet by a fixed amount in the appropriate direction.
     * 
     * <p>The pet will also enter a walking animation state when moving.
     * 
     * @param e the KeyEvent containing information about the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int moveAmount = 5; // Adjust movement speed as needed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                pet.move(0, -moveAmount); // Move up
                pet.walk();
                break;
            case KeyEvent.VK_A:
                pet.move(-moveAmount, 0); // Move left
                pet.walk();
                break;
            case KeyEvent.VK_S:
                pet.move(0, moveAmount); // Move down
                pet.walk();
                break;
            case KeyEvent.VK_D:
                pet.move(moveAmount, 0); // Move right
                pet.walk();
                break;
        }
    }

    /**
     * Handles key released events by stopping the pet's walking animation.
     * This method is called when any key is released, returning the pet
     * to its idle animation state.
     * 
     * @param e the KeyEvent containing information about the key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pet.stopWalking();
    }
}
