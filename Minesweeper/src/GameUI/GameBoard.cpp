#include "GameBoard.h" // Includes the GameBoard class definition

/**
 * @author Arya Zarei
 * @date 02/04/2025
 * @class GameBoard
 *
 * The GameBoard class manages the Minesweeper game interface and organizes the logic.
 * This class is responsible for creating the game UI, handling user interactions, and managing
 * the game state. The GameBoard class is the root of this application as all the commands, mechanics
 * and logics are handled through this class as GameBoard interacts with every other class.
 */

/**
 * This constructor initializes the GameBoard class, setting up the user interface
 * for the Minesweeper game. It creates the display window that the user interacts with
 * and designs the main layout and header section for the display.From the constructor the
 * grid gets created with left and right click handlers.
 *
 * @param parent A pointer to the parent widget, which is passed to the QWidget constructor.
 */
GameBoard::GameBoard(QWidget *parent) : QWidget(parent)
{
    gameLogic = new GameLogic(rows, cols, mineCount); // Initialize game logic
    QVBoxLayout *mainLayout = new QVBoxLayout(this);  // Main layout for the entire game window
    mainLayout->setSpacing(0);                        // Remove widget spacing
    mainLayout->setContentsMargins(0, 0, 0, 0);       // Grid spacing

    QWidget *headerWidget = new QWidget; // Header section with 3D effect styling
    headerWidget->setStyleSheet(
        "background-color: #C0C0C0;"     // Classic Minesweeper gray background
        "border-top: 2px solid #ffffff;" // Light borders for 3D effect
        "border-left: 2px solid #ffffff;"
        "border-right: 2px solid #808080;" // Dark borders for 3D effect
        "border-bottom: 2px solid #808080;");
    QHBoxLayout *headerLayout = new QHBoxLayout(headerWidget);
    headerWidget->setFixedHeight(60);

    /**
     * The mine counter was implemented to make the game easier and to help the user win a game. Whenever
     * a square with a mine is flagged, the counter decreases to help the user identify all mines. Once all
     * mines have been flagged and every other square is open, the user wins and the mine counter will be 0
     * to signify that every mine has been correctly marked. A flagged non-mine square will not decrease the counter.
     */
    mineCounter = new QLCDNumber(3); // Mine counter display (top-left) 3 digits display
    mineCounter->setSegmentStyle(QLCDNumber::Filled);
    mineCounter->setStyleSheet("background-color: black; color: red; padding: 2px;");
    mineCounter->setFixedSize(70, 35);
    mineCounter->display(mineCount); // Initial mine count (99)

    // Smiley face reset button (center)
    smileyButton = new QPushButton(this);
    smileyButton->setFixedSize(40, 40);
    smileyButton->setStyleSheet(
        "QPushButton {"
        "   background-color: #C0C0C0;"
        "   border-top: 2px solid #ffffff;" // 3D effect when not pressed
        "   border-left: 2px solid #ffffff;"
        "   border-right: 2px solid #808080;"
        "   border-bottom: 2px solid #808080;"
        "   font-family: Arial;"
        "   font-size: 24px;"
        "}"
        "QPushButton:pressed {" // Inverted 3D effect when pressed
        "   border-top: 2px solid #808080;"
        "   border-left: 2px solid #808080;"
        "   border-right: 2px solid #ffffff;"
        "   border-bottom: 2px solid #ffffff;"
        "}");
    smileyButton->setText("☺");                                                   // emoji text for smiley face
    connect(smileyButton, &QPushButton::clicked, this, &GameBoard::startNewGame); // connects new game/reset with clicking smiley face

    // timer is purley for visuals, simply counts how long a user has been on a single game
    timeCounter = new QLCDNumber(3); // Create the timer display (top-right)
    timeCounter->setSegmentStyle(QLCDNumber::Filled);
    timeCounter->setStyleSheet("background-color: black; color: red; padding: 2px;");
    timeCounter->setFixedSize(70, 35);
    timeCounter->display(0); // Initial time

    headerLayout->setContentsMargins(10, 5, 10, 5); // Spacing for header widgets
    headerLayout->addWidget(mineCounter);
    headerLayout->addStretch(); // Space between counter and smiley
    headerLayout->addWidget(smileyButton);
    headerLayout->addStretch(); // Space between smiley and timer
    headerLayout->addWidget(timeCounter);

    // Create layout for the mine field
    gridLayout = new QGridLayout;
    gridLayout->setSpacing(0);
    gridLayout->setContentsMargins(0, 0, 0, 0);

    // Add both header and grid to main layout
    mainLayout->addWidget(headerWidget);
    mainLayout->addLayout(gridLayout);

    // Set the fixed window size
    const int windowWidth = 780;
    const int buttonSize = windowWidth / cols;
    const int headerHeight = 60;
    setFixedSize(windowWidth, (windowWidth / cols) * rows + headerHeight);

    // Center the GameBoard on the user's screen
    QRect screenGeometry = QGuiApplication::primaryScreen()->availableGeometry();
    move((screenGeometry.width() - width()) / 2, (screenGeometry.height() - height()) / 2);

    // Create the game board and set up connections
    createBoard(buttonSize);
    setupConnections();

    // Initialize and connect the game timer
    timer = new QTimer(this);
    connect(timer, &QTimer::timeout, this, &GameBoard::updateTimer);
}

/**
 * The start new game method starts a new game by resetting the game
 * state and UI elements bringing the header, grid and mechanics
 * back to the state of before the first button is pressed.
 */
void GameBoard::startNewGame()
{
    // Stop and reset timer
    timer->stop();
    timeCounter->display(0);
    mineCounter->display(mineCount); // Initialize counter to total number of mines (99)
    gameLogic->startNewGame();       // Restart game mechanics (numbers, blanks and mines)

    // Reset button appearances
    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            buttons[i][j]->setText("");      // Clear any text
            buttons[i][j]->setEnabled(true); // Enable buttons
            buttons[i][j]->setIcon(QIcon()); // Clear any icons
            buttons[i][j]->setStyleSheet(
                "QPushButton {"
                "   background-color: #C0C0C0;" // Reset to default background color
                "   border-top: 2px solid #ffffff;"
                "   border-left: 2px solid #ffffff;"
                "   border-right: 2px solid #808080;"
                "   border-bottom: 2px solid #808080;"
                "   margin: 0px;"
                "   padding: 0px;"
                "}");
        }
    }

    smileyButton->setText("☺"); // Reset smiley button
}

// Destructor to clean up game board and mechanics
GameBoard::~GameBoard()
{
    delete gameLogic; // delete game logic pointer
}

/**
 * The createBoard method sets up the grid layout for the game. This
 * method uses specific colours and button size to replicate the original game.ADJ_OFFSET_SINGLESHOT
 *
 * @param buttonSize dimensions of each grid square that acts as a button to interact with game
 */
void GameBoard::createBoard(int buttonSize)
{
    // Initialize the 2D vector of buttons
    buttons.resize(rows, std::vector<QPushButton *>(cols));

    QString buttonStyle = // Minesweeper button style
        "QPushButton {"
        "   background-color: #C0C0C0;"
        "   border-top: 2px solid #ffffff;"
        "   border-left: 2px solid #ffffff;"
        "   border-right: 2px solid #808080;"
        "   border-bottom: 2px solid #808080;"
        "   margin: 0px;"
        "   padding: 0px;"
        "}";
    // loops through all grid indexes initalizing them all indenticaly as buttons
    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            buttons[i][j] = new QPushButton(this);
            buttons[i][j]->setFixedSize(buttonSize, buttonSize);
            buttons[i][j]->setStyleSheet(buttonStyle);
            gridLayout->addWidget(buttons[i][j], i, j);
        }
    }
}

/**
 * the setupConnections method creates the left and right click signal
 * for each square on the grid for the game mechanics
 */
void GameBoard::setupConnections()
{
    HandleClicks handleClicks;
    GameBoard *gameBoard = this; // 'this' is the parent widget

    // set up connection for every square on grid
    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            // Set the context menu for the buttons to right clicks to mark squares and left clicks to open squares
            buttons[i][j]->setContextMenuPolicy(Qt::CustomContextMenu);

            // Connect the left-click signal of the button to the handleLeftClick method
            // This allows the game to respond to left-click actions on the button
            connect(buttons[i][j], &QPushButton::clicked, [=]()
                    { handleClicks.handleLeftClick(gameBoard, i, j); });

            // Connect the right-click signal of the button to the handleRightClick method
            // This allows the game to respond to right-click actions on the button
            connect(buttons[i][j], &QPushButton::customContextMenuRequested, [=](const QPoint &)
                    { handleClicks.handleRightClick(gameBoard, i, j); });
        }
    }
}

// Updates the timer display, capping at 999
void GameBoard::updateTimer()
{
    int currentTime = timeCounter->intValue();
    if (currentTime < 999)
    { // updates the game timer every second
        timeCounter->display(currentTime + 1);
    }
}
