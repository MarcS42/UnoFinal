package TestClasses;

import java.util.ArrayList;

import Main.uno3.UnoCard;
import Main.uno3.UnoDeck;

public class TestUnoV2 {
    
    /**
     * @param deck
     * @return
     */
    public static UnoDeck cloneDeck(UnoDeck deck) {
        ArrayList<UnoCard> deckCopy = new ArrayList<>();
        UnoDeck deckClone = new UnoDeck("DeckCopy", deckCopy);
        for(UnoCard d:deck.getUnocards()) {
            deckClone.getUnocards().add(d);
        }
        return deckClone;
    }

    public static void main(String[] args) {
        
/** instantiate two decks, one as a regular filled UnoDeck,
     and another as an empty deck.
     then copy deck1 to empty deck and print side-by-side 
     to see if they are correct copies  
     Use will be for debugging: anomalies from playing
     deck1 can be repeated in debug mode because you keep
      a copy of the deck that caused the anomalies 
      */ 
        UnoDeck deck = new UnoDeck("Deck");
        deck.shuffle();
        UnoDeck deckClone = cloneDeck(deck);
        
        for(int index = 0; index < deck.size()-1; index++) {
            System.out.printf("%-16s\t%-16s%n", 
                deck.getUnocards().get(index),deckClone.getUnocards().get(index));
        }
        System.out.println(deckClone.size());
    }

    

}
