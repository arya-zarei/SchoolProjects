#ifndef MENU_H
#define MENU_H

#include <QWidget>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QVBoxLayout>
#include <QResizeEvent>
#include <QRegExpValidator>
#include <QPixmap>
#include <QDebug>
#include <QSpacerItem>
#include <QFile>
#include <QTextStream>
#include <QMessageBox>
#include "gamewindow.h"

class Menu : public QWidget
{
    Q_OBJECT

public:
    explicit Menu(QWidget *parent = nullptr);

    // Public Getters to Access Private Members for Testing
    QLineEdit *getUsernameInput() { return usernameInput; }
    QPushButton *getEnterGameButton() { return enterGameButton; }

signals:
    void usernameEntered(const QString &username);

private slots:
    void onEnterGameButtonClicked();

protected:
    void resizeEvent(QResizeEvent *event) override;

private:
    bool checkOrCreatePlayer(const QString &playerId);

    QLabel *backgroundLabel;
    QLabel *titleLabel;
    QLabel *usernameLabel;
    QLineEdit *usernameInput;
    QPushButton *enterGameButton;
};

#endif // MENU_H
