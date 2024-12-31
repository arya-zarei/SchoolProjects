package Animation;

/**
 * Enum representing the various states of an animation.
 * @author Mark
 */
public enum AnimationState {
    /** The idle state, where no action is being performed. */
    IDLE,
    
    /** The state representing an attack action. */
    ATTACK,
    
    /** The state representing a death action. */
    DEATH,
    
    /** The state representing a hurt action. */
    HURT,
    
    /** The state representing a walking action. */
    WALK
}