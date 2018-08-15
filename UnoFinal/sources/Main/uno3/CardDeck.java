package Main.uno3;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static String fileName = "savedDeck.ser";

    public CardDeck() {
    }
    
    /**Constructor used for Hand/Piles
     * @param label
     * @param cards
     */
    public CardDeck(String label, 
            ArrayList<Card> cards) {
        this.label = label;
        this.cards = cards;
    }// End hand Constructor
    
    public CardDeck(String label, boolean AceHi) {
        this.label=label;
        this.cards = new ArrayList<Card>();
        setAceHi(AceHi);
        if(isAceHi()) {
//        CardDeck Builder....
            cardDeckBuilder(2, 14, 0, 3);
        } else {
            cardDeckBuilder(1, 13, 0, 3);
        }
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
   *Used for debugging to replay preceding CardDeck
   * @param deck Deck to be saved/cloned
   * @return 
   */
    public static CardDeck cloneDeck(CardDeck deck) {
        ArrayList<Card> deckCopy = new ArrayList<>();
        CardDeck deckClone = new CardDeck("DeckCopy", deckCopy);
        for(Card d:deckCopy) {
            deckClone.getCards().add(d);
        }
        serializeCardDeck(deckClone);
        return deckClone;
    }
  
  public static void serializeCardDeck(CardDeck deck) {
      fileName = getFileName();

      try (ObjectOutputStream oos = 
              new ObjectOutputStream(new FileOutputStream(fileName))) {

          oos.writeObject(deck);
          System.out.println("CloneDeck Done");
          System.out.println("");

      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }

  /**
 * @return CardDeck from savedFile.ser
 */
public static CardDeck deserializeCardDeck() {
      String filename = getFileName();

      CardDeck deck = null;

      try (ObjectInputStream ois 
              = new ObjectInputStream(new FileInputStream(filename))) {

          deck = (CardDeck) ois.readObject();
          System.out.println("Deserializatoin Done");
          System.out.println("");

      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return deck;
  }
     
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
   
   /** Deal(that,n) Removes n cards from 
    * this.CardDeck, and
    *  adds n cards to that.CardHand
    *  */
   public void deal(CardHand that, int n) {
       for (int i=0; i < n; i++) {
           Card card = popCard();
           that.addCard(card);
       }
   }
   
   /**Used in SH Class Constructor 
    * (drawPile)
    * moves all remaining cards to that 
    * given CardHand
    * 
    **/
   public void dealAll(CardHand that) {
       int n = size();
       deal(that, n);
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

    /**Removes AL cards(card), and shifts all cards 
     * above it to the left
     * 
     * 
     * 
     * @param card tgt card in ArrayList
     * @return card removed from specific index posit.
     */
 public Card popCard(Card card) { 
        return cards.remove(cards.indexOf(card));
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
     *      SH Constructor.
     *  
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

    /**
     * Checks if CardDeck AL is empty
     * Used with CardHand.
     * @return true if hand is empty
     */
    public boolean empty() {
        return getCards().isEmpty();
    }
    
    /**Helper/Utility method.
        * Used in many methods
     * @param card card to be added to end of hand AL
     */
    public void addCard(Card card) {
           cards.add(card);
       }
    
    /**Helper/Utility method.
     * Used in many methods
  * @param Cardhand cardsToPlay to be added to end of discardPile AL
  */
 public void addCards(CardHand cardsToPlay) {
        cards.addAll(cardsToPlay.getCards());
    }

    /**Utility used wherever you need For control loop
     * @return
     */
    public int size() {
        return cards.size();
    }
    
    /*
     * Used in takeTurn() gets last card from calling CardCollection, but
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

    public static String getFileName() {
        return fileName;
    }
}