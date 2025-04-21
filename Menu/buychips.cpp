#include "buychips.h"

// Constructor for the BuyChips class
// Initializes the UI for buying chips and loads the player's chip data
BuyChips::BuyChips(const QString &playerId, QWidget *parent)
    : QWidget(parent), playerId(playerId), totalChips(100) // Initialize member variables
{
    // Load the player's chip count from the CSV file
    loadPlayerData();

    // Set the window title and size
    setWindowTitle("Buy Chips");
    setFixedSize(800, 600);

    // Set up the background image for the window
    QLabel *backgroundLabel = new QLabel(this); // Create a QLabel for the background
    QPixmap bgPixmap(":/images/tutorial.jpg");  // Load the background image using the Qt resource system
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    // Scale the image to fit the window size while maintaining its aspect ratio
    backgroundLabel->setGeometry(0, 0, width(), height()); // Position the background to cover the entire window
    backgroundLabel->lower();                              // Ensure the background stays behind all other widgets

    // Create a label to display the player's total chip count
    QLabel *totalChipsLabel = new QLabel(this);
    totalChipsLabel->setText(QString("Total: $%1").arg(totalChips)); // Display the total chip count
    totalChipsLabel->setStyleSheet(
        "font-size: 18px; font-weight: bold; color: white; "
        "background-color: rgba(0, 0, 0, 0.7); padding: 5px;"); // Style the label with a semi-transparent background and bold white text
    totalChipsLabel->setAlignment(Qt::AlignRight);              // Align the text to the right
    totalChipsLabel->setGeometry(width() - 180, 20, 160, 40);   // Position the label in the top-right corner

    // Create a container for the chip buttons
    QWidget *chipContainer = new QWidget(this);
    chipContainer->setStyleSheet("background-color: rgba(0, 0, 0, 0.7); border-radius: 15px; padding: 20px;");
    chipContainer->setGeometry(150, 150, 500, 300); // Position the container in the center of the window

    // Create "+" buttons for each chip type
    QPushButton *whiteChipPlus = new QPushButton("+", this);
    QPushButton *redChipPlus = new QPushButton("+", this);
    QPushButton *greenChipPlus = new QPushButton("+", this);
    QPushButton *blackChipPlus = new QPushButton("+", this);

    // Style the "+" buttons
    QString plusButtonStyle = "font-size: 20px; font-weight: bold; color: white; "
                              "background-color: #555; border: 2px solid white; "
                              "border-radius: 10px; padding: 5px 15px;";
    whiteChipPlus->setStyleSheet(plusButtonStyle);
    redChipPlus->setStyleSheet(plusButtonStyle);
    greenChipPlus->setStyleSheet(plusButtonStyle);
    blackChipPlus->setStyleSheet(plusButtonStyle);

    // Set the size of the "+" buttons
    QSize plusButtonSize(50, 33);
    whiteChipPlus->setFixedSize(plusButtonSize);
    redChipPlus->setFixedSize(plusButtonSize);
    greenChipPlus->setFixedSize(plusButtonSize);
    blackChipPlus->setFixedSize(plusButtonSize);

    // Create buttons for each chip type
    QPushButton *whiteChipButton = new QPushButton("White Chip ($1)", this);
    QPushButton *redChipButton = new QPushButton("Red Chip ($5)", this);
    QPushButton *greenChipButton = new QPushButton("Green Chip ($25)", this);
    QPushButton *blackChipButton = new QPushButton("Black Chip ($100)", this);

    // Style the chip buttons
    QString chipButtonStyle = "font-size: 16px; font-weight: bold; color: white; "
                              "background-color: #333; border: 2px solid white; "
                              "border-radius: 10px; padding: 5px 10px;";
    whiteChipButton->setStyleSheet(chipButtonStyle);
    redChipButton->setStyleSheet(chipButtonStyle);
    greenChipButton->setStyleSheet(chipButtonStyle);
    blackChipButton->setStyleSheet(chipButtonStyle);

    // Adjust the size of the chip buttons
    whiteChipButton->setSizePolicy(QSizePolicy::Minimum, QSizePolicy::Fixed);
    redChipButton->setSizePolicy(QSizePolicy::Minimum, QSizePolicy::Fixed);
    greenChipButton->setSizePolicy(QSizePolicy::Minimum, QSizePolicy::Fixed);
    blackChipButton->setSizePolicy(QSizePolicy::Minimum, QSizePolicy::Fixed);

    // Create a grid layout for the chip buttons and "+" buttons
    QGridLayout *chipLayout = new QGridLayout();
    chipLayout->addWidget(whiteChipButton, 0, 0);
    chipLayout->addWidget(whiteChipPlus, 0, 1);
    chipLayout->addWidget(redChipButton, 1, 0);
    chipLayout->addWidget(redChipPlus, 1, 1);
    chipLayout->addWidget(greenChipButton, 2, 0);
    chipLayout->addWidget(greenChipPlus, 2, 1);
    chipLayout->addWidget(blackChipButton, 3, 0);
    chipLayout->addWidget(blackChipPlus, 3, 1);

    chipContainer->setLayout(chipLayout); // Set the layout for the chip container

    // Connect "+" buttons to increment the chip count
    connect(whiteChipPlus, &QPushButton::clicked, [=]()
            {
                totalChips += 1;                                                 // Add 1 chip
                totalChipsLabel->setText(QString("Total: $%1").arg(totalChips)); // Update the label
            });
    connect(redChipPlus, &QPushButton::clicked, [=]()
            {
                totalChips += 5;                                                 // Add 5 chips
                totalChipsLabel->setText(QString("Total: $%1").arg(totalChips)); // Update the label
            });
    connect(greenChipPlus, &QPushButton::clicked, [=]()
            {
                totalChips += 25;                                                // Add 25 chips
                totalChipsLabel->setText(QString("Total: $%1").arg(totalChips)); // Update the label
            });
    connect(blackChipPlus, &QPushButton::clicked, [=]()
            {
                totalChips += 100;                                               // Add 100 chips
                totalChipsLabel->setText(QString("Total: $%1").arg(totalChips)); // Update the label
            });
}

// Function to load the player's chip count from a CSV file
void BuyChips::loadPlayerData()
{
    QFile file("players.csv"); // Open the CSV file

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        qDebug() << "Failed to open players.csv"; // Log an error if the file cannot be opened
        return;
    }

    QTextStream stream(&file); // Create a QTextStream to read the file
    while (!stream.atEnd())
    {
        QString line = stream.readLine();     // Read a line from the file
        QStringList fields = line.split(","); // Split the line into fields

        if (fields.size() >= 3 && fields[0] == playerId) // Check if the line matches the player's ID
        {
            totalChips = fields[2].toInt(); // Load the chip count
            break;
        }
    }

    file.close(); // Close the file
}

// Function to save the player's chip count to a CSV file
void BuyChips::savePlayerData()
{
    QFile file("players.csv"); // Open the CSV file

    if (!file.open(QIODevice::ReadWrite | QIODevice::Text))
    {
        qDebug() << "Failed to open players.csv"; // Log an error if the file cannot be opened
        return;
    }

    QTextStream stream(&file);
    QStringList lines;
    bool playerFound = false;

    // Read all lines and update the player's chip count
    while (!stream.atEnd())
    {
        QString line = stream.readLine();
        QStringList fields = line.split(",");

        if (fields.size() >= 3 && fields[0] == playerId)
        {
            fields[2] = QString::number(totalChips); // Update the chip count
            line = fields.join(",");
            playerFound = true;
        }

        lines.append(line);
    }

    // If the player was not found, add a new entry
    if (!playerFound)
    {
        lines.append(QString("%1,0,%2").arg(playerId).arg(totalChips));
    }

    // Rewrite the file with updated data
    file.resize(0);
    for (const QString &line : lines)
    {
        stream << line << "\n";
    }

    file.close(); // Close the file
}

// Override closeEvent to save the player's chip count and emit the closed signal
void BuyChips::closeEvent(QCloseEvent *event)
{
    savePlayerData();           // Save the updated chip count
    emit closed();              // Emit the closed signal
    QWidget::closeEvent(event); // Call the base class implementation
}