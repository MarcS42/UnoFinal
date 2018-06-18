/**
 * 
 */
package Main.uno3;

import static Main.uno3.SpecialCardsSH.*;
import static Main.uno3.Card.*;
import static Main.uno3.CardHand.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author MarcSherman
 *
 */
public class PlayerSH {
    private String name;
    private CardHand hand;
    private CardHand river;
    private CardHand hole;
    
    public PlayerSH(String name) {
        ArrayList<Card> plyrHand = new ArrayList<>();
        ArrayList<Card> plyrRiver = new ArrayList<>();
        ArrayList<Card> plyrHole = new ArrayList<>();
        this.name = name;
        this.hand = new CardHand(name, plyrHand);
        this.river = new CardHand(name, plyrRiver);
        this.hole = new CardHand(name, plyrHole);
    }
    
    /**
     * 
     * @param sh Makes ShitHead state variables available
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
     * Searches current Player's hand(s) for match to
     *  Card prev.
     * Three hands to search:
     * 1) hand Cards - 7
     * 2) river Cards - choice of <= 3
     * 3) hole Cards - random guess <= 3
     * @param prev card
     * @return match from hand(s)
     */
    public Card searchForMatch(Card prev) {
//**** ToDo wrt multiple same Card matches(hover over task tag)***/
        if(!hand.empty()) {
            insertionSortCardHand(hand);
            for (int i = 0; i < hand.size(); i++) {
                Card card = hand.getCard(i);
     /**       Look for not special low cards */            
                if (!specialCardSH(card) && card.getRank() 
                        < 11 && cardsPlayableRank(prev, card)) { 
                    return hand.popCard(i);
                }
            }
            // search from end of hand as hand sorted ascending
            for (int i = hand.size()-1; i >=0 ; i--) {
                Card card = hand.getCard(i);
     /**       Look for not special high cards, plays them next */
                if(!specialCardSH(card) && card.getRank() 
                        > 11 && cardsPlayableRank(prev, card)) {
                    return hand.popCard(i);
                }
            }
            for (int i = 0; i < hand.size(); i++) { 
                Card card = hand.getCard(i);
     /**       Look for and play special cards */              
                if (specialCardSH(card) 
                        && cardsPlayableRank(prev, card)) {
                    return hand.popCard(i);
                }
            } // End !hand.empty() 

        } else if(!river.empty()) {
            insertionSortCardHand(river);
            for (int i = 0; i < river.size(); i++) {
                Card card = river.getCard(i);
     /**       Look for not special low cards */            
                if (!specialCardSH(card) && card.getRank() 
                        < 11 && cardsPlayableRank(prev, card)) { 
                    return river.popCard(i);
                }
            }
            // search from end of hand as hand sorted ascending
            for (int i = river.size()-1; i >=0 ; i--) {
                Card card = river.getCard(i);
     /**       Look for not special high cards, plays them next */
                if(!specialCardSH(card) && card.getRank() 
                        > 11 && cardsPlayableRank(prev, card)) {
                    return river.popCard(i);
                }
            }
            for (int i = 0; i < river.size(); i++) { 
                Card card = river.getCard(i);
     /**       Look for and play special cards */              
                if (specialCardSH(card) 
                        && cardsPlayableRank(prev, card)) {
                    return river.popCard(i);
                }
            }// End !river.empty()
            
            Random rand = new Random();
            int i = rand.nextInt(hole.size()+1);
            if(cardsPlayableRank(prev, hole.getCard(i))) {
                return hole.getCard(i);
            }
        }
        return null;
    } // End searchForMatch
    
//    public Card drawForMatch(ShitHead sh, Card prev) {
//        while (true) {
//            Card card = sh.draw();
//            System.out.println(name + " draws " + card);
//            if (cardsPlayableRank(prev, card)) { 
//                return card;  
//                // "return statement gets u out of while(true) loop
//            }
//            hand.addCard(card);
//        }
//    } //End drawForMatch

//++++++++++++++++ Helper Methods +++++++++++   
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

    public CardHand getRiver() {
        return river;
    }

    public CardHand getHole() {
        return hole;
    }
}//End PlayerSH
