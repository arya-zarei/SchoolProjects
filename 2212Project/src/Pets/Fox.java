package Pets;

import Animation.Animation;

/**
 * Represents a Fox, which is a specific type of Pet.
 * This class extends the Pet class and provides specific behavior and attributes for foxes.
 * 
 * <p>
 * A Fox object is initialized with a name and an animation. The name is set to "Fox" by default.
 * The animation defines how the fox behaves visually in the application.
 * </p>
 * @author Mark
 */
public class Fox extends Pet {
    /**
     * Constructs a Fox instance with the specified animation.
     *
     * @param animation the Animation object that defines the fox's animation
     *                  and visual behavior in the application.
     */
    public Fox(Animation animation) {
        super("Fox", animation); // Pass the Animation object and the name
    }

    // Additional Fox-specific methods, if any
}
