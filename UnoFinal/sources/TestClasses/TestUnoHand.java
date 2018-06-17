package TestClasses;

import java.util.ArrayList;

import Main.uno3.UnoCard;
import Main.uno3.UnoDeck;
import Main.uno3.UnoHand;
import static Main.uno3.UnoSpecialCards.*;

public class TestUnoHand {
    
    public TestUnoHand() {
        UnoDeck deck = new UnoDeck("UnoHand Test");

//        for (UnoCard unocard : deck.getUnocards()) {
//            System.out.printf("%-16s", unocard);
//            System.out.printf("%5s%n", unoCardWildDrawFour(unocard));
//        }
//        System.out.println();
        deck.shuffle();

        for (UnoCard unocard : deck.getUnocards()) {
            System.out.printf("%-16s", unocard);
            System.out.printf("%5s%n", unoCardWildDrawFour(unocard));
        }
        System.out.println("------------");

        UnoHand player1;
        ArrayList<UnoCard> ALPlayer1 = new ArrayList<UnoCard>(); 
        player1 = new UnoHand("P1",ALPlayer1);
        System.out.println(player1.getLabel());
        if (player1.empty())
            System.out.println("empty hand 1");
        System.out.println("");

        deck.deal(player1, 7);
        player1.display();
        System.out.println("P1 UnoHand Score " + UnoHand.scoreHandUno(player1));
        System.out.println("");

        UnoHand player2;
        ArrayList<UnoCard> ALPlayer2 = new ArrayList<UnoCard>();
        player2 = new UnoHand("P2", ALPlayer2);
        System.out.println(player2.getLabel());
        if (player2.empty())
            System.out.println("empty hand 2");
        System.out.println("");

        deck.deal(player2, 7);
        player2.display();
        System.out.println("P2 UnoHand Score " + UnoHand.scoreHandUno(player2));
        System.out.println("");

        UnoHand player3;
        ArrayList<UnoCard> ALPlayer3 = new ArrayList<UnoCard>();
        player3 = new UnoHand("P3", ALPlayer3);
        System.out.println(player3.getLabel());
        if (player3.empty())
            System.out.println("empty hand 3");
        System.out.println("");

        deck.deal(player3, 7);
        player3.display();
        System.out.println("P3 UnoHand Score " + UnoHand.scoreHandUno(player3));
        System.out.println("");

        UnoHand discardpile;
        ArrayList<UnoCard> dPile = new ArrayList<UnoCard>();
        discardpile = new UnoHand("Discard Pile", dPile);
        if (discardpile.empty())
            System.out.println("Empty discard pile");
        System.out.println("");

        deck.deal(discardpile, 1);
        discardpile.display();
        System.out.println("");

        UnoHand drawpile;
        ArrayList<UnoCard> drPile = new ArrayList<>();
        drawpile = new UnoHand("Draw Pile", drPile);
        if (drawpile.empty())
            System.out.println("Empty draw pile");
        System.out.println("");

        deck.dealAll(drawpile);
        System.out.println("Draw Pile size: " + drawpile.size());
        System.out.println("");

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
       TestUnoHand test1; 
       test1 = new TestUnoHand();
       
        // for(int indexP=0;indexP < 3; indexP++) {
        // int indexNextPlayer;
        // int indexIncrement=2;
        // int playersSize=3;
        // if (skip) {
        // indexIncrement = 2;
        // } else {
        // indexIncrement = 1;
        // }
        // if (clockwise) {
        // indexNextPlayer = (players.indexOf(current) + indexIncrement) %
        // players.size();
        // } else {
        // indexNextPlayer = (indexP - indexIncrement) % playersSize;
        // if (indexNextPlayer < 0 && indexP==1) {// need to fix this when 3 players,
        // counter clockwise, and skip on currentplayer 1
        // indexNextPlayer = playersSize - indexIncrement +1;// 1-2=-1 => <0 =>3-2 = 1
        // again
        // }else if(indexNextPlayer <0) {
        // indexNextPlayer = playersSize-indexIncrement;
        // }
        // System.out.println(indexP + " "+ indexNextPlayer);
        // }
        //
        // }

    }
}