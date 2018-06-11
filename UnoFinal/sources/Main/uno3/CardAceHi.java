/**
 * 
 */
package Main.uno3;

/**
 * @author MarcSherman
 * Little polymorphism or code reuse from class Card
 *  because a) most Card methods are static => cannot
 *  Override, and b) since AceHi RANKS[] is different,
 *  need to copy and change Card class code.
 *  SubClassing AceHi to Card May be more trouble than it's worth. 
 */
public class CardAceHi extends Card {

    /**
     *So you can save Deck later for debugging. 
     */
    private static final long serialVersionUID = 1L;
    
    private static final String[] RANKS = 
        {null,null,"2","3","4","5",
          "6","7","8","9","10",
          "Jack","Queen","King","Ace"};
    
    public static String[] getRanks() {
        return RANKS;
    }
    
    private final int rank;


    /**
     * @return the rank
     * So as to return AceHi version of 'rank'
     */
    @Override
    public int getRank() {
        return rank;
    }

    /**
     * Zero arg constructor
     */
    public CardAceHi() {
        super();
        this.rank = 2;
        System.out.println("Created in zero arg"
                + " AceHi "+ rank +" "+ getSuit());
    }
    
    /**
     * Two arg constructor
     */
    public CardAceHi(int rank, int suit) {
        super(rank, suit);
        this.rank = rank;
        System.out.println("Created in 2-args"
                + " AceHi "+ rank +" "+ getSuit());
    }        
    
        /**
         * Doesn't care about suit in this Compare
         * 
         * Positive number means c1 > c2
         * @param card1
         * @param card2
         * @return +1, 0, -1
         * NOT override: cannot override static parent
         *  methods
         */
        public static int compareCards(Card card1, 
                Card card2) { 
          //Doesn't care about suit in Comparing
            
            if (card1.getRank() > card2.getRank())
                {return 1;}
            if (card1.getRank() < card2.getRank())
            {return -1;}
            
            return 0;
        }//End compareCards(card1,card2)
 
        /**Determines if card2 matches/can be played vs card1 
         * using ShitHead type rules
         *  
         * @param card1 Prev. card you are trying to match.
         * @param card2 Card card played that is potential match.
         * @return
         */
        public static boolean cardsMatch(CardAceHi card1, 
                CardAceHi card2) { 
            //card1 = previous card
            if (card1.getRank() <= card2.getRank()) {
                    return true;
                     }
            return false;
        }
        
        /**Determines if card1.rank matches card2.rank. 
         * Needed for tracking consecutive Cards played 
         *  
         * @param card1 first Card played whose rank might
         *  match card2
         * @param card2 second Card played whose rank might
         *  match card1
         * @return
         */
        public static boolean ranksMatch(CardAceHi card1, 
                CardAceHi card2) { 
            //card2 = second card
            if (card1.getRank() == card2.getRank()) {
                    return true;
                     }
            return false;
        }
    
        /**
         * Card level scoring by AceHi game rules
         * Called from Hand
         * @param card
         * @return
         */
        public static int scoreCard(CardAceHi card) {
            if(card.getRank() >= 2) { 
            return card.getRank();
            }
            return 0;
        }
        
        
        /**
         * This is only place where RANKS[] and SUITS[] 
         * arrays are used.
         * Override because AceHi RANKS[] <> Card RANKS[].
         */
        @Override
        public String toString() {
            String s = RANKS[this.rank] + " of " + 
                             getSuits()[this.getSuit()];
            return s;
        }
        
}
