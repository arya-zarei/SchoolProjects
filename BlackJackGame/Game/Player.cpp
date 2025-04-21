#include "Player.h"

using namespace std;

Player::Player(string id)
    : id(id) 
{
    // empty
}

Player::~Player(){
    // empty
}
void Player::receiveCard(const Card& card) {
    hand.push_back(card);
}

void Player::showHand() const
{
    for (size_t i = 0; i < hand.size(); i++)
    {
    }
}

void Player::clearHand()
{
    hand.clear();
}

const std::vector<Card> &Player::getHand() const
{
    return hand;
}

int Player::getHandValue() const
{
    int total = 0;
    int aces = 0; 

    // treat every Ace as 11
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

    // convert Aces from 11 to 1 if total is ovr 21
    while (total > 21 && aces > 0) {
        total -= 10;  // convert one Ace from 11 to 1
        aces--;
    }

    return total;
}

string Player::getId(){
    return this->id;
}