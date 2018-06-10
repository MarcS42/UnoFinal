/**
 * 
 */
package Main.uno3;

/**
 * @author MarcSherman
 *
 */
public class CardAceHi extends Card {

    private static final String[] RANKS = 
        {"null","null","2","3","4","5",
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
         * Doesn't care about suit in Compare
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
    
        /**
         * This is only place where RANKS[] and SUITS[] 
         * arrays are used
         */
        @Override
        public String toString() {
            String s = RANKS[this.rank] + " of " + 
                             getSuits()[this.getSuit()];
            return s;
        }
        
}
