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
            sh.getDiscardPile().dealAll(hand);
            return null;
        }
        sh.draw();
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
//            ArrayList<Card> multiPlayable = new ArrayList<>();
//            ArrayList<Card> singlePlayable = new ArrayList<>();
            insertionSortCardHand(hand);
            
     /**       Look for not special low cards */      
            for (int i = 0; i < hand.size(); i++) {
                Card card = hand.getCard(i);
                    
                if (!specialCardNt7SH(card) && card.getRankAceHi() 
                        <= 11 && cardsPlayableRankSH(prev, card)) { 
//                    singlePlayable.add(card);
                    return hand.popCard(i);
                }
            } 

            /*can pull out consecutive pairs(ok bcuz sortAscending),
             *  but not 3's or 4's*/
//            if(singlePlayable.size() > 1) {
//                int p = singlePlayable.size()-1;
//                while (p > 0) { 
//                    if(singlePlayable.get(p).getRankAceHi() == 
//                            singlePlayable.get(p-1).getRankAceHi()) {
//                        multiPlayable.add(singlePlayable.remove(p));
//                        multiPlayable.add(singlePlayable.remove(p-1));
//                        p--;
//                    }
//                    p--;  
//                }
//            }

       /**    Look for not special high cards, plays them next */
            for (int i = hand.size()-1; i >=0 ; i--) {
                Card card = hand.getCard(i);
         
                if(!specialCardSH(card) && card.getRankAceHi() 
                        > 11 && cardsPlayableRankSH(prev, card)) {
                    return hand.popCard(i);
                }
            }
         
      /**     Look for and play special cards */
            for (int i = 0; i < hand.size(); i++) { 
                Card card = hand.getCard(i);
                        
                if (specialCardSH(card) 
                        && cardsPlayableRankSH(prev, card)) {
                    return hand.popCard(i);
                }
            }  
         return null;
        } // End !hand.empty()
        
        else if(!river.empty()) {
            insertionSortCardHand(river);
            
       /**       Look for not special low cards */            
            for (int i = 0; i < river.size(); i++) {
                Card card = river.getCard(i);
            
                if (!specialCardNt7SH(card) && card.getRankAceHi() 
                        <= 11 && cardsPlayableRankSH(prev, card)) { 
                    return river.popCard(i);
                }
            }
       /**       Look for not special high Rcards, plays them next */
            for (int i = river.size()-1; i >=0 ; i--) {
                Card card = river.getCard(i);

                if(!specialCardSH(card) && card.getRankAceHi() 
                        > 11 && cardsPlayableRankSH(prev, card)) {
                    return river.popCard(i);
                }
            }
       /**       Look for and play special Rcards */            
            for (int i = 0; i < river.size(); i++) { 
                Card card = river.getCard(i);
              
                if (specialCardSH(card) 
                        && cardsPlayableRankSH(prev, card)) {
                    return river.popCard(i);
                }
            }
             return null;
            }// End !river.empty()

            Random rand = new Random();
            int i = rand.nextInt(hole.size());
            if(cardsPlayableRankSH(prev, hole.getCard(i))) {
                return hole.popCard(i);
            }//End pickRandom Hole card
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
     * Gets Hand
     * 
     * @return
     */
    public CardHand getHand() {
        return hand;
    }
    
    public ArrayList<Card> getHCards() {
        return getHand().getCards();
    }

    public CardHand getRiver() {
        return river;
    }

    public ArrayList<Card> getRCards() {
        return getRiver().getCards();
    }
    
    public CardHand getHole() {
        return hole;
    }
}//End PlayerSH
