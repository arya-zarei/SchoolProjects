#include "leaderboard.h"

// Constructor for the Leaderboard class
// Sets up the UI for displaying the leaderboard
Leaderboard::Leaderboard(QWidget *parent) : QWidget(parent)
{
    // Set the window title and size
    setWindowTitle("Leaderboard");
    setFixedSize(800, 600);

    // Set up the background label (using Qt resource system: :/images/tutorial.jpg)
    QLabel *backgroundLabel = new QLabel(this);
    QPixmap bgPixmap(":/images/tutorial.jpg"); // Load the background image
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    // Scale the image to fit the window size while maintaining its aspect ratio
    backgroundLabel->setGeometry(0, 0, width(), height()); // Position the background to cover the entire window
    backgroundLabel->lower();                              // Ensure the background stays behind all other widgets

    // Create a main layout for the leaderboard
    QVBoxLayout *mainLayout = new QVBoxLayout(this);

    // Add a title for the leaderboard
    QLabel *titleLabel = new QLabel("Leaderboard", this);
    titleLabel->setAlignment(Qt::AlignCenter); // Center-align the title
    titleLabel->setStyleSheet("font-size: 32px; font-weight: bold; color: gold; text-decoration: underline;");
    mainLayout->addWidget(titleLabel);

    mainLayout->addSpacing(50); // Add spacing below the title

    QWidget *LeaderboardContainer = new QWidget(this);
    LeaderboardContainer->setGeometry(150, 85, 500, 340); // Adjust the y-coordinate as needed
    LeaderboardContainer->setStyleSheet("background-color: rgba(0, 0, 0, 0.7); border-radius: 15px; padding: 20px;");

    // Create a container for the leaderboard entries
    QWidget *leaderboardContainer = new QWidget(this);
    QVBoxLayout *leaderboardLayout = new QVBoxLayout(leaderboardContainer);

    // Load and sort player data
    QList<Player> players = loadPlayerData(); // Load player data from the CSV file
    std::sort(players.begin(), players.end(), [](const Player &a, const Player &b)
              {
                  if (a.handsWon != b.handsWon)
                      return a.handsWon > b.handsWon; // Sort by hands won (descending)
                  if (a.chips != b.chips)
                      return a.chips > b.chips;       // Sort by chips (descending)
                  return a.id.toInt() > b.id.toInt(); // Sort by ID (descending)
              });

    // Display the top 10 players
    for (int i = 0; i < players.size() && i < 10; ++i)
    {
        // Create a label for the player's information
        QLabel *playerLabel = new QLabel(this);
        QString playerText = QString("%1. ID: %2 | Wins: %3 | Chips: $%4")
                                 .arg(i + 1)               // Rank
                                 .arg(players[i].id)       // Player ID
                                 .arg(players[i].handsWon) // Hands won
                                 .arg(players[i].chips);   // Chips

        // Style the player label based on rank
        if (i == 0)
        {
            playerLabel->setStyleSheet("font-size: 24px; font-weight: bold; color: gold;"); // Gold for 1st place
        }
        else if (i == 1)
        {
            playerLabel->setStyleSheet("font-size: 22px; font-weight: bold; color: silver;"); // Silver for 2nd place
        }
        else if (i == 2)
        {
            playerLabel->setStyleSheet("font-size: 20px; font-weight: bold; color: #cd7f32;"); // Bronze for 3rd place
        }
        else
        {
            playerLabel->setStyleSheet("font-size: 18px; font-weight: normal; color: white;"); // White for others
        }

        playerLabel->setText(playerText);
        playerLabel->setAlignment(Qt::AlignCenter); // Center-align the text

        // Add the player label to the leaderboard layout
        leaderboardLayout->addWidget(playerLabel);
    }

    // Add the leaderboard container to the main layout
    mainLayout->addWidget(leaderboardContainer);

    // Add spacing and set the layout
    mainLayout->addStretch(); // Add stretch to push content to the top
    setLayout(mainLayout);    // Set the main layout for the widget
}

// Function to load player data from the CSV file
QList<Leaderboard::Player> Leaderboard::loadPlayerData()
{
    QList<Player> players;     // List to store player data
    QFile file("players.csv"); // Open the CSV file

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        qDebug() << "Failed to open players.csv"; // Log an error if the file cannot be opened
        return players;                           // Return an empty list
    }

    QTextStream stream(&file); // Create a QTextStream to read the file
    while (!stream.atEnd())
    {
        QString line = stream.readLine();     // Read a line from the file
        QStringList fields = line.split(","); // Split the line into fields

        if (fields.size() >= 3) // Ensure the line has at least 3 fields
        {
            Player player;
            player.id = fields[0];               // Player ID
            player.handsWon = fields[1].toInt(); // Hands won
            player.chips = fields[2].toInt();    // Chips
            players.append(player);              // Add the player to the list
        }
    }

    file.close();   // Close the file
    return players; // Return the list of players
}

// Override closeEvent to emit the closed signal
void Leaderboard::closeEvent(QCloseEvent *event)
{
    emit closed();              // Emit the closed signal to notify the parent window
    QWidget::closeEvent(event); // Call the base class implementation
}