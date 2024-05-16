/**
 * The Configurations class tracks the Tic-Tac-Toe game using the hash
 * table, storing all the configurations and their scores to manage the game
 * and calculate the evaluations of the game.
 * 
 * @author Arya Zarei
 *         2210B Assignment 2: Tic-Tac-Toe
 */
public class Configurations {

    private char[][] board; // 2d array of the game board
    private int boardSize; // game board dimension
    private int lengthToWin; // length of consecutive pieces needed to win

    /**
     * Every entry of the board initially store a space.
     * Every entry of the board will store 'X', 'O', or " ".
     * 
     * @param boardSize
     * @param lengthToWin
     * @param maxLevels   maximum depth of possible outcomes (increases the
     *                    difficulty)
     * 
     */
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {

        this.board = new char[boardSize][boardSize]; // board has row and column dimensions of boardSize
        this.boardSize = boardSize;
        this.lengthToWin = lengthToWin;

        // iterate through board and make each entry a space
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Selection of a prime number hash table size between 6000-10000
     * 
     * @return an empty hash table size
     */
    public HashDictionary createDictionary() {
        int tableSize = 8311; // chosen prime number size of 8311
        return new HashDictionary(tableSize);
    }

    /**
     * checks if string representation of the board is in the hash table
     * 
     * @param hashTable is the hash table of all the records entered from the game
     * @return configuration score if string exists in hash table or -1 if not
     */
    public int repeatedConfiguration(HashDictionary hashTable) {
        // calls helper method boardToString to create a string of the board
        String boardString = boardToString(board);
        // gets score of configuration(boardString)
        int score = hashTable.get(boardString);

        if (score != -1) {
            return score; // if hash table key (configuration) was found
        } else {
            return -1; // if no hash table key (configuration) was found
        }

    }

    /**
     * @param board is the 2d array of the game board
     *              board goes through 2d array from top to bottom and left to right
     * @return the string of every entry to the board
     */
    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) { // rows
            for (int j = 0; j < boardSize; j++) { // columns
                sb.append(board[i][j]); // adds piece to board
            }
        }
        return sb.toString(); // returns string of board
    }

    /**
     * represents the board as a string and inserts this string and score into the
     * hashDictionary
     * 
     * @param hashDictionary
     * @param score
     */
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        // convert board to string
        String boardString = boardToString(board); // string of board
        // inserts key and value into hash dictionary
        hashDictionary.put(new Data(boardString, score));
    }

    // adds the character symbol to the given board position
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // returns true is board position is empty (' '), false otherwise
    public boolean squareIsEmpty(int row, int col) {

        return board[row][col] == ' ';
    }

    /**
     * @returns true if there is a continuous sequence of the length to win
     *          this sequence can either be vertical, horizontal or diagonal
     * @param symbol is the specific sequence that is checked for (either 'X' or
     *               'O')
     */
    public boolean wins(char symbol) {
        // Check for horizontal win using private helper
        if (checkHorizontal(symbol)) {
            return true;
        }
        // Check for vertical win using private helper
        if (checkVertical(symbol)) {
            return true;
        }
        // Check for diagonal win using private helper
        if (checkDiagonal(symbol)) {
            return true;
        }
        // No one has won
        return false;
    }

    // checks for horizontal sequence for the given character
    private boolean checkHorizontal(char symbol) {
        for (int row = 0; row < boardSize; row++) { // goes through one row at a time
            int count = 0; // resets counter for each row
            for (int col = 0; col < boardSize; col++) { // checks each column of the row
                if (board[row][col] == symbol) {
                    count++; // increments count by 1 if symbol is found
                } else {
                    count = 0; // resets count so only consecutive symbols are tracked
                }
                // If continuous sequence of length to win is found, return true
                if (count >= lengthToWin) {
                    return true;
                }
            }
        } // if no horizontal sequence is found return false
        return false;
    }

    // checks for vertical sequence for the given character (reversed nested
    // for-loop as horizontal, same count method)
    private boolean checkVertical(char symbol) {
        for (int col = 0; col < boardSize; col++) { // goes through one column at a time
            int count = 0; // resets counter for each column
            for (int row = 0; row < boardSize; row++) { // checks each row of the column
                if (board[row][col] == symbol) {
                    count++;
                } else {
                    count = 0;
                }

                if (count >= lengthToWin) {
                    return true;
                }
            }
        }
        return false;
    }

    // checks for diagonal sequences for the given character
    private boolean checkDiagonal(char symbol) {
        /**
         * Check main diagonal: top-left to bottom-right
         * Due to the possibility that the lengthToWin is less than the board we must
         * check every diagonal pattern of lengthToWin that for could fit on the board
         * we use boardSize - lengthToWin to avoid out of board error for the starting
         * position of the diagonal
         */
        for (int row = 0; row <= boardSize - lengthToWin; row++) {
            for (int col = 0; col <= boardSize - lengthToWin; col++) {
                boolean diagonalSequence = true; // boolean to track sequence
                for (int diagonal = 0; diagonal < lengthToWin; diagonal++) {
                    if (board[row + diagonal][col + diagonal] != symbol) {
                        // position: [0][0], [1][1] ... [lengthToWin-1][lengthToWin-1]
                        diagonalSequence = false; // if another symbol occurs than
                        break;
                    }
                }
                if (diagonalSequence) {
                    return true; // return true is diagonalSequence of at least length to win was found
                }
            }
        }

        // Check reverse direction diagonal (top-right to bottom-left)
        for (int row = 0; row <= boardSize - lengthToWin; row++) {
            for (int col = lengthToWin - 1; col < boardSize; col++) {
                boolean reverseDiagonalSequence = true; // boolean to track sequence
                for (int diagonal = 0; diagonal < lengthToWin; diagonal++) {
                    if (board[row + diagonal][col - diagonal] != symbol) {
                        // position: [0][lengthToWin - 1], [1][lengthToWin - 2] ... [lengthToWin-1][0]
                        reverseDiagonalSequence = false;
                        break;
                    }
                }
                if (reverseDiagonalSequence) {
                    return true;
                }
            }
        }

        // If no winning diagonal sequences are found, return false
        return false;
    }

    // return true if board has no empty positions left and no player has won
    public boolean isDraw() {
        // iterates through board
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == ' ') {
                    return false; // if any empty position is found, the game is not a draw
                }
            }
        }

        if (wins('X') || wins('O')) {
            return false; // if a player has won, the game is not a draw
        }

        // if no empty positions are left and no player has won, the game is a draw
        return true;
    }

    /**
     * @return board score evaluation for game
     * 
     *         3: if the computer has won
     *         0: if the human player has won
     *         2: if the game is a draw
     *         1: if the game is still undecided (empty positions in board and no
     *         player has won yet)
     */
    public int evalBoard() {
        // Check if the computer player (symbol 'O') has won
        if (wins('O')) {
            return 3;
        }

        // Check if the human player (symbol 'X') has won
        else if (wins('X')) {
            return 0;
        }

        // Check if the game is a draw
        else if (isDraw()) {
            return 2;
        }

        // if none of the above conditions are met, the game is still undecided
        else {
            return 1;
        }
    }
}