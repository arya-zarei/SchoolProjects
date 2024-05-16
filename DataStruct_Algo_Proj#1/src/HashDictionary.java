
/**
 * The HashDictionary class implements the dictionary for all the configurations and scores using a hash table with
 * separate chaining. Consisting of a LinkedList for the hash table and LinkedLists for all the chains of the table.
 * 
 * @author Arya Zarei
 * 2210B Assignment 2: Tic-Tac-Toe
 */

import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {
    private LinkedList<Data>[] table; // Hash table
    private int size; // size of Hash table

    /**
     * Constructor returns an empty hash table
     * 
     * @param size is the size of the hash table
     */
    public HashDictionary(int size) {
        this.size = size;
        table = new LinkedList[size];
        /**
         * iterate through the table to create an new LinkedList for each bucket of the
         * hash table for separate chaining
         */
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    /**
     * Adds record to the hash table and throws exception if record already exists
     * 
     * @param record is the record to be added to the table
     * @throws DictionaryException if there is a duplicate Data object record in the
     *                             separate chain
     */
    public int put(Data record) throws DictionaryException {
        // calculate the index for the record using private hash function
        int index = hash(record.getConfiguration());
        // linked list at position of record for separate chaining
        LinkedList<Data> chain = table[index];
        // for each loop iterates to check for duplicates in chain
        for (Data data : chain) {
            if (data.getConfiguration().equals(record.getConfiguration())) {
                throw new DictionaryException(); // throws exception if duplicate is found
            }
        }
        chain.add(record); // add record to chain

        if (chain.size() > 1) {
            return 1; // return 1 if collision occurred
        } else {
            return 0; // return 0 if the chain was empty
        }
    }

    /**
     * private polynomial hash function for the given key
     * 
     * @param key is the board configuration
     */

    private int hash(String key) {
        int hash = 0;

        // iterate through each character of the configuration
        for (int i = 0; i < key.length(); i++) {
            /**
             * multiply the previous hash by the prime #811 and add the ASCII value of 'X'
             * or 'O' for each entry on the board and then modulo by the table size to
             * create a polynomial hash function
             */

            hash = (811 * hash + key.charAt(i)) % size;
        }
        return hash;
    }

    /**
     * removes record with given configuration from hash table
     * 
     * @param config is the configuration from hash table
     * @throws DictionaryException if no record in hash table stores config
     */
    public void remove(String config) throws DictionaryException {
        // calculate the index for the record using private hash function
        int index = hash(config);
        // linked list at position of record for separate chaining
        LinkedList<Data> chain = table[index];

        boolean removed = false; // boolean to track removal
        for (Data data : chain) { // for each statement to iterates through separate chain
            if (data.getConfiguration().equals(config)) { // checks if data is the desired removal
                chain.remove(data); // removes record from chain
                removed = true; // changes boolean to removed
                break;
            }
        }

        if (!removed) { // if no record was remove throw an exception
            throw new DictionaryException();
        }
    }

    /**
     * @param config is the configuration from hash table
     * @return score of the record given the key configuration or -1 if config is
     *         not in the hash table
     */
    public int get(String config) {
        // calculate the index for the record using private hash function
        int index = hash(config);
        // linked list at position of record for separate chaining
        LinkedList<Data> chain = table[index];
        // for each statement to iterates through separate chain
        for (Data data : chain) {
            if (data.getConfiguration().equals(config)) { // checks if data is the desired configuration
                // finds the desired record and returns the score
                return data.getScore();
            }
        }
        return -1; // if the specified configuration was not found return -1

    }

    /**
     * @return number of Data objects stored in the separate chaining hash table
     */
    public int numRecords() {
        // count the total number of records
        int count = 0;

        // iterate through the hash table
        for (LinkedList<Data> chain : table) {
            count += chain.size(); // adds the size of each separate chain
        }
        return count;
    }
}