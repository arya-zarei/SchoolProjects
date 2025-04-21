// GameLogic.h
#ifndef GAMELOGIC_H
#define GAMELOGIC_H

#include <random> // Provides random number generation
#include <vector> // Include vector for dynamic array support

enum class SquareType // Enumeration for square types
{
    Empty,  // Represents a square with no adjacent mines
    Number, // Represents a square with adjacent mines
    Mine    // Represents a mine square
};

enum class SquareState // Enumeration for square states
{
    Hidden,      // Square is hidden
    Revealed,    // Square is revealed
    Flagged,     // Square is flagged
    QuestionMark // Square is marked with a question
};

class GameLogic
{
public:
    GameLogic(int rows, int cols, int mineCount);             // Constructor
    void startNewGame();                                      // Starts a new game
    bool placeMines(int firstRow, int firstCol);              // Places mines on the board
    void calculateAdjacentMines();                            // Calculates adjacent mines for each square
    bool isMine(int row, int col) const;                      // Checks if a square is a mine
    bool isValidCell(int row, int col) const;                 // Checks if a cell is valid
    int getAdjacentMines(int row, int col) const;             // Gets number of adjacent mines
    SquareType getSquareType(int row, int col) const;         // Gets the type of a square
    SquareState getSquareState(int row, int col) const;       // Gets the state of a square
    void setSquareState(int row, int col, SquareState state); // Sets the state of a square
    bool isCorrectlyFlagged(int row, int col) const;          // Checks if a square is correctly flagged
    void flagMine();                                          // Flags a mine
    void unflagMine();                                        // Unflags a mine
    int getFlaggedMines() const;                              // Gets the count of flagged mines
    bool isGameOver() const { return gameOver; }              // Checks if the game is over
    void setGameOver(bool state) { gameOver = state; }        // Sets the game over state

private:
    struct Square // Structure representing initial square on the board
    {
        SquareType type = SquareType::Empty;     // Type of the square
        SquareState state = SquareState::Hidden; // State of the square
        int adjacentMines = 0;                   // Number of adjacent mines
    };

    std::vector<std::vector<Square>> board; // 2D vector representing the game board
    const int rows;                         // Number of rows in the board
    const int cols;                         // Number of columns in the board
    const int mineCount;                    // Total number of mines
    int remainingMines;                     // Count of remaining mines
    int flaggedMines;                       // Count of flagged mines
    bool gameOver;                          // Game over state
    bool win;                               // Win state
    bool firstClick;                        // Indicates if it's the first click
};

#endif // GAMELOGIC_H
