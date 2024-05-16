/**
 * The key class represents the key of data items stored in the binary search
 * tree. In the Interface class the Key class is used to get the label and
 * type of the data stored in text files.
 * 
 * @author Arya Zarei
 * 2210B Assignment 4
 */

public class Key {
    private String label; // private label instance variable
    private int type; // private type instance variable

    /**
     * @param theLabel initializes key label
     * @param theType  initializes key type
     */
    public Key(String theLabel, int theType) {
        this.label = theLabel.toLowerCase(); // Convert label to lowercase (case in-sensitive)
        this.type = theType;
    }

    public String getLabel() {
        return label; // returns the label of a key
    }

    public int getType() {
        return type; // returns the type of a key
    }

    /**
     * @param k
     * @return this compare method compares two key objects first by their label
     * lexicographically then by their type returning 0 if the two keys
     * are equal, -1 if this key object is smaller than k and 1 if this key
     * object is larger than k
     */
    public int compareTo(Key k) {
        // Compare labels lexicographically
        int labelComparison = this.label.compareTo(k.getLabel());

        if (labelComparison == 0) {
            // If labels are equal, compare types
            if (this.type == k.getType()) {
                return 0; // Key types are equal
            } else if (this.type < k.getType()) {
                return -1; // This key type is smaller
            } else {
                return 1; // This key type is larger
            }
        } else {
            return labelComparison; // Labels are different
        }
    }
}