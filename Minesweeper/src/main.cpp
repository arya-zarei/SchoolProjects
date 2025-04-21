/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class main
 *
 * The main c++ file creates an instance of the GameBoard, which is the main element of the Minesweeper UI
 * and opens the window for the game until the window is closed and then ends the code
 */

#include <QApplication>       // Include the QApplication class from the Qt framework
#include "GameUI/GameBoard.h" // Include the GameBoard class from the GameUI directory

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    GameBoard gameBoard;                     // Instantiate a GameBoard object
    gameBoard.setWindowTitle("Minesweeper"); // Title of game display window
    gameBoard.resize(800, 600);              // Set the size of game window
    gameBoard.show();                        // Show Game window

    return app.exec(); // Enter the main event loop and wait for events (like mouse clicks)
                       // return the status on application exit.
}
