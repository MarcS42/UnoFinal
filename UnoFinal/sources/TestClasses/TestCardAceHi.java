package TestClasses;

import Main.uno3.Card;
import Main.uno3.CardAceHi;

import static Main.uno3.CardAceHi.*;

public class TestCardAceHi {

    public static void main(String[] args) {
        CardAceHi zeroArg = new CardAceHi();
        Card.printCard(zeroArg);
        System.out.println("");
        
        CardAceHi twoArg = new CardAceHi(6,2);
        Card.printCard(twoArg);
        CardAceHi sixC = new CardAceHi(6,0);
        Card.printCard(sixC);
        CardAceHi aceD = new CardAceHi(14,1);
        Card.printCard(aceD);
        CardAceHi aceS = new CardAceHi(14,3);
        Card.printCard(aceS);
        System.out.println("");
        
        System.out.println("AceHi test: ");
        System.out.println("compareCards(twoArgs, zeroArgs)"
                + " twoArgs should be larger than zeroArgs: "
                 + CardAceHi.compareCards(twoArg,zeroArg));
        System.out.println(" compareCards(twoArg,twoArgs): " +  
                 CardAceHi.compareCards(twoArg,twoArg));
        System.out.println(" compareCards(twoArg,ace): " +                  
                 CardAceHi.compareCards(twoArg,aceD));
        System.out.println("");
        
        System.out.println("cardsMatch(CardAceHi) SH type"
                + " rules test: ");
        System.out.println("cardsMatch(twoArg,aceD): "
                + cardsMatch(twoArg,aceD));
        System.out.println("cardsMatch(aceD,twoArg): "
                + cardsMatch(aceD,twoArg));
        System.out.println("cardsMatch(aceD,aceS): "
                + cardsMatch(aceD,aceS));
        System.out.println("");
        
        System.out.println("ranksMatch(CardAceHi) Test: ");
        System.out.println("ranksMatch(twoArg,aceD): "
                + ranksMatch(twoArg,aceD));
        System.out.println("ranksMatch(sixC,twoArg): "
                + ranksMatch(sixC,twoArg));
        System.out.println("cardsMatch(aceD,aceS): "
                + ranksMatch(aceD,aceS));
        System.out.println("");
        
        System.out.println("scoreCard(CardAceHi) Test: ");
        System.out.println("scoreCard(twoArg): "
                + scoreCard(twoArg));
        System.out.println("scoreCard(sixC): "
                + scoreCard(sixC));
        System.out.println("scoreCard(aceS): "
                + scoreCard(aceS));
        System.out.println("");
    }

}
