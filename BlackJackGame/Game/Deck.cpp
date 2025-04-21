#include "Deck.h"

using namespace std;


Deck* Deck::_instance = NULL;
Deck& Deck::instance() 

{
    if (_instance == NULL) {
        _instance = new Deck();
    }
    return *_instance;
}

Deck::Deck() 

    : wasShuffled(false) 
    {
        std::random_device rd; // mersenne_twister_engine 
        rng = std::mt19937(rd()); // print random number 
        initDeck(); // populate shoe 
        shuffleDeck(); // suffle shoe
    }

Deck::~Deck() {
    // deconstructor, empty 
}

// populate shoe with 4 decks  
void Deck::initDeck() {
    cards.clear();
    for (int d = 0; d < 4; ++d) {
        for (int s = Hearts; s <= Spades; ++s) {
            for (int r = Two; r <= Ace; ++r) {
                Card card(static_cast<Rank>(r),static_cast<Suit>(s));
                cards.push_back(card);
            }
        }
    }
}

void Deck::shuffleDeck() {
    std::shuffle(cards.begin(), cards.end(), rng);
}



Card Deck::drawCard() {
    if (cards.empty()) { // always check if cards need shoe change 
        initDeck();
        shuffleDeck();
        wasShuffled = true; //set suffled flag
    }
    
    Card topCard = cards.back();
    cards.pop_back();
    return topCard;
}

bool Deck::checkAndResetShuffleSignal() {
    bool signal = wasShuffled;
    wasShuffled = false;
    return signal;
}

// helper function
size_t Deck::remainingCards() const {
    return cards.size();
}


void Deck::displayHandString(const std::vector<Card> &hand){
    for (size_t i = 0; i < hand.size(); i ++ ){
        hand[i].toString();
    }
}