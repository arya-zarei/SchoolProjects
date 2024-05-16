/**
 * The Record class represents the nodes of the binary search tree, leaf nodes
 * have a null record and internal nodes store data in their record.
 * 
 * @author Arya Zarei
 * 2210B Assignment 4
 */

public class Record {
    private Key theKey; // private key instance variable
    private String data; // private data instance variable

    /**
     * initalize a new record object
     * 
     * @param k       initialize the key of the node
     * @param theData initialize the data of the node
     */
    public Record(Key k, String theData) {
        this.theKey = k;
        this.data = theData;
    }

    public Key getKey() {
        return theKey; // returns the key of the record of a node
    }

    public String getDataItem() {
        return data; // returns the data of the record of a node
    }
}