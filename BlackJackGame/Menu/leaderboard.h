#ifndef LEADERBOARD_H
#define LEADERBOARD_H

#include <QWidget>
#include <QString>
#include <QList>
#include <QLabel>
#include <QVBoxLayout>
#include <QFile>
#include <QTextStream>
#include <QDebug>
#include <algorithm>

class Leaderboard : public QWidget
{
    Q_OBJECT

public:
    explicit Leaderboard(QWidget *parent = nullptr);

signals:
    void closed();

protected:
    void closeEvent(QCloseEvent *event) override;

private:
    struct Player
    {
        QString id;
        int handsWon;
        int chips;
    };

    QList<Player> loadPlayerData();
};

#endif // LEADERBOARD_H