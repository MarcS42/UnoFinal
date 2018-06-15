package TestClasses;

import Main.uno3.CardAceHi;
import Main.uno3.DeckAceHi;

public class TestDeckAceHi {

    public static void main(String[] args) {
        DeckAceHi deckAceHiTest; 
        deckAceHiTest = new DeckAceHi("DeckAceHiTest");
        
        System.out.println("DeckAceHiTest full deck: ");
        for(CardAceHi card:deckAceHiTest.getCardSAceHi()) {
            System.out.printf("%-17s"+" ", card);
            System.out.printf("%3d%n", CardAceHi.scoreCard(card));
        }
        System.out.println();
        
        deckAceHiTest.shuffle();
        
        System.out.println("DeckAceHiTest Shuffle(CardDeck): ");
        for(CardAceHi card:deckAceHiTest.getCardSAceHi()) {
            System.out.printf("%-17s"+" ", card);
            System.out.printf("%3d%n", CardAceHi.scoreCard(card));
        }
        System.out.println();
        
        
    }

}
