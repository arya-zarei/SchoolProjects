package Animation;

import java.io.IOException;
import javax.swing.*;

/**
 * The CatAnimation class is responsible for loading and managing the animations
 * for a cat character in the animation framework. It extends the Animation class
 * and overrides the loadAnimations method to specify the frames for different
 * cat actions such as idle, attack, death, hurt, and walking.
 */
public class CatAnimation extends Animation{
    /**
     * Loads the animation frames for the cat character.
     * This method attempts to load various animation frames from specified file paths.
     * If any IOException occurs during the loading process, an error message is displayed
     * to the user.
     *
     * The following animations are loaded:
     * - Idle animation frames
     * - Attack animation frames
     * - Death animation frames
     * - Hurt animation frames
     * - Walk animation frames
     *
     * @throws IOException if there is an error loading the animation assets
     * @author Mark
     */
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/Animals/3 Cat/Idle.png", 4, 48, 48);
            attackFrames = loadFrames(
                    "Assets/Animals/3 Cat/Attack.png", 4, 48, 48);
            deathFrames = loadFrames(
                    "Assets/Animals/3 Cat/Death.png", 4, 48, 48);
            hurtFrames = loadFrames(
                    "Assets/Animals/3 Cat/Hurt.png", 2, 48, 48);
            walkFrames = loadFrames(
                    "Assets/Animals/3 Cat/Walk.png", 4, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
