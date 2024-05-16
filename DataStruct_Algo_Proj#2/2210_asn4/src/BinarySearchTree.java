/**
 * The BinarySearchTree class implements a BinarySearchTree for all
 * the Records of the nodes of the Interface. This class gets, removes, inserts,
 * returns the predecessor and successor of a node as well as the smallest and
 * largest node in the BinarySearchTree.
 * 
 * @author Arya Zarei
 * 2210B Assignment 4
 */

public class BinarySearchTree {
    private BSTNode root; // private instance variable for root of BinarySearchTree(BST)

    // Constructor method creates a leaf node to create a base BST
    public BinarySearchTree() {
        this.root = new BSTNode(null); // Creating a leaf node as the root of the tree
    }

    public BSTNode getRoot() {
        return root; // returns the root node of the BST
    }

    /**
     * @param r root of tree
     * @param k key of node to find
     * @return the node of the key to find in the BST starting at root r
     */
    public BSTNode get(BSTNode r, Key k) {
        if (r == null || k == null || r.getRecord() == null) {
            return null; // base case for empty tree, null key, or leaf node
        }

        // compares key to current node
        int compare = k.compareTo(r.getRecord().getKey());

        if (compare == 0) {
            return r; // if current node equals key return node
        } else if (compare < 0) {
            return get(r.getLeftChild(), k); // if key is smaller than current node check left tree
        } else {
            return get(r.getRightChild(), k); // if key is larger than current node check right tree
        }
    }

    /**
     * @ param r root node of tree
     * 
     * @param d record to insert
     * @throws DictionaryException if node already exists in tree throw exception
     * 
     * Insert record d into the appropriate spot in the BST based on the key of 
     * the record d. Using recursion to traverse the BST to and insert the
     * new record into an appropriate leaf node.
     */
    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (r == null || r.getRecord() == null) {
            // Base case: if empty tree or leaf node
            r.setRecord(d); // insert record in this null node position

            // create two new null record leaf children for the newly inserted leaf node
            r.setLeftChild(new BSTNode(null));
            r.setRightChild(new BSTNode(null));
        }

        else {
            // compares key of new record to key of current node
            int comparison = d.getKey().compareTo(r.getRecord().getKey());

            if (comparison == 0) {
                // Key already exists in the tree, throw DictionaryException
                throw new DictionaryException("Key already exists in the tree");
            }

            else if (comparison < 0) { // if new key is smaller than current node

                // if left child is null
                if (r.getLeftChild().getRecord() == null) {

                    r.getLeftChild().setRecord(d); // insert record into left child of current node
                    r.getLeftChild().setParent(r); // set parent of the new node to the current node

                    // create two new null record leaf children for newly inserted node
                    r.getLeftChild().setLeftChild(new BSTNode(null));
                    r.getLeftChild().setRightChild(new BSTNode(null));
                }

                else { // if left child of current is not null
                       // recursive call to compare new node key with the key of left child of current
                    insert(r.getLeftChild(), d);
                }
            }

            else { // if new key is larger than current node
                   // if right child is null
                if (r.getRightChild().getRecord() == null) {

                    r.getRightChild().setRecord(d); // insert record into right child of current node
                    r.getRightChild().setParent(r); // set parent of the new node to the current node

                    // create two new null record leaf children for newly inserted node
                    r.getRightChild().setLeftChild(new BSTNode(null));
                    r.getRightChild().setRightChild(new BSTNode(null));
                }

                else { // if right child of current is not null
                       // recursive call to compare new node key with the key of right child of current
                    insert(r.getRightChild(), d);
                }
            }
        }
    }

    /**
     * @param r root of BST tree
     * @param k key of node to remove
     * @throws DictionaryException throws exception if node does not exist in tree
     * 
     * Removes a node from the BST given the root of the tree and key to get and remove and then
     * reconstruct the BST after removal to maintain the BST structure.
     */
    public void remove(BSTNode r, Key k) throws DictionaryException {

        BSTNode nodeToRemove = get(r, k); // Find the node to remove given the key and root

        if (nodeToRemove == null || nodeToRemove.getRecord() == null) {
            // Throw an exception if the node was not found in the tree or is a leaf node
            throw new DictionaryException("No record found with the given key.");
        }

        BSTNode parent = nodeToRemove.getParent(); // Get the parent of the node to remove
        // Check if node to remove has two null record leaf children
        if ((nodeToRemove.getLeftChild()) == null && (nodeToRemove.getRightChild() == null)) {

            if (parent != null) { // If not root
                nodeToRemove.setLeftChild(null); // remove null record left child
                nodeToRemove.setRightChild(null); // remove null record right child
                nodeToRemove.setRecord(null); // set record of node to null (turn node into a leaf)

            }

            else {
                nodeToRemove.setLeftChild(null); // remove null record root left child
                nodeToRemove.setRightChild(null);// remove null record root right child
                nodeToRemove = null; // set root to null
            }
        }

        else if (nodeToRemove.getLeftChild().isLeaf() || nodeToRemove.getRightChild().isLeaf()) {
            // If the node to remove has one leaf (null record node)
            BSTNode child;
            // Check which child of the node to remove is leaf
            if (!nodeToRemove.getLeftChild().isLeaf()) {
                child = nodeToRemove.getLeftChild();
            } else {
                child = nodeToRemove.getRightChild();
            }

            // Check if the node to remove is not root
            if (parent != null) {
                if (parent.getLeftChild() == nodeToRemove) {
                    // Replace node to remove with child
                    parent.setLeftChild(child);
                } else {
                    /// Replace node to remove with child
                    parent.setRightChild(child);
                }
            } else {
                root = child; // Replace root to remove with child
            }
        }

        else { // If the node to remove has two children

            // Find the smallest node of the right subtree
            BSTNode smallestRightTree = smallest(nodeToRemove.getRightChild());
            // Replace the node to remove with the smallest node of the right subtree
            nodeToRemove.setRecord(smallestRightTree.getRecord());

            // Recursively call the method with the replaced node to maintain the BST structure
            remove(smallestRightTree, smallestRightTree.getRecord().getKey());
        }

    }

    /**
     * @param r root of BST
     * @param k key of BST
     * @return successor of key parameter
     * 
     * Given the key and the root of a BST the successor method gets the
     * node in the BST.Then the successor finds the next largest key in the tree by 
     * first checking if a right subtree to the node exists and finding the smallest 
     * node in the right subtree and if not going up the parent until the first key 
     * node larger than the key parameter is found. If no successor exists null is returned.
     */
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode node = get(r, k); // Find the node containing the key k

        if (node == null) {
            return null; // Return null if successor does not exist (no greater key value)
        }

        if (!node.getRightChild().isLeaf()) {
            // If the node has a right subtree, find the leftmost
            // (smallest) node in the right subtree
            return smallest(node.getRightChild());
        }

        else {
            // If the node has no right subtree, find the ancestor of the node
            BSTNode parent = node.getParent(); // Get the parent of the node
            // while parent key is less than node key

            while (parent != null && parent.getRecord().getKey().compareTo(k) < 0) {
                // Iterate up the tree until we find a node whose key is greater than k
                parent = parent.getParent();
            }
            return parent; // Return the successor
        }
    }

    /**
     * @param r root of BST
     * @param k key of BST
     * @return predecessor of key parameter
     * 
     * Given the key and the root of a BST the predecessor method gets the
     * node in the BST. Then the predecessor finds the next smallest key in
     * the tree by first checking if a left subtree to the node exists
     * and finding the largest node in the left subtree and if not going
     * up the parent until the first key node smaller than the key parameter
     * is found. If no predecessor exists null is returned
     */
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode node = get(r, k); // Find the node containing the key k

        if (node == null) {
            return null; // Return null if predecessor doesn't exist (no smaller key value)
        }

        if (!node.getLeftChild().isLeaf()) {
            // If the node has a left subtree, find the rightmost node in the left subtree
            return largest(node.getLeftChild());
        } else {
            // If the node has no left subtree, find the ancestor of the node
            BSTNode parent = node.getParent(); // Get the parent of the node

            while (parent != null && parent.getRecord().getKey().compareTo(k) > 0) {
                // Iterate up the tree until we find a node whose key is less than k
                parent = parent.getParent();
            }
            return parent; // Return the predecessor
        }
    }

    /**
     * @param r root of tree or subtree of BST
     * @return the smallest node in the tree starting at r as the root
     */
    public BSTNode smallest(BSTNode r) {
        if (r == null || r.getRecord() == null) {
            return null; // return null if tree or node is empty
        }

        BSTNode current = r; // current node starts at root

        while (!current.getLeftChild().isLeaf()) {
            // traverse to the left node mode
            current = current.getLeftChild();
        }
        return current; // return leftmost node of tree (smallest)
    }

    /**
     * @param r root of tree or subtree of BST
     * @return the largest node in the tree starting at r as the root
     */
    public BSTNode largest(BSTNode r) {
        if (r == null || r.getRecord() == null) {
            return null;// return null if tree or node is empty
        }

        BSTNode current = r; // current node starts at root

        while (!current.getRightChild().isLeaf()) {
            // traverse to rightmost node of tree
            current = current.getRightChild();
        }
        return current; // return rightmost node of tree (largest)
    }
}