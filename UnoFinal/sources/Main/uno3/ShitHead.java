/**
 * 
 */
package Main.uno3;

//*****Fix fourOfaKindAL and player plays > 1 card ************
import static Main.uno3.SpecialCardsSH.*;
//import static Main.uno3.Card.cardsPlayableRankSH;
//import static Main.uno3.Card.*;
import static Main.uno3.CardHand.*;
//import Main.uno3.PlayerSH.*;

import java.util.ArrayList;

/**
 * @author MarcSherman
 *
 */
public class ShitHead {
    
    private ArrayList<PlayerSH> players;
    private ArrayList<Card> fourOfaKindAL;
    PlayerSH player;
    private Card prev;
    private static boolean fourOfaKind;
    private CardHand drawPile;
    private CardHand bomb;
    CardHand discardPile;
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

        /** Fix needed, test failed. derserialization returns Hands/Piles w/Null
         *  for Card rank
         * 
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
            
            set3MSPCardsInDeck(deck);

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
            insertionSortCardHand(p.getHand());
        }
        deck.dealAll(drawPile);
       
        /**
         * State variables initialization
         */
        fourOfaKind = false;
        setFirstPass(true);
        setPlayer(players.get(0));
        prev = null;

                
    }// End Constructor SH

    /**Sets SpecialCardsSH threeMirror variables.
     * Enables use of contains(Card threeMirror) in SpecialCardsSH handling.
     * @param deck
     */
    public void set3MSPCardsInDeck(CardDeck deck) {
        for(int i=0; i < deck.size(); i++) {
            if(threeMirror(deck.getCard(i))) {
                if(deck.getCard(i).getSuit() == 0) {
                    SpecialCardsSH.threeC = deck.getCard(i);
                }
                if(deck.getCard(i).getSuit() == 1) {
                    SpecialCardsSH.threeD = deck.getCard(i);
                }
                if(deck.getCard(i).getSuit() == 2) {
                    SpecialCardsSH.threeH = deck.getCard(i);
                }
                if(deck.getCard(i).getSuit() == 3) {
                    SpecialCardsSH.threeS = deck.getCard(i);
                }
            }
        }
    }
    
    /**
     * Displays the current State of the Game
     */
    public void displayState() {
        System.out.println("");
        System.out.println("State Variables: ");
        System.out.println( 
                " \nCurrent Player: " + player.getName() + "; Debug: " + debug + 
                "; Card prev: " + prev);
        
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
     * @return isGameOver?
     */
    public boolean isDone() {
        for (int i = 0; i < players.size(); i++) {
            if (PlayerSH.playerIsDone(players.get(i))) 
            {
                System.out.println("**********Player " + 
                        players.get(i).getName() + " is done.**********");
                players.remove(i);
            }
            if(players.size() <= 1) 
                {
                System.out.println("***********Player " + 
                        players.get(0).getName() + " is a "
                                + "ShitHead!**********\n" + "********Game Over!*********");
                return true; 
                }
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
    public void draw() {
        while (!drawPile.empty() && player.getHand().size()
                < handSize) 
        {
            Card drawCard = drawPile.popCard();
            System.out.println("\n"+player.getName() +
                    " draws: " + drawCard);
            player.getHand().addCard(drawCard);
        }
        
        if(drawPile.empty())
        {
            System.out.println("");
        }else
        {
        System.out.println("Player has plenty Hand Cards: ");
        }
    } // End draw()
    
    public void playGame() {
    
            //keep playing until there's a winner (no cards left in hand)
            while(!isDone()) {
    //            displayState();
                //            waitForUser();
                takeTurn2();
            }
        }

        //**********************************************************************
        /** ShitHead- Basic functioning of game is (see takeTurn()) below. 
        * a) First Pass actions:
        *       1) optimizeRiver() Cards with Hand cards;
        *       2) FirstPlayer playFirstCard()'s !Special, draw new card;
        *       3) Advance nextPlayer(Player);
        *       4) displayState().
        * b) Start with previous card == discardPile.last;
        * c) try to match it with what you have; 
        *   c1) if previous is 3Mirror, exit standard 
        *   play waterfall to handle this card where 
        *   multiple cards played in sequence changes regular 
        *   waterfall;
        * d) Eventually, if no Match, pickup discardPile;
        * e) Next card played becomes previous card to following
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
            pickFirstPlayer();
            playFirstCard();
            player = nextPlayer(player);
            setFirstPass(false);
            displayState();
        }

        /**
         * Problem is if players last hole card is tenBomb.
         * Discard Pile.size()= 0, yet game is still trying to
         * get tenBomb player to playNext()     
         */
        if(!PlayerSH.playerIsDone(player)) {
            System.out.println("Player into TakeTurn() = " 
                    + player.getName());
        } else {
            player = nextPlayer(player);
            System.out.println("Player into TakeTurn()#2 = " 
                    + player.getName());
            player.playNext(this);//needed if last card tenBomb && playerDone
            player = nextPlayer(player);
            System.out.println("Next Player into TakeTurn()#3 = " 
                    + player.getName());
        }

        if(!discardPile.empty()) {
            prev = discardPile.last();
        }else {prev = player.playNext(this);}

        /**
         * Start actions for previous card = Special
         */
        if(specialCardNt7SH(prev)) {

            if (threeMirror(prev) && discardPile.size() >= 2) 
            {

                Card tgtMatch = 
                        discardPile.getCard(Math.max((discardPile.size()-numThreeMirrors(discardPile)-1),0));
                threeMirrorPlay(player, tgtMatch);
                
                if(!isDone()) {
                player=nextPlayer(player);
                System.out.println("Player out 3Mirror " + player.getName() +
                        " is next player: ");
                }
            }

        }//End actions for previous card = Special

        Card next = player.play(this, prev);
        if(next==(null) && !isDone()) {
            System.out.println(player.getName() + " picked up DiscardPile.");
            player = nextPlayer(player);
            System.out.println(player.getName() + " is CurrentPlayer");
            next = player.playNext(this);
            displayState();
            player = nextPlayer(player);
        }else 
            if(!tenBomb(next))
            {
                discardPile.addCard(next);
                System.out.println(player.getName()+ " plays "+ next);
                System.out.println("");
                setTenBomb(false);
                displayState();
                player = nextPlayer(player);
            }

        /**
         * Actions needed when NEXT card is special card
         */
        if (specialCardSH(next)) {

         if(tenBomb(next)) 
         {
         setTenBomb(true);
         int count = 0;
          while(isTenBomb() && !PlayerSH.playerIsDone(player))
            {count++;
             setPlayer(player);
             System.out.println(player.getName() + 
                        " Bombed DiscardPile " + count + " times");
             discardPile.addCard(next);
             displayState();
             discardPile.dealAll(bomb);
             setPrevCard(player.playNext(this));
             displayState();
             
                if(!tenBomb(prev)) {
                    setTenBomb(false);    
                    player = nextPlayer(player);
                      } else 
                         {
                          if(!PlayerSH.playerIsDone(player)) 
                         {
                          continue;
                         }  else {System.out.println(player.getName() + " Bombed DiscardPile Again with a " + next +"! "
                                  + "\n And is Done!!");
                          discardPile.addCard(prev);
                          displayState();
                          discardPile.dealAll(bomb);
                          
                          player = nextPlayer(player);
                          setPrevCard(player.playNext(this));
                          displayState();
                         }// End if(!PlayerSH.playerIsDone(player))
                    }//End if(!tenBomb(prev))
            }//End While
         }// End if(tenBomb(next))?
        }//End special card next
    } //End takeTurn(PlayerSH)
 
    public void takeTurn2() {

        /**
         * Start firstPass
         * 
         */
        if(isFirstPass()) {
            optimizeRiver();
            pickFirstPlayer();
            playFirstCard2();
            player = nextPlayer(player);
            setFirstPass(false);
            displayState();
        }

        /**
         * Problem is if players last hole card is tenBomb.
         * Discard Pile.size()= 0, yet game is still trying to
         * get tenBomb player to playNext()     
         */
        if(!PlayerSH.playerIsDone(player)) {
            System.out.println("Player into TakeTurn() = " 
                    + player.getName());
        } else {
            player = nextPlayer(player);
            System.out.println("Player into TakeTurn()#2 = " 
                    + player.getName());
            CardHand playNext = player.playNext2(this);//needed if last card tenBomb && playerDone
            setPrevCard(playNext.last());
            player = nextPlayer(player);
            System.out.println("Next Player into TakeTurn()#3 = " 
                    + player.getName());
        }

        if(!discardPile.empty()) {
            prev = discardPile.last();
        }else {CardHand playNext = player.playNext2(this);
            prev = playNext.last();}

        /**
         * Start actions for previous card = Special
         */
        if(specialCardNt7SH(prev)) {

            if (threeMirror(prev) && discardPile.size() >= 2) 
            {
/** one problem: 2) if serchForMatch2 returns 
 *    null, end up trying to pop a null card from a cardhand 
 *    that is null itself; this gives index OutOfBound error as 
 *    popcard is trying to popcard(-1): size()-1 => 0 - 1 => popcard(-1). 
 *     
 *    
 */
                Card tgtMatch = 
                      discardPile.getCard(Math.max((discardPile.size()-numThreeMirrors(discardPile)-1),0));
                threeMirrorPlay(player, tgtMatch);
                
                if(!isDone()) {
                player=nextPlayer(player);
                System.out.println("Player out 3Mirror " + player.getName() +
                        " is next player: ");
                }
            }

        }//End actions for previous card = Special

        CardHand cardsToPlay = player.play2(this, prev);
        Card next;
        if(cardsToPlay != null) {
        next = cardsToPlay.last();
        } else 
        {next = null;}
            
        if(next==(null) && !isDone()) {
            System.out.println(player.getName() + " picked up DiscardPile.");
            
            player = nextPlayer(player);
            System.out.println(player.getName() + " is CurrentPlayer");
            
            CardHand playNext = player.playNext2(this);
            next = playNext.last();
            displayState();
            
            while(tenBomb(next) && !PlayerSH.playerIsDone(player)) {
                discardPile.dealAll(bomb);
                playNext = player.playNext2(this);
                next = playNext.last();
                displayState();
                }
            
            player = nextPlayer(player);
        }else 
            if(!tenBomb(next))
            {
                discardPile.addCards(cardsToPlay);
                System.out.println(player.getName()+ " plays " + cardsToPlay.size() +
                     " x " +  next + " card(s).");
                System.out.println("");
                setTenBomb(false);
                displayState();
                player = nextPlayer(player);
            }

        /**
         * Actions needed when NEXT card is special card
         */
        if (specialCardSH(next)) {

         if(tenBomb(next)) 
         {
         setTenBomb(true);
         int count = cardsToPlay.size();
          while(isTenBomb() && !PlayerSH.playerIsDone(player))
            {setPlayer(player);
             System.out.println(player.getName() + 
                        " Bombed DiscardPile " + count + " times");
             discardPile.addCards(cardsToPlay);
             displayState();
             discardPile.dealAll(bomb);
             CardHand playNext = player.playNext2(this);
             setPrevCard(playNext.last());
             displayState();
             
                if(!tenBomb(prev)) {
                    setTenBomb(false);    
                    player = nextPlayer(player);
                      } else 
                         {
                         if(!PlayerSH.playerIsDone(player)) 
                         {
                          continue;
                         }  else {System.out.println(player.getName() + " Bombed DiscardPile Again with a " + next +"! "
                                  + "\n And is Done!!");
                          discardPile.addCards(cardsToPlay);
                          displayState();
                          discardPile.dealAll(bomb);
                          
                          player = nextPlayer(player);
                          setPrevCard(player.playNext(this));
                          displayState();
                         }// End if(!PlayerSH.playerIsDone(player))
                    }//End if(!tenBomb(prev))
            }//End While
         }// End if(tenBomb(next))?
        }//End special card next
    } //End takeTurn2(PlayerSH)
        
        /**Fixed tenBomb code
         * 
         * 
         * @param player
         * @param tgtMatch
         * @return
         */
        public PlayerSH threeMirrorPlay(PlayerSH player, Card tgtMatch) {

            boolean threeMsMatchChK = true;

            if(!player.getHand().empty()) {

             /*****Looking for threeMirrors in Hand cards****/

                    while(threeMsMatchChK) 
                    {
                        CardHand cardsToPlay = player.searchForMatch2(tgtMatch);
                        Card next;
                        int numCardsPlayed;
                        
                        if(cardsToPlay != null) {
                        next = cardsToPlay.last();
                        numCardsPlayed = cardsToPlay.size();
                        } else next = null;

                        if((next !=null) && threeMirror(next)) 
                        {
                            numCardsPlayed= cardsToPlay.size();
                            System.out.println("Player " + player.getName() +
                                    " plays from Lp1 "+ numCardsPlayed+ " x " + next+ " cards(s).");
                            discardPile.addCards(cardsToPlay);
                            draw();
                            threeMsMatchChK=true;
                            
                            player = nextPlayer(player);
                            setPlayer(player);
                            System.out.println("Player " + player.getName() +
                                    " is next player out 3Match after LP1: ");
                            displayState();
                            
                         continue;

                        }// End if((next !=null) && threeMirror(next))

                        if(next==null) {
                            discardPile.dealAll(player.getHand());
                            System.out.println(player.getName() + " picked up DiscardPile.");
                            player = nextPlayer(player);
                            System.out.println(player.getName() + " is CurrentPlayer");
                            cardsToPlay = player.playNext2(this);
                            next = cardsToPlay.last();

                            setPrevCard(next);
                            setPlayer(player);
                            displayState();
                            return player;   
                        }else {//!3mirror and !null
                            numCardsPlayed= cardsToPlay.size();
                            System.out.println("Player " + player.getName() +
                                    " plays " + numCardsPlayed + " x " 
                                    + next + " card(s)");
                            discardPile.addCards(cardsToPlay);
                            displayState();
                            draw();
                            if(tenBomb(next) && !PlayerSH.playerIsDone(player))
                            {
                                System.out.println("Player "+ player.getName()+
                                        " Bombed the discardPile");
                                discardPile.dealAll(bomb);
                                displayState();
                                cardsToPlay = player.playNext2(this);
                                next = cardsToPlay.last();
                            }

                            setPrevCard(next);
                            setPlayer(player);
                            displayState();
                            return player;
                        }//End if(next==null)
                    }// End while(threeMsMatchChK) loop 1
            }// End if(!player.hand.empty)

            if(!player.getRiver().empty() && player.getHand().empty()) {

             /*****Looking for threeMirrors in River cards****/
                while(threeMsMatchChK) 
                {
                    CardHand cardsToPlay = player.searchForMatch2(tgtMatch);
                    Card next;
                    int numCardsPlayed;
                    if(cardsToPlay != null) {
                    next = cardsToPlay.last();
                    numCardsPlayed = cardsToPlay.size();
                    } else {next = null;
                    numCardsPlayed = 0;}

                    if((next != null) && threeMirror(next)) 
                    {
                        System.out.println("Player " + player.getName() +
                                " plays from RLp2 " + numCardsPlayed + 
                                " x " + next + " card(s).");
                        discardPile.addCards(cardsToPlay);
                        draw();
                        threeMsMatchChK=true;
                        
                        player = nextPlayer(player);
                        setPlayer(player);
                        System.out.println("Player " + player.getName() +
                                " is next player out 3Match after RLP2: ");
                        displayState();
                        
                     continue;

                    }// End River if((next !=null) && threeMirror(next))

                    if(next==null) {
                        discardPile.dealAll(player.getHand());
                        System.out.println(player.getName() + " picked up DiscardPile.");
                        player = nextPlayer(player);
                        System.out.println(player.getName() + " is CurrentPlayer");
                        cardsToPlay = player.playNext2(this);
                        next = cardsToPlay.last();

                        setPrevCard(next);
                        setPlayer(player);
                        displayState();
                        return player;   
                    }

                    if(cardsPlayableRankSH(tgtMatch, next))
                    {                             
                        System.out.println("Player " + player.getName() +
                                " plays "+ numCardsPlayed +" x "+ 
                                next + " card(s)");
                        discardPile.addCards(cardsToPlay);
                        
                        if(tenBomb(next) && !PlayerSH.playerIsDone(player))
                        {
                            System.out.println("Player "+ player.getName()+
                                    " Bombed the discardPile");
                            discardPile.dealAll(bomb);
                            displayState();
                            cardsToPlay = player.playNext2(this);
                            next = cardsToPlay.last();
                        }

                        setPrevCard(discardPile.last());
                        setPlayer(player);
                        displayState();
                        return player; 
                    }// End if(cardsPlayableRankSH(tgtMatch, next)
                }// End for loop 2
            }// End if(!player.River.empty)

            /****** Playing Hole cards in ThreeMirror *******/
            if(player.getHand().empty() && player.getRiver().empty())
            {
                CardHand cardToPlay = player.pickRandomHoleCard2(tgtMatch);
                Card next;
                if(cardToPlay != null) {
                next = cardToPlay.last();
                } else {next = null;}

                if(next != null)
                {                             
                    System.out.println("Player " + player.getName() +
                            " plays Hole card (fm 3Mirror) " + next);
                    discardPile.addCard(next);
                    
                    if(tenBomb(next))
                    {
                        System.out.println("Player "+player.getName()+
                                " Bombed the discardPile from the Hole");
                        discardPile.dealAll(bomb);
                        if(!PlayerSH.playerIsDone(player)) {
                           CardHand cardsToPlay = player.playNext2(this);
                            next = cardsToPlay.last();
                        }else 
                         {player = nextPlayer(player);
                        System.out.println(player.getName() + " is CurrentPlayer");
                        CardHand cardsToPlay = player.playNext2(this);
                        next = cardsToPlay.last();
                        }
                    }

                    setPrevCard(next);
                    setPlayer(player);
                    displayState();
                    return player;
                }//End if(next != null)
                else 
                {
                    discardPile.dealAll(player.getHand());
                    System.out.println(player.getName() + " picked up DiscardPile.");
                    player = nextPlayer(player);
                    System.out.println(player.getName() + " is CurrentPlayer");
                    CardHand cardsToPlay = player.playNext2(this);
                    next = cardsToPlay.last();


                    setPrevCard(next);
                    setPlayer(player);
                    displayState();
                    return player;   
                }//End if(next==null)
            }//End Hand.empty() && River.empty()
           return player;  
        }//End method threeMirrorPlay(PlayerSH player, Card tgtMatch)
        
    /**OptimizeRiver()
     * For each Player:
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

                int rCardsOptimized = 0;/*Counts number of R 
                             cards actually optimized*/
                int Hc;
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
                        for(Hc=handSize-1; Hc >= 0; Hc--) {
                            ArrayList<Card> tempR = new ArrayList<>(), 
                                    tempH= new ArrayList<>();
                            Card handCard = p.getHand().getCard(Hc);
                            if(specialCardNt7SH(handCard) || 
                                    handCard.getRankAceHi() > 11) {
                                tempR.add(riverCard);
                                tempH.add(handCard);
                                p.getHand().getCards().removeAll(tempH);
                                p.getHand().getCards().addAll(tempR);
                                p.getRiver().getCards().removeAll(tempR);
                                p.getRiver().getCards().addAll(tempH);
                                rCardsOptimized++;
                                Hc=-1;//Once R card optimized with swap 
                                    //from H card, stop looking at H cards
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

    public void playFirstCard() {
        Card firstCard = null;
        for(int i = 0; i < handSize; i++) {
            if(firstCard == null) {
                Card f1 = player.getHand().getCards().get(i);
                if(!specialCardSH(f1)) {
                    firstCard = player.getHand().popCard(i);
                    System.out.println("FirstPlayer "+ player.getName()+" plays "+ firstCard);
                    discardPile.addCard(firstCard);
                    draw();
                } else {
                    continue;
                }
            }
        }
        // if firstPlayer only has special cards
        if(firstCard == null) {
            firstCard = player.getHand().popCard(0);
            discardPile.addCard(firstCard);
            System.out.println("FirstPlayer only has special HCards " +
            player.getName()+" plays "+ firstCard);
        }  
    }// End playFirstCard().

    public void playFirstCard2() {
        player.playNext2(this);
        
//        ArrayList<Card> firstCards;
//        for(int i = 0; i < handSize; i++) {
//            Card f1 = player.getHand().getCards().get(i);
//            
//            if(!specialCardSH(f1)) {
//             firstCards.add(player.getHand().popCard(i));
//                System.out.println("FirstPlayer "+ player.getName()+" plays "+ firstCard);
//                discardPile.addCard(firstCard);
//                draw();
//            } else {
//                continue;
//            }
//        }
//        // if firstPlayer only has special cards
//        if(firstCard == null) {
//            firstCard = player.getHand().popCard(0);
//            discardPile.addCard(firstCard);
//            System.out.println("FirstPlayer only has special HCards " +
//            player.getName()+" plays "+ firstCard);
//        }  
    }// End playFirstCard2().
    
    public PlayerSH nextPlayer(PlayerSH currentPlayer) {
        int indexNextPlayer;
        indexNextPlayer = (players.indexOf(currentPlayer)+1) % players.size();
        return players.get(indexNextPlayer);
    }
 
//*****************************************************    
    public PlayerSH getPlayer() {
        return this.player;
    }
    
    public void setPlayer(PlayerSH player) {
       this.player = player;
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
        
    }
    

    public Card getPrevCard() {
        return prev;
    }

    public void setPrevCard(Card prev) {
        this.prev = prev;
    }

    public boolean fourOfaKind(boolean fourOfaKind) {
        return fourOfaKind;
    }

    public ArrayList<Card> getFourOfaKindAL() {
        return fourOfaKindAL;
    }

    public void setFourOfaKind(boolean fourOfaKind) {
        ShitHead.fourOfaKind = fourOfaKind;
    }

    public static boolean isFourOfaKind() {
        return fourOfaKind;
    }

    public boolean cardCountMoreThan52() {
        int playerSum, playersSum=0, ShSum;
        for(PlayerSH p:players) {
            playerSum=p.getHand().size()+
                    p.getRiver().size()+p.getHole().size();
            playersSum=+playerSum;
        }
        ShSum = bomb.size()+discardPile.size()+
                drawPile.size();
        if(playersSum+ShSum > 52) {
           return true; 
        }
        return false;
    }
    

}// End ShitHead class
