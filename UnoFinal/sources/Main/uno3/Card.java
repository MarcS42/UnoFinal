package Main.uno3;

import java.io.Serializable;

public class Card implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final String[] RANKS = 
        {"Ace","2","3","4","5",
          "6","7","8","9","10",
          "Jack","Queen","King"};
    
    /**
     * @return the ranks
     */
    public static String[] getRanks() {
        return RANKS;
    }

    private static final String[] SUITS = 
        {"Clubs","Diamonds","Hearts","Spades"};
    
    /**
     * @return the suits
     */
    public static String[] getSuits() {
        return SUITS;
    }
    
    private final int rank;
    private final int suit;

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @return the suit
     */
    public int getSuit() {
        return suit;
    }

    public Card() {
        this.rank=0;
        this.suit=0;
    }

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    /**Determines if card1 matches card2
     * @param card1 Card that is potential match
     * @param card2 Prev. card you are trying to match
     * @return
     */
    public static boolean cardsMatch(Card card1, 
            Card card2) { 
        //card2 = previous card
        if (card2.getSuit() == card1.getSuit() &&
                card2.getRank() == card1.getRank()) {
                return true;
                 }
        return false;
    }

    /**
     * Positive number means c1 > c2
     * @param card1
     * @param card2
     * @return +1, 0, -1
     */
    public static int compareCards(Card card1, Card card2) { 
      //Assumes Suit takes precedence over Rank
      //and Suit low to hi is clubs, diamonds, 
        //hearts, spades (Bridge Style)
        if (card1.getSuit() > card2.getSuit()) 
            {return 1;}
        if (card1.getSuit() < card2.getSuit()) 
            {return -1;}
        
        if (card1.getRank() > card2.getRank())
            {return 1;}
        if (card1.getRank() < card2.getRank())
        {return -1;}
        
        return 0;
    }//End compareCards(card1,card2)

    /**
     * Card level scoring by game rules
     * Called from Hand
     * @param card
     * @return
     */
    public static int scoreCard(Card card) {
        int cardScore;
        if(card.getRank()>10 ) return cardScore = 10;
        cardScore = card.getRank();
        return cardScore;
    } 

    /**
     * This is only place where RANKS[] and SUITS[] 
     * arrays are used
     */
    @Override
    public String toString() {
        String s = RANKS[this.rank] + " of " + 
                         SUITS[this.suit];
        return s;
    }
    
// With a toString() method present, System.out.println will auto use toString()    
    public static void printCard(Card card) {
        System.out.println(card);
    }

}//End class Card