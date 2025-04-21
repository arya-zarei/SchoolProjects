#ifndef DEALER_H
#define DEALER_H

#include <iostream>
#include <sstream>
#include <vector>
#include "Card.h"
#include "Player.h"
#include <vector>
#include <iostream>
#include "Deck.h"
// struct to hold the result of the dealer's hand
struct DealerResult {
    int finalValue;
    bool busted;
};

class Dealer {
public:


    Dealer();
    ~Dealer();
    // deal starting hands for all players and the dealer
    void dealInitialHands(Player *player);
    
    
    // reveals the hidden card, then hits as needed (including on soft 17) 
    DealerResult playTurn();
    
    // dealer draw
    void receiveCard(const Card& card);
    
    // clears the dealer's hand for a new round
    void clearHand();
    
    // displays the dealer's hand
    // if revealAll is false, only the first card is shown (players arent finished their turn)
    void showHand(bool revealAll = false) const;
    
    // dealer deck value
    int getHandValue() const;
    // returns true if the hand is soft 
    bool isSoft() const;

    //hand getter
    const std::vector<Card>& getHand() const;
    
private:
    std::vector<Card> hand;
};

#endif 
