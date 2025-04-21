#include "tutorial.h"

// Constructor for Tutorial
// Sets up the tutorial window with game instructions
Tutorial::Tutorial(QWidget *parent) : QWidget(parent)
{
    // Set window title and size
    setWindowTitle("Tutorial");
    setFixedSize(800, 600);

    // Set up background label (using Qt resource system: :/images/tutorial.jpg)
    backgroundLabel = new QLabel(this);
    QPixmap bgPixmap(":/images/tutorial.jpg"); // Load the background image
    backgroundLabel->setPixmap(bgPixmap.scaled(size(), Qt::KeepAspectRatioByExpanding, Qt::SmoothTransformation));
    // Scale the background image to fit the window size while maintaining its aspect ratio
    backgroundLabel->setGeometry(0, 0, width(), height()); // Position the background to cover the entire window
    backgroundLabel->lower();                              // Ensure it stays behind all other widgets

    // Create a label for the tutorial content
    QLabel *tutorialContent = new QLabel(this);
    tutorialContent->setText(
        "<div style='text-align: center; font-family: Arial; color: white;'>"
        "<h1>🃏 Blackjack Tutorial & Game Instructions</h1>"
        "<p>Welcome to <b>Blackjack</b>! Whether you're a beginner or need a quick refresher, this guide will walk you through how to play one of the most popular card games in the world.</p>"
        "<hr>"
        "<h2>🃏 Objective of the Game</h2>"
        "<p>The goal of Blackjack is simple:<br><b>Get as close to 21 as possible without going over.</b><br>Beat the dealer by having a hand total higher than the dealer's, or by the dealer busting (going over 21).</p>"
        "<hr>"
        "<h2>🃏 Card Values</h2>"
        "<ul>"
        "<li><b>Number cards (2–10)</b>: Face value</li>"
        "<li><b>Face cards (J, Q, K)</b>: 10 points</li>"
        "<li><b>Ace (A)</b>: Can be <b>1 or 11</b>, whichever benefits your hand most</li>"
        "</ul>"
        "<p><i>Example: A hand with an Ace and 9 = 20 (Ace counts as 11)</i></p>"
        "<hr>"
        "<h2>🃏 Game Setup</h2>"
        "<ul>"
        "<li>Blackjack is typically played with <b>1 to 5 players</b> against a <b>dealer</b>.</li>"
        "<li>Each player is dealt <b>two face-up cards</b>.</li>"
        "<li>The <b>dealer receives one face-up and one face-down card</b>.</li>"
        "</ul>"
        "<hr>"
        "<h2>🃏 How to Play</h2>"
        "<h3>1. Initial Deal</h3>"
        "<p>You and the dealer each receive two cards. You see both your cards and <b>one of the dealer’s</b>.</p>"
        "<h3>2. Blackjack Check</h3>"
        "<p>If you have an <b>Ace + 10</b> (face card or 10), you have <b>Blackjack!</b><br>If the dealer also has Blackjack, it’s a <b>tie (push)</b>.</p>"
        "<h3>3. Player’s Turn</h3>"
        "<p>You can choose to:</p>"
        "<ul>"
        "<li><b>Hit</b>: Take another card</li>"
        "<li><b>Stand</b>: Keep your current hand</li>"
        "<li><b>Double Down</b>: Double your bet, take <b>one</b> final card, and stand</li>"
        "<li><b>Split</b>: If you have two cards of the same value, split into two hands and play each separately</li>"
        "</ul>"
        "<h3>4. Dealer’s Turn</h3>"
        "<p>Dealer reveals the hidden card.<br>Dealer must <b>hit</b> until their hand totals <b>17 or more</b>.<br>Dealer must <b>stand</b> on <b>17 or more</b>.<br>If the dealer busts (over 21), you <b>win!</b></p>"
        "<hr>"
        "<h2>🃏 Winning the Game</h2>"
        "<table border='1' style='margin: 0 auto; color: white;'>"
        "<tr><th>Scenario</th><th>Result</th></tr>"
        "<tr><td>Your hand > dealer’s (≤ 21)</td><td>You win</td></tr>"
        "<tr><td>Dealer busts (goes over 21)</td><td>You win</td></tr>"
        "<tr><td>Your hand = dealer’s</td><td>Push (tie)</td></tr>"
        "<tr><td>Your hand < dealer’s</td><td>You lose</td></tr>"
        "<tr><td>You bust (over 21)</td><td>You lose</td></tr>"
        "<tr><td>You get Blackjack</td><td>1.5x payout</td></tr>"
        "</table>"
        "<hr>"
        "<h2>🃏 Tips & Strategies</h2>"
        "<ul>"
        "<li>Always <b>stand</b> on a hard 17 or more.</li>"
        "<li><b>Hit</b> on anything below 12 if the dealer shows a 7–Ace.</li>"
        "<li><b>Split</b> Aces and 8s.</li>"
        "<li><b>Never split</b> 10s — you're starting with 20!</li>"
        "</ul>"
        "<hr>"
        "<h2>🃏 Quick Terms</h2>"
        "<ul>"
        "<li><b>Bust</b>: When your hand goes over 21</li>"
        "<li><b>Push</b>: A tie with the dealer</li>"
        "<li><b>Natural Blackjack</b>: An Ace + 10-value card on the initial deal</li>"
        "</ul>"
        "<p>Let the games begin! <br><b>Ready to put your skills to the test?</b></p>"
        "</div>");
    tutorialContent->setWordWrap(true); // Enable word wrapping for the content
    tutorialContent->setStyleSheet("background-color: rgba(0, 0, 0, 0.7); padding: 20px; border-radius: 10px;");
    tutorialContent->setAlignment(Qt::AlignCenter); // Center-align the content

    // Create a scroll area for the content
    QScrollArea *scrollArea = new QScrollArea(this);
    scrollArea->setWidget(tutorialContent); // Add the tutorial content to the scroll area
    scrollArea->setWidgetResizable(true);   // Allow the content to resize with the scroll area
    scrollArea->setStyleSheet("background: transparent; border: none;");

    // Create a layout to center the scroll area
    QVBoxLayout *layout = new QVBoxLayout(this);
    layout->addWidget(scrollArea); // Add the scroll area to the layout
    setLayout(layout);             // Set the layout for the widget
}

// Override closeEvent to emit the closed signal
void Tutorial::closeEvent(QCloseEvent *event)
{
    emit closed();              // Emit the closed signal to notify the parent window
    QWidget::closeEvent(event); // Call the base class implementation
}