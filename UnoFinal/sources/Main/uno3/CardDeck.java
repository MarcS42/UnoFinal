package Main.uno3;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class CardDeck implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String label;
    private ArrayList<Card> cards;

    public String getLabel() {
        return label;
    }

    public CardDeck() {
    }
    
    public CardDeck(String label) {
        this.label=label;
        this.cards = new ArrayList<Card>();
        
//      CardDeck Builder....  
//        for(int suit = 0; suit < 4; suit++ ) {
//            for(int rank = 0; rank < 13; rank++) {
//                addCard(new Card(rank, suit));
//            }
//        }
    }

   protected abstract void cardDeckBuilder(int rank, int suit); 
    
    /**Used in reshuffle of discardPile when 
        * discardPile becomes drawPile. 
        * These 'Piles' are UnoHands
     * 
     */
    public void shuffle() {
           Random rand = new Random();
           for (int i= size()-1; i > 0; i--) {
               int j = rand.nextInt(i);
               swapCards(i,j);
           }
       }

    /**Removes AL card[i], and shifts all cards 
        * above it to the left
        * 
        * 
        * 
        * @param i int of tgt card in ArrayList
        * @return card removed from specific index posit.
        */
    public Card popCard(int i) { 
           return cards.remove(i);
       }

    /**Used in draw, deal(Hand, int), and 
     *      Uno Constructor.
     * "Overloaded" 
     * Removes top card, no need to shift left
     * @return top/last card
     */
    public Card popCard() { 
           int i = size() - 1;
           return cards.remove(i);
       }

    /**helper method used within shuffle() and 
     * insertionSort()
     * @param i
     * @param j
     */
    public void swapCards(int i, int j) {
        Card temp = getCard(i);
        cards.set(i, getCard(j));
        cards.set(j, temp);
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    /**Helper/Utility method.
        * Used in many methods
     * @param card card to be added to end of hand AL
     */
    public void addCard(Card card) {
           cards.add(card);
       }

    /**Utility used wherever you need For control loop
     * @return
     */
    public int size() {
        return cards.size();
    }
}