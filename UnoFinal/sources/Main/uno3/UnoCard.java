package Main.uno3;


import java.io.Serializable;

import static Main.uno3.UnoSpecialCards.*;

public class UnoCard extends Card implements Serializable{
    
    
/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
// Class Variables that are: a) shared, and b) immutable (constants) 
        private static final String[] RANKS = {"0", "1", 
                "1", "2", "2", "3", "3", "4", "4", "5", 
                "5", "6", "6",
                "7", "7", "8", "8", "9", "9", "Skip", 
                "Skip", "Reverse", "Reverse",
                "DrawTwo", "DrawTwo", "Wild", "Wild", 
                "Wild", "Wild", "WildDraw4", "WildDraw4",
                "WildDraw4","WildDraw4"};
        /**
         * @return the ranks
         */
        public static String[] getRanks() {
            return RANKS;
        }

       
        private static final String[] COLORS = {"Yellow", "Blue", 
                "Green", "Red", "Black"};
        
        /**
         * @return the colors
         */
        public static String[] getColors() {
            return COLORS;
        }
        
        private final int color; // 'final' keeps guys from messing with these and
        private final int rank; // can only assign values within a constructor

    public UnoCard() { // zero-args Constructor
        this.color=0;
        this.rank=0;
    }
    
    public UnoCard(int color, int rank) {
        this.color= color; //5 colors: y,blue,g,r, black
     // 27 ranks (0 plus 2 of each other card including 
    //   skip, reverse, drawTwo, wild, wildDraw4)
        this.rank = rank; 
        
    }
    
    public int getColor() {
        return this.color;
    }

    /* (non-Javadoc)
     * @see Uno2.Card#getRank()
     * 
     * unoCard Ranks are different than Std Deck
     */
    @Override
    public int getRank() {
        return this.rank;
    }
    
 /** With a toString() method present, 
  * System.out.println will auto use toString(
* (becuz toString() overrides built-in toString)   
*/    
    public static void printCard(UnoCard unocard) {
        System.out.printf("%-16s%n", unocard);
    }
    
    @Override
    public String toString() {
        String s = COLORS[this.color] + " " 
            + RANKS[this.rank];
        return s;
    }
    
    /**Determines if card1 matches card2
     * @param card1 Card that is potential match
     * @param card2 Prev. card you are trying to match
     * @return
     */
    public static boolean cardsMatch(UnoCard unocard1, 
            UnoCard unocard2) { 
     //unocard2 = PREV
        int unoCardTgtColor = 0;
      //Prev was wild and declared tgtColor

        if (uCardWldorWD4(unocard2)) { 
            unoCardTgtColor = Uno.getWildColor();
            if (unoCardTgtColor == unocard1.color) {
                return true; // > 24 => wild or wd4
                 }
        }
        if (uCardWldorWD4(unocard1)) {
            return true; 
        } else if (unocard2.color == unocard1.color || 
                unoCardRankConversion(unocard2) == 
                  unoCardRankConversion(unocard1)) {
            return true;
            }
        return false;
    }
    
    /**
     * @override
     * Positive number means c1 > c2
     * @param unocard1
     * @param unocard2
     * @return +1, 0, -1
     */
    public static int compareCards(UnoCard unocard1, 
            UnoCard unocard2) { 
        if (unoCardRankConversion( unocard1) > 
        unoCardRankConversion(unocard2)) {
            return 1;
        } else if (unoCardRankConversion(unocard1) == 
                unoCardRankConversion(unocard2)) {
            return 0;
        }
        return -1;
    }

    /**
     * Card level scoring by Uno rules
     * Called from UnoHand
     * @param unocard
     * @return
     */
    public static int scoreCard(UnoCard unocard) { 
        
        int cardScore = 0;
        if (uCardWldorWD4(unocard)) {
            cardScore = -50;
        }
        int rank = unocard.getRank();
        if (specialNotWild(unocard)) {
            cardScore = -20;
        } else if (rank < 20) {
            if (rank % 2 == 0) {
                cardScore = -rank / 2;
            } else {
                cardScore = -rank / 2 - 1;
            }
        }
        return cardScore;
    }
    
    /**
 * Problem is there are 2 of each card, so need to 
 * convert array index number to comparable value
 * so that, for example a yellow 5 where 5 is 1st 5 
 * in Array index, matches second 5 that will have 
 * higher Array index number.  
 * See OneNote for Index, UnoCard, Rank, Value Table  
 * @param unocard
 * @return
 */
    public static int unoCardRankConversion(UnoCard unocard)
    {
        int value = 0;
        if(unocard.rank % 2 == 0) {
            value = unocard.rank / 2;
        } else {
            value = unocard.rank / 2 +1;
        }
        return value;
    }
    

}// End class UnoCard

