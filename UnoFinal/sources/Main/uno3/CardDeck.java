package Main.uno3;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import static Main.uno3.Card.*;

public class CardDeck implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String label;
    private ArrayList<Card> cards;

    public CardDeck() {
    }
    
    public CardDeck(String label, boolean AceHi) {
        this.label=label;
        this.cards = new ArrayList<Card>();
        setAceHi(AceHi);
        if(isAceHi()) {
            cardDeckBuilder(2, 14, 0, 3);
        } else {
            cardDeckBuilder(1, 13, 0, 3);
        }
        
//      CardDeck Builder....
        

    }

    public void cardDeckBuilder(int rankMin, int rankMax,
            int suitMin, int suitMax) {
        for(int suit = suitMin; suit <= suitMax; suit++ ) {
            for(int rank = rankMin; rank <= rankMax; rank++) {
                addCard(new Card(rank, suit, isAceHi()));
            }
        }
    }
    
   /**
    * Didn't Work Out
    * Abstract because Class is abstract/cannot
    *  be instantiated.
   * @param deck Deck to be saved/cloned
   * @return 
   */
//  protected abstract CardDeck cloneDeck(CardDeck deck);
//      ArrayList<Card> deckCopy = new ArrayList<>();
//      CardDeck deckClone = new CardDeck("DeckCopy", deckCopy);
//      for(Card d:deck.getCards()) {
//          deckClone.getCards().add(d);
//      }
//      serializeUnoDeck(deckClone);
//      return deckClone;
//  }
   
   
   @Override
   public String toString() {
       return getLabel() + ":" + cards.toString();
   }
   
   public static void printDeck(CardDeck deck) {
       for(Card card:deck.getCards()) {
           System.out.printf("%-17s"+" ", card);
           System.out.printf("%3d%n", scoreCard(card));
       }
   System.out.println();
   }

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
    
    /*
     * Used in Uno takeTurn() gets last card from calling CardCollection, but
     * doesn't remove it So it is like taking a look at the card
     */
    public Card last() {
        int i = size() - 1;
        return getCard(i);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Maybe used in running saved deck
     * @param cards
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getLabel() {
        return label;
    }
}