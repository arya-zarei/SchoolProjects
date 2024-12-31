package Animation;

import java.io.IOException;
import javax.swing.*;

/**
 * The RatAnimation class is responsible for loading and managing the animations
 * for a rat character in the game. It extends the Animation class and overrides
 * the loadAnimations method to load specific animation frames for various actions
 * such as idle, death, hurt, and walking.
 */
public class RatAnimation extends Animation {

    /**
     * Loads the animation frames for the rat character.
     * This method attempts to load the frames for different states of the rat,
     * including idle, death, hurt, and walking. If any of the frames fail to load,
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
                    "Assets/Animals/6 Rat 2/Idle.png", 4, 32, 32);
//            attackFrames = loadFrames(
//                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Attack.png", 4);
            deathFrames = loadFrames(
                    "Assets/Animals/6 Rat 2/Death.png", 2, 32, 32);
            hurtFrames = loadFrames(
                    "Assets/Animals/6 Rat 2/Hurt.png", 2, 32, 32);
            walkFrames = loadFrames(
                    "Assets/Animals/6 Rat 2/Walk.png", 4, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
