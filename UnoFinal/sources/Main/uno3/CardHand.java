package Main.uno3;

import java.util.ArrayList;

public class CardHand extends CardDeck {

    private static final long serialVersionUID = 1L;
    
    /**Constructor generates a CardHand
     * Actual constructor is in Parent Class
     * @param pileLabels
     * @param piles
     */
    public CardHand(String pileLabels, ArrayList<Card> piles) {
        super(pileLabels, piles);
    }
    
    public static CardHand insertionSortCardHand(CardHand hand) {
        for (int i = 1; i < hand.size(); i++) {
            for (int k = i; (k > 0 && (Card.compareRanks(hand.getCard(k),
                    hand.getCard(k - 1)) > -1)); k--) {
                hand.swapCards(k, k - 1);
            }
        }
        return hand;
    }

    public static int scoreCardHand(CardHand hand) {
        int sum = 0;
        for (Card c:hand.getCards()) {
            sum += Card.scoreCard(c);
        }
        return sum;
    }
    
    /**
     * Used in SH displayState()
     * 
     */
    public void display() {
        System.out.println(getLabel() + ": ");
        for (int i=0; i < getCards().size(); i++) {
            System.out.printf("%-15s", getCards().get(i));
            System.out.println(" " + Card.scoreCard(getCard(i)));
        }
        System.out.println();
    }

}
