/**
 * 
 */
package Main.uno3;

//*****Fix  and takeTurn() and fourOfaKindAL and player plays > 1 card ************
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
    private boolean debug;
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
        debug = false;
        CardDeck deck;
        if(!debug) {
            deck = new CardDeck("Deck", true);//AceHiLo Constructor of CardDeck
            deck.shuffle();
            CardDeck.cloneDeck(deck);//copies deck to file 
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
       
        /**
         * State variables initialization
         */
        fourOfaKind = false;
        setFirstPass(true);
        player = pickFirstPlayer();

                
    }// End Constructor SH
    
    /**
     * Displays the current State of the Game
     */
    public void displayState() {
        System.out.println("");
        System.out.println("State Variables: ");
        System.out.println("FourOfaKind: " + fourOfaKind + "; FirstPass: " + firstPass + 
                "; \nCurrent Player: " + player.getName() + "; Debug: " + debug);
        
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
            if(dPSize > 10) {
                for(int i= dPSize - 10; i <= dPSize-1; i++) {
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
        while (!drawPile.empty() && player.getHand().size()
                < handSize) {
            
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

    /** ShitHead- Basic functioning of game is (see takeTurn() below). 
    * a) First Pass actions:
    *       1) optimizeRiver() Cards with Hand cards;
    *       2) FirstPlayer playFirstCard() !Special, draw new card;
    *       3) Advance nextPlayer(Player);
    *       4) displayState().
    * b) Start with previous card == discardPile.last;
    * -) try to match it with what you have; 
    *   b1) if previous is -- or --, exit standard 
    *   play waterfall to handle these cards where 
    *   multiple cards played in sequence changes regular 
    *   waterfall;
    * -) Eventually, if no Match, drawForMatch == next card;
    * -) Next card played becomes previous card to following
    *   player.
     * 
     */
    public void takeTurn() {
        
        /**
         * Start firstPass
         * 
         */
        if(isFirstPass()) {
            optimizeRiver();
            playFirstCard();
            player = nextPlayer(player);
            setFirstPass(false);
            displayState();
        }
        
        System.out.println("Player into TakeTurn() = " 
               + player.getName());
        

        Card prev = discardPile.last();


        /**
         * Start actions for previous card = Special
         */
        if(specialCardSH(prev)) {

            if (threeMirror(prev)) {
                Card prev2 = discardPile.getCard(discardPile.size()-2);

            }
            
            if (sevenPlayLowerCard(prev)) {

            }
            
            if (tenBomb(prev)) {

            }
        }//End actions for previous card = Special

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
 *  2) otherwise searches Player Hand cards for 2, 3, or 10, 
 *      or Q, K, Ace;
 *  3) if found, swaps Hand card for River card.
 *  3.1) Insertion Sort Player Hand cards
 *  3.2) Insertion Sort Player River cards
 *  4) Not found, do nothing/keep looking.
 *  5) Rerun again to pickup riverCards that changed 
 *     position when Hand previously swapped for River
 *  6) Next Player.
 */
    public void optimizeRiver() {
        //   Fixed riverCards sometimes not being replaced with better handCards by running twice.
        //   Problem is that when removeAll and addAll is done on riverCards, card order
        //     changes such that low river card can be skipped. 
        
        for(int j = 0; j < 2; j++) {//Run optimize twice to overcome addAll/removAll discussed above
            for(PlayerSH p:players) {

                int rCardsOptimized = 0;//Counts number of R 
//                cards actually optimized
                int c;
                int optPasses = 0; //count the number of optim. attempts to avoid 
//                     perpetual loop if Hand cards not better than River cards
                while((rCardsOptimized < riverSize) && (optPasses < riverSize)) {
                    optPasses++;
                    Card riverCard = p.getRiver().getCard(rCardsOptimized);
                    if(specialCardNt7SH(riverCard) || 
                            riverCard.getRankAceHi() > 11) {
                        rCardsOptimized++;
                        continue;
                    } else {// Starts at end w/sorted ascending(..??..)
                        for(c=handSize-1; c >= 0; c--) {
                            ArrayList<Card> tempR = new ArrayList<>(), tempH= new ArrayList<>();
                            Card handCard = p.getHand().getCard(c);
                            if(specialCardNt7SH(handCard) || 
                                    handCard.getRankAceHi() > 11) {
                                tempR.add(riverCard);
                                tempH.add(handCard);
                                p.getHand().getCards().removeAll(tempH);
                                p.getHand().getCards().addAll(tempR);
                                p.getRiver().getCards().removeAll(tempR);
                                p.getRiver().getCards().addAll(tempH);
                                rCardsOptimized++;
                                c=-1;//Once R card optimized with swap from H card, stop looking at H cards
                                insertionSortCardHand(p.getHand());
                                insertionSortCardHand(p.getRiver());
                            }//* End if (handCard Special || > 11) 
                        }// End for(c=handSize-1;c >=0 ;c--)
                    }//*End if (riverCard Special || > 11) 
                }// End while(rCardOptimized < riverSize)
            }// End p:players
        }// End rerun via for(j=0;j<2;j++)
    }// End optimize riverCards()
    
    /**
     * 1) Set Rank to look for, starting act 3; 
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

    public void playFirstCard() {
        Card firstCard = null;
        int firstCdCt = 0;
        for(int i = 0; i < handSize; i++) {
            if(firstCard == null) {
                Card f1 = player.getHand().getCards().get(i);
                if(!specialCardSH(f1)) {
                    firstCard = player.getHand().popCard(i);
                    System.out.println("FirstPlayer "+ player.getName()+" plays "+ firstCard);
                    discardPile.addCard(firstCard);
                    Card dCard = draw();
                    System.out.println("FirstPlayer draws "+ dCard);
                    player.getHand().addCard(dCard);
                    firstCdCt++;
                } else {
                    continue;
                }
            }
        }
        // if firstPlayer only has special cards
        if(firstCdCt == 0) {
            firstCard = player.getHand().popCard(0);
            discardPile.addCard(firstCard);
        }  

    }// End playFirstCard().

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
    

}// End ShitHead class
