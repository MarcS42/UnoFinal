/**
 * 
 */
package Main.uno3;

import static Main.uno3.SpecialCardsSH.*;
import static Main.uno3.Card.*;

import java.util.ArrayList;

/**
 * @author MarcSherman
 *
 */
public class PlayerSH {
    private String name;
    private CardHand hand;
    
    public PlayerSH(String name) {
        ArrayList<Card> playerHand = new ArrayList<>();
        this.name = name;
        this.hand = new CardHand(name, playerHand);
    }
    
    /**The heart of the game: first searchForMatch in your hand,
     * then if no match, drawForMatch
     * @param sh Card played against previous card
     * @param prev - previous card played
     * @return
     */
    public Card play(ShitHead sh, Card prev) {
        Card card = searchForMatch(prev);
        if (card == null) {
            card = drawForMatch(sh, prev);
        }
        return card;  
    }
    
    /**
     * Searches existing hand for match to Card prev.
     * 
     * @param prev card
     * @return match from hand
     */
    public Card searchForMatch(Card prev) {
        
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
/**      Runs thru hand looks for ????
 *            
*/
            if (specialCardSH(card)) { 
                return hand.popCard(i);               
/**       Look for special cards, plays them next */
            } else if(!specialCardSH(card) && 
                    cardsPlayableRank(prev, card)) {
                return hand.popCard(i);
            }
        }
        
/**     After 'filters above, only cases are ? 
 *         or card ?.  
        Sort cards that are not special cards or 
        regular wild cards to play highest first 
        */                
        CardHand.insertionSortCardHand(hand);    
      
        for (int i = hand.size() - 1; i >= 0; i--) { 
            // search from end of hand as hand sorted ascending
                Card card = hand.getCard(i);
                if (!specialCardSH(card) 
                      && cardsPlayableRank(prev, card)) 
                {
                    return hand.popCard(i);
                }
        }
        // all else fails, play DrawFour
        for (int i = hand.size() - 1; i >= 0; i--) {
            // search from end of hand as hand sorted ascending
                Card card = hand.getCard(i);
                if (tenBomb(card)) { 
                   return hand.popCard(i);
                } 
        }
     return null;
    } // End searchForMatch
    
    public Card drawForMatch(ShitHead sh, Card prev) {
        while (true) {
            Card card = hand.draw();
            System.out.println(name + " draws " + card);
            if (cardsPlayableRank(prev, card)) { 
                return card;  
                // "return statement gets u out of while(true) loop
            }
            hand.addCard(card);
        }
    } //End drawForMatch

    /**
     * Gets the player's name
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's hand
     * 
     * @return
     */
    public CardHand getHand() {
        return hand;
    }
}
