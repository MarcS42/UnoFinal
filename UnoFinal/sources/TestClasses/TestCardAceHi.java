package TestClasses;

import Main.uno3.Card;
import Main.uno3.CardAceHi;

public class TestCardAceHi {

    public static void main(String[] args) {
        CardAceHi zeroArg = new CardAceHi();
        Card.printCard(zeroArg);
        System.out.println("");
        CardAceHi twoArg = new CardAceHi(6,2);
        Card.printCard(twoArg);
        
        CardAceHi ace = new CardAceHi(14,1);
        Card.printCard(ace);
        System.out.println("");
        
        System.out.println("AceHi test: ");
        System.out.println("compareCards(twoArgs, zeroArgs)"
                + " twoArgs should be larger than zeroArgs: "
                 + CardAceHi.compareCards(twoArg,zeroArg));
        System.out.println(" compareCards(twoArg,twoArgs): " +  
                 CardAceHi.compareCards(twoArg,twoArg));
        System.out.println(" compareCards(twoArg,ace): " +                  
                 CardAceHi.compareCards(twoArg,ace));
    }

}
