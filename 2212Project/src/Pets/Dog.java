package Pets;

import Animation.Animation;

/**
 * Represents a Dog, which is a specific type of Pet.
 * This class extends the Pet class and provides specific behavior and attributes for dogs.
 * 
 * <p>
 * A Dog object is initialized with a name and an animation. The name is set to "Dog" by default.
 * The animation defines how the dog behaves visually in the application.
 * </p>
 * @author Mark
 */
public class Dog extends Pet {
    
    /**
     * Constructs a Dog instance with the specified animation.
     *
     * @param animation the Animation object that defines the dog's animation
     *                  and visual behavior in the application.
     */
    public Dog(Animation animation) {
        super("Dog", animation); // Pass the Animation object and the name
    }

    // Additional Dog-specific methods, if any
}
