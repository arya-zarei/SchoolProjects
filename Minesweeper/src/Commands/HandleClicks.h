#ifndef HANDLECLICKS_H
#define HANDLECLICKS_H

#include "src/GameUI/Reveal.h"      // Include the GameBoard header for game board
#include "src/Logic/GameLogic.h"    // Include the GameLogic header for game mechanics
#include "src/GameUI/GameOutcome.h" // Include the GameOutcome class for handling game over scenarios

class HandleClicks
{
public:
    void handleLeftClick(GameBoard *gameBoard, int row, int col) const;  // Handles left click events
    void handleRightClick(GameBoard *gameBoard, int row, int col) const; // Handle right click events

private:
};

#endif // HANDLECLICKS_H
