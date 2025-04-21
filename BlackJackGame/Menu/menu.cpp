#include "menu.h"

// Constructor for Menu
// Sets up the main menu UI for entering the game
Menu::Menu(QWidget *parent) : QWidget(parent)
{
    // Set window title and size
    setWindowTitle("BlackJack");
    setFixedSize(800, 600);

    // Set up background label (using Qt resource system: :/images/menu.png)
    backgroundLabel = new QLabel(this);
    QPixmap bgPixmap(":/images/menu.png");
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    // Scale the background image to fit the window size while maintaining its aspect ratio
    backgroundLabel->setGeometry(0, 0, width(), height());
    backgroundLabel->lower(); // Ensure it stays behind all other widgets

    // Title Label with a bold font
    titleLabel = new QLabel("Welcome to Blackjack", this);
    titleLabel->setAlignment(Qt::AlignCenter); // Center-align the title
    titleLabel->setStyleSheet("font-family: 'Impact'; font-size: 28px; font-weight: bold; color: white;");

    // Username Label with white text
    usernameLabel = new QLabel("ENTER USERNAME:", this);
    usernameLabel->setStyleSheet("font-family: 'Arial'; font-size: 14px; color: white;");

    // Username Input (Exactly 3 Digits Allowed)
    usernameInput = new QLineEdit(this);
    usernameInput->setMaxLength(3);                                              // Allow exactly 3 digits
    QRegExpValidator *validator = new QRegExpValidator(QRegExp("\\d{3}"), this); // Only allow 3-digit numbers
    usernameInput->setValidator(validator);
    usernameInput->setPlaceholderText("123"); // Placeholder text for guidance
    usernameInput->setFixedWidth(60);         // Smaller width to only fit 3 digits

    // Enter Game Button (styled with bold text and a modern look)
    enterGameButton = new QPushButton("ENTER GAME", this);
    enterGameButton->setStyleSheet("font-family: 'Arial Black'; font-size: 14px; font-weight: bold; "
                                   "color: white; background-color: #333; border: 2px solid white; "
                                   "border-radius: 5px; padding: 5px 10px;");
    enterGameButton->setFixedSize(120, 40); // Set the button size
    connect(enterGameButton, &QPushButton::clicked, this, &Menu::onEnterGameButtonClicked);

    // Layout (widgets will appear on top of the background)
    QVBoxLayout *layout = new QVBoxLayout();
    layout->addSpacerItem(new QSpacerItem(20, 400, QSizePolicy::Minimum, QSizePolicy::Expanding)); // Spacer to push widgets down
    layout->addWidget(titleLabel);                                                                 // Add the title label
    layout->addWidget(usernameLabel);                                                              // Add the username label
    layout->addWidget(usernameInput, 0, Qt::AlignCenter);                                          // Add the username input field
    layout->addWidget(enterGameButton, 0, Qt::AlignCenter);                                        // Add the Enter Game button
    layout->setAlignment(Qt::AlignCenter);                                                         // Center-align the layout
    setLayout(layout);                                                                             // Set the layout for the widget
}

// Override resizeEvent to update the background image size when the window is resized
void Menu::resizeEvent(QResizeEvent *event)
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

// Slot for handling Enter Game button click
void Menu::onEnterGameButtonClicked()
{
    QString playerId = usernameInput->text(); // Get the entered username

    // Validate the input
    if (playerId.isEmpty())
    {
        QMessageBox::warning(this, "Invalid Input", "Please enter a valid Player ID."); // Show a warning if input is empty
        return;
    }

    // Check or create player in the CSV file
    if (checkOrCreatePlayer(playerId))
    {
        emit usernameEntered(playerId); // Emit the signal with the username if successful
    }
    else
    {
        QMessageBox::critical(this, "Error", "Failed to access player data."); // Show an error if the operation fails
    }
}

// Function to check or create a player in the CSV file
bool Menu::checkOrCreatePlayer(const QString &playerId)
{
    QFile file("players.csv"); // Open the CSV file

    // Open the file in read/write mode, create it if it doesn't exist
    if (!file.open(QIODevice::ReadWrite | QIODevice::Text))
    {
        qDebug() << "Failed to open players.csv"; // Log an error if the file cannot be opened
        return false;
    }

    QTextStream stream(&file);
    QStringList lines; // List to store all lines from the file
    bool playerExists = false;

    // Read all lines from the file
    while (!stream.atEnd())
    {
        QString line = stream.readLine();
        lines.append(line); // Add the line to the list

        // Check if the player ID already exists
        QStringList fields = line.split(",");
        if (fields.size() > 0 && fields[0] == playerId)
        {
            playerExists = true; // Mark the player as existing
        }
    }

    // If the player does not exist, add them with default values
    if (!playerExists)
    {
        lines.append(QString("%1,0,100").arg(playerId)); // Default: 0 hands won, 100 chips
    }

    // Rewrite the file with updated data
    file.resize(0); // Clear the file
    for (const QString &line : lines)
    {
        stream << line << "\n"; // Write each line back to the file
    }

    file.close(); // Close the file
    return true;  // Return success
}
