/**
 * This BSTDictionary class implements the ordered dictionary using a binary
 * search tree from the BinarySearchTree class. The information of the
 * dictionary is stored in the internal nodes of the BST as leaves have null
 * records. The BSTDictionary calls the BinarySearchTree methods to implement
 * the dictionary.
 * 
 * @author Arya Zarei
 * 2210B Assignment 4
 */
public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree BinarySearch; // private BinarySearchTree instance variable

    public BSTDictionary() {
        this.BinarySearch = new BinarySearchTree(); // creates new BST
    }

    /**
     * The get methods calls the BinarySearchTree to get the node
     * of the key in the parameter and returns the record if it
     * exists and null if there is no key or node is a leaf.
     */
    public Record get(Key k) {
        BSTNode node = BinarySearch.get(BinarySearch.getRoot(), k);

        if (node != null && node.getRecord() != null) {
            return node.getRecord();
        }

        else {
            return null;
        }
    }

    /**
     * The put method puts a record into the BST by calling
     * the insert method from BinarySearchTree and throws a
     * dictionary exception if the record already exists in the tree.
     */
    public void put(Record d) throws DictionaryException {
        try {
            BinarySearch.insert(BinarySearch.getRoot(), d); // insert record
        }

        catch (DictionaryException e) { // if record already exists in BST
            throw new DictionaryException("A record with the same key already exists.");
        }
    }

    /**
     * The remove method removes a record from the BST by
     * calling the remove method from BinarySearchTree
     * and throws a dictionary exception if the key to remove
     * does not exist
     */
    public void remove(Key k) throws DictionaryException {
        try {
            BinarySearch.remove(BinarySearch.getRoot(), k); // remove key
        }

        catch (DictionaryException e) { // if key does not exist in BST
            throw new DictionaryException("No record found with the given key.");
        }
    }

    /**
     * The successor method returns the successor of the key
     * even if the key does not exist in the tree. If the key exists in the tree
     * the successor will call the successor method in BinarySearchTree. If the key
     * does not exist in the tree the successor method will call the private helper
     * method to find the closest key larger than the key value, replicating the
     * successor. If no closest successor exists then null will be returned,
     * meaning there is no possible successor.
     */
    public Record successor(Key k) {

        BSTNode exist = BinarySearch.get(BinarySearch.getRoot(), k); // checks if key exists in BST

        if (exist == null) { // if key doesnt exist
            // call private helper to find the successor if key was in BST
            BSTNode closest = findClosestSuccessor(BinarySearch.getRoot(), k);

            if (closest != null) {
                return closest.getRecord(); // reurn the record of the hypothetical successor
            }

            else {
                return null; // return null if no possible successor (key is largest)
            }
        }

        else {
            // if key is in tree call successor method from BinarySearchTree
            BSTNode successorNode = BinarySearch.successor(BinarySearch.getRoot(), k);

            // if successor exists return record of successor
            if (successorNode != null) {
                return successorNode.getRecord();
            }

            // if the node has no possible successor (key is largest) return null
            else {
                return null;
            }
        }
    }

    /**
     * @param root of BST
     * @param k    key to find hypothetical successor
     * @return return the closest larger key after key k
     */
    private BSTNode findClosestSuccessor(BSTNode root, Key k) {

        // Start searching from the root of the tree
        BSTNode current = root;
        BSTNode closestLarger = null; // initalize successor tracker to null

        while (!current.isLeaf()) { // iterate through BST

            int compare = k.compareTo(current.getRecord().getKey()); // compare key of current to key of successor node

            if (compare < 0) {
                // If k is smaller than the current node's key, move to the left subtree
                closestLarger = current; // Update closest larger node to the current node
                current = current.getLeftChild(); // traverse left subtree to find a closer in difference larger key
            }

            else {
                // If key of k is larger than the current node's key, move to the right subtree
                current = current.getRightChild();
            }
        }

        // Return the closest larger node found during traversal
        return closestLarger;
    }

    /**
     * The predecessor method returns the predecessor of the key
     * even if the key does not exist in the tree. If the key exists in the tree
     * the predecessor will call the predecessor method in BinarySearchTree. If the
     * key does not exist in the tree the predecessor method will call the private
     * helper method to find the closest key smaller than the key value, replicating the
     * predecessor. If no closest predecessor exists then null will be returned,
     * meaning there is no possible predecessor.
     */
    public Record predecessor(Key k) {

        // check if key k exists in BST
        BSTNode exist = BinarySearch.get(BinarySearch.getRoot(), k);

        if (exist == null) { // if the key does not exist in the BST
            // call the private helper method to find the hypothetical predecessor
            BSTNode closest = findClosestPredecessor(BinarySearch.getRoot(), k);

            // if no hypothetical predecessor exists (key is the smallest)
            if (closest == null) {
                return null; // return null
            }

            else {
                return closest.getRecord(); // return record of hypothetical predecessor
            }
        }

        else { // if key exists in BST
               // call predecessor method from BinarySearchTree
            BSTNode node = BinarySearch.predecessor(BinarySearch.getRoot(), k);

            if (node != null) {
                return node.getRecord(); // return record of predecessor
            }

            else {
                return null; // return null (key is the smallest)
            }

        }
    }

    /**
     * @param root of BST
     * @param k    key to find hypothetical predecessor
     * @return return the closest smaller key after key k
     */
    private BSTNode findClosestPredecessor(BSTNode root, Key k) {

        // Start searching from the root of the tree
        BSTNode current = root;
        BSTNode closestSmaller = null; // initalize predecessor tracker to null

        while (!current.isLeaf()) { // iterate through BST
            // compare key of k to key of current node
            int compare = k.compareTo(current.getRecord().getKey());

            if (compare < 0) {
                // If k is smaller than the current node's key, move to the left subtree
                current = current.getLeftChild();
            } else {
                // If k is larger than the current node's key, update the closest node
                closestSmaller = current; // Update closest to the current node
                current = current.getRightChild(); // move to the right subtree to check for a closer smaller node
            }
        }

        // Return the closest node found during traversal
        return closestSmaller;
    }

    // Return the smallest record in the BST
    public Record smallest() {
        // calls the smallest method from BinarySearchTree
        BSTNode node = BinarySearch.smallest(BinarySearch.getRoot());

        if (node != null) {
            return node.getRecord(); // returns smallest record if exists
        } else {
            return null; // returns null if smallest record does not exist
        }
    }

    // Return largets record in the BST
    public Record largest() {
        // calls the largest method from BinarySearchTree
        BSTNode node = BinarySearch.largest(BinarySearch.getRoot());

        if (node != null) {
            return node.getRecord(); // returns largest record if exists
        } else {
            return null; // returns null if largest record does not exist
        }
    }
}
