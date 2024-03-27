package com.kalashnikov;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static ArrayList<Card> deck = new ArrayList<Card>();
    public static ArrayList<Card> playerHand = new ArrayList<Card>();
    public static ArrayList<Card> dealerHand = new ArrayList<Card>();
    public static String[] suit = {"d", "h", "s", "c"};
    public static int[] value = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    public static int suitNumber = 0;
    public static int valueNumber = 0;
    public static int playerTotal;
    public static int dealerTotal;
    public static boolean playerNatural = false;
    public static boolean dealerNatural = false;

    public static void clearScreen() {
        //System.out.print("\033[H\033[2J");
        System.out.print("\033c");
        System.out.flush();
    }

    public static void initDeck() {
        deck.clear();
        for (suitNumber = 0; suitNumber < suit.length; suitNumber++) {
            for (valueNumber = 0; valueNumber < value.length; valueNumber++) {
                deck.add(new Card(value[valueNumber], suit[suitNumber]));
            }
        }
    }

    public static void dealCard(String player, int amount) {
        Random r = new Random();
        if (player == "player") {
            for (int i = 0; i < amount; i++) {
                int randCard = r.nextInt(deck.size() + 1);
                playerHand.add(deck.get(randCard));
                deck.remove(randCard);
            }
        } else {
            for (int i = 0; i < amount; i++) {
                int randCard = r.nextInt(deck.size() + 1);
                dealerHand.add(deck.get(randCard));
                deck.remove(randCard);
            }
        }
    }


    public static void displayHands() {
        for (Card card : playerHand) {
            if (card.getValue() == 1) {
                System.out.print("A" + card.getSuit() + ", ");
            } else if (card.getValue() == 11) {
                System.out.print("J" + card.getSuit() + ", ");
            } else if (card.getValue() == 12) {
                System.out.print("Q" + card.getSuit() + ", ");
            } else if (card.getValue() == 13) {
                System.out.print("K" + card.getSuit() + ", ");
            } else {
                System.out.print(card.getValue() + card.getSuit() + ", ");
            }
        }
        System.out.println();
        for (Card card : dealerHand) {
            if (card.getValue() == 1) {
                System.out.print("A" + card.getSuit() + ", ");
            } else if (card.getValue() == 11) {
                System.out.print("J" + card.getSuit() + ", ");
            } else if (card.getValue() == 12) {
                System.out.print("Q" + card.getSuit() + ", ");
            } else if (card.getValue() == 13) {
                System.out.print("K" + card.getSuit() + ", ");
            } else {
                System.out.print(card.getValue() + card.getSuit() + ", ");
            }
        }
        System.out.println("\nPlayer total: " + playerTotal);
        System.out.println("Dealer total: " + dealerTotal);
    }

    public static void addTotals() {
        playerTotal = 0;
        dealerTotal = 0;
        for (Card card : playerHand) {
            playerTotal += card.getValue();
        }
        for (Card card : dealerHand) {
            dealerTotal += card.getValue();
        }
    }

    public static void switchCard() {
        if (!playerNatural || !dealerNatural) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter card number to swap (1-4): ");
            int indexToSwitch = scanner.nextInt() - 1;

            playerHand.remove(indexToSwitch);
            dealCard("player", 1);

            int indexOfLowest = 0;
            for (int i = 0; i < dealerHand.size(); i++) {
                if (dealerHand.get(i).getValue() < indexOfLowest) {
                    indexOfLowest = i;
                }
            }
            dealerHand.remove(indexToSwitch);
            dealCard("dealer", 1);
        }
    }

    public static String findWinner() {
        if (playerTotal > dealerTotal) {
            return "player";
        } else if (dealerTotal > playerTotal) {
            return "dealer";
        } else {
            return "tie";
        }
    }
    // i don't like the way i do this, but it's 4 am and i'm feeling froggy fresh
    public static void determineNatural() {
        boolean aceFound = false;
        boolean kingFound = false;
        boolean fourFound = false;
        boolean sevenFound = false;
        for (Card card : playerHand) {
            if (card.getValue() == 1) {
                aceFound = true;
            }
            if (card.getValue() == 13) {
                kingFound = true;
            }
            if (card.getValue() == 4) {
                fourFound = true;
            }
            if (card.getValue() == 7) {
                sevenFound = true;
            }
        }
        if (aceFound && kingFound && fourFound && sevenFound) {
            playerNatural = true;
        }
        aceFound = false;
        kingFound = false;
        fourFound = false;
        sevenFound = false;
        for (Card card : dealerHand) {
            if (card.getValue() == 1) {
                aceFound = true;
            }
            if (card.getValue() == 13) {
                kingFound = true;
            }
            if (card.getValue() == 4) {
                fourFound = true;
            }
            if (card.getValue() == 7) {
                sevenFound = true;
            }
        }
        if (aceFound && kingFound && fourFound && sevenFound) {
            dealerNatural = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        initDeck();
        dealCard("player", 4);
        dealCard("dealer", 4);
        addTotals();
        displayHands();
        determineNatural();
        if (playerNatural) {
            System.out.println("Player wins with AK47");
            System.exit(0);
        } else if (dealerNatural) {
            System.out.println("Dealer wins with AK47");
            System.exit(0);
        }
        switchCard();
        addTotals();
        TimeUnit.SECONDS.sleep(3);
        clearScreen();
        displayHands();
        if (findWinner() == "player"){
            System.out.println("Player wins!");
        } else if (findWinner() == "dealer") {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
        System.exit(0);
    }
}