#include "Dealer.h"



using namespace std;


Dealer::Dealer() {

}

Dealer::~Dealer(){

}
void Dealer::dealInitialHands(Player *player)
{
    // deal inital hands following blackjack sequence 
    // each player receives one card

    //players first card
    player->receiveCard(Deck::instance().drawCard()); 
    

    // dealers first (visible) card
    receiveCard(Deck::instance().drawCard());

    // players second card
    player->receiveCard(Deck::instance().drawCard());
    
    // dealer second (hdden) card
    receiveCard(Deck::instance().drawCard());
}



   //adds a card to the dealer's hand.
void Dealer::receiveCard(const Card& card)
{
    hand.push_back(card); 
}

// set up next round
void Dealer::clearHand()
{
    hand.clear(); 
}

/*

   if revealAll is false, only show the first card;
   otherwise, display the entire hand.
*/
void Dealer::showHand(bool revealAll) const
{
    if (hand.empty()) {
    } else {
        if (revealAll) {
            // display every card in the dealer's hand
            for (size_t i = 0; i < hand.size(); i++) {
            }
        } else {
        }
    }
}


/*
   calculates the dealer's total. Aces are counted as 11
   by default, then reduced to 1 if the total exceeds 21
*/
int Dealer::getHandValue() const
{
    int total = 0;
    int aces = 0; 

    // treat every ace as 11
    for (size_t i = 0; i < hand.size(); i++) {
        Rank r = hand[i].getRank();
        if (r >= Two && r <= Nine) {
            total += r; 
        } 
        else if (r >= Ten && r < Ace) {
            total += 10; 
        }
        else if (r == Ace) {
            total += 11;
            aces++;
        }
    }

    // convert aces from 11 to 1 if total is ovr 21
    while (total > 21 && aces > 0) {
        total -= 10;  // convert one Ace from 11 to 1
        aces--;
    }
    
    return total;
}

const std::vector<Card>& Dealer::getHand() const {
    return hand;
}

 
   // return true if a ace is in deck and counted as a 11
bool Dealer::isSoft() const
{
    int hardTotal = 0;
    int aceCount = 0;

    // compute a "hard" total counting all aces as 1
    for (size_t i = 0; i < hand.size(); i++) {
        if (hand[i].getRank() == Ace) {
            hardTotal += 1;
            aceCount++;
        } else if (hand[i].getRank() >= Two && hand[i].getRank() <= Ten) {
            hardTotal += hand[i].getRank();
        } else {
            hardTotal += 10; 
        }
    }

    return (aceCount > 0 && hardTotal + 10 <= 21);
}

/*
 
   reveals the dealer's hidden card, then hits until
   reaching at least 17 or busting. returns a DealerResult
   struct with the final hand value and bust status.
*/
DealerResult Dealer::playTurn()
{
    showHand(true); // reveal all cards

    int value = getHandValue();
    // dealer hits if under 17 or if its a soft 17
    while (value < 17 || (value == 17 && isSoft()) || (value > 21 && isSoft())) {
        Card card = Deck::instance().drawCard();
        receiveCard(card);
        value = getHandValue();
    }

    DealerResult result;
    result.finalValue = value;
    result.busted = (value > 21);
    return result;
}

