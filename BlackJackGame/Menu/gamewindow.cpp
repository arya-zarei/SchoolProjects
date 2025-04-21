#include "gamewindow.h"

// Constructor for GameWindow
// Initializes the main menu window with buttons for various actions
GameWindow::GameWindow(const QString &playerId, QWidget *parent)
    : QWidget(parent), playerId(playerId) // Initialize member variables
{
    // Set the window title and size
    setWindowTitle("BlackJack");
    setFixedSize(800, 600);

    int windowWidth = this->width();   // Get the width of the GameWindow
    int windowHeight = this->height(); // Get the height of the GameWindow

    // Set up the background label (using Qt resource system: :/images/menu.png)
    backgroundLabel = new QLabel(this);
    QPixmap bgPixmap(":/images/menu.png");
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    // Scale the background image to fit the window size while maintaining its aspect ratio
    backgroundLabel->setGeometry(0, 0, width(), height()); // Position the background to cover the entire window
    backgroundLabel->lower();                              // Ensure the background stays behind all other widgets

    // Create the "Play Game" button
    playGameButton = new QPushButton("PLAY GAME", this);
    playGameButton->setStyleSheet("font-family: 'Arial Black'; font-size: 14px; font-weight: bold; "
                                  "color: white; background-color: #333; border: 2px solid white; "
                                  "border-radius: 5px; padding: 5px 10px;");
    playGameButton->setFixedSize(150, 50);                                      // Set the button size
    playGameButton->setGeometry(windowWidth / 3, windowHeight / 1.75, 150, 50); // Position the button
    connect(playGameButton, &QPushButton::clicked, this, &GameWindow::onPlayGameButtonClicked);

    // Create the "Tutorial" button
    tutorialButton = new QPushButton("TUTORIAL", this);
    tutorialButton->setStyleSheet("font-family: 'Arial Black'; font-size: 14px; font-weight: bold; "
                                  "color: white; background-color: #333; border: 2px solid white; "
                                  "border-radius: 5px; padding: 5px 10px;");
    tutorialButton->setFixedSize(150, 50);                                             // Set the button size
    tutorialButton->setGeometry((windowWidth / 2) + 25, windowHeight / 1.75, 150, 50); // Position the button
    connect(tutorialButton, &QPushButton::clicked, this, &GameWindow::onTutorialButtonClicked);

    // Create the "Buy Chips" button
    buyChipsButton = new QPushButton("BUY CHIPS", this);
    buyChipsButton->setStyleSheet("font-family: 'Arial Black'; font-size: 14px; font-weight: bold; "
                                  "color: white; background-color: #333; border: 2px solid white; "
                                  "border-radius: 5px; padding: 5px 10px;");
    buyChipsButton->setFixedSize(150, 50);                                             // Set the button size
    buyChipsButton->setGeometry(windowWidth / 3, (windowHeight / 1.25) - 75, 150, 50); // Position the button
    connect(buyChipsButton, &QPushButton::clicked, this, &GameWindow::onBuyChipsButtonClicked);

    // Create the "Leaderboard" button
    leaderboardButton = new QPushButton("LEADERBOARD", this);
    leaderboardButton->setStyleSheet("font-family: 'Arial Black'; font-size: 14px; font-weight: bold; "
                                     "color: white; background-color: #333; border: 2px solid white; "
                                     "border-radius: 5px; padding: 5px 10px;");
    leaderboardButton->setFixedSize(150, 50);                                                    // Set the button size
    leaderboardButton->setGeometry((windowWidth / 2) + 25, (windowHeight / 1.25) - 75, 150, 50); // Position the button
    connect(leaderboardButton, &QPushButton::clicked, this, &GameWindow::onLeaderboardButtonClicked);
}

// Override resizeEvent to update the background image size when the window is resized
void GameWindow::resizeEvent(QResizeEvent *event)
{
    QWidget::resizeEvent(event); // Call the base class implementation
    if (backgroundLabel)
    {
        QPixmap bgPixmap(":/images/menu.png");
        backgroundLabel->setPixmap(bgPixmap.scaled(event->size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
        // Scale the background image to fit the new window size
        backgroundLabel->setGeometry(0, 0, event->size().width(), event->size().height());
    }
}

// Slot for handling "Play Game" button click
void GameWindow::onPlayGameButtonClicked()
{
    Game *gameWindow = new Game(playerId);                                     // Create a new Game window
    connect(gameWindow, &Game::closed, this, &GameWindow::onGameWindowClosed); // Reopen GameWindow when Game is closed
    gameWindow->show();                                                        // Show the Game window
    this->close();                                                             // Close the current GameWindow
}

// Slot for handling "Tutorial" button click
void GameWindow::onTutorialButtonClicked()
{
    Tutorial *tutorialWindow = new Tutorial();                                         // Create a new Tutorial window
    connect(tutorialWindow, &Tutorial::closed, this, &GameWindow::onGameWindowClosed); // Reopen GameWindow when Tutorial is closed
    tutorialWindow->show();                                                            // Show the Tutorial window
    this->close();                                                                     // Close the current GameWindow
}

// Slot for handling "Buy Chips" button click
void GameWindow::onBuyChipsButtonClicked()
{
    BuyChips *buyChipsWindow = new BuyChips(playerId);                                 // Create a new BuyChips window
    connect(buyChipsWindow, &BuyChips::closed, this, &GameWindow::onGameWindowClosed); // Reopen GameWindow when BuyChips is closed
    buyChipsWindow->show();                                                            // Show the BuyChips window
    this->close();                                                                     // Close the current GameWindow
}

// Slot for handling "Leaderboard" button click
void GameWindow::onLeaderboardButtonClicked()
{
    Leaderboard *leaderboardWindow = new Leaderboard();                                      // Create a new Leaderboard window
    connect(leaderboardWindow, &Leaderboard::closed, this, &GameWindow::onGameWindowClosed); // Reopen GameWindow when Leaderboard is closed
    leaderboardWindow->show();                                                               // Show the Leaderboard window
    this->close();                                                                           // Close the current GameWindow
}

// Slot for handling the close event of any child window
void GameWindow::onGameWindowClosed()
{
    this->show(); // Reopen the GameWindow
}
