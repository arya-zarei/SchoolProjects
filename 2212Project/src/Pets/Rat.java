package Pets;

import Animation.Animation;

/**
 * Represents a Rat, which is a type of Pet.
 * This class extends the Pet class and provides specific behavior for a Rat.
 * @author Mark
 */
public class Rat extends Pet {
    
    /**
     * Constructs a new Rat instance with the specified animation.
     *
     * @param animation the Animation object that defines the Rat's animation
     */
    public Rat(Animation animation) {
        super("Rat", animation); // Pass the Animation object and the name
    }

    // Additional Dog-specific methods, if any
}
