/**
 * The BSTNode class represents nodes of the Binary Search Tree. This
 * class returns and updates the records of a node as well as
 * returning and updating a nodes parent, left child and right child. The
 * BSTNode also checks if a node is a leaf where a leaf is a node that
 * has a null record and 2 null children.
 * 
 * @author Arya Zarei
 * 2210B Assignment 4
 */
public class BSTNode {
    private Record item; // private Record object instance variable
    private BSTNode leftChild; // private left child instance variable
    private BSTNode rightChild; // private right child instance variable
    private BSTNode parent; // private parent node instance variable

    /**
     * @param item initializes the Record of a node: sets both children and parent 
     * of a node in the Binary Search Tree(BST) to null.
     */
    public BSTNode(Record item) {
        this.item = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    public Record getRecord() {
        return item; // returns the record of a node
    }

    public void setRecord(Record item) {
        this.item = item; // updates record of a node
    }

    public BSTNode getLeftChild() {
        return leftChild; // returns left child of a node
    }

    public BSTNode getRightChild() {
        return rightChild; // returns right child of a node
    }

    public BSTNode getParent() {
        return parent; // returns parent of a node
    }

    public void setLeftChild(BSTNode u) {
        leftChild = u; // updates left child of a node
    }

    public void setRightChild(BSTNode u) {
        rightChild = u; // updates right child of a node
    }

    public void setParent(BSTNode u) {
        parent = u; // updates parent of a node
    }

    /**
     * @return true is node is a leaf checks if both children 
     * are null (leaf is a node with a null record)
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
}