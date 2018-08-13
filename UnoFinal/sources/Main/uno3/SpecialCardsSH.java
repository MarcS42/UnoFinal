package Main.uno3;

import java.lang.Math.*;

public class SpecialCardsSH extends Card {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static boolean specialCardSH;
    private static boolean specialCardNt7SH;
    private static boolean twoReset;
    private static boolean threeMirror;
    private static boolean tenBomb;
    private static boolean sevenPlayLowerCard;
    private static boolean fourOfaKindBomb;
    
    
    /**
    * SpecialCard true/false    
    * @param card
    * @return true if Rank == 2, 3, 7, or 10.
    */
   public static boolean specialCardSH(Card card) {
       specialCardSH = false;
       int rank = card.getRankAceHi();
       if(rank == 2 || rank == 3 || rank == 10 || rank == 7) {
           specialCardSH = true;
       }
       return specialCardSH;
   }
   
   /**
    * SpecialCardNotSeven true/false    
    * @param card
    * @return true if Rank == 2, 3, or 10.
    */
   public static boolean specialCardNt7SH(Card card) {
       specialCardNt7SH = false;
       int rank = card.getRankAceHi();
       if(rank == 2 || rank == 3 || rank == 10) {
           specialCardNt7SH = true;
       }
       return specialCardNt7SH;
   }
   
   public static boolean twoReset(Card card) {
       twoReset = false;
       if(card.getRankAceHi() == 2) {
           return true;
       }
       return twoReset;
   }
   
   public static boolean threeMirror(Card card) {
       threeMirror = false;
       if(card.getRankAceHi() == 3) {
           threeMirror = true;
           return threeMirror;
       }
       return threeMirror;
   }
   
   public static int numThreeMirrors(CardHand discardPile) {
       int count=0;
       for(int i = discardPile.size()-1; i > Math.max(discardPile.size()-4,0); i--) {
           if((discardPile.getCard(i).getRankAceHi()==3) && (discardPile.getCard(i-1).getRankAceHi()==3)) {
               count++;
           }
       }
    return count;
   }
   
   public static boolean tenBomb(Card card) {
       tenBomb = false;
       if(card.getRankAceHi() == 10) {
           tenBomb = true;
           return tenBomb;
       }
       return tenBomb;
   }
  
   public static boolean sevenPlayLowerCard(Card card) {
       sevenPlayLowerCard = false;
       if(card.getRankAceHi() == 7) {
           sevenPlayLowerCard = true;
           return sevenPlayLowerCard;
       }
       return sevenPlayLowerCard;
   }
   
   /**Four cards must be played in sequence; if so, becomes a Bomb.
 * @param card
 * @return
 */
public static boolean fourOfaKindBomb(Card card) {
       fourOfaKindBomb = false;
       if(ShitHead.isFourOfaKind() == true) {
           return true;
       }
       return fourOfaKindBomb;
   }

public static boolean isThreeMirror() {
    return threeMirror;
}

public static void setThreeMirror(boolean threeMirror) {
    SpecialCardsSH.threeMirror = threeMirror;
}

public static boolean isTenBomb() {
    return tenBomb;
}

public static void setTenBomb(boolean tenBomb) {
    SpecialCardsSH.tenBomb = tenBomb;
}


/**
 * These special cards, declared here, are assigned values in the SH
 * Deck Constructor. Need to do things this way
 * in order to use boolean Contains(object)
 * to test for threeMirrors in an AL without looking
 * at each card in Hand using for loop.
 */
static Card threeC;
static Card threeD;
static Card threeH;
static Card threeS;
   
}//End class SpecialCardSH 
