#ifndef REVEAL_H
#define REVEAL_H

#include <QIcon>                 // Include QIcon for setting icons on buttons
#include "GameBoard.h"           // Include the definition of the GameBoard class.
#include "src/Logic/GameLogic.h" // Include the definition of the GameLogic class.

class GameBoard; // Forward declaration of GameBoard

class Reveal
{
public:
    void revealAllMines(GameBoard *gameBoard);                 // Reveals all mines after loss
    void revealSquare(GameBoard *gameBoard, int row, int col); // Reveals square player clicked on
};

#endif // REVEAL_H
