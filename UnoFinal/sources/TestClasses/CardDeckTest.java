/**
 * 
 */
package TestClasses;

import static Main.uno3.Card.isAceHi;
import static Main.uno3.CardDeck.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Main.uno3.CardDeck;

/**
 * @author MarcSherman
 *
 */
class CardDeckTest {

    @BeforeAll
    static void initAll() {
        CardDeck deckAceHi = new CardDeck("deckTest2", true);
        
        System.out.println("Test deckTest2 pre shuffle AceHi: " + isAceHi());
        printDeck(deckAceHi); 
        System.out.println("");
        
        
        deckAceHi.shuffle();
        cloneDeck(deckAceHi);
        
        System.out.println("Test deckTest2 Shuffled AceHi: " + isAceHi());
        printDeck(deckAceHi); 
        System.out.println("");
        
        CardDeck replayDeckHi = deserializeCardDeck();
        
        System.out.println("Test deserialize replayDeckHi: " + isAceHi());
        printDeck(replayDeckHi); 
        System.out.println("");
        
    }
    
    /**
     * Test method for {@link Main.uno3.CardDeck#CardDeck(java.lang.String)}.
     */
    @Test
    final void testCardDeckString() {
        CardDeck deck = new CardDeck("deckTest", true);
        String shouldBelabel = "deckTest";
        boolean shouldBeAceHi = true;
        
        assertEquals(deck.getLabel(), shouldBelabel,
                "construtor failed Label");
        assertEquals(true, shouldBeAceHi,
                "construtor failed AceHi");
       
    }

    /**
     * Test method for {@link Main.uno3.CardDeck#shuffle()}.
     */
    @Test
    final void testShuffle() {
        fail("Not yet implemented");
    }

}
