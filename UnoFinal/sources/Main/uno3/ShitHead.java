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
    private ArrayList<Card> fourOfaKindAL;
    private PlayerSH player;
    private static boolean fourOfaKind;
    private CardHand drawPile;
    private CardHand bomb;
    private CardHand discardPile;
    private boolean firstPass;
    

    public ShitHead() {
        /**
         * SetUp variables
         */
        int handSize = 7;
        int holeSize = 3;
        int riverSize = 3;
        int numberPlayers;
        
        
        ArrayList<Card> dwPiles = new ArrayList<>();
        ArrayList<Card> disPiles = new ArrayList<>();
        ArrayList<Card> bomPiles = new ArrayList<>();
        ArrayList<PlayerSH> players = new ArrayList<>();
        
        CardHand drawpile = new CardHand("dwPile", dwPiles);
        CardHand discardPiles = new CardHand("disPile", disPiles);
        CardHand bombPiles = new CardHand("bomPile", bomPiles);

        /*
         * Debugging tool lets you repeat a game with the same 
         * Deck and initial deal. 
         * 
         * Hole card play alters the game as 
         * 1) the hole card played is selected at random, and 
         * 2) each hole card played is not memorized.
         */
        boolean debug = false;
        CardDeck deck;
        if(!debug) {
            deck = new CardDeck("Deck", true);
            deck.shuffle();
            CardDeck.cloneDeck(deck);//copies deck to file 
            //in case need to rerun game
        } else {deck = CardDeck.deserializeCardDeck();
           }
        
        numberPlayers = 4;
        for (int i = 0; i < numberPlayers; i++) {
            PlayerSH newPlayer = new PlayerSH("P " +i);
            deck.deal(newPlayer.getHole(), holeSize);
//            CardHand.insertionSortCardHand(newPlayer.getHand());
            players.add(newPlayer);
        }
        for(PlayerSH p:players) {
            deck.deal(p.getRiver(), riverSize);
            CardHand.insertionSortCardHand(p.getRiver());
        }
        for(PlayerSH p:players) {
            deck.deal(p.getHand(), handSize);
            CardHand.insertionSortCardHand(p.getHand());
        }
        
        /**
         * State variables initialization
         */
        fourOfaKind = false;
        firstPass = true;
        player = players.get(0);

                
    }
    
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
        ShitHead.fourOfaKind = fourOfaKind;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
