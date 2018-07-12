package Main.uno3;

import java.io.Serializable;

public class Card implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final String[] RANKS = 
        {null,"Ace","2","3","4","5",
          "6","7","8","9","10",
          "Jack","Queen","King"};
    
    private static final String[] RANKSAceHi = 
        {null,null,"2","3","4","5",
          "6","7","8","9","10",
          "Jack","Queen","King","Ace"};
    
    private static final String[] SUITS = 
        {"Clubs","Diamonds","Hearts","Spades"};
    
    private static boolean aceHi;
    private int rank;
    private int rankAceHi;
    private int suit;

    public Card() {
        this.rank=1;
        this.suit=0;
    }

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(int rank, int suit, boolean aceHi) {
        Card.aceHi = aceHi;
        if(isAceHi()) {
            this.rankAceHi = rank;
            this.suit = suit;
        } else {
            this.rank = rank;
            this.suit = suit;
        }
    }
    
    /**Determines if card1 matches card2 in Suit and Rank
     * @param card1 Prev. card you are trying to match
     * @param card2 Card that is potential match
     * @return
     */
    public static boolean cardsMatch(Card card1, 
            Card card2) { 
        //card1 = previous card
        if(!isAceHi()) {
            if (card1.getSuit() == card2.getSuit() &&
                    card1.getRank() == card2.getRank()) {
                return true;
            }
        } else {
            if (card1.getSuit() == card2.getSuit() &&
                    card1.getRankAceHi() == card2.getRankAceHi()) {
                return true;
            }
        }
        return false;
    }

    /**Determines if card2 playable after card1 independent of
     *  Suits.
     *   'Playable' means "> than or = to"
     *    
     *  This is for games like Shithead
     *  
     * @param card1 Prev. card you are trying to play on
     * @param card2 Card that is potentially playable
     * @return
     */
    public static boolean cardsPlayableRankSH(Card card1, 
            Card card2) { 
        //card1 = previous card
        if(!isAceHi()) {
            if (card2.getRank() >= card1.getRank()) {
                return true;
            }
        } else {
            if(SpecialCardsSH.sevenPlayLowerCard(card1) &&
                card2.getRankAceHi() <= card1.getRankAceHi() || 
                SpecialCardsSH.specialCardNt7SH(card2)) {
                return true;
            }
            if (card2.getRankAceHi() >= card1.getRankAceHi() || 
                    SpecialCardsSH.specialCardNt7SH(card2)) {
                return true;
            }
        }
        return false;
    }
    
    /**  Bridge Style
     * Positive number means c2 > c1
     * @param card1
     * @param card2
     * @return +1, 0, -1
     */
    public static int compareCards(Card card1, Card card2) { 
        //Assumes Suit takes precedence over Rank
        //and Suit low to hi is clubs, diamonds, 
        //hearts, spades (Bridge Style)
        if(!isAceHi()) {
            if (card1.getSuit() < card2.getSuit()) 
               {return 1;}
            if (card1.getSuit() > card2.getSuit()) 
               {return -1;}

            if (card1.getRank() < card2.getRank())
               {return 1;}
            if (card1.getRank() > card2.getRank())
               {return -1;}
        } else {
            if (card1.getSuit() < card2.getSuit()) 
               {return 1;}
            if (card1.getSuit() > card2.getSuit()) 
               {return -1;}

            if (card1.getRankAceHi() < card2.getRankAceHi())
               {return 1;}
            if (card1.getRankAceHi() > card2.getRankAceHi())
               {return -1;}
        }  
        return 0;
    }//End compareCards(card1,card2)

    /**Suit does not matter
     * Checks AceHi/!AceHi
     * Positive number means ****c1 <= c2****
     * 
     * @param card1
     * @param card2
     * @return +1, 0, -1
     */
    public static int compareRanks(Card card1, Card card2) { 
        if(!isAceHi()) {
            if (card1.getRank() <= card2.getRank())
               {return 1;}
            if (card1.getRank() > card2.getRank())
               {return -1;}
        } else {
            if (card1.getRankAceHi() <= card2.getRankAceHi())
               {return 1;}
            if (card1.getRankAceHi() > card2.getRankAceHi())
               {return -1;}
        }  
        return 0;
    }//End compareRanks(card1,card2)
    
    /**Suit does not matter++
     * 
     * Positive number means ****c1 < c2****
     * 
     * need this for testing multiple card Ranks Match (ex: pair of threes)
     * If card1 == card2 --> return 0.
     * @param card1
     * @param card2
     * @return +1, 0, -1
     */
    public static int compareRanks2(Card card1, Card card2) { 
        if(!isAceHi()) {
            if (card1.getRank() < card2.getRank())
               {return 1;}
            if (card1.getRank() > card2.getRank())
               {return -1;}
        } else {
            if (card1.getRankAceHi() < card2.getRankAceHi())
               {return 1;}
            if (card1.getRankAceHi() > card2.getRankAceHi())
               {return -1;}
        }  
        return 0;
    }//End compareRanks2(card1,card2)
    
    /**
     * Card level scoring by game rules
     * Called from Hand
     * @param card
     * @return
     */
    public static int scoreCard(Card card) {
        int cardScore;
        if(!isAceHi()) {
            if(card.getRank()>10 ) return cardScore = 10;
            if(card.getRank() >= 1) {
                cardScore = card.getRank();
            } else {
                cardScore = 0;
            }
        } else {
            if(card.getRankAceHi() >= 2) {
                cardScore = card.getRankAceHi();
            } else {
                cardScore = 0;
            }
        }
        return cardScore;
    } 

    /**
     * This is only place where RANKS[] and SUITS[] 
     * arrays are used
     */
    @Override
    public String toString() {
        String s;
        if(!isAceHi()) {
            s = RANKS[this.rank] + " of " + 
                    SUITS[this.suit];
        } else {
            s = RANKSAceHi[this.rankAceHi] + " of " + 
                    SUITS[this.suit];    
        }
        return s;
    }
    
// With a toString() method present, System.out.println will auto use toString()    
    public static void printCard(Card card) {
        System.out.println(card);
    }

//*******************Getters/Setters*****************************    
    /**
 * @return the suits
 */
public static String[] getSUITS() {
    return SUITS;
}

    /**
 * @return the ranks
 */
public static String[] getRANKS() {
    return RANKS;
}

    public static String[] getRANKSAceHi() {
        return RANKSAceHi;
    }

    /**
     * @return the suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    public int getRankAceHi() {
        return rankAceHi;
    }

    public static boolean isAceHi() {
        return aceHi;
    }

    public static void setAceHi(boolean aceHi) {
        Card.aceHi = aceHi;
    }

}//End class Card