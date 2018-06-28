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
            return null;
        }
        return card;  
    }
    
    /**
     * Searches current Player's hand(s) for match to
     *  Card prev.
     * Three hands to search:
     * 1) hand Cards - 4 cards
     * 2) river Cards - choice of <= 3
     * 3) hole Cards - random guess <= 3
     * @param prev card
     * @return match from hand(s)
     */
    public Card searchForMatch(Card prev) {
//**** ToDo wrt multiple same Card matches(hover over task tag)***/
        if(!hand.empty()) {
            ArrayList<Card> multiPlayable = new ArrayList<>();
            ArrayList<Card> singlePlayable = new ArrayList<>();
            insertionSortCardHand(hand);
            for (int i = 0; i < hand.size(); i++) {
                Card card = hand.getCard(i);
     /**       Look for not special low cards */            
                if (!specialCardSH(card) && card.getRank() 
                        < 11 && cardsPlayableRank(prev, card)) { 
                    singlePlayable.add(card);
                }
            } 
            
            /**can pullout consecutive pairs(ok bcuz sortAscending), but not 3's or 4's*/
            if(singlePlayable.size() > 1) {
                int p = singlePlayable.size()-1;
                while (p > 0) { 
                    if(singlePlayable.get(p).getRank() == singlePlayable.get(p-1).getRank()) {
                        multiPlayable.add(singlePlayable.remove(p));
                        multiPlayable.add(singlePlayable.remove(p-1));
                        p--;
                    }
                    p--;  
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
            }//End pickRandom Hole card
        }
        return null;
    } // End searchForMatch

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
