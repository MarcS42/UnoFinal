/**
 * 
 */
package Main.uno3;

import java.util.ArrayList;

/**
 * @author MarcSherman
 *
 */
public class ShitHead {
    
    private ArrayList<PlayerSH> players;
    private PlayerSH player;
    private static boolean fourOfaKind;
    private CardHand drawPile;
    private CardHand bomb;
    private CardHand discardPile;
    
    /**In SH only draw if 
     * 1) you played a card; 
     * 2) have < 3 cards in your hand; and
     * 3) drawPile is not empty
     *  
     * @return
     */
    public Card draw() {
        while (!drawPile.empty() && player.getHand().size()<3) {
        return drawPile.popCard();
        }
        return null;
    }
    
    public PlayerSH getPlayer(PlayerSH player) {
        return player;
    }
    
    public void setPlayer(PlayerSH player) {
       this.player = player;
    }
    
    public boolean fourOfaKind(boolean fourOfaKind) {
        return fourOfaKind;
    }
    
    public static boolean isFourOfaKind() {
        return fourOfaKind;
    }

    public void setFourOfaKind(boolean fourOfaKind) {
        this.fourOfaKind = fourOfaKind;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
