/**
 * 
 */
package Main.uno3;

//*****Fix  and takeTurn() and fourOfaKindAL and player plays> 1 card ************
import static Main.uno3.SpecialCardsSH.*;
//import static Main.uno3.Card.*;
import static Main.uno3.CardHand.*;

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
    private int handSize;
    private int riverSize;
    

    public ShitHead() {//ShitHead Constructor
        /**
         * SetUp variables
         */
        handSize = 4;
        int holeSize = 3;
        riverSize = 3;
        int numberPlayers;
        
        
        ArrayList<Card> dwPile = new ArrayList<>();
        ArrayList<Card> disPile = new ArrayList<>();
        ArrayList<Card> bomPile = new ArrayList<>();
        
        fourOfaKindAL = new ArrayList<>();
        
        players = new ArrayList<>();
        
        drawPile = new CardHand("dwPile", dwPile);
        discardPile = new CardHand("disPile", disPile);
        bomb = new CardHand("bomPile", bomPile);

        /**
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
            deck = new CardDeck("Deck", true);//AceHiLo Constructor of CardDeck
            deck.shuffle();
            cloneDeck(deck);//copies deck to file 
            //in case need to rerun game
        } else {deck = CardDeck.deserializeCardDeck();
           }
        
        numberPlayers = 4;
        for (int i = 0; i < numberPlayers; i++) {
            PlayerSH newPlayer = new PlayerSH("P " + i);
            deck.deal(newPlayer.getHole(), holeSize);
            players.add(newPlayer);
        }
        for(PlayerSH p:players) {
            deck.deal(p.getRiver(), riverSize);
            insertionSortCardHand(p.getRiver());
        }
        for(PlayerSH p:players) {
            deck.deal(p.getHand(), handSize);
            CardHand.insertionSortCardHand(p.getHand());
        }
        deck.dealAll(drawPile);
       
//        System.out.println("Constructor test: ");
//        System.out.println("Player Cards test: ");
//        for(PlayerSH player:players) {
//            System.out.println("Hand cards");
//            player.getHand().display();
//            System.out.println("River cards");
//            player.getRiver().display();
//            System.out.println("Hole cards");
//            player.getHole().display();
//        }
//        System.out.println("Draw Pile: " + drawPile.size());
//        drawPile.display();
        
        /**
         * State variables initialization
         */
        fourOfaKind = false;
        setFirstPass(true);
        player = players.get(0);

                
    }// End Constructor SH
    
    /**
     * Displays the current State of the Game
     */
    public void displayState() {
        System.out.println("");
        System.out.println("State Variables: ");
        System.out.println("FourOfaKind: " + fourOfaKind + "; FirstPass: " + firstPass + "; Current Player: " + player.getName() );
        
        System.out.println("NextPlayer = " + nextPlayer(player).getName());
        System.out.println("");
        
        for (PlayerSH player:players) {
            insertionSortCardHand(player.getHand());
            insertionSortCardHand(player.getRiver());
            System.out.println("Hand cards");
            player.getHand().display();
            System.out.println("River cards");
            player.getRiver().display();
            System.out.println("Hole cards remaining: " + player.getHole().size());
            System.out.println("");
        }
        
        System.out.println("Discard Pile:");
        if(discardPile != null) {
            int dPSize = discardPile.size();
            if(dPSize > 15) {
                for(int i= dPSize - 15; i <= dPSize-1; i++) {
                    Card.printCard(discardPile.getCard(i));
                }
                System.out.println("");    
            } else {discardPile.display();}
        }
        
        if(drawPile != null) {
            System.out.println("Draw Pile:");
            System.out.println(drawPile.size() + " cards");
            System.out.println("");
        }
    } // End displayState()
    
    /**
     * Is player out of cards?
     * Yes => remove from players AL
     * Print "player done"
     * @return
     */
    public boolean isDone() {
        for (int i = 0; i < players.size(); i++) {
            if (player.getHand().empty() && 
                    player.getRiver().empty() && 
                      player.getHole().empty()) {
                System.out.println("Player " + 
                        players.get(i).getName() + " is done.");
                players.remove(i);
            }
            if(players.size() <= 1) {
                return true; }
        }
        return false;
    }// End isDone()?
    
    /**In SH only draw if 
     * 1) you played a card; 
     * 2) have < #handSize cards in your hand; and
     * 3) drawPile is not empty
     *  
     * @return
     */
    public Card draw() {
        while (!drawPile.empty() && player.getHand().size()< handSize) {
        return drawPile.popCard();
        }
        return null;
    }
    
    public PlayerSH getPlayer() {
        return this.player;
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

    public boolean isFirstPass() {
        return firstPass;
    }

    public void setFirstPass(boolean firstPass) {
        this.firstPass = firstPass;
    }

    public CardHand getDiscardPile() {
        return discardPile;
        
    }public CardHand getDrawPile() {
        return drawPile;
    }

    public CardHand getBomb() {
        return bomb;
    }

    public ArrayList<PlayerSH> getPlayers() {
        return players;
        
    }public ArrayList<Card> getFourOfaKindAL() {
        return fourOfaKindAL;
    }
    
  //------------------------------------------------------------------------    

    /** Uno- Basic functioning of game is (see takeTurn() below). 
    * a) start with previous card == discardPile.last;
    *   a1) If it is first round of game, deal with special
    *   cards that change the initial player from P0(skip, reverse) 
    * b) try to match it with what you have; 
    *   b1) if previous is WD4 or D2, exit standard 
    *   play waterfall to handle these cards where 
    *   multiple cards played in sequence changes regular 
    *   waterfall;
    * c) Eventually, if no Match, drawForMatch == next card;
    * d) Next card played becomes previous card to following
    *   player.
     * 
     */
    public void takeTurn() {
        
        if(isFirstPass()) {
//            exchange Hand and River cards
            player = pickFirstPlayer();
            setFirstPass(false);
        }
        
        System.out.println("Player into TakeTurn() = " 
               + player.getName());
        

        Card prev = discardPile.last();

        /**
         * Start firstPass && Special Card
         * 
         * Only spC's you can have affected by FirstPass are
         * Skip and Reverse because they change the order of play 
         */
        if (firstPass && 
                specialCardSH(prev)) {
            firstPass = false;
//            if (unoCardSkip(prev)) {
//                player = players.get(players.indexOf(player)+1);
//            }
//            if (unoCardReverse(prev)) {
//                toggleDirectionOfPlay();
//                player = players.get(players.size()-1);
//            }
        } //End firstPass && Special tests

        /**
         * Start actions for previous card = Special
         */
        if(specialCardSH(prev)) {
            if (twoReset(prev)) {

            }

            if (threeMirror(prev)) {

            }
//            prev = discardPile.last();
        }//End actions for previous card = Special

//        if(!uCardWldorWD4(prev)) {
//            System.out.println("nextPlayer() = " + 
//                    player.getName() + " prev card = " + 
//                    prev);
//        } else {System.out.println("nextPlayer() = " + 
//                player.getName() + " prev card = " + prev+ 
//                " Match " + Card.getRANKSAceHi()[wildColor]);}
//
        Card next = player.play(this, prev);


        /**
         * Actions needed when NEXT card is special card
         */
        if (specialCardSH(next)) {
            if (twoReset(next)) {
                
            }
            
            if (threeMirror(next)) {
                
            }

            if(tenBomb(next)) {
                discardPile.dealAll(bomb);
                //currentPlayer=nextPlayer
            }

            if(sevenPlayLowerCard(next)) {

            }
        }//End special card next

        if (!specialCardSH(next)) {
//            playerPlaysNext(next);            
        }

    } //End takeTurn(PlayerSH)

/**For each Player:
 * Looks at river Cards:
 *  1) If card is 2, 3, or 10, or Q, K, Ace, do nothing; 
 *  2) otherwise searches Player Hand cards for 2, 3, or 10, or Q, K, Ace;
 *  3) if found, swaps Hand card for River card.
 *  3.1) Insertion Sort Player Hand cards
 *  4) Not found, do nothing/keep looking.
 *  5) Next Player.
 */
public void optimizeRiver() {
    
    for(PlayerSH p:players) {
        for(Card card:p.getRiver().getCards()) {
            if(specialCardNt7SH(card) || 
                    card.getRank() > 11) {
                continue;
            } else {
                for(Card c:p.getHand().getCards()) {
                    if(specialCardNt7SH(c) || 
                            c.getRank() > 11) {
                        Card tempR = card;
                        Card tempH = c;
                        p.getHand().getCards().remove(c);
                        p.getRiver().getCards().remove(card);
                        p.getHand().getCards().add(tempR);
                        p.getRiver().getCards().add(tempH);
                        insertionSortCardHand(p.getHand());
                    } else {
                        continue;
                    }
                }
            }
        }
        
    }
}//End optimizeRiver()
    
/**
 * 1) Set Rank to look for, starting at 3; 
 * 2) Run through players counterclockwise looking at River cards(face up) first; 
 * 3) Run through players counterclockwise looking at Hand cards(hidden).
 * @return FirstPlayer
 */
public PlayerSH pickFirstPlayer() {
    for(int rank = 3; rank <= 14; rank++) {
        for(int i = players.size()-1; i >= 0; i--) {// counterclockwise
            ArrayList<Card> pRiverCards = players.get(i).getRiver().getCards();
            for(Card card:pRiverCards) {
                if(card.getRankAceHi() == rank) {
                    setPlayer(players.get(i));
                    return getPlayer(); // Short circuit
                }
            }
        }// End look at River cards
        for(int i = players.size()-1; i >= 0; i--) {
            ArrayList<Card> pHandCards = players.get(i).getHand().getCards();
            for(Card card:pHandCards) {
                if(card.getRankAceHi() == rank) {
                    setPlayer(players.get(i));
                    return getPlayer(); // Short circuit
                }
            }
        }// End look at Hand cards
    }
    return getPlayer();
}//End pickFirstPlayer
    
    public void playGame() {

        //keep playing until there's a winner (no cards left in hand)
        while(!isDone()) {
            displayState();
            //            waitForUser();
            takeTurn();
        }
    }
    
    public PlayerSH nextPlayer(PlayerSH currentPlayer) {
        int indexNextPlayer;
        indexNextPlayer = (players.indexOf(currentPlayer)+1) % players.size();
        return players.get(indexNextPlayer);
    }
    

}
