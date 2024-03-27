package com.kalashnikov;

import java.util.ArrayList;

public class Card {
    public int value;
    public String suit;

    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

}
