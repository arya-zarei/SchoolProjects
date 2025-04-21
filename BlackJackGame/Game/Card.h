#ifndef CARD_H
#define CARD_H

#include <string>
#include <iostream>

// enum for card suits.
enum Suit {
    Hearts,
    Diamonds,
    Clubs,
    Spades
};

// enum for card ranks.
// will handle card value logic sepereatly, label just for displaying 
enum Rank {
    Two = 2, // 2
    Three,   // 3.. 
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,     // 10 - king will be value 10
    Jack,
    Queen,
    King,
    Ace      // 11 or
};


class Card {
    private:
        Rank rank;
        Suit suit;

    public:
        
        Card(Rank rank, Suit suit);
        ~Card(); // Declare the destructor
        Rank getRank() const;
        Suit getSuit() const;
        void setRank(Rank r);
        void setSuit(Suit s);

        // builds string for easy printing/testing 
        // return like "King of Hearts",
        std::string toString() const;
};

#endif
