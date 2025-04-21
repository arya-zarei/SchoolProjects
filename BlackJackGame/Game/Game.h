#ifndef GAME_H
#define GAME_H

#include <QWidget>
#include <QtCore>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>
#include <QPixmap>
#include <QCloseEvent>
#include <QString>
#include <QTextStream>
#include <QTimer>
#include <vector>
#include <iostream>
#include <QMessageBox>
#include <QLineEdit>
#include <qcoreapplication.h>
#include <QPropertyAnimation>
#include <QSequentialAnimationGroup>
#include <QEasingCurve>
#include <QThread>
#include <chrono>
#include <thread>
#include "Dealer.h"
#include "Deck.h"
#include "Card.h"

class Game : public QWidget
{
    Q_OBJECT

public:
    explicit Game(const QString &playerId, QWidget *parent = nullptr);
    void startGame(); // Starts the game

signals:
    void closed(); // Signal emitted when the window is closed

protected:
    void closeEvent(QCloseEvent *event) override;
    void displayCard(const Card &card, bool isDealer, int cardIndex, bool hidden);
    void placeBet();
    void maxBet();

private:
    void loadPlayerData();                 // Loads player data from a CSV file
    void savePlayerData();                 // Saves player data to a CSV file
    void hit();                            // Handles the "Hit" button logic
    void stand();                          // Handles the "Stand" button logic
    void endRound(const QString &message); // Ends the game and displays a popup message
    void playBlackjackRound();

    QString playerId;            // Player ID
    QLabel *chipLabel;           // Label to display the chip image
    QLabel *backgroundLabel;     // Background label
    QLabel *totalChipsLabel;     // Label to display the total chips
    QLabel *handsWonLabel;       // Label to display the number of hands won
    QPushButton *startButton;    // "Start Game" button
    QPushButton *hitButton;      // "Hit" button
    QPushButton *standButton;    // "Stand" button
    QLineEdit *betInput;         // Input field for the bet amount
    QPushButton *placeBetButton; // Button to place the bet
    QPushButton *maxBetButton;   // Button for placing the max bet
    int currentBet;              // Stores the current bet amount
    int totalChips;              // Total chips for the player
    int handsWon;                // Number of hands won by the player
    bool playerTurn;             // Tracks if it's the player's turn
    Player player;
    Dealer dealer;                      // Dealer instance
    Deck *deck;                         // Pointer to the deck
    QVector<QLabel *> dealerCardLabels; // Labels for dealer's cards
    QVector<QLabel *> playerCardLabels; // Labels for player's cards
};

#endif // GAME_H
