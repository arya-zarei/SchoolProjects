#include "Card.h"
using namespace std;

Card::Card(Rank rank, Suit suit) {
    this->rank = rank;
    this->suit = suit;
}

Card::~Card()
{
    // No special cleanup required, but the destructor must be defined
}

string Card::toString() const
{

    // for printing and comparing cards
    const char *rankStrings[] = {
        "", // unused 0 index
        "", // unused 1 index, we will use in place logic for aces
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "J",
        "Q",
        "K",
        "A"};

    const char *suitStrings[] = {"Hearts", "Diamonds", "Clubs", "Spades"};

    return string(rankStrings[rank]) + " of  " + suitStrings[suit];
}



Rank Card::getRank() const
{
    return rank;
}

Suit Card::getSuit() const
{
    return suit;
}

void Card::setRank(Rank r)
{
    rank = r;
}

void Card::setSuit(Suit s)
{
    suit = s;
}
