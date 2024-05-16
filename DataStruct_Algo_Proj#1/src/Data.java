/**
 * Data class represents the records stored in the HashDictionary.
 * Each record consists of two parts: the configuration of the board as the key
 * and the integer score of the board as the value.
 * 
 * @author Arya Zarei
 *         2210B Assignment 2: Tic-Tac-Toe
 */
public class Data {

    private String config; // key string of board
    private int score; // score integer value

    // Initialize a new Data object with the specified configuration and score.
    public Data(String config, int score) {
        this.config = config;
        this.score = score;
    }

    // return the configuration stored in this data object
    public String getConfiguration() {
        return config;
    }

    // return the score of the configuration
    public int getScore() {
        return score;
    }
}