#include "GameLogic.h" // Includes the GameLogic class definition

/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class GameLogic
 *
 * This class is responsible for initializing the game board, placing mines, calculating adjacent mines,
 * and managing the state of each square on the board. It also handles game state transitions such as
 * starting a new game, flagging mines, and checking for win conditions.
 */

/**
 * The constructor initializes an instance of the grid with the given parameters and sets up the initial state of the game.
 * @param rows, cols, mineCount 16 rows, 30 cols and 99 mines for every game initalized in the GameBoard header file.
 */
GameLogic::GameLogic(int rows, int cols, int mineCount)
    : rows(rows), cols(cols), mineCount(mineCount),
      remainingMines(mineCount), flaggedMines(0), gameOver(false), win(false), firstClick(true)
{
    board.resize(rows, std::vector<Square>(cols)); // 2D vector for the grid (each element is one of the game squares)
}

/**
 * startNewGame resets all squares on the board to their initial state,
 * sets the remaining mines to the total mine count, and resets game state variables.
 */
void GameLogic::startNewGame()
{
    // Reset the board
    for (auto &row : board)
    { // auto & ensures row or square(col) is a refrence to avoid copying
        for (auto &square : row)
        { // set type to empty
            square.type = SquareType::Empty;
            square.state = SquareState::Hidden;
            square.adjacentMines = 0;
        }
    }
    // reset all game stats
    remainingMines = mineCount;
    flaggedMines = 0;
    gameOver = false;
    win = false;
    firstClick = true;
}

/**
 * placeMines randomly places mines on the board while ensuring that the square clicked first by the
 * player does not contain a mine. It uses random number generation to select positions for the mines.
 * @param firstRow The row of the first click.
 * @param firstCol The column of the first click.
 */
bool GameLogic::placeMines(int firstRow, int firstCol)
{
    if (!firstClick) // Do not place mines for first click
        return false;

    std::random_device rd;                                // Create a random device
    std::mt19937 gen(rd());                               // Create Random-number generator
    std::uniform_int_distribution<> rowDist(0, rows - 1); // Define a uniform distribution for row indices
    std::uniform_int_distribution<> colDist(0, cols - 1); // Define a uniform distribution for column indices

    int placedMines = 0;            // Initialize placed mines count
    while (placedMines < mineCount) // Continue until all mines are placed
    {                               // Generate random grid index
        int row = rowDist(gen);
        int col = colDist(gen);

        if (abs(row - firstRow) <= 1 && abs(col - firstCol) <= 1)
            continue; // Skip placing a mine in the first clicked square or its adjacent squares

        if (board[row][col].type == SquareType::Mine)
            continue; // Skip if there's already a mine at this location

        board[row][col].type = SquareType::Mine; // Place a mine at grid index
        placedMines++;                           // Increment the count of placed mines
    }

    calculateAdjacentMines(); // Calculate adjacent mines for all squares
    firstClick = false;       // Set first click to false after the first move is made
    return true;              // indicates mines were successfully placed
}

/**
 * calculateAdjacentMines iterates through each square on the board and counts the number of mines
 * in the surrounding squares, updating the adjacentMines property for each square accordingly.
 */
void GameLogic::calculateAdjacentMines()
{ // iterate through all squares
    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            if (board[i][j].type != SquareType::Mine) // Check if current square is not a mine
            {
                int count = 0;                         // Initialize the count of adjacent mines
                for (int adjI = -1; adjI <= 1; ++adjI) // Iterate over all adjacent squares
                {
                    for (int adjJ = -1; adjJ <= 1; ++adjJ)
                    {
                        if (adjI == 0 && adjJ == 0)
                            continue;         // Skip the current square itself
                        int cordI = i + adjI; // Calculate the row index of the adjacent square
                        int cordJ = j + adjJ; // Calculate the column index of the adjacent square

                        // Check if the adjacent square is within bounds and is a mine
                        if (isValidCell(cordI, cordJ) && board[cordI][cordJ].type == SquareType::Mine)
                        {
                            count++; // If so increment the count of adjacent mines
                        }
                    }
                }
                board[i][j].adjacentMines = count;                                       // Update the adjacent mines count for the current square
                board[i][j].type = (count > 0) ? SquareType::Number : SquareType::Empty; // Set the square type based on the count of adjacent mine
            }
        }
    }
}

/**
 * Check if square is a mine.
 * @param row The row of the square to check.
 * @param col The column of the square to check.
 */
bool GameLogic::isMine(int row, int col) const
{
    if (!isValidCell(row, col))
        return false;                                // If not a valid coordinate return false
    return board[row][col].type == SquareType::Mine; // Return true if square is a mine, false if not
}

/**
 * Validates if the specified cell is within the bounds of the board.
 * @param row The row of the cell to validate.
 * @param col The column of the cell to validate.
 */
bool GameLogic::isValidCell(int row, int col) const
{
    return row >= 0 && row < rows && col >= 0 && col < cols; // Return true if valid coordinate, false if not
}

/**
 * Retrieves the number of adjacent mines for a specific square.
 * @param row The row of the square.
 * @param col The column of the square.
 */
int GameLogic::getAdjacentMines(int row, int col) const
{
    if (!isValidCell(row, col))
        return 0;                         // Check if valid coordinate
    return board[row][col].adjacentMines; // Return the number of adjacent mines for the given square
}

/**
 * Retrieves the type of a specific square.
 * @param row The row of the square.
 * @param col The column of the square.
 */
SquareType GameLogic::getSquareType(int row, int col) const
{
    if (!isValidCell(row, col))
        return SquareType::Empty; // Return empty square if coordinate not valid
    return board[row][col].type;  // Return square type (empty, numbered, mine)
}

/**
 * @brief Retrieves the state of a specific square.
 * @param row The row of the square.
 * @param col The column of the square.
 */
SquareState GameLogic::getSquareState(int row, int col) const
{
    if (!isValidCell(row, col))
        return SquareState::Hidden; // Return hidden square if coordinate not valid
    return board[row][col].state;   // Return square state (hidden, revealed, flagged, question marked)
}

/**
 * Sets the state of a specific square.
 * @param row The row of the square.
 * @param col The column of the square.
 */
void GameLogic::setSquareState(int row, int col, SquareState state)
{
    if (isValidCell(row, col))
    {
        board[row][col].state = state; // sets the state of square at specific coordinate
    }
}

/**
 * Checks if a specific square is correctly flagged as a mine.
 * @param row The row of the square.
 * @param col The column of the square.
 */
bool GameLogic::isCorrectlyFlagged(int row, int col) const
{
    if (!isValidCell(row, col))
        return false; // return false if not a valid square
    // Return true if a square is flagged and is a mine, return false otherwise
    return board[row][col].type == SquareType::Mine &&
           board[row][col].state == SquareState::Flagged;
}

// flagMine increments the count of flagged mine.
void GameLogic::flagMine()
{ // if a mine has been flagged, increment the flagged mine count
    if (flaggedMines < mineCount)
    {
        flaggedMines++;
    }
}

// unflagMine if a mine is unflagged, decrease the count of flagged mines.
void GameLogic::unflagMine()
{
    if (flaggedMines > 0)
    {
        flaggedMines--;
    }
}

// Retrieves the current count of flagged mines.
int GameLogic::getFlaggedMines() const
{
    return flaggedMines;
}
