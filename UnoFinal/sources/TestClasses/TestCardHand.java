package TestClasses;

import java.util.ArrayList;

import Main.uno3.Card;
import Main.uno3.CardDeck;
import Main.uno3.CardHand;
//import static Main.uno3.specialCardsV2.*;

public class TestCardHand {
    
    public TestCardHand() {
        CardDeck deck = new CardDeck("CardHand Test", true);

        for (Card card : deck.getCards()) {
            System.out.printf("%-16s", card);
//            System.out.printf("%5s%n", unoCardWildDrawFour(unocard));
            System.out.println("");
        }
        System.out.println();
        deck.shuffle();

        for (Card card : deck.getCards()) {
            System.out.printf("%-16s", card);
//            System.out.printf("%5s%n", unoCardWildDrawFour(unocard));
            System.out.println("");
        }
        System.out.println("-----CardHand Tests-------");

        CardHand player1;
        ArrayList<Card> ALPlayer1 = new ArrayList<Card>(); 
        player1 = new CardHand("P1",ALPlayer1);
        System.out.println(player1.getLabel());
        if (player1.empty())
            System.out.println("empty hand 1");
        System.out.println("");

        deck.deal(player1, 7);
        player1.display();
        System.out.println("P1 UnoHand Score " + CardHand.scoreCardHand(player1));
        System.out.println("");

        CardHand player2;
        ArrayList<Card> ALPlayer2 = new ArrayList<Card>();
        player2 = new CardHand("P2", ALPlayer2);
        System.out.println(player2.getLabel());
        if (player2.empty())
            System.out.println("empty hand 2");
        System.out.println("");

        deck.deal(player2, 7);
        player2.display();
        System.out.println("P2 UnoHand Score " + CardHand.scoreCardHand(player2));
        System.out.println("");

        CardHand player3;
        ArrayList<Card> ALPlayer3 = new ArrayList<Card>();
        player3 = new CardHand("P3", ALPlayer3);
        System.out.println(player3.getLabel());
        if (player3.empty())
            System.out.println("empty hand 3");
        System.out.println("");

        deck.deal(player3, 7);
        player3.display();
        System.out.println("P3 UnoHand Score " + CardHand.scoreCardHand(player3));
        System.out.println("");

        CardHand discardpile;
        ArrayList<Card> dPile = new ArrayList<Card>();
        discardpile = new CardHand("Discard Pile", dPile);
        if (discardpile.empty())
            System.out.println("Empty discard pile");
        System.out.println("");

        deck.deal(discardpile, 1);
        discardpile.display();
        System.out.println("");

        CardHand drawpile;
        ArrayList<Card> drPile = new ArrayList<>();
        drawpile = new CardHand("Draw Pile", drPile);
        if (drawpile.empty())
            System.out.println("Empty draw pile");
        System.out.println("");

        deck.dealAll(drawpile);
        System.out.println("Draw Pile size: " + drawpile.size());
        System.out.println("");

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
       TestCardHand test1; 
       test1 = new TestCardHand();
       

    }
}
