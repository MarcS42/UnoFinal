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

/**Plays next card when discardPile.empty()
             * 
             * needs to be fixed. Works poorly on special cards/situations
             * 
             * @param shitHead TODO
     * @return
             */
            public Card playNext(ShitHead shitHead) {
                Card card = null;
                
                if(!hand.empty()) {
             /*    Looking for not special HCards */
                    for(int i = 0; i < hand.size(); i++) {
                        if(card == null) {
                            Card chkCard = hand.getCard(i);
                            if(!specialCardNt7SH(chkCard)) {
                                card = hand.popCard(i);
                                System.out.println("Player "+ name +
                                        " plays "+ card);
                                shitHead.discardPile.addCard(card);
                                shitHead.draw();
                                i = hand.size()+1; //short circuit for loop
                        } else {
                            continue;
                        }
                    }//end if(card==null)
                  }// End for loop
                    
          /*     if Player only has special Hcards    */
                  if((card == null) && !hand.empty()) 
                    {
                        card = hand.popCard(hand.size()-1);
                        System.out.println("Player "+ name +
                                " plays "+ card);
                        shitHead.discardPile.addCard(card);
                        shitHead.draw();
                    }
                   
                 return card;
                }// End PlayeNext.!Hand.empty().
                    
                if(!river.empty()) {
                    for(int i = 0; i < river.size(); i++) {
                        if(card == null) {
                            Card chkCard = getRCards().get(i);
                            if(specialCardSH(chkCard)) {
                                card = river.popCard(i);
                                System.out.println("Player "+ name +
                                        " plays "+ card);
                                shitHead.discardPile.addCard(card);
                                shitHead.draw();
                            } else {
                                continue;
                               }
                        }// End (Rcard == null)
                    }// End for Loop
                    
                    /*     if Player only has !special Rcards    */
                    if((card == null) && !river.empty()) 
                      {
                          card = river.popCard(river.size()-1);
                          System.out.println("Player "+ name +
                                  " plays "+ card);
                          shitHead.discardPile.addCard(card);
                          shitHead.draw();
                      }
                    
                    return card;
                }// End PlayeNext.!river.empty().
                
  /*Problem when last hole card is a tenBomb. Leads to trying 
   * to play another card when do not have any left*/      
              if(!hole.empty()) {
                Random rand = new Random();
                int i = rand.nextInt(hole.size());
                card = hole.getCard(i);
                System.out.println("Player "+ name +
                        " plays "+ card);
                shitHead.discardPile.addCard(card);
                shitHead.draw();
               return card;
              } else {
                  shitHead.player = shitHead.nextPlayer(shitHead.player);
              }
              System.out.println("Player into playNext = " 
                      + shitHead.player.getName());
              return shitHead.player.playNext(shitHead);//needed if last card tenBomb && playerDone
            }//End playNext(SH)

            public static boolean playerHas3MHcards(PlayerSH player) {

                if(player.getHCards().contains(threeC) ||
                        player.getHCards().contains(threeD) ||
                        player.getHCards().contains(threeH) ||
                        player.getHCards().contains(threeS))
                {
                    return true;}
                return false;
            }

            public static boolean playerHas3MRcards(PlayerSH player) {

                if(player.getRCards().contains(threeC) ||
                        player.getRCards().contains(threeD) ||
                        player.getRCards().contains(threeH) ||
                        player.getRCards().contains(threeS))
                {
                    return true;}
                return false;
            }

            public static boolean playerHas3MHolecards(PlayerSH player) {

                if(player.getHoleCards().contains(threeC) ||
                        player.getHoleCards().contains(threeD) ||
                        player.getHoleCards().contains(threeH) ||
                        player.getHoleCards().contains(threeS))
                {
                    return true;}
                return false;
            }
            
            public static boolean playerIsDone(PlayerSH player) {
              if(player.hand.empty() && player.river.empty() && 
                      player.hole.empty()) {
                  return true;
              }
                return false;
            }
            
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
    
    public ArrayList<Card> getHoleCards() {
        return getHole().getCards();
    }
    
}//End PlayerSH
