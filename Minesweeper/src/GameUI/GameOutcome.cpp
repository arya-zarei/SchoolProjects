#include "GameOutcome.h" // Include the header file for the GameOutcome class

/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class GameOutcome
 *
 * This class handles the outcome of the game, including win and loss conditions.
 * It displays appropriate message boxes to the player based on whether the player won or lost.
 */

/**
 * checkWinCondition checks if the player has won the game by opening every square and flagging all the mines.
 * If the user has won, then a game winner pop up appears asking the user if they would like to play again
 * or "ok" to just view the board which goes frozen after the game, the cool face in the center header can then
 * be pressed to replay game or the player can exit the application.
 * @param gameBoard instance of game board
 */
void GameOutcome::checkWinCondition(GameBoard *gameBoard)
{
    // Initialize flags to track win conditions
    bool allMinesCorrectlyFlagged = true; // Flag to check if all mines are flagged correctly
    bool allOtherSquaresRevealed = true;  // Flag to check if all non-mine squares are revealed

    // Iterate through all squares on the game board
    for (int i = 0; i < gameBoard->rows; ++i)
    {
        for (int j = 0; j < gameBoard->cols; ++j)
        {
            // Check if the current square is a mine
            if (gameBoard->gameLogic->isMine(i, j))
            {
                // If a mine, check if it is flagged correctly
                if (!gameBoard->gameLogic->isCorrectlyFlagged(i, j))
                {
                    allMinesCorrectlyFlagged = false; // Set flag to false if any mine is not flagged
                    break;                            // exit loop since player has not won yet
                }
            }
            else
            {
                // If not a mine, check if the square is revealed
                if (gameBoard->gameLogic->getSquareState(i, j) != SquareState::Revealed)
                {
                    allOtherSquaresRevealed = false; // Set flag to false if non-mine square is not revealed
                    break;                           // exit loop since player has not won yet
                }
            }
        }
    }

    // If both conditions are met, the player has won the game
    if (allMinesCorrectlyFlagged && allOtherSquaresRevealed)
    {
        gameBoard->gameLogic->setGameOver(true); // Set the game state to over
        gameBoard->timer->stop();                // Stop the game timer
        gameBoard->smileyButton->setText("😎");  // Change the smiley button to a victory smiley

        QMessageBox victoryBox;                                              // Create a message box for victory
        victoryBox.setWindowTitle("Congratulations!");                       // Set the title of the message box
        victoryBox.setText("You've won the game!");                          // Set the text of the message box
        victoryBox.setStandardButtons(QMessageBox::Ok | QMessageBox::Reset); // Add buttons for OK and Reset

        // Center the message box on the GameBoard
        QPoint boardCenter = gameBoard->mapToGlobal(gameBoard->rect().center()); // Get the center point of the game board
        int x = boardCenter.x() - victoryBox.width() / 6;                        // Calculate x position for centering
        int y = boardCenter.y() - victoryBox.height() / 6;                       // Calculate y position for centering
        victoryBox.move(x, y);                                                   // Move the message box to the calculated position

        int result = victoryBox.exec();   // Show the message box and wait for user response
        if (result == QMessageBox::Reset) // If the user clicked Reset
        {
            gameBoard->startNewGame(); // Start a new game
        }
    }
}

/**
 * gameOver handles when a player losses, the middle header smile icon becomes a frown and the message box
 * appears to inform the user. The player can either replay the game from the pop up or can press okay to restart
 * from the header or to exit the application.
 * @param gameBoard instance of game board
 */
void GameOutcome::gameOver(GameBoard *gameBoard)
{
    gameBoard->timer->stop();              // Stop the game timer
    gameBoard->smileyButton->setText("☹"); // Set smiley to sad for loss

    // Show game over message box
    QMessageBox gameOverBox;                                              // Create a message box for game over
    gameOverBox.setWindowTitle("Game Over");                              // Set the title of the message box
    gameOverBox.setText("You hit a mine! Game Over!");                    // Set the text of the message box
    gameOverBox.setStandardButtons(QMessageBox::Ok | QMessageBox::Reset); // Add buttons for OK and Reset

    // Center the message box on the GameBoard
    QPoint boardCenter = gameBoard->mapToGlobal(gameBoard->rect().center()); // Get the center point of the game board
    int x = boardCenter.x() - gameOverBox.width() / 6;                       // Calculate x position for centering
    int y = boardCenter.y() - gameOverBox.height() / 6;                      // Calculate y position for centering
    gameOverBox.move(x, y);                                                  // Move the message box to the calculated position

    int result = gameOverBox.exec();  // Show the message box and wait for user response
    if (result == QMessageBox::Reset) // If the user clicked Reset
    {
        gameBoard->startNewGame(); // Start a new game
    }
}
