// GameBoard.h
#ifndef GAMEBOARD_H
#define GAMEBOARD_H

#include <QWidget>                     // Provides the QWidget class, the base class for all UI objects
#include <QGridLayout>                 // Provides the QGridLayout class for arranging widgets in a grid
#include <QPushButton>                 // Provides the QPushButton class for creating clickable buttons
#include <vector>                      // Provides the std::vector container for dynamic arrays
#include <QLCDNumber>                  // Provides the QLCDNumber class for displaying numbers in a digital format
#include <QTimer>                      // Provides the QTimer class for creating timers
#include <QGuiApplication>             // Provides the QGuiApplication class for GUI application management
#include <QScreen>                     // Provides the QScreen class for screen-related information
#include "src/Logic/GameLogic.h"       // Includes the GameLogic class that handles the game mechanics
#include "src/Commands/HandleClicks.h" // Includes the HandleClicks class for managing click events

// Main game board class that handles the Minesweeper game interface
class GameBoard : public QWidget
{
    Q_OBJECT

public:
    GameBoard(QWidget *parent = nullptr); // Constructor for creating the game board
    void startNewGame();                  // Starts a new game, resetting all game elements
    ~GameBoard();                         // Destructor to clean up resources

private:
    void createBoard(int buttonSize); // Creates the grid of buttons for the game board
    void setupConnections();          // Sets up signal/slot connections for game interactions
    void updateTimer();               // Updates the game timer every second

    // UI Elements
    QLCDNumber *mineCounter;   // Displays the number of remaining mines
    QLCDNumber *timeCounter;   // Displays the elapsed time since the game started
    QTimer *timer;             // Timer for counting elapsed time
    QPushButton *smileyButton; // Reset button with a smiley face to start a new game
    QGridLayout *gridLayout;   // Layout for arranging mine field buttons in a grid

    // Game board buttons stored in a 2D vector
    std::vector<std::vector<QPushButton *>> buttons;

    // Game constants
    const int rows = 16;      // Number of rows in the game board
    const int cols = 30;      // Number of columns in the game board
    const int mineCount = 99; // Total number of mines in the game

    GameLogic *gameLogic; // Pointer to the GameLogic class that handles game mechanics

    // Friend classes that can access private members of GameBoard
    friend class HandleClicks; // Allows HandleClicks to access private members
    friend class Reveal;       // Allows Reveal to access private members
    friend class GameOutcome;  // Allows GameOutcome to access private members
};
#endif // GAMEBOARD_H
