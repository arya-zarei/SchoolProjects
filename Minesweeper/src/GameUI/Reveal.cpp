#include "Reveal.h" // Include the header file for the Reveal class

/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class Reveal
 *
 * The Reveal class is responsible for revealing all squares on the game board.
 * Revealing a square occurs when a user left clicks on a square, if a mine is left clicked
 * on the game is over and all bombs are revealed. If an empty square is revealed all of its
 * adjacent empty squares are also revealed with the numbered squares they touch. If a square
 * adjacent to a mine is touched the number of mines it is touching is revealed with its given colour.
 */

/**
 * Reveals all mines on the game grid once user left clicks a mine.
 * @param gameBoard instance of gameboard
 */
void Reveal::revealAllMines(GameBoard *gameBoard)
{
    // Iterate through the game board
    for (int i = 0; i < gameBoard->rows; ++i) // Access rows from gameBoard
    {
        for (int j = 0; j < gameBoard->cols; ++j) // Access cols from gameBoard
        {
            if (gameBoard->gameLogic->isMine(i, j)) // Check if the current square is a mine, if so reveal the mine
            {
                QIcon mineIcon("Images/Mine.png");                    // Icon image for mine
                gameBoard->buttons[i][j]->setIcon(mineIcon);          // Set the mine icon on the corresponding button
                gameBoard->buttons[i][j]->setIconSize(QSize(20, 20)); // Set the size of the icon
            }
        }
    }
}

/**
 * Reveals the selected square on the game board.
 * @param gameBoard, row, col index of the square selected by player and instance of game board.
 */
void Reveal::revealSquare(GameBoard *gameBoard, int row, int col)
{
    // If the square is already revealed, flagged, or invalid, return early
    if (!gameBoard->gameLogic->isValidCell(row, col) ||
        gameBoard->gameLogic->getSquareState(row, col) == SquareState::Revealed)
        return;

    gameBoard->gameLogic->setSquareState(row, col, SquareState::Revealed); // Set the state of the square to revealed
    gameBoard->buttons[row][col]->setEnabled(false);                       // Disable the button for the revealed square

    int adjacentCount = gameBoard->gameLogic->getAdjacentMines(row, col); // Get the number of adjacent mines for the current square
    if (adjacentCount > 0)                                                // If there are adjacent mines, display the count
    {
        QString numberColor;   // Holds the color for the number display
        switch (adjacentCount) // Determine the color based on the number of adjacent mines
        {
        case 1:
            numberColor = "blue"; // Color for 1 adjacent mine
            break;
        case 2:
            numberColor = "green"; // Color for 2 adjacent mines
            break;
        case 3:
            numberColor = "red"; // Color for 3 adjacent mines
            break;
        case 4:
            numberColor = "purple"; // Color for 4 adjacent mines
            break;
        case 5:
            numberColor = "maroon"; // Color for 5 adjacent mines
            break;
        case 6:
            numberColor = "turquoise"; // Color for 6 adjacent mines
            break;
        case 7:
            numberColor = "black"; // Color for 7 adjacent mines
            break;
        case 8:
            numberColor = "gray"; // Color for 8 adjacent mines
            break;
        default:
            numberColor = "white"; // Default color (will never be used)
        }

        gameBoard->buttons[row][col]->setText(QString::number(adjacentCount)); // Set the text of the button to the number of adjacent mines
        // Set the style of the button to display the number with the appropriate color
        gameBoard->buttons[row][col]->setStyleSheet(
            QString("QPushButton {"
                    "   color: %1;"                 // Set the text color
                    "   background-color: #C0C0C0;" // Set the background color
                    "   border: 1px solid #808080;" // Set the border color
                    "   margin: 0px;"               // Set margin
                    "   padding: 0px;"              // Set padding
                    "   font-weight: bold;"         // Make the text bold
                    "}")
                .arg(numberColor)); // Use the determined color
    }
    else
    {
        // If the square is empty, reveal it and recursively reveal adjacent squares
        gameBoard->buttons[row][col]->setStyleSheet(
            "QPushButton {"
            "   background-color: #C0C0C0;" // Set background color for empty square
            "   border: 1px solid #808080;" // Set border color
            "   margin: 0px;"               // Set margin
            "   padding: 0px;"              // Set padding
            "}");

        // Recursively reveal all adjacent squares
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if (i == 0 && j == 0)
                    continue; // Skip the current square
                // Calculate the new row and column indices
                int newRow = row + i;
                int newCol = col + j;
                // Check if the new square is valid, not a mine, and not flagged
                if (gameBoard->gameLogic->isValidCell(newRow, newCol) &&
                    !gameBoard->gameLogic->isMine(newRow, newCol) &&
                    gameBoard->gameLogic->getSquareState(newRow, newCol) != SquareState::Flagged)
                {
                    revealSquare(gameBoard, newRow, newCol); // Recursively reveal the adjacent square
                }
            }
        }
    }
}
