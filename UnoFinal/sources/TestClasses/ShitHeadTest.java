package TestClasses;

import Main.uno3.Card;
import Main.uno3.CardHand;
import Main.uno3.*;
import Main.uno3.SpecialCardsSH;

import java.util.ArrayList;

public class ShitHeadTest {

    public static int numThreeMirrors(CardHand discardPile) {
        int count=1;
        for(int i = discardPile.size()-1; i > Math.max(discardPile.size()-4,0); i--) {
            if((discardPile.getCard(i).getRankAceHi()==3) && (discardPile.getCard(i-1).getRankAceHi()==3)) {
                count++;
            }
        }
     return count;
    }
    
    public static void main(String[] args) {
        ShitHead game = new ShitHead();
        
       game.playGame();
//ArrayList<Card> cards = new ArrayList<>();
//Card threeC = new Card(3,0,true);
//Card threeD = new Card(3,1,true);
//Card threeH = new Card(3,2,true);
//Card threeS = new Card(3,3,true);
//Card kingS = new Card(13,3,true);
//Card kingH = new Card(13,2,true);
//Card twoC = new Card(2,0,true);
////cards.add(threeC);
////cards.add(threeD);
////cards.add(threeH);
////cards.add(threeS);
////cards.add(kingS);
////cards.add(twoC);
////
////for(Card c:cards)
////System.out.println(c);
////
////if(cards.contains(kingS)) System.out.println(kingS);
 
//ArrayList<Card> discardPileAL = new ArrayList<>();
//
//CardHand discardPile = new CardHand("disPile", discardPileAL);
//
//discardPile.addCard(twoC);
//discardPile.addCard(threeS);
//discardPile.addCard(kingH);
//discardPile.addCard(kingS);
//discardPile.addCard(threeC);
//discardPile.addCard(threeH);
//discardPile.addCard(threeD);
//
//System.out.println("FourOfAKindBomb is: " + SpecialCardsSH.fourOfaKindBomb(discardPile));
//System.out.println("disPile Size:\n"+discardPile.size()+"\nnumThreeMirrors returned:\n"+numThreeMirrors(discardPile));
//
//Card tgtMatch = 
//discardPile.getCard(Math.max((discardPile.size()-numThreeMirrors(discardPile)-1),0));

//System.out.println("tgtMatch:\n" + tgtMatch);
    }

}
