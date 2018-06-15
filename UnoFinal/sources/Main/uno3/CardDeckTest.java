/**
 * 
 */
package Main.uno3;

import static Main.uno3.Card.isAceHi;
import static Main.uno3.CardDeck.printDeck;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author MarcSherman
 *
 */
class CardDeckTest {

    @BeforeAll
    static void initAll() {
        CardDeck deckAceLo = new CardDeck("deckTest2", false);
        
        System.out.println("Test deckTest2 pre shuffle AceLo: " + isAceHi());
        printDeck(deckAceLo); 
        System.out.println("");
        
        deckAceLo.shuffle();
        
        System.out.println("Test deckTest2 Shuffled AceLo: " + isAceHi());
        printDeck(deckAceLo); 
        System.out.println("");
    }
    
    /**
     * Test method for {@link Main.uno3.CardDeck#CardDeck(java.lang.String)}.
     */
    @Test
    final void testCardDeckString() {
        CardDeck deck = new CardDeck("deckTest", false);
        String shouldBelabel = "deckTest";
        boolean shouldBeAceHi = false;
        
        assertEquals(deck.getLabel(), shouldBelabel,
                "construtor failed Label");
        assertEquals(false, shouldBeAceHi,
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
