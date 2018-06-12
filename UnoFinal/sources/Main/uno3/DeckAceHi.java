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
    
    public ArrayList<CardAceHi> getCardSAceHi() {
        return cardSAceHi;
    }
    
    @Override
    public String getLabel() {
        return label;
    }
    
    public static String getFileName() {
        return fileName;
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
    protected void cardDeckBuilder(int rankMin, int rankMax,
            int suitMin, int suitMax) {
        for(int suit = suitMin; suit <= suitMax; suit++ ) {
            for(int rank = rankMin; rank <= rankMax; rank++) {
                addCard(new CardAceHi(rank, suit));
            }
        }
    }//End cardDeckbuilder

    /**
     * @param deck
     * @return
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
}
