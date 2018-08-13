/**
 * 
 */
package Main.uno3;

import static Main.uno3.SpecialCardsSH.*;
import static Main.uno3.CardHand.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author MarcSherman
 *
 */
public class PlayerSH {
    private String name;
    private CardHand hand;
    private CardHand river;
    private CardHand hole;
    
    public PlayerSH(String name) {
        ArrayList<Card> plyrHand = new ArrayList<>();
        ArrayList<Card> plyrRiver = new ArrayList<>();
        ArrayList<Card> plyrHole = new ArrayList<>();
        this.name = name;
        this.hand = new CardHand(name, plyrHand);
        this.river = new CardHand(name, plyrRiver);
        this.hole = new CardHand(name, plyrHole);
    }
    
    /**
     * 
     * @param sh Makes ShitHead state variables available
     * @param prev - previous card played
     * @return
     */
    public Card play(ShitHead sh, Card prev) {
        Card card = searchForMatch(prev);
        if (card == null) {
            sh.getDiscardPile().dealAll(hand);
            return null;
        }
        sh.draw();
        return card;  
    }
    
    /**
     * 
     * @param sh Makes ShitHead state variables available
     * @param prev - previous card played
     * @return CardHand of cards to be played (multiple cards if matching).
     */
    public CardHand play2(ShitHead sh, Card prev) {
        CardHand cardsToPlay = searchForMatch2(prev);
        if (cardsToPlay == null || cardsToPlay.empty() ) {
            sh.getDiscardPile().dealAll(hand);
            return null;
        }
        sh.draw();
        return cardsToPlay;  
    }
    
    /**Searches current Player's hand(s) for matches to
     * Card prev card. 
     * 
     * ******Pops matching card/multiple matching cards****
     *  but does NOT add them to discardPile or print anything out.
     * 
     * Three hands to search:
     * 1) hand Cards - >= 4 cards =returnALH1,ALH2,ALH3
     * 2) river Cards - choice of <= 3 = returnALR1,ALR2,ALR3
     * 3) hole Cards - random guess <= 3
     * Strategy:PlayLowNtSpec;PlayHiNtSpec;PlaySpecial.
     * @param prev card
     * @return AL of matching cards from hand(s)
     */
    public CardHand searchForMatch2(Card prev) {
        if(!hand.empty()) {
            ArrayList<Card> returnALH1;
            ArrayList<Card> returnALH2;
            ArrayList<Card> returnALH3;
            
          insertionSortCardHand(hand);
            
     /**       Look for low not special(LNS) cards */      
            
            returnALH1 = lowNtSpecial2(hand, prev);
            if(!returnALH1.isEmpty())
            return new CardHand("ALH1",returnALH1);
        
     /**       Look for high not special(HNS) cards, plays them next */
            returnALH2 = highNtSpecial2(hand, prev);
            if(!returnALH2.isEmpty())
            return new CardHand("ALH2",returnALH2);
         
      /**     Look for and play special cards */
            returnALH3 = playSpecialCards2(hand, prev);
            if(!returnALH3.isEmpty())
            return new CardHand("ALH3",returnALH3);
         
         return null;
        } // End !hand.empty()
        
        else if(!river.empty()) {
            ArrayList<Card> returnALR1;
            ArrayList<Card> returnALR2;
            ArrayList<Card> returnALR3;
            
          insertionSortCardHand(river);
            
       /**       Look for not special low cards */            
            returnALR1 = lowNtSpecial2(river, prev);
            if(!returnALR1.isEmpty())
            return new CardHand("ALR1",returnALR1);
    
      /**       Look for not special high Rcards, plays them next */
            returnALR2 = highNtSpecial2(river, prev);
            if(!returnALR2.isEmpty())
            return new CardHand("ALR2",returnALR2);
            
       /**       Look for and play special Rcards */            
            returnALR3 = playSpecialCards2(river, prev);
            if(!returnALR3.isEmpty())
            return new CardHand("ALR3",returnALR2);
             
         return null;
        }// End !river.empty()
        
    
     if(hand.empty() && river.empty())    
     return pickRandomHoleCard2(prev);
    
    return null;
    } // End searchForMatch2********************

    /**Removes card from hole if playable
     * @param prev
     * @return
     */
    public CardHand pickRandomHoleCard2(Card prev) {
        ArrayList<Card> returnALRandHole = new ArrayList<>();
        
        Random rand = new Random();
        
        if(!playerIsDone(this))
        {
        int i = rand.nextInt(hole.size());
        
        Card next = hole.getCard(i);
        
            if(cardsPlayableRankSH(prev, next)) {
                returnALRandHole.add(next);
                System.out.println("Player "+ name +
                        " plays HoleCard "+ next);
                hole.popCard(next); //***********
                return new CardHand("ALHole",returnALRandHole);
            }
        } else {
        System.out.println("Player "+ this.name + " is Done!");
        }
        return null;
    }//End pickRandomHoleCard2

    /**Plays next card when discardPile.empty()
     * Added handler for playNext call when player isDone
     * 
     * @param shitHead TODO
     * @return
     */
    public CardHand playNext2(ShitHead shitHead) {
        ArrayList<Card> cardsToPlayNext = new ArrayList<>();
        ArrayList<Card> cardsPlayable = new ArrayList<>();
        ArrayList<ArrayList<Card>> cardsToBePlayed;
    
        if(!hand.empty()) {
            /*    Looking for not special HandCards */
            for(int i = 0; i < hand.size(); i++) {
                Card chkCard = hand.getCard(i);
                
                if(!specialCardNt7SH(chkCard)) {
                    cardsPlayable.add(chkCard);
                }
            }//End build Hand cardsPlayablePlayNext
            
            if(!cardsPlayable.isEmpty()) {
                cardsToBePlayed = cardsPlayableAnalyzer(cardsPlayable);
                cardsToPlayNext = cardsToPlay(hand,cardsToBePlayed,cardsToPlayNext);    
                
            } else //End if(!cardsPlayable.isEmpty())     
            
            /*     if Player only has special Hcards    */
            if((cardsPlayable.isEmpty()) && !hand.empty()) 
            {
                cardsPlayable = hand.getCards();
                cardsToBePlayed = cardsPlayableAnalyzer(cardsPlayable);
                cardsToPlayNext = cardsToPlay(hand,cardsToBePlayed,cardsToPlayNext);
                
            }
            
            CardHand playNext = new CardHand("playNext", cardsToPlayNext);        
            for(Card c:cardsToPlayNext) {
            shitHead.discardPile.addCard(c);
            }
            System.out.println("Player "+ name +
                            " plays "+ playNext.size() + " x " + 
                        shitHead.discardPile.last() + " card(s).");
            shitHead.draw();
            return playNext;
    
        }// End PlayeNext.!Hand.empty().
    
        if(!river.empty()) {
            /*    Looking for not special RCards */
            for(int i = 0; i < river.size(); i++) {
                    Card chkCard = getRCards().get(i);
                    
                    if(!specialCardNt7SH(chkCard)) {
                        cardsPlayable.add(chkCard);
                    }
            }//End build River cardsPlayable PlayNext
            
            if(!cardsPlayable.isEmpty()) {
                cardsToBePlayed = cardsPlayableAnalyzer(cardsPlayable);
                cardsToPlayNext = cardsToPlay(river,cardsToBePlayed,cardsToPlayNext);    
                
            } else //End if(!cardsPlayable.isEmpty())     
            
            /*     if Player only has special Rcards    */
            if((cardsPlayable.isEmpty()) && !river.empty()) 
            {
                cardsPlayable = river.getCards();
                cardsToBePlayed = cardsPlayableAnalyzer(cardsPlayable);
                cardsToPlayNext = cardsToPlay(river,cardsToBePlayed,cardsToPlayNext);
                
            }
            
            CardHand playNext = new CardHand("playNext", cardsToPlayNext);        
            for(int i = 0; i < cardsToPlayNext.size(); i++) {
            
            shitHead.discardPile.addCard(playNext.getCard(i));
            }
            System.out.println("Player "+ name +
                    " plays "+ playNext.size() + " x " + 
                  shitHead.discardPile.last() + " card(s).");
            shitHead.draw();
            return playNext;
        }// End PlayeNext.!river.empty().
    
        /*Problem when last hole card is a tenBomb. Leads to trying 
         * to play another card when do not have any left*/      
        if(!hole.empty()) {
            Random rand = new Random();
            int i = rand.nextInt(hole.size());
            Card card = hole.popCard(i);
            cardsToPlayNext.add(card);
            shitHead.discardPile.addCard(card);
            System.out.println("Player "+ name +
                    " plays HoleCard "+ card);
            shitHead.draw();
            CardHand playNext = new CardHand("playNext", cardsToPlayNext);
            return playNext;
        } else {
            shitHead.setPlayer(shitHead.nextPlayer(shitHead.player));
        }
        System.out.println("Player into playNext = " 
                + shitHead.player.getName());
        return shitHead.player.playNext2(shitHead);//needed if last card tenBomb && playerDone
    }//End playNext2(SH)**********************************

    /**
     * @param hand
     * @param prev
     * @return AL CardsToPlay to searchForMatch2().
     * Calls cardsPlayableAnalyzer() which pops cards from hand, 
     * but does NOT add them to discardPile or do any System print out.
     */
    public ArrayList<Card> lowNtSpecial2(CardHand hand, Card prev) {
        ArrayList<Card> playableLNS = new ArrayList<>();
        ArrayList<ArrayList<Card>> cardsToBePlayed;
        ArrayList<Card> cardsToPlayLNS = new ArrayList<>();
        
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
                
            if (!specialCardNt7SH(card) && card.getRankAceHi() 
                    <= 11 && cardsPlayableRankSH(prev, card)) { 
                playableLNS.add(card);
            }
        }
            
        if(!playableLNS.isEmpty()) {
            
          cardsToBePlayed = cardsPlayableAnalyzer(playableLNS);
          
          return cardsToPlay(hand, cardsToBePlayed, cardsToPlayLNS);
        } //End if(!playableLNS.isEmpty())
        
     return playableLNS;
    }//End ArrayList<Card> lowNtSpecial2(CardHand hand, Card prev)

    /**
     * @param hand
     * @param prev
     * @return AL of cards to play that are HNS
     * Calls cardsPlayableAnalyzer() which pops cards from hand, 
     * but does NOT add them to discardPile or do any System print out.
     */
    public ArrayList<Card> highNtSpecial2(CardHand hand, Card prev) {
        ArrayList<Card> playableHNS = new ArrayList<>();
        ArrayList<ArrayList<Card>> cardsToBePlayed;
        ArrayList<Card> cardsToPlayHNS = new ArrayList<>();
        
        for (int i = hand.size()-1; i >=0 ; i--) {
            Card card = hand.getCard(i);
    
           if(!specialCardSH(card) && card.getRankAceHi() 
                    > 11 && cardsPlayableRankSH(prev, card)) {
                playableHNS.add(card);
           }
        }//End build cardsPlayable
        
        if(!playableHNS.isEmpty()) {
            
        cardsToBePlayed = cardsPlayableAnalyzer(playableHNS);
            
            return cardsToPlay(hand, cardsToBePlayed, cardsToPlayHNS);
        }//End if(!playableHNS.isEmpty())
            
     return playableHNS;
    }//End ArrayList<Card> highNtSpecial2(CardHand hand, Card prev)

    public ArrayList<Card> playSpecialCards2(CardHand hand, Card prev) {
        ArrayList<Card> playableSpc = new ArrayList<>();
        ArrayList<ArrayList<Card>> cardsToBePlayed;
        ArrayList<Card> cardsToPlaySpc = new ArrayList<>();
        
        for (int i = 0; i < hand.size(); i++) { 
            Card card = hand.getCard(i);
                    
            if (specialCardSH(card) 
                    && cardsPlayableRankSH(prev, card)) {
                playableSpc.add(card);
            }
        }//End build cards playableSpc
        
        if(!playableSpc.isEmpty()) {
            
        cardsToBePlayed = cardsPlayableAnalyzer(playableSpc);
    
        return cardsToPlaySpc(hand, cardsToBePlayed, cardsToPlaySpc);
      }//End if(!playableSpc.isEmpty()) 
        
      return playableSpc;
    }//End ArrayList<Card> playSpecialCards2(CardHand hand, Card prev)

    /**Pops Cards from hand that is parameter.
     * @param hand
     * @param cardsToBePlayed
     * @param cardsToPlay
     * @return AL of cardsToPlay using logic of playing FoaK,
     * then triples, doubles, and lastly singles.
     * Idea being to off-load highest number of cards possible.
     */
    public ArrayList<Card> cardsToPlay(CardHand hand, ArrayList<ArrayList<Card>> cardsToBePlayed,
            ArrayList<Card> cardsToPlay) {
        int szeFoaK;
        int szeTriples;
        int szeDoubles;
        int szeSingles;
        if(!cardsToBePlayed.get(3).isEmpty()) {
              szeFoaK = cardsToBePlayed.get(3).size();
              /**
               * Plays lowest Card playable sets by counting from end.
               */
              for(int i2 = szeFoaK-1; i2 > szeFoaK-1-4; i2--)
              {
              cardsToPlay.add(cardsToBePlayed.get(3).get(i2));
              }
              for(Card c:cardsToPlay) {
                  hand.popCard(c); //Remove cardsToPlay from hand
              }
              return cardsToPlay;
          } else
          if(!cardsToBePlayed.get(2).isEmpty()) {
              szeTriples = cardsToBePlayed.get(2).size();
              for(int i2 = szeTriples-1; i2 > szeTriples-1-3; i2--)
              {
              cardsToPlay.add(cardsToBePlayed.get(2).get(i2));
              }
              for(Card c:cardsToPlay) {
                  hand.popCard(c); //Remove cardsToPlay from hand
              }
              return cardsToPlay;
          } else
          if(!cardsToBePlayed.get(1).isEmpty()) {
              szeDoubles = cardsToBePlayed.get(1).size();
              for(int i2 = szeDoubles-1; i2 > szeDoubles-1-2; i2--)
              {
              cardsToPlay.add(cardsToBePlayed.get(1).get(i2));
              }
              for(Card c:cardsToPlay) {
                  hand.popCard(c); //Remove cardsToPlay from hand
              }
              return cardsToPlay;
          } else
          if(!cardsToBePlayed.get(0).isEmpty()) {
              szeSingles = cardsToBePlayed.get(0).size();
              for(int i2 = szeSingles-1; i2 > szeSingles-1-1; i2--)
              {
              cardsToPlay.add(cardsToBePlayed.get(0).get(i2));
              }
          }
             for(Card c:cardsToPlay) {
                 hand.popCard(c); //Remove cardsToPlay from hand
             }
             return cardsToPlay;
    }//End ArrayList<Card> cardsToPlay(CardHand hand, ArrayList<ArrayList<Card>> cardsToBePlayed,

    /**Pops cards from hand parameter input.
     * @param hand
     * @param cardsToBePlayed
     * @param cardsToPlaySpc
     * @return Al cardsToPlaySpc using reverse logic by playing
     *     single specialCards before playing doubles, triples, etc.
     */
    public ArrayList<Card> cardsToPlaySpc(CardHand hand, ArrayList<ArrayList<Card>> cardsToBePlayed,
            ArrayList<Card> cardsToPlaySpc) {
        int szeFoaK;
        int szeTriples;
        int szeDoubles;
        int szeSingles;
        /** Try to play single Special cards, then doubles, triples, quads
        * Maybe should only play half of double,triples, quads to keep some Spc?
        * 
        */     
        if(!cardsToBePlayed.get(0).isEmpty()) {
            szeSingles = cardsToBePlayed.get(0).size();
            for(int i2 = szeSingles-1; i2 > szeSingles-1-1; i2--)
            {
            cardsToPlaySpc.add(cardsToBePlayed.get(0).get(i2));
            }
            for(Card c:cardsToPlaySpc) {
                hand.popCard(c); //Remove cardsToPlay from hand
            }
            
           return cardsToPlaySpc;
        }else
        if(!cardsToBePlayed.get(1).isEmpty()) {
            szeDoubles = cardsToBePlayed.get(1).size();
            for(int i2 = szeDoubles-1; i2 > szeDoubles-1-2; i2--)
            {
            cardsToPlaySpc.add(cardsToBePlayed.get(1).get(i2));
            }
            for(Card c:cardsToPlaySpc) {
                hand.popCard(c); //Remove cardsToPlay from hand
            }
            
           return cardsToPlaySpc;
        } else
        if(!cardsToBePlayed.get(2).isEmpty()) {
            szeTriples = cardsToBePlayed.get(2).size();
            for(int i2 = szeTriples-1; i2 > szeTriples-1-3; i2--)
            {
            cardsToPlaySpc.add(cardsToBePlayed.get(2).get(i2));
            }
            for(Card c:cardsToPlaySpc) {
                hand.popCard(c); //Remove cardsToPlay from hand
            }
            
           return cardsToPlaySpc;
        } else
        if(!cardsToBePlayed.get(3).isEmpty()) {
            szeFoaK = cardsToBePlayed.get(3).size();
            for(int i2 = szeFoaK-1; i2 > szeFoaK-1-4; i2--)
            {
            cardsToPlaySpc.add(cardsToBePlayed.get(3).get(i2));
            }
        } 
        for(Card c:cardsToPlaySpc) {
            hand.popCard(c); //Remove cardsToPlay from hand
        }
       return cardsToPlaySpc;
    }

    /**workingSet-to-cardsPlayable index position 
         * converter.
         * @param cardsPlayable AL of playable cards
         * @param workingSet Integer AL of quads,triple,doubles, 
         * and single playable cards.
         * @return Returns an AL(sdtq) of ArrayLists of cards
         * single, double, triple, quads
         */
        public ArrayList<ArrayList<Card>> workingSetCdsPlayableIndexConverter(ArrayList<Card> cardsPlayable, ArrayList<Integer> workingSet ) {
            ArrayList<Card> singles0 = new ArrayList<>();
            ArrayList<Card> doubles0 = new ArrayList<>();
            ArrayList<Card> triples0 = new ArrayList<>();
            ArrayList<Card> quads0 = new ArrayList<>();
            ArrayList<ArrayList<Card>> sdtq0 = new ArrayList<>();
            
        int cPSize = cardsPlayable.size(), wSVal, j, cPIndex;
        for(int wSPosit=0; wSPosit < workingSet.size(); wSPosit++){
            if(wSPosit != 0) {
            cPSize -= workingSet.get(wSPosit-1);
            }
            wSVal = workingSet.get(wSPosit);
            for(j=0; j<wSVal; j++){
                cPIndex=cPSize-1-j;
    
                switch(wSVal) {
                  case 1: singles0.add(cardsPlayable.get(cPIndex));
                      break;
                  case 2: doubles0.add(cardsPlayable.get(cPIndex));
                      break;
                  case 3: triples0.add(cardsPlayable.get(cPIndex));
                      break;
                  case 4: quads0.add(cardsPlayable.get(cPIndex));
                      break;
                  default :
                      System.out.println("There is a problem in the Switch code block");
                }// End Switch block
            }//End for(j=0; j<wSVal; j++)
        }//End for(int wSPosit=0; wSPosit < workingSet.size(); wSPosit++)
        
        System.out.println("Four-of-A-Kind: \n" + quads0 + "\nTriples: \n" + triples0 +
                "\nDoubles: \n" + doubles0 + "\nSingles: \n" + singles0);
        
        sdtq0.add(singles0);
        sdtq0.add(doubles0);
        sdtq0.add(triples0);
        sdtq0.add(quads0);
        
        return sdtq0;
    
      }

    /**Takes a cardsPlayable AL and analyzes it for
     * four-of-a-kind, triples, doubles, and singles
     * 
     * @param cardsPlayable
     * 
     * @return integer AL 'workingSet' with numbers from 4 to 1
     * The position of the integers in the array is
     * linked to their original position in cardsPlayable.
     */
    public ArrayList<Integer> cardsPlayableXRay(ArrayList<Card> cardsPlayable){
        ArrayList<Integer> workingSet = new ArrayList<>();
        if(!cardsPlayable.isEmpty()) {
            int p = cardsPlayable.size()-1;
            Integer count = 1;
            while (p >= 1) { 
                if(cardsPlayable.get(p).getRankAceHi() == 
                        cardsPlayable.get(p-1).getRankAceHi()) {
                    count++;
                    p--;
                    continue;
                } else {
                    workingSet.add(count);
                    count = 1;
                    p--;
                }
            }//End while(p)   
            workingSet.add(count);
        }
        System.out.println((workingSet));
    return workingSet;    
    }

    /**For games where multiple playable cards are possible
         * @param cardsPlayable AL of playable Cards that may come from
         *  any Hand CardHand via Search for Match
         * @return AL of cardsToBePlayed as determined by analysis of cardsPlayable.
         * Actual logic is left to game-specific 'searchMatch2()' method
         */
        public ArrayList<ArrayList<Card>> cardsPlayableAnalyzer(ArrayList<Card> cardsPlayable){
            ArrayList<ArrayList<Card>> sdtq = new ArrayList<>();
          
          ArrayList<ArrayList<Card>> cardsToBePlayed = new ArrayList<>();
          
          ArrayList<Integer> workingSet = cardsPlayableXRay(cardsPlayable);
            
         /********workingSet-to-cardsPlayable index position converter***/
          sdtq = workingSetCdsPlayableIndexConverter(cardsPlayable,workingSet);
          
          cardsToBePlayed = sdtq;  
            
          
          return cardsToBePlayed;
        }// End cardsPlayableAnalyzer

    /**Searches current Player's hand(s) for match to
     * Card prev card. ******Pops matching card****
     *  .
     * Three hands to search:
     * 1) hand Cards - >= 4 cards =returnCdH1,Cdh2,CdH3
     * 2) river Cards - choice of <= 3 = returnCdR1,CdR2,CdHR3
     * 3) hole Cards - random guess <= 3
     * Strategy:PlayLowNtSpec;PlayHiNtSpec;PlaySpecial.
     * @param prev card
     * @return match from hand(s)
     */
    public Card searchForMatch(Card prev) {
    //**** ToDo wrt multiple same Card matches(hover over task tag)***/
        if(!hand.empty()) {
          insertionSortCardHand(hand);
            
     /**       Look for low not special(LNS) cards */      
            
            Card returnCdH1 = lowNtSpecial(hand, prev);
            if(returnCdH1 != null)
            return returnCdH1;
        
     /**       Look for high not special(HNS) cards, plays them next */
            Card returnCdH2 = highNtSpecial(hand, prev);
            if(returnCdH2 != null)
            return returnCdH2;
         
      /**     Look for and play special cards */
            Card returnCdH3 = playSpecialCards(hand, prev);
            if(returnCdH3 != null)
            return returnCdH3;
         
         return null;
        } // End !hand.empty()
        
        else if(!river.empty()) {
          insertionSortCardHand(river);
            
       /**       Look for not special low cards */            
            Card returnCdR1 = lowNtSpecial(river, prev);
            if(returnCdR1 != null)
            return returnCdR1;
       
      /**       Look for not special high Rcards, plays them next */
            Card returnCdR2 = highNtSpecial(river, prev);
            if(returnCdR2 != null)
            return returnCdR2;
            
       /**       Look for and play special Rcards */            
            Card returnCdR3 = playSpecialCards(river, prev);
            if(returnCdR3 != null)
            return returnCdR3;
             
         return null;
        }// End !river.empty()
        

     if(hand.empty() && river.empty())    
     return pickRandomHoleCard(prev);
     
    return null;
    } // End searchForMatch********************

    /**pickRandomHoleCard removes the selected card from Hole if it's playable.
     * 
     * add handler for case where 3 hole cards are all tenBombs
     * @param prev card to match
     * @return 
     */
    public Card pickRandomHoleCard(Card prev) {
        Random rand = new Random();
        
        if(!playerIsDone(this))
        {
        int i = rand.nextInt(hole.size());
        
        Card next = hole.getCard(i);
            if(cardsPlayableRankSH(prev, next)) {
                System.out.println("Player "+ name +
                        " plays HoleCard "+ next);
                return hole.popCard(i);
            }
        } else {
        System.out.println("Player "+ this.name + " is Done!");
        }
        return null;
    }//End pickRandom Hole card
    
    /**Plays next card when discardPile.empty()
     * Added handler for playNext call when player isDone
     * 
     * @param shitHead TODO
     * @return
     */
    public Card playNext(ShitHead shitHead) {
        Card card = null;

        if(!hand.empty()) {
            /*    Looking for not special HandCards */
            for(int i = 0; i < hand.size(); i++) {
                if(card == null) {
                    Card chkCard = hand.getCard(i);
                    if(!specialCardNt7SH(chkCard)) {
                        card = hand.popCard(i);
                        shitHead.discardPile.addCard(card);
                        System.out.println("Player "+ name +
                                " plays "+ card);
                        shitHead.draw();
                        return card;
                    } else {
                        continue;
                    }
                }//end if(card==null)
            }// End for loop

            /*     if Player only has special Hcards    */
            if((card == null) && !hand.empty()) 
            {
                card = hand.popCard(hand.size()-1);
                shitHead.discardPile.addCard(card);
                System.out.println("Player "+ name +
                        " plays "+ card);
                shitHead.draw();
            }

            return card;
        }// End PlayeNext.!Hand.empty().

        if(!river.empty()) {
            /*    Looking for not special RCards */
            for(int i = 0; i < river.size(); i++) {
                if(card == null) {
                    Card chkCard = getRCards().get(i);
                    if(!specialCardNt7SH(chkCard)) {
                        card = river.popCard(i);
                        shitHead.discardPile.addCard(card);
                        System.out.println("Player "+ name +
                                " plays "+ card);
                        shitHead.draw();
                        return card;
                    } else {
                        continue;
                    }
                }// End (Rcard == null)
            }// End for Loop

            /*     if Player only has !special Rcards    */
            if((card == null) && !river.empty()) 
            {
                card = river.popCard(river.size()-1);
                shitHead.discardPile.addCard(card);
                System.out.println("Player "+ name +
                        " plays "+ card);
                shitHead.draw();
            }

            return card;
        }// End PlayeNext.!river.empty().

        /*Problem when last hole card is a tenBomb. Leads to trying 
         * to play another card when do not have any left*/      
        if(!hole.empty()) {
            Random rand = new Random();
            int i = rand.nextInt(hole.size());
            card = hole.popCard(i);
            System.out.println("Player "+ name +
                    " plays HoleCard "+ card);
            shitHead.discardPile.addCard(card);
            shitHead.draw();
            return card;
        } else {
            shitHead.player = shitHead.nextPlayer(shitHead.player);
        }
        System.out.println("Player into playNext = " 
                + shitHead.player.getName());
        return shitHead.player.playNext(shitHead);//needed if last card tenBomb && playerDone
    }//End playNext(SH)**********************************

    /**      Look for not special low cards 
     * @param hand a player's CardHand: river, hand, etc
     * @param prev Card you are trying to match.
     * @return Card that meets card play strategy:
     * Play Low, Play High, Play Special cards
     */
    public Card lowNtSpecial(CardHand hand, Card prev) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
                
            if (!specialCardNt7SH(card) && card.getRankAceHi() 
                    <= 11 && cardsPlayableRankSH(prev, card)) { 
                return hand.popCard(i);
            }
        }
        return null;
    }
    
    public Card highNtSpecial(CardHand hand, Card prev) {
        for (int i = hand.size()-1; i >=0 ; i--) {
            Card card = hand.getCard(i);
     
            if(!specialCardSH(card) && card.getRankAceHi() 
                    > 11 && cardsPlayableRankSH(prev, card)) {
                return hand.popCard(i);
            }
        }
        return null;
      }
    
    public Card playSpecialCards(CardHand hand, Card prev) {
        
        for (int i = 0; i < hand.size(); i++) { 
            Card card = hand.getCard(i);
                    
            if (specialCardSH(card) 
                    && cardsPlayableRankSH(prev, card)) {
                return hand.popCard(i);
            }
        }  
        return null;
    }

    public static boolean playerHas3MHcards(PlayerSH player) {
    
        if(player.getHCards().contains(threeC) ||
                player.getHCards().contains(threeD) ||
                player.getHCards().contains(threeH) ||
                player.getHCards().contains(threeS))
        {
            return true;}
        return false;
    }
    
    public static boolean playerHas3MRcards(PlayerSH player) {

        if(player.getRCards().contains(threeC) ||
                player.getRCards().contains(threeD) ||
                player.getRCards().contains(threeH) ||
                player.getRCards().contains(threeS))
        {
            return true;}
        return false;
    }

    public static boolean playerHas3MHolecards(PlayerSH player) {

        if(player.getHoleCards().contains(threeC) ||
                player.getHoleCards().contains(threeD) ||
                player.getHoleCards().contains(threeH) ||
                player.getHoleCards().contains(threeS))
        {
            return true;}
        return false;
    }
    
    public static boolean playerIsDone(PlayerSH player) {
      if(player.hand.empty() && player.river.empty() && 
              player.hole.empty()) {
          return true;
      }
        return false;
    }
            
    //++++++++++++++++ Helper Methods +++++++++++   
    /**
     * Gets the player's name
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets Hand
     * 
     * @return
     */
    public CardHand getHand() {
        return hand;
    }
    
    public ArrayList<Card> getHCards() {
        return getHand().getCards();
    }

    public CardHand getRiver() {
        return river;
    }

    public ArrayList<Card> getRCards() {
        return getRiver().getCards();
    }
    
    public CardHand getHole() {
        return hole;
    }
    
    public ArrayList<Card> getHoleCards() {
        return getHole().getCards();
    }
    
}//End PlayerSH
