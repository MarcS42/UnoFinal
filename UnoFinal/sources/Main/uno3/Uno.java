package Main.uno3;


import java.util.Scanner;

import static Main.uno3.UnoSpecialCards.*;

import java.util.ArrayList;


/**
 * Uno.java encapsulates the state of the game
 * 
 * Basic functioning of game is(see takeTurn() below). 
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
 * 
 * @author MarcSherman
 *
 */
public class Uno {
    private ArrayList<UnoPlayer> players;
    private UnoPlayer player;
    private Scanner in;
    private UnoHand drawPile;
    private UnoHand discardPile;
    private static boolean clockwise;
    private static boolean skip;
    private boolean firstPass;
    private static int wildColor;
    
    
    /**
     * Constructor of Uno
     * Sets up initial game State.
     * Gets Player input from user
     * Initializes Player instances and ArrayList<Player> objects
     * Start with a deck, shuffle it, 
     *      deal the hands to the players, 
     * put initial card into the discard pile, then deal rest 
     *         of deck to draw pile.
     * Initialize key game variables starting values.
     */
    public Uno() { 
        in = new Scanner(System.in);
        players = new ArrayList<UnoPlayer>();

        int handSize = 7;
        int numberPlayers;
//        String playerName;
        
/*
 * Debugging tool lets you repeat a game with the same 
 * Deck and initial deal. 
 * Wild cards alter the game as 
 * the Target Color for each Wild card played is not 
 * memorized.
 */
        boolean debug = false;
        UnoDeck deck;
        if(!debug) {
            deck = new UnoDeck("Deck");
            deck.shuffle();
            UnoDeck.cloneDeck(deck);//copies deck to file 
            //in case need to rerun game
        } else {deck = UnoDeck.deserializeUnoDeck();
        }
//        System.out.println("Enter the number of players this game: ");
//        numberPlayers = in.nextInt();
          numberPlayers = 4; //remove later
        for (int i = 0; i < numberPlayers; i++) {
//            System.out.println("Enter players' names separated by a  space: ");
//            playerName = in.next();
            UnoPlayer newPlayer = new UnoPlayer("P " +i);
            deck.deal(newPlayer.getHand(), handSize);
            UnoHand.insertionSortUnoHand(newPlayer.getHand());
            players.add(newPlayer);
        }
        
        ArrayList<UnoCard> dPile = new ArrayList<>();
        discardPile = new UnoHand("Discards", dPile);
        deck.deal(discardPile, 1);
//     1st card cannot be wild
        while (discardPile.getCard(0).getColor()>3){
            deck.addCard(discardPile.popCard());
            deck.shuffle();
            deck.deal(discardPile, 1);
        }

// initialize state of key game variables
        player = players.get(0);
        clockwise = true;
        skip = false;
        firstPass = true;
        wildColor=4;
        
        ArrayList<UnoCard> drPile = new ArrayList<>();
        drawPile = new UnoHand("Draw Pile", drPile);
        deck.dealAll(drawPile);
    }//End constructor
    
    public static boolean getDirectionOfPlay() {
        return clockwise;
    }
    
    public UnoPlayer getPlayer(UnoPlayer player) {
        return player;
    }
    
    public void setPlayer(UnoPlayer player) {
       this.player = player;
    }
    
    public static boolean getSkip() {
        return skip;
    }
  
    public static boolean toggleDirectionOfPlay() {
        if(clockwise) {
            clockwise = false;
        }else {
            clockwise = true;
        }
        return clockwise;
    }
   
    public static int getWildColor() {
        return wildColor;
    }
    
    /**
     * Displays the current State of the Game
     */
    public void displayState() {
        System.out.println("");
        if(clockwise) {
            System.out.println("Direction of play is clockwise.");
        }else {
            System.out.println("Direction of play is counter-clockwise.");
        }
        System.out.println("wildColor= " + wildColor+ " Skip= " + skip + " firstPass = " + firstPass);
        System.out.println("NextPlayer = " + player.getName());
        
        for (UnoPlayer player:players) {
            UnoHand.insertionSortUnoHand(player.getHand());
            player.getHand().display();
        }
        
        System.out.println("Discard Pile:");
        int dPSize = discardPile.size();
        if(dPSize > 10) {
            for(int i= dPSize - 10; i <= dPSize-1; i++) {
                UnoCard.printCard(discardPile.getCard(i));
            }
        System.out.println("");    
        } else {discardPile.display();}
        
        System.out.println("Draw Pile:");
        System.out.println(drawPile.size() + " cards");
        System.out.println("");
    } // End displayState()
    
    /**
     * Is game over?
     * @return
     */
    public boolean isDone() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getHand().empty()) {
                return true;
            }
        }
        return false;
    }
    
    /** For when drawPile runs out of cards.
     * Removes top card from discardPile, makes discardPile 
     * the drawPile and then shuffles
     * it; adds top card to new discardPile
     */
    public void reshuffle() {
//    remove top card from discard pile
        UnoCard prev = discardPile.popCard(); 
//    transfer remaining discard pile to new draw pile
        discardPile.dealAll(drawPile);
//    shuffle new draw pile        
        drawPile.shuffle();
//    add back the orig top card to the new draw pile
        discardPile.addCard(prev);
    }
    
    /**
     * Draws a Card from drawPile provided drawPile not empty 
     * @return
     */
    public UnoCard draw() {
        if (drawPile.empty()) {
            reshuffle();
        }
        return drawPile.popCard();
    }
    
    /**One of 2 main side methods that takes you out
     * of standard play waterfall when a WD4 is played
     * 
     * This is for when PREV card is WD4
     * Checks if next player has wd4, if not draws 4; If so,
     * it keeps track of players if > 2 WD4s are played in a row.
     * @param player
     * @param wd4CardsPlayed
     */
    public UnoPlayer drawWildDrawFour(UnoPlayer player, int wd4CardsPlayed) {

        do {//This do-loop runs until a player is reached that does not have 
            // a WD4 card and has to draw cards
            boolean wd4MatchCheck = false;
            int i = 0;
            int counter = 0;
            do {// This Do-Loop only checks to see if current player has wd4
                // to play on top of previously played WD4
                if (counter == 0) {
                    i = player.getHand().size() - 1;
                }

                UnoCard next = player.getHand().getCard(i);

                if (unoCardWildDrawFour(next)) { // check to see if he has WD4
                    wd4MatchCheck = true;
                    wd4CardsPlayed++;
                    wildColor=unoWildCardColor();
                    System.out.println(player.getName() + " plays " + next + 
                            " Match " + UnoCard.getColors()[wildColor]);
                    next = player.getHand().popCard(i);
                    discardPile.addCard(next);
                    player = nextPlayer(player);
                    displayState();
                }

                counter++;
                i--;
            } while (!wd4MatchCheck && i > -1);

            /**
             * When you get to this 'If' Check you know that 
             * there are no more WD4 matches and player has 
             * either played a wd4 or will
             * be picking cards and then returning from
             * drawWildDrawFour() to SpecialCardWD4 IF statement
             */
            if (!wd4MatchCheck) {
                for (int j = 0; j < 4 * wd4CardsPlayed; j++) {
                    UnoCard unocard = draw();
                    System.out.println(player.getName() + " draws " + unocard);
                    player.getHand().addCard(unocard);
                }

                /**
                 * Need to use setPlayer() as 'players' running 
                 * thru drawWildDrawFour() do not have any 
                 * scope access to player on record in  
                 * SpecialCard(prev) waterfall filters. 
                 * 
                 * So if nextPlayer() advances you more than one 
                 * player via playing follow-on WD4 card, player 
                 * in takeTurn() <> sync with player coming out 
                 * of drawWildDrawFour().         
                 */
                System.out.println("");
                if (wd4CardsPlayed > 1) {
                    setPlayer(player);
                }
            wd4CardsPlayed=0;
            return player;
            } 
        }while (wd4CardsPlayed >= 1); 
        /** This return never gets hit, but if you pull it, 
         * get problems with drawTurn() bug detection
         *   looking for return statement
         */
        return player; 
    }//End wildDrawFour()
        
    /**Second of 2 main side methods that takes you out
     * of standard play waterfall when a D2 is played.
     * 
     * card PREV was DrawTwo checks if next player has D2, 
     * if not draws2;
     * if so, plays D2, moves on to check following player
     * (more than two D2's in a row) and keeps track of how many D2s have 
     * been played 
     * in a row
     */
    public UnoPlayer drawTwo(UnoPlayer player, int d2CardsPlayed) {
     
        do {//This do-loop runs until a player is reached that does not have 
            // a D2 card and has to draw cards
            boolean d2MatchCheck = false;
            int i = 0;
            int counter = 0;
            do {// This do-loop is effectively a for(i;condition;i++) loop 
                // looking for D2s in hand
                if (counter == 0) {
                    i = player.getHand().size() - 1;
                }

                UnoCard next = player.getHand().getCard(i);

                if (unoCardDrawTwo(next)) { // check to see if he has D2
                    d2MatchCheck = true;
                    d2CardsPlayed++;
                    System.out.println(player.getName() + " plays " + next);
                    next = player.getHand().popCard(i);
                    discardPile.addCard(next);
                    player = nextPlayer(player);
                    displayState();
                }

                counter++;
                i--;
            } while (!d2MatchCheck && i > -1);

            /**
             * When you get to this 'If' Check you know that player played d2 or
             *  there are no more D2 matches and player will be picking cards
             *  and then returning from
             * drawTwo() to SpecialCardD2 IF statement
             */
            if (!d2MatchCheck) {
                for (int j = 0; j < 2 * d2CardsPlayed; j++) {
                    UnoCard unocard = draw();
                    System.out.println(player.getName() + " draws " + unocard);
                    player.getHand().addCard(unocard);
                }
                
                /**
                 * Need to use setPlayer() as 'players' running thru drawTwo() do 
                 * not have any scope access to player on record in SpecialCard(prev) 
                 * waterfall filters. 
                 * So if nextPlayer() advances you more than one 
                 * player via playing follow-on D2 card, player in takeTurn() <> 
                 * sync with player coming out of drawTwo().         
                 */
                System.out.println("");
                if (d2CardsPlayed > 1) {
                    setPlayer(player);
                }
                d2CardsPlayed=0;
                return player;
            } 
        } while (d2CardsPlayed >= 1);

        /** This return never gets hit, but if you pull it, get problems with
         *  drawTwo() bug detection looking for return statement
         */
        return player; 
    }//End drawTwo()

    /**
     * Pick random wild card color
     * @return int color
     */
    public int unoWildCardColor() {
        int color = randomColor();
        return color;
    }
    
    public void waitForUser() {// Waits until you hit enter
        in.nextLine();
    }
//------------------------------------------------------------------------    
    /* Basic functioning of game is(see takeTurn() below). 
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
    */
    public void takeTurn() {
        System.out.println("Player into TakeTurn() = " 
               + player.getName());

        UnoCard prev = discardPile.last();

        /**
         * Start firstPass && Special Card
         * 
         * Only spC's you can have affected by FirstPass are
         * Skip and Reverse because they change the order of play 
         */
        if (firstPass && 
                unoSpecialCard(prev)) {
            firstPass = false;
            if (unoCardSkip(prev)) {
                player = players.get(players.indexOf(player)+1);
            }
            if (unoCardReverse(prev)) {
                toggleDirectionOfPlay();
                player = players.get(players.size()-1);
            }
        } //End firstPass && Special tests

        /**
         * Start actions for previous card = Special
         */
        if(unoSpecialCard(prev)) {
            if (unoCardDrawTwo(prev)) {

                int d2CardsPlayed = 1;
                drawTwo(player, d2CardsPlayed);

                player = nextPlayer(player);
            }

            if (unoCardWildDrawFour(prev)) {

                int wd4CardsPlayed = 1;
                drawWildDrawFour(player, wd4CardsPlayed);

                player = nextPlayer(player);
            }
            prev = discardPile.last();
        }//End actions for previous card = Special

        if(!uCardWldorWD4(prev)) {
            System.out.println("nextPlayer() = " + 
                    player.getName() + " prev card = " + 
                    prev);
        } else {System.out.println("nextPlayer() = " + 
                player.getName() + " prev card = " + prev+ 
                " Match " + UnoCard.getColors()[wildColor]);}

        UnoCard next = player.play(this, prev);


        /**
         * Actions needed when NEXT card is special card
         */
        if (unoSpecialCard(next)) {
            if (unoCardSkip(next)) {
                skip = true;
                playerPlaysNext(next);
            }
            if (unoCardReverse(next)) {
                toggleDirectionOfPlay();
                playerPlaysNext(next);
            }

            if(unoCardWild(next)) {
                wildColor=unoWildCardColor();
                playerPlaysWldNext(next);
            }

            if(unoCardWildDrawFour(next)) {
                wildColor=unoWildCardColor();
                playerPlaysWldNext(next);
            }

            if (unoCardDrawTwo(next)) {
                playerPlaysNext(next);
            }

        }//End special card next

        if (!unoSpecialCard(next)) {
            playerPlaysNext(next);            
        }

    } //End takeTurn(UnoPlayer)

    /**
     * @param next = When Next card played is wild/WD4, need to
     * choose a color to Match.
     */
    private void playerPlaysWldNext(UnoCard next) {
        discardPile.addCard(next);
        System.out.println(player.getName() + " plays " + next + 
                " Match "+ UnoCard.getColors()[wildColor]);
        System.out.println(); 
        player = nextPlayer(player);
    }

    /**
     * @param next = UnoCard play card sequence when next not wild
     * private restricts method access to Uno class
     */
    private void playerPlaysNext(UnoCard next) {
        discardPile.addCard(next);
        System.out.println(player.getName() + 
                " plays " + next);
        System.out.println();
        player = nextPlayer(player);
    }
    
      /**
        * Change Players: handles skips and reverses
        * Uses ArrayList method indexOf(Obj) to return 
        * current players index #
        * Uses modulus: (current player index + 1) mod 
        * players, AL.size() 
        * 
        * @param current Player
        * @return next player
        */
    public UnoPlayer nextPlayer(UnoPlayer current) {
        int indexNextPlayer;
        int indexIncrement;

        if (skip) {
            indexIncrement = 2;
        } else {
            indexIncrement = 1;
        }

        if (clockwise) {
            indexNextPlayer = (players.indexOf(current) + indexIncrement) % players.size();
        } else {
            indexNextPlayer = (players.indexOf(current) - indexIncrement) % players.size();
            if (indexNextPlayer < 0 && players.indexOf(current) == 1) {// need to fix this when 3
                                                                       // players, counter
                                                                       // clockwise, and skip on
                                                                       // currentplayer 1
                indexNextPlayer = players.size() - indexIncrement + 1;// 1-2=-1 => <0 =>3-2 = 1
                                                                      // again
            } else if (indexNextPlayer < 0) {
                indexNextPlayer = players.size() - indexIncrement;
            }
        }//End 'clockwise'
        
        firstPass=false;  
        skip = false;

        return players.get(indexNextPlayer);   
    }//End nextPlayer

        public void playGame() {

            //keep playing until there's a winner (no cards left in hand)
            while(!isDone()) {
                displayState();
                //            waitForUser();
                takeTurn();
            }

            //      Display final score.
            System.out.println("Final Scores:");
            for (int i= 0; i <players.size(); i++) {
                System.out.println(players.get(i).getName() + " scores " + UnoHand.scoreHandUno(players.get(i).getHand()));
            }
        }
        public static void main(String[] args) {
            Uno game = new Uno();
            game.playGame();

        }    


}

