package Pets;

import Animation.Animation;

/**
 * The Cat class represents a specific type of Pet, which is a cat.
 * It extends the Pet class and provides specific behavior or attributes
 * related to cats.
 * @author Mark
 */
public class Cat extends Pet {
    /**
     * Constructs a new Cat instance with the specified animation.
     *
     * @param animation the Animation object that defines the cat's animation
     */
    public Cat(Animation animation) {
        super("Cat", animation); // Pass the Animation object and the name
    }

    // Additional Dog-specific methods, if any
}
