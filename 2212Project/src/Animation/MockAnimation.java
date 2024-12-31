package Animation;

import Animation.Animation;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * A mock animation class for testing purposes.
 * Provides minimal implementation of the abstract Animation class.
 */
public class MockAnimation extends Animation {

    @Override
    public void loadAnimations() {
        // Create a simple 1x1 pixel BufferedImage for each animation type
        BufferedImage mockFrame = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        // Initialize all frame arrays with the mock frame
        idleFrames = new BufferedImage[]{mockFrame};
        attackFrames = new BufferedImage[]{mockFrame};
        deathFrames = new BufferedImage[]{mockFrame};
        hurtFrames = new BufferedImage[]{mockFrame};
        walkFrames = new BufferedImage[]{mockFrame};
    }

    private int x = 0;
    private JPanel panel;

    public MockAnimation() {
        panel = new JPanel();
    }

    public void moveLeft() {
        x -= 5;
    }

    public void moveRight() {
        x += 5;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public JPanel getAnimationPanel() {
        return panel;
    }
}