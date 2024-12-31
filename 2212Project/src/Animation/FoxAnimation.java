package Animation;

import java.io.IOException;
import javax.swing.*;

/**
 * The FoxAnimation class is a subclass of the Animation class.
 * It is responsible for loading and managing the animation frames
 * for a fox character in the game. This includes various states
 * such as idle, attack, death, hurt, and walking.
 * @author Mark
 */
public class FoxAnimation extends Animation {

    /**
     * Loads the animation frames for the fox character.
     * This method attempts to load the frames from specified asset files
     * and handles any IOExceptions that may occur during the loading process.
     * 
     * The following animations are loaded:
     * - Idle animation with 4 frames
     * - Attack animation with 4 frames
     * - Death animation with 4 frames
     * - Hurt animation with 2 frames
     * - Walk animation with 4 frames
     * 
     * If an error occurs while loading the animations, an error message
     * is displayed to the user.
     */
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/Animals/2 Dog 2/Idle.png", 4, 48, 48);
            attackFrames = loadFrames(
                    "Assets/Animals/2 Dog 2/Attack.png", 4, 48, 48);
            deathFrames = loadFrames(
                    "Assets/Animals/2 Dog 2/Death.png", 4, 48, 48);
            hurtFrames = loadFrames(
                    "Assets/Animals/2 Dog 2/Hurt.png", 2, 48, 48);
            walkFrames = loadFrames(
                    "Assets/Animals/2 Dog 2/Walk.png", 4, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
