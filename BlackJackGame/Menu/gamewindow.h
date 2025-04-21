#ifndef GAMEWINDOW_H
#define GAMEWINDOW_H

#include <QWidget>
#include <QPushButton>
#include <QVBoxLayout>
#include <QGridLayout>
#include <QLabel>
#include <QResizeEvent>
#include <QDebug>
#include "../Game/Game.h"
#include "tutorial.h"
#include "leaderboard.h"
#include "buychips.h"

class GameWindow : public QWidget
{
    Q_OBJECT

public:
    explicit GameWindow(const QString &playerId, QWidget *parent = nullptr);
    void resizeEvent(QResizeEvent *event);

    QPushButton *getPlayGameButton() { return playGameButton; }
    QPushButton *getTutorialButton() { return tutorialButton; }

private:
    void onPlayGameButtonClicked();
    void onTutorialButtonClicked();
    void onBuyChipsButtonClicked();
    void onLeaderboardButtonClicked(); // Slot for Leaderboard button
    void onGameWindowClosed();

    QLabel *backgroundLabel; // For displaying the background image
    QPushButton *playGameButton;
    QPushButton *tutorialButton;
    QPushButton *buyChipsButton;
    QPushButton *leaderboardButton; // Leaderboard button
    QString playerId;
};

#endif // GAMEWINDOW_H
