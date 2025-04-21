#ifndef BUYCHIPS_H
#define BUYCHIPS_H

#include <QWidget>
#include <QString>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QGridLayout>
#include <QFile>
#include <QTextStream>
#include <QDebug>

class BuyChips : public QWidget
{
    Q_OBJECT

public:
    explicit BuyChips(const QString &playerId, QWidget *parent = nullptr);

signals:
    void closed();

protected:
    void closeEvent(QCloseEvent *event) override;

private:
    QString playerId; // Store the player's ID
    int totalChips;   // Store the player's chip count

    void loadPlayerData(); // Load the player's chip count from the CSV file
    void savePlayerData(); // Save the player's chip count to the CSV file
};

#endif // BUYCHIPS_H