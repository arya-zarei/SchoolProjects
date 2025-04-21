#ifndef PLAYER_H
#define PLAYER_H

#include "Card.h"
#include <vector>
#include <iostream>
#include <string>
class Player
{
public:
    Player(std::string id);
    ~Player();
    void receiveCard(const Card &card);
    void showHand() const;

    void clearHand();

    const std::vector<Card> &getHand() const;
    int getHandValue() const;

    std::string getId();

private:
    std::vector<Card> hand;
    const std::string id;
};

#endif
