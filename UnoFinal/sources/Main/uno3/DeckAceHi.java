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
    private static String fileName = new String("deckacehi.ser");
    
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
    }
    
    /**
     * @param label
     */
    public DeckAceHi(String label) {
        this.label = label;
        cardSAceHi = new ArrayList<CardAceHi>();

        cardDeckBuilder(14, 3);
    }

    /* (non-Javadoc)
     * @see Main.uno3.CardDeck#cardDeckBuilder(int, int)
     */
    @Override
    protected void cardDeckBuilder(int rankMax, int suitMax) {
        for(int suit = 0; suit <= suitMax; suit++ ) {
            for(int rank = 2; rank <= rankMax; rank++) {
                addCard(new CardAceHi(rank, suit));
            }
        }

    }//End cardDeckbuilder

}
