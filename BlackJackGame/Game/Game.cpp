#include "Game.h"

using namespace std;

// Constructor for the Game class
Game::Game(const QString &playerId, QWidget *parent)
    : QWidget(parent), playerId(playerId), totalChips(0), playerTurn(false), player(playerId.toStdString()), dealer() // Initialize member variables
{
    // Set the title of the game window
    setWindowTitle("BlackJack Game");

    // Set the fixed size of the game window
    setFixedSize(1000, 700);

    // Set up the background image for the game window
    backgroundLabel = new QLabel(this);
    QPixmap bgPixmap(":/images/blackjack.jpg");
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    backgroundLabel->setGeometry(0, 0, width(), height());
    backgroundLabel->lower();

    // Load the player's chip count from a CSV file
    loadPlayerData();

    // Create a label to display the player's total chip count
    totalChipsLabel = new QLabel(this);
    totalChipsLabel->setText(QString("Total: $%1").arg(totalChips));
    totalChipsLabel->setStyleSheet(
        "font-size: 18px; font-weight: bold; color: white; "
        "background-color: rgba(0, 0, 0, 0.7); padding: 5px;");
    totalChipsLabel->setAlignment(Qt::AlignLeft);
    totalChipsLabel->setGeometry(20, 20, 160, 40);

    // Create a label to display the number of hands won
    handsWonLabel = new QLabel(this);
    handsWonLabel->setText(QString("Wins: %1").arg(handsWon));
    handsWonLabel->setStyleSheet(
        "font-size: 18px; font-weight: bold; color: white; "
        "background-color: rgba(0, 0, 0, 0.7); padding: 5px;");
    handsWonLabel->setAlignment(Qt::AlignLeft);
    handsWonLabel->setGeometry(200, 20, 160, 40); // Position it beside the totalChipsLabel

    // Create a label and input field for placing bets
    QLabel *betLabel = new QLabel("Place Your Bet:", this);
    betLabel->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: rgba(0, 0, 0, 0.7); padding: 5px;");
    betLabel->setGeometry(20, 70, 160, 40); // Position at the top left

    betInput = new QLineEdit(this);
    betInput->setPlaceholderText("Enter bet amount");
    betInput->setStyleSheet(
        "font-size: 16px; color: black; background-color: white; "
        "padding: 5px; border: 2px solid grey; border-radius: 5px;");
    betInput->setGeometry(20, 120, 160, 40); // Position below the "Place Your Bet:" label

    // Create a "Place Bet" button
    placeBetButton = new QPushButton("Place Bet", this);
    placeBetButton->setGeometry(20, 170, 160, 40); // Position below the bet input field
    placeBetButton->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: #333; padding: 10px; border: 2px solid white; border-radius: 10px;");
    connect(placeBetButton, &QPushButton::clicked, this, &Game::placeBet);

    // Create a "Max Bet" button
    maxBetButton = new QPushButton("Max Bet", this);
    maxBetButton->setGeometry(20, 220, 160, 40); // Position below the "Place Bet" button
    maxBetButton->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: #333; padding: 10px; border: 2px solid white; border-radius: 10px;");
    connect(maxBetButton, &QPushButton::clicked, this, &Game::maxBet);

    // Create a "Start Game" button
    startButton = new QPushButton("Start Game", this);
    startButton->setGeometry(width() - 180, 20, 150, 50);
    startButton->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: #333; padding: 10px; border: 2px solid white; border-radius: 10px;");
    connect(startButton, &QPushButton::clicked, this, &Game::startGame);

    // Create a "Hit" button
    hitButton = new QPushButton("Hit", this);
    hitButton->setGeometry(width() / 2 - 250, height() - 80, 150, 50); // Moved 20 pixels lower
    hitButton->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: #333; padding: 10px; border: 2px solid white; border-radius: 10px;");
    hitButton->setEnabled(false); // Disable initially
    connect(hitButton, &QPushButton::clicked, this, &Game::hit);

    // Create a "Stand" button
    standButton = new QPushButton("Stand", this);
    standButton->setGeometry(width() / 2 + 100, height() - 80, 150, 50); // Moved 20 pixels lower
    standButton->setStyleSheet(
        "font-size: 16px; font-weight: bold; color: white; "
        "background-color: #333; padding: 10px; border: 2px solid white; border-radius: 10px;");
    standButton->setEnabled(false); // Disable initially
    connect(standButton, &QPushButton::clicked, this, &Game::stand);

    chipLabel = new QLabel(this);
    chipLabel->setGeometry(width() / 2 - 50, height() - 150, 100, 100);
    chipLabel->setScaledContents(true);
    chipLabel->hide();

    // Initialize card labels
    dealerCardLabels.clear();
    playerCardLabels.clear();
}

// Function to load the player's chip count from a CSV file
void Game::loadPlayerData()
{
    QFile file("players.csv");

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        totalChips = 100; // Default chip count if file cannot be opened
        handsWon = 0;     // Default hands won
        return;
    }

    QTextStream stream(&file);
    while (!stream.atEnd())
    {
        QString line = stream.readLine();
        QStringList fields = line.split(",");

        if (fields.size() >= 3 && fields[0] == playerId) // Ensure there are at least 3 fields
        {
            handsWon = fields[1].toInt();   // Load total wins
            totalChips = fields[2].toInt(); // Load total chips
            break;
        }
    }

    file.close();
}

void Game::savePlayerData()
{
    QFile file("players.csv");

    if (!file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        cerr << "Error opening player data file for reading!" << endl;
        return;
    }

    // Read all existing player data
    QTextStream in(&file);
    QStringList lines;
    while (!in.atEnd())
    {
        lines.append(in.readLine());
    }
    file.close();

    // Update the player's record or add a new one if it doesn't exist
    bool playerFound = false;
    for (int i = 0; i < lines.size(); ++i)
    {
        QStringList fields = lines[i].split(",");
        if (fields.size() >= 3 && fields[0] == playerId)
        {
            // Update the player's data
            lines[i] = QString("%1,%2,%3").arg(playerId).arg(handsWon).arg(totalChips);
            playerFound = true;
            break;
        }
    }

    if (!playerFound)
    {
        // Add a new record for the player
        lines.append(QString("%1,%2,%3").arg(playerId).arg(handsWon).arg(totalChips));
    }

    // Write the updated data back to the file
    if (!file.open(QIODevice::WriteOnly | QIODevice::Text | QIODevice::Truncate))
    {
        cerr << "Error opening player data file for writing!" << endl;
        return;
    }

    QTextStream out(&file);
    for (const QString &line : lines)
    {
        out << line << "\n";
    }
    file.close();
}

// Function to start the game
void Game::startGame()
{

    if (currentBet <= 0)
    {
        QMessageBox::warning(this, "No Bet", "Please place a bet before starting the game.");
        return;
    }

    // Disable the "Start Game" button
    startButton->setEnabled(false);

    deck = &Deck::instance();
    playBlackjackRound();
}

void Game::playBlackjackRound()
{
    // clear previous hands

    dealer.clearHand();

    player.clearHand();

    // deal inital hands, 2 cards for each player, 2 cards for dealer - 1 is hidden.
    dealer.dealInitialHands(&player);
    // cards dealt, players turn.

    // Display the player's cards
    size_t i = 0;
    for (const Card &card : player.getHand())
    {
        displayCard(card, false, i++, false); // Player's cards
    }

    // Display the dealer's first card and hide the second card
    displayCard(dealer.getHand()[0], true, 0, false); // Dealer's first card (visible)
    displayCard(dealer.getHand()[1], true, 1, true);  // Dealer's second card (hidden)

    playerTurn = true;
    player.showHand();
    dealer.showHand();

    int playerHandValue = player.getHandValue();
    int dealerHandValue = dealer.getHandValue();
    // Blackjack win
    if (playerHandValue == 21 && dealerHandValue != 21)
    {
        // Player wins with a 3:2 payout
        dealer.playTurn();
        endRound("Blackjack Win"); // End the game immediately
    }
    else if (playerHandValue == 21 && dealerHandValue == 21)
    {
        // Player push
        dealer.playTurn();
        displayCard(dealer.getHand()[0], true, 1, false);
        endRound("Blackjack Push");
    }
    else if ((playerHandValue = !21) && (dealerHandValue == 21))
    {
        // Player push
        dealer.playTurn();
        displayCard(dealer.getHand()[0], true, 1, false);
        endRound("Dealer Blackjack");
    }
    else
    {
        // Enable the "Hit" and "Stand" buttons, Player chooses what to do
        hitButton->setEnabled(true);
        standButton->setEnabled(true);
    }
}

// Function for the "Hit" button
void Game::hit()
{
    if (!playerTurn)
    {
        return;
    }

    Card card = Deck::instance().drawCard();
    player.receiveCard(card);
    displayCard(card, false, player.getHand().size() - 1, false);

    int handValue = player.getHandValue();
    if (handValue > 21)
    {
        endRound("Busted");
    }
}

// Function for the "Stand" button
void Game::stand()
{

    if (!playerTurn)
    {
        return;
    }
    // Disable the player's turn
    playerTurn = false;
    hitButton->setEnabled(false);
    standButton->setEnabled(false);

    // Dealer's turn
    DealerResult dealerResult = dealer.playTurn();
    // Display the dealer's remaining cards incrementally
    displayCard(dealer.getHand()[1], true, 1, false);
    QThread::msleep(500);
    for (size_t j = 1; j < dealer.getHand().size(); ++j)
    {
        displayCard(dealer.getHand()[j], true, j, false);
        QCoreApplication::processEvents(); // Allow UI to update
        QThread::msleep(500);              // Add a delay of 500ms between cards
    }

    // Determine the winner
    int playerValue = player.getHandValue();

    if (dealerResult.busted)
    {
        endRound("Win");
    }
    else if (playerValue > dealerResult.finalValue)
    {
        endRound("Win");
    }
    else if (dealerResult.finalValue > playerValue)
    {
        endRound("Lose");
    }
    else
    {
        endRound("Push");
    }
}

// Function to end the game and display a popup message
void Game::endRound(const QString &message)
{
    // Disable the "Hit" and "Stand" buttons
    hitButton->setEnabled(false);
    standButton->setEnabled(false);

    if (message == "Blackjack Win")
    {
        totalChips += static_cast<int>(currentBet * 1.5);
        handsWon++;
        handsWonLabel->setText(QString("Wins: %1").arg(handsWon));
        QMessageBox::information(this, "Round Over", "You have Blackjack! You win with a 3:2 payout.");
    }
    else if (message == "Blackjack Push")
    {
        QMessageBox::information(this, "Round Over", "Both you have the dealer have blackjack. Push.");
    }
    else if (message == "Dealer Blackjack")
    {
        totalChips -= currentBet;
        QMessageBox::information(this, "Round Over", "The dealer has blackjack. You lose.");
    }
    else if (message == "Win")
    {
        totalChips += currentBet; // Add the bet amount to the total chips
        handsWon++;
        handsWonLabel->setText(QString("Wins: %1").arg(handsWon)); // Update the hands won label
        QMessageBox::information(this, "Round Over", "You win!");
    }
    else if (message == "Lose")
    {
        totalChips -= currentBet; // Add the bet amount to the total chips
        QMessageBox::information(this, "Round Over", "Dealer wins.");
    }
    else if (message == "Busted")
    {
        totalChips -= currentBet; // Add the bet amount to the total chips
        QMessageBox::information(this, "Round Over", "You busted.");
    }
    else if (message == "Push")
    {
        QMessageBox::information(this, "Round Over", "You pushed.");
    }

    // Update the chip count label
    totalChipsLabel->setText(QString("Total: $%1").arg(totalChips));
    // Save the updated data to the CSV file
    savePlayerData();
    
    // Show a popup message
    QMessageBox::information(this, "Game Over", "Click start to play again!");

    // Clear previous card labels
    for (QLabel *label : dealerCardLabels)
        label->hide();
    for (QLabel *label : playerCardLabels)
        label->hide();
    dealerCardLabels.clear();
    playerCardLabels.clear();

    // Allow the player to restart the game
    startButton->setEnabled(true);
    betInput->setEnabled(true);
    placeBetButton->setEnabled(true);
    maxBetButton->setEnabled(true);
}

void Game::placeBet()
{
    // Get the bet amount from the input field
    bool ok;
    int bet = betInput->text().toInt(&ok);

    // Validate the bet amount
    if (!ok || bet <= 0)
    {
        QMessageBox::warning(this, "Invalid Bet", "Please enter a valid bet amount.");
        return;
    }
    if (bet > totalChips)
    {
        QMessageBox::warning(this, "Invalid Bet", "You cannot bet more than your total chips.");
        return;
    }

    // Set the current bet and start the game
    currentBet = bet;
    QMessageBox::information(this, "Bet Placed", QString("You placed a bet of $%1.").arg(currentBet));

    // Enable the "Start Game" button
    startButton->setEnabled(true);

    // Disable the bet input and button
    betInput->setEnabled(false);
    placeBetButton->setEnabled(false);
    maxBetButton->setEnabled(false);
    // Show the appropriate chip image based on the bet amount
    if (currentBet < 100)
    {
        chipLabel->setPixmap(QPixmap(":/images/smallchips.png"));
    }
    else if (currentBet <= 250)
    {
        chipLabel->setPixmap(QPixmap(":/images/mediumchips.png"));
    }
    else
    {
        chipLabel->setPixmap(QPixmap(":/images/largechips.png"));
    }
    chipLabel->show(); // Reveal the chip image
}

void Game::maxBet()
{
    if (totalChips <= 0)
    {
        QMessageBox::warning(this, "Invalid Bet", "You have no chips to bet.");
        return;
    }

    // Set the bet to the total chip count
    currentBet = totalChips;

    // Update the bet input field to reflect the max bet
    betInput->setText(QString::number(currentBet));

    QMessageBox::information(this, "Max Bet", QString("You placed a max bet of $%1.").arg(currentBet));

    // Disable the bet input and button
    betInput->setEnabled(false);
    placeBetButton->setEnabled(false);
}

// Override the closeEvent function to handle the window close event
void Game::closeEvent(QCloseEvent *event)
{
    emit closed();              // Emit a custom signal to notify that the window is closing
    QWidget::closeEvent(event); // Call the base class implementation to handle the close event
}

void Game::displayCard(const Card &card, bool isDealer, int cardIndex, bool hidden)
{
    QString fileName;
    if (hidden)
    {
        fileName = ":/images/Cards/cardBack_red.png"; // Use a placeholder image for hidden cards
    }
    else
    {
        QString suit;
        switch (card.getSuit())
        {
        case Clubs:
            suit = "Clubs";
            break;
        case Diamonds:
            suit = "Diamonds";
            break;
        case Hearts:
            suit = "Hearts";
            break;
        case Spades:
            suit = "Spades";
            break;
        }

        QString rank;
        switch (card.getRank())
        {
        case Ace:
            rank = "A";
            break;
        case Jack:
            rank = "J";
            break;
        case Queen:
            rank = "Q";
            break;
        case King:
            rank = "K";
            break;
        default:
            rank = QString::number(card.getRank());
        }

        fileName = QString(":/images/Cards/card%1%2.png").arg(suit).arg(rank);
    }

    // Create a QLabel for the card
    QLabel *cardLabel = new QLabel(this);
    QPixmap frontPixmap(fileName); // e.g. ":/images/Cards/cardSpadesA.png"
    QPixmap backPixmap(":/images/Cards/cardBack_red.png");
    cardLabel->setPixmap(backPixmap); // Show back first
    cardLabel->setScaledContents(true);
    cardLabel->setFixedSize(80, 120); // Set card size

    // Position the card
    int xOffset = cardIndex * 90; // Space between cards
    if (isDealer)
    {
        cardLabel->move(width() / 2 - 140 + xOffset, 90); // Dealer's cards
        dealerCardLabels.append(cardLabel);
    }
    else
    {
        cardLabel->move(width() / 2 - 140 + xOffset, height() - 320); // Player's cards
        playerCardLabels.append(cardLabel);
    }

    cardLabel->show();

    // Shrink animation (flip-in)
    QRect original = cardLabel->geometry();
    QRect flat = QRect(original.center().x(), original.y(), 1, original.height());

    QPropertyAnimation *shrink = new QPropertyAnimation(cardLabel, "geometry");
    shrink->setDuration(150);
    shrink->setStartValue(original);
    shrink->setEndValue(flat);
    shrink->setEasingCurve(QEasingCurve::InQuad);

    // Expand animation (flip-out)
    QPropertyAnimation *expand = new QPropertyAnimation(cardLabel, "geometry");
    expand->setDuration(150);
    expand->setStartValue(flat);
    expand->setEndValue(original);
    expand->setEasingCurve(QEasingCurve::OutQuad);

    // Switch pixmap at midpoint
    QObject::connect(shrink, &QPropertyAnimation::finished, [cardLabel, frontPixmap]()
                     { cardLabel->setPixmap(frontPixmap); });

    // Sequential animation group
    QSequentialAnimationGroup *flip = new QSequentialAnimationGroup(this);
    flip->addAnimation(shrink);
    flip->addAnimation(expand);
    flip->start(QAbstractAnimation::DeleteWhenStopped);
}
