package Main.uno3;


import java.util.ArrayList;

/**
 * 
 */



/**
 * @author MarcSherman
 *
 */
public class UnoHand extends UnoDeck {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**Constructor generates a UnoHand
     * Actual constructor is in Parent Class
     * @param pileLabels
     * @param piles
     */
    public UnoHand(String pileLabels, ArrayList<UnoCard> piles) {
        super(pileLabels, piles);
    }

    public static UnoHand insertionSortUnoHand(UnoHand unohand) {
        for (int i = 1; i < unohand.size(); i++) {
            for (int k = i; (k > 0 && (UnoCard.compareCards(unohand.getCard(k),
                    unohand.getCard(k - 1)) < 1)); k--) {
                unohand.swapCards(k, k - 1);
            }
        }
        return unohand;
    }

    /**
     * Scoring system unique to Uno is in class UnoCard
     */
    public static int scoreHandUno(UnoHand unohand) {
        int sum = 0;
        for (UnoCard u:unohand.getUnocards()) {
            sum += UnoCard.scoreCard(u);
        }
        return sum;
    }

    /**
     * Used in Uno displayState()
     * 
     */
    public void display() {
        System.out.println(label + ": ");
        for (int i=0; i < unocards.size(); i++) {
            System.out.printf("%-15s", unocards.get(i));
            System.out.println(" " + UnoCard.scoreCard(getCard(i)));
        }
        System.out.println();
    }

}// End class UnoHand
