#include "HandleClicks.h" // Include the header file for the HandleClicks class

/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class HandleClicks
 *
 * This class handles mouse click events for the game board, including left and right clicks.
 * Right clicks are for marking and unmarking squares (flagged or question marked). Left clicks
 * are for opening a square.
 */

/**
 * handleLeftClick, handles the left mouse click event on the game board. This method checks if the game is over,
 * handles the first click by placing mines, reveals squares, and checks for game over conditions.
 * If a mine is clicked, it reveals all mines and updates the game state accordingly.
 * @param gameBoard, row, col index of left clicked square and game board instance
 */
void HandleClicks::handleLeftClick(GameBoard *gameBoard, int row, int col) const
{
    Reveal reveal;       // Create a Reveal object to handle square revealling
    GameOutcome outcome; // Create a GameOutcome object to handle the game over state

    if (gameBoard->gameLogic->isGameOver())
        return; // Check if the game is over; if so, exit the function

    if (gameBoard->gameLogic->placeMines(row, col)) // Handle the first click by placing mines if the click is valid
    {
        gameBoard->timer->start(1000); // Start the game timer after the first click
    }

    if (gameBoard->gameLogic->getSquareState(row, col) == SquareState::Flagged) // Check if the clicked square is flagged
    {
        gameBoard->gameLogic->setSquareState(row, col, SquareState::Hidden); // If flagged square is not a mine, remove the flag
        gameBoard->buttons[row][col]->setIcon(QIcon());                      // Clear the icon from the button
    }

    if (gameBoard->gameLogic->isMine(row, col)) // Check if the clicked square is a mine
    {
        if (gameBoard->gameLogic->getSquareState(row, col) == SquareState::Flagged)
        { // If the square is flagged, increment the mine counter
            gameBoard->mineCounter->display(gameBoard->mineCounter->intValue() + 1);
        }
        gameBoard->buttons[row][col]->setText(""); // Clear the text on the button

        reveal.revealAllMines(gameBoard);        // Reveal all mines on the board
        gameBoard->timer->stop();                // Stop the game timer
        gameBoard->gameLogic->setGameOver(true); // Set the game state to over when player hits mine
        outcome.gameOver(gameBoard);             // Call the game over method
    }
    else
    {
        reveal.revealSquare(gameBoard, row, col); // Reveal the clicked square
        outcome.checkWinCondition(gameBoard);     // Check if the player has won the game
    }
}

/**
 * handleRightClick, handles the right mouse click event on the game board. This method checks
 * if the game is over or if the button is disabled, and manages the flagging of squares. It
 * allows the player to flag, unflag, or mark squares with a question mark based on the current state.
 * @param gameBoard, row, col index of right clicked square and game board instance
 */
void HandleClicks::handleRightClick(GameBoard *gameBoard, int row, int col) const
{
    if (gameBoard->gameLogic->isGameOver() || !gameBoard->buttons[row][col]->isEnabled())
        return; // Check if the game is over or if the button is disabled; if so, exit the function

    SquareState currentState = gameBoard->gameLogic->getSquareState(row, col); // Get the current state of the clicked square

    switch (currentState) // Handle the right-click action based on the current state of the square
    {
    case SquareState::Hidden:
        gameBoard->gameLogic->setSquareState(row, col, SquareState::Flagged); // If the square is hidden, flag it
        gameBoard->buttons[row][col]->setIcon(QIcon("Images/Flag.png"));      // Set the flag icon on the button
        gameBoard->buttons[row][col]->setIconSize(QSize(20, 20));             // Set the size of the flag icon

        if (gameBoard->gameLogic->isMine(row, col))
        { // If the flagged square is a mine, decrement the mine counter
            gameBoard->mineCounter->display(gameBoard->mineCounter->intValue() - 1);
        }
        gameBoard->gameLogic->flagMine(); // Call the method to flag the mine in the game logic
        break;

    case SquareState::Flagged: // If the square is flagged, change it to a question mark state
        gameBoard->gameLogic->setSquareState(row, col, SquareState::QuestionMark);
        gameBoard->buttons[row][col]->setIcon(QIcon()); // Clear the icon from the button
        gameBoard->buttons[row][col]->setText("?");     // Set the button text to a question mark
        if (gameBoard->gameLogic->isMine(row, col))
        { // If the square is a mine, increment the mine counter
            gameBoard->mineCounter->display(gameBoard->mineCounter->intValue() + 1);
        }
        gameBoard->gameLogic->unflagMine(); // Call the method to unflag the mine in the game logic
        break;

    case SquareState::QuestionMark: // If the square is a question mark, change it back to hidden
        gameBoard->gameLogic->setSquareState(row, col, SquareState::Hidden);
        gameBoard->buttons[row][col]->setText(""); // Clear the text on the button
        break;

    default:
        break; // Do nothing for any other state
    }
    GameOutcome win;
    win.checkWinCondition(gameBoard); // Check if the player has won the game after the right-click action
}
