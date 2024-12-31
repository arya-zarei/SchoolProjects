package Animation;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JPanel;

/**
 * Tests the Animation class functionality.
 */
public class AnimationTest {
    private Animation animation;

    /**
     * Sets up the test environment before each test.
     * Initializes a new instance of MockAnimation.
     */
    @BeforeEach
    void setUp() {
        animation = new MockAnimation();
    }

    /**
     * Tests the initialization of the Animation instance.
     * Asserts that the animation and its panel are not null.
     */
    @Test
    void testAnimationInitialization() {
        assertNotNull(animation, "Animation should be initialized");
        assertNotNull(animation.getPanel(), "Animation panel should be initialized");
    }

    /**
     * Tests the state changes of the Animation.
     * Verifies that the animation can change between different states.
     */
    @Test
    void testAnimationState() {
        // Test initial state
        animation.setAnimation(AnimationState.IDLE);

        // Test changing state
        animation.setAnimation(AnimationState.WALK);

        // Test attack state
        animation.setAnimation(AnimationState.ATTACK);

        // Test hurt state
        animation.setAnimation(AnimationState.HURT);

        // Test death state
        animation.setAnimation(AnimationState.DEATH);
    }

    /**
     * Tests the lock and unlock functionality of the Animation.
     */
    @Test
    void testLockUnlock() {
        animation.lock();
        animation.unlock();
    }

    /**
     * Tests the position setting of the Animation.
     * Sets the location of the animation to a specified x and y coordinate.
     */
    @Test
    void testPosition() {
        int testX = 100;
        animation.setLocation(testX, 240);
    }

    /**
     * Tests that the Animation panel is not null.
     * Asserts that the panel returned by the animation is not null.
     */
    @Test
    void testPanel() {
        JPanel panel = animation.getPanel();
        assertNotNull(panel, "Animation panel should not be null");
    }
}