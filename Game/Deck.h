#ifndef DECK_H
#define DECK_H

#include <algorithm>
#include <random>
#include<iostream>
#include <vector>

#include "Card.h"

class Deck {
public:


    virtual ~Deck();
    
    // returns the singleton instance
    static Deck& instance();
    

    // draws a card from the top. if empty, reinitializes and shuffles automatically
    // for dealing inital hands, and actions such as hit, dealer draw.
    Card drawCard();
    
    // checks if the deck was reshuffled since the last check and resets the signal
    // can give players a signal when a reshuffle is done, if we want to display that a shuffle has happened to our players
    bool checkAndResetShuffleSignal();
    
    // returns the number of remaining cards
    size_t remainingCards() const;

    void displayHandString(const std::vector<Card> &hand);
    
protected:
    Deck();
    
private:

    Deck(const Deck& other) = delete;
    Deck& operator=(const Deck& other) = delete;
    
    // initializes the shoe with 4 decks (4 * 52 cards).
    void initDeck();
    
    // shuffle the deck
    void shuffleDeck();
    
    std::vector<Card> cards;
    bool wasShuffled; 
    std::mt19937 rng; 
    
    static Deck* _instance;
};

#endif 
