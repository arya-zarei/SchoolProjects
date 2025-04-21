#ifndef TUTORIAL_H
#define TUTORIAL_H

#include <QWidget>
#include <QString>
#include <QVBoxLayout>
#include <QLabel>
#include <QPushButton>
#include <QScrollArea>
#include <QCloseEvent>

class Tutorial : public QWidget
{
    Q_OBJECT

public:
    explicit Tutorial(QWidget *parent = nullptr);

signals:
    void closed(); // Signal to indicate the window is closed
    
protected:
    void closeEvent(QCloseEvent *event) override;

private:
    QLabel *backgroundLabel; // For displaying the background image
    QString playerId;
};

#endif // TUTORIAL_H