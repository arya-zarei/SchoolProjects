package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Abstract class representing an animation for a game character.
 * This class handles the loading and rendering of different animation states
 * such as idle, attack, death, hurt, and walk.
 * @author Mark
 */
public abstract class Animation extends JPanel{
    // Array of BufferedImages for different animation frames
    protected BufferedImage[] idleFrames; // Frames for idle animation
    protected BufferedImage[] attackFrames; // Frames for attack animation
    protected BufferedImage[] deathFrames; // Frames for death animation
    protected BufferedImage[] hurtFrames; // Frames for hurt animation
    protected BufferedImage[] walkFrames; // Frames for walk animation
    private boolean[] framesDone = new boolean[4]; // Tracks if frames are done

    protected boolean flipHorizontally = false; // Indicates if the animation should be flipped
    private boolean isLocked = false; // Indicates if the animation is locked

    private BufferedImage[] currentAnimation; // Tracks which animation is currently playing
    private int currentFrame = 0; // Current frame index
    private ScheduledExecutorService scheduler; // Scheduler for frame updates

    /**
     * Locks the animation, preventing it from changing.
     */
    public void lock() {
        this.isLocked = true; // Set isLocked to true
    }

    /**
     * Unlocks the animation, allowing it to change.
     */
    public void unlock() {
        this.isLocked = false; // Set isLocked to false
    }

    private int x = 380; // x-coordinate of the character
    private int y = 240; // y-coordinate of the character

    @Override
    public int getX() {
        return x; // Return the x-coordinate
    }

    @Override
    public int getY() {
        return y; // Return the y-coordinate
    }

    @Override
    public void setLocation(int x, int y) {
        if (!isLocked) { // Check if the animation is not locked
            flipHorizontally = this.x > x; // Determine if the animation should be flipped
            this.x = x; // Update x-coordinate
            this.y = y; // Update y-coordinate
            repaint(); // Repaint the panel with the new position
        }
    }

    private AnimationState currentState = AnimationState.IDLE; // Current animation state

    /**
     * Constructor for the Animation class.
     * Initializes the animation and sets up the scheduled frame updates.
     */
    public Animation() {
        loadAnimations(); // Load the animation frames

        if (idleFrames == null || idleFrames.length == 0) {
            throw new IllegalStateException("Failed to load idle animation frames."); // Check for idle frames
        }

        currentAnimation = idleFrames; // Set current animation to idle

        // Increase the panel size to allow more space for movement
        int panelWidth = 1600; // Width of the panel
        int panelHeight = 900; // Height of the panel
        setPreferredSize(new Dimension(panelWidth, panelHeight)); // Set preferred size

        scheduler = Executors.newScheduledThreadPool(1); // Create a scheduled executor
        scheduler.scheduleAtFixedRate(() -> {
            currentFrame = (currentFrame + 1) % currentAnimation.length; // Update current frame
            repaint(); // Repaint on the EDT
        }, 0, 100, TimeUnit.MILLISECONDS); // Update every 100ms (10 FPS)

        this.setOpaque(false); // Set panel to be transparent
        // Start with idle animation
        this.setAnimation(AnimationState.IDLE); // Set initial animation state
    }

    /**
     * Loads the animations for the character.
     * This method must be overridden in subclasses to provide specific animations.
     */
    public void loadAnimations() {
        System.out.println("loadAnimations method must be overridden"); // Placeholder message
    }

    /**
     * Loads frames from a sprite sheet.
     *
     * @param path       The path to the sprite sheet file.
     * @param frameCount The number of frames to load.
     * @param frameWidth The width of each frame.
     * @param frameHeight The height of each frame.
     * @return An array of BufferedImages representing the loaded frames.
     * @throws IOException If the file cannot be found or read.
     */
    protected BufferedImage[] loadFrames(String path, int frameCount, int frameWidth, int frameHeight) throws IOException {
        File file = new File(path); // Create a file object
        if (!file.exists()) {
            throw new IOException("File not found: " + path); // Check if file exists
        }
        BufferedImage spriteSheet = ImageIO.read(file); // Read the sprite sheet
        BufferedImage[] frames = new BufferedImage[frameCount]; // Initialize frames array

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight); // Load each frame
        }
        return frames; // Return the loaded frames
    }

    /**
     * Sets the current animation state.
     *
     * @param state The desired animation state.
     */
    public void setAnimation(AnimationState state) {
        if (!isLocked) { // Check if the animation is not locked
            this.framesDone = new boolean[4]; // Reset frames done
            if (currentState != state) { // Check if the state has changed
                currentState = state; // Update current state
                currentFrame = 0; // Reset to first frame
                switch (state) {
                    case IDLE -> currentAnimation = idleFrames; // Set idle animation
                    case ATTACK -> currentAnimation = attackFrames; // Set attack animation
                    case DEATH -> currentAnimation = deathFrames; // Set death animation
                    case HURT -> currentAnimation = hurtFrames; // Set hurt animation
                    case WALK -> currentAnimation = walkFrames; // Set walk animation
                }
                // Fallback to idle if the desired animation is null
                if (currentAnimation == null || currentAnimation.length == 0) {
                    currentAnimation = idleFrames; // Fallback to idle animation
                }
            }
        }
    }

    /**
     * Returns the JPanel representing this animation.
     *
     * @return The JPanel for this animation.
     */
    public JPanel getPanel() {
        return this; // Return the JPanel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass method

        if (currentAnimation == null || currentAnimation.length == 0) {
            currentAnimation = idleFrames; // Fallback to idle animation if current is null
        }

        // Get panel size
        int panelWidth = 192; // Width of the panel
        int panelHeight = 192; // Height of the panel

        if (this.currentAnimation == deathFrames) { // Check if current animation is death
            if (!this.framesDone[currentFrame]) { // Check if the current frame is done
                this.framesDone[currentFrame] = true; // Mark frame as done
                g.drawImage(currentAnimation[currentFrame], this.x, this.y, panelWidth, panelHeight, this); // Draw current frame
            } else {
                g.drawImage(currentAnimation[3], this.x, this.y, panelWidth, panelHeight, this); // Draw last frame if done
            }

        } else {
            if (flipHorizontally) { // Check if the image should be flipped
                // Flip the image horizontally by using Graphics2D transformation
                Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
                // Apply horizontal flip by scaling with -1 on the X-axis
                g2d.translate(panelWidth, 0); // Move the origin to the right side of the image
                g2d.scale(-1, 1); // Flip horizontally
                // Dispose of the Graphics2D object
                // Draw the flipped image
                g2d.drawImage(currentAnimation[currentFrame], -this.x, this.y, panelWidth, panelHeight, this); // Draw the flipped image
                g2d.dispose(); // Dispose of the Graphics2D object
            } else {
                // Draw the current frame of the character, scaled to panel size
                g.drawImage(currentAnimation[currentFrame], this.x, this.y, panelWidth, panelHeight, this); // Draw current frame
            }
        }
    }

}
