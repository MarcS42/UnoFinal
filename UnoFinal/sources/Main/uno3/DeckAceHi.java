/**
 * 
 */
package Main.uno3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author MarcSherman
 *
 */
public class DeckAceHi extends CardDeck {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<CardAceHi> cardSAceHi;
    private String label;
    private static String fileName = new String("deckAceHi.ser");
    
    @Override
    public String getLabel() {
        return label;
    }
    
    /**
     * 
     */
    public DeckAceHi() {
        // TODO Auto-generated constructor stub
    }

    /**Constructor used for HandAceHi
     * @param label
     * @param cardSAceHi
     */
    public DeckAceHi(String label, 
            ArrayList<CardAceHi> cardSAceHi) {
        this.label = label;
        this.cardSAceHi = cardSAceHi;
    }// End hand Constructor
    
    /**
     * @param label
     */
    public DeckAceHi(String label) {
        this.label = label;
        cardSAceHi = new ArrayList<CardAceHi>();

        cardDeckBuilder(2,14,0,3);
    }// End Constructor

    /* (non-Javadoc)
     * @see Main.uno3.CardDeck#cardDeckBuilder(int, int)
     */
    @Override
    public void cardDeckBuilder(int rankMin, int rankMax,
            int suitMin, int suitMax) {
        for(int suit = suitMin; suit <= suitMax; suit++ ) {
            for(int rank = rankMin; rank <= rankMax; rank++) {
                addCard(new CardAceHi(rank, suit));
            }
        }
    }//End cardDeckbuilder

    /**
     * @param deck - DeckAceHi
     * @return deckClone
     */
    public static DeckAceHi cloneDeck(DeckAceHi deck) {
        ArrayList<CardAceHi> deckCopy = new ArrayList<>();
        DeckAceHi deckClone = new DeckAceHi("DeckCopy", deckCopy);
        for(CardAceHi d:deck.getCardSAceHi()) {
            deckClone.getCardSAceHi().add(d);
        }
        serializeDeckAceHi(deckClone);
        return deckClone;
    }

    public static void serializeDeckAceHi(DeckAceHi deck) {
        fileName= getFileName();

        try (ObjectOutputStream oos = 
                new ObjectOutputStream(new FileOutputStream(fileName))) {

            oos.writeObject(deck);
            System.out.println("CloneDeck Done");
            System.out.println("");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static DeckAceHi deserializeDeckAceHi() {
        String filename = getFileName();

        DeckAceHi deck = null;

        try (ObjectInputStream ois 
                = new ObjectInputStream(new FileInputStream(filename))) {

            deck = (DeckAceHi) ois.readObject();
            System.out.println("Deserializatoin Done");
            System.out.println("");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return deck;
    }
    
public static void printDeck(ArrayList<CardAceHi> cardSAceHi) {
        
        for(CardAceHi card:cardSAceHi) {
            System.out.println(card);
        }
    }

@Override
public String toString() {
    return getLabel() + ":" + cardSAceHi.toString();
}
    
    /**Helper/Utility method.
     * Used in many methods
  * @param card card to be added to end of hand AL
  */
 public void addCard(Card card) {
     cardSAceHi.add((CardAceHi) card);
    }

    public ArrayList<CardAceHi> getCardSAceHi() {
        return cardSAceHi;
    }

    public static String getFileName() {
        return fileName;
    }
}
