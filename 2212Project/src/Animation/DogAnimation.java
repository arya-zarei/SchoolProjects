package Animation;

import java.io.IOException;
import javax.swing.*;

/**
 * The DogAnimation class is a subclass of the Animation class that handles
 * the loading and management of various animations for a dog character.
 * This includes animations for idle, attack, death, hurt, and walking states.
 */
public class DogAnimation extends Animation {

    /**
     * Loads the animation frames for the dog character.
     * This method attempts to load the frames for different animations
     * from specified file paths. If an error occurs during loading,
     * an error message is displayed to the user.
     *
     * @throws IOException if there is an error loading the animation assets.
     * @author Mark
     */
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/Animals/1 Dog/Idle.png", 4, 48, 48);
            attackFrames = loadFrames(
                    "Assets/Animals/1 Dog/Attack.png", 4, 48, 48);
            deathFrames = loadFrames(
                    "Assets/Animals/1 Dog/Death.png", 4, 48, 48);
            hurtFrames = loadFrames(
                    "Assets/Animals/1 Dog/Hurt.png", 2, 48, 48);
            walkFrames = loadFrames(
                    "Assets/Animals/1 Dog/Walk.png", 4, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
