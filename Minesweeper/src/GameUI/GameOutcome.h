#ifndef GAMEOUTCOME_H
#define GAMEOUTCOME_H

#include <QMessageBox> // Provides the QMessageBox class for creating win/loss message pop up boxes
#include "GameBoard.h" // Includes the GameBoard class that handles the game UI

class GameBoard; // Forward declaration

class GameOutcome
{
public:
    void checkWinCondition(GameBoard *gameBoard); // Method to check the win condition of the game
    void gameOver(GameBoard *gameBoard);          // Method to handle player loss
};

#endif // GAMEOUTCOME_H
