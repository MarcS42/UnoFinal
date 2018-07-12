package TestClasses;

import Main.uno3.Card;

import static Main.uno3.Card.*;

public class TestCard {

    public static void main(String[] args) {
        Card aceC = new Card();
        Card.printCard(aceC);
        System.out.println("");
        
        System.out.println("Not AceHi: ");
        Card sixH = new Card(6,2,false);
        printCard(sixH);
        Card sixC = new Card(6,0,false);
        printCard(sixC);
        Card aceD = new Card(1,1,false);
        printCard(aceD);
        Card kingD = new Card(13,1,false);
        printCard(kingD);
        Card aceS = new Card(1,3,false);
        printCard(aceS);
        Card queenS = new Card(12,3,false);
        printCard(queenS);
        System.out.println("");
        
        System.out.println("AceHi: ");
        Card aceCAH = new Card(14,0,true);
        printCard(aceCAH);
        Card sixHAH = new Card(6,2,true);
        printCard(sixHAH);
        Card sixCAH = new Card(6,0,true);
        printCard(sixCAH);
        Card aceDAH = new Card(14,1,true);
        printCard(aceDAH);
        Card kingDAH = new Card(13,1,true);
        printCard(kingDAH);
        Card aceSAH = new Card(14,3,true);
        printCard(aceSAH);
        Card queenSAH = new Card(12,3,true);
        printCard(queenSAH);
        System.out.println("");
   
        System.out.println("Bridge-NotAceHi test: ");
        setAceHi(false);
        
        System.out.println("compareCards(sixH, aceC)"
                + " sixH should be larger than aceC: "
                 + compareCards(sixH,aceC));
        System.out.println(" compareCards(sixH,sixH): " +  
                 compareCards(sixH,sixH));
        System.out.println(" compareCards(sixH,aceD): " +                  
                 compareCards(sixH,aceD));
        System.out.println(" compareCards(kingD,aceD): " +                  
                 compareCards(kingD,aceD));
        System.out.println(" compareCards(queenS,aceS): " +                  
                 compareCards(queenS,aceS));
        System.out.println("");
      
        System.out.println("Bridge-AceHi test: ");
        setAceHi(true);
        
        System.out.println("compareCards(sixHAH, aceCAH)"
                + " sixHAH should be larger than aceCAH: "
                 + compareCards(sixHAH,aceCAH));
        System.out.println(" compareCards(sixHAH,sixHAH): " +  
                   compareCards(sixHAH,sixHAH));
        System.out.println(" compareCards(sixHAH,aceDAH): " +                  
                   compareCards(sixHAH,aceDAH));
        System.out.println(" compareCards(kingDAH,aceDAH): " +                  
                   compareCards(kingDAH,aceDAH));
        System.out.println(" compareCards(queenSAH,aceSAH): " +                  
                   compareCards(queenSAH,aceSAH));
        System.out.println("");
//************************************************      
        System.out.println("compareRanks is <= or >=, so no zeros: ");
        System.out.println("compareRanks - NotAceHi test: ");
        setAceHi(false);
        
        System.out.println("compareRanks(sixH, aceC)"
                + " sixH should be larger than aceC: "
                 + compareRanks(sixH,aceC));
        System.out.println(" compareRanks(sixH,sixH): " +  
                 compareRanks(sixH,sixH));
        System.out.println(" compareRanks(sixH,aceD): " +                  
                 compareRanks(sixH,aceD));
        System.out.println(" compareRanks(kingD,aceD): " +                  
                 compareRanks(kingD,aceD));
        System.out.println(" compareRanks(queenS,aceS): " +                  
                 compareRanks(queenS,aceS));
        System.out.println("");
      
        System.out.println("compareRanks - AceHi test: ");
        setAceHi(true);
        
        System.out.println("compareRanks(sixHAH, aceCAH)"
                + " aceCAH should be larger than sixHAH: "
                 + compareRanks(sixHAH,aceCAH));
        System.out.println(" compareRanks(sixHAH,sixHAH): " +  
                   compareRanks(sixHAH,sixHAH));
        System.out.println(" compareRanks(sixHAH,aceDAH): " +                  
                   compareRanks(sixHAH,aceDAH));
        System.out.println(" compareRanks(kingDAH,aceDAH): " +                  
                   compareRanks(kingDAH,aceDAH));
        System.out.println(" compareRanks(queenSAH,aceSAH): " +                  
                   compareRanks(queenSAH,aceSAH));
        System.out.println("");
//************************************************       
        System.out.println("cardsMatch - Match suit and rank: ");
        System.out.println("cardsMatch - NotAceHi test: ");
        setAceHi(false);
        
        System.out.println("cardsMatch(sixH, aceC): "
                 + cardsMatch(sixH,aceC));
        System.out.println(" cardsMatch(sixH,sixH): " +  
                cardsMatch(sixH,sixH));
        System.out.println(" cardsMatch(aceD,aceD): " +                  
                cardsMatch(aceD,aceD));
        System.out.println(" cardsMatch(kingD,aceD): " +                  
                cardsMatch(kingD,aceD));
        System.out.println("");
      
        System.out.println("cardsMatch - AceHi test: ");
        setAceHi(true);
        
        System.out.println("cardsMatch(sixHAH, aceCAH): "
                + cardsMatch(sixHAH,aceCAH));
       System.out.println(" cardsMatch(sixHAH,sixHAH): " +  
               cardsMatch(sixHAH,sixHAH));
       System.out.println(" cardsMatch(aceDAH,aceDAH): " +                  
               cardsMatch(aceDAH,aceDAH));
       System.out.println(" cardsMatch(kingDAH,aceDAH): " +                  
               cardsMatch(kingDAH,aceDAH));
        System.out.println("");
//************************************************ 
        System.out.println("cardsPlayableRank - Playable rank only: ");
        System.out.println("cardsPlayableRank - NotAceHi test: ");
        setAceHi(false);
        
       System.out.println("cardsPlayableRank(sixH, aceC): "
                 + cardsPlayableRankSH(sixH,aceC));
       System.out.println(" cardsPlayableRank(sixH,sixH): " +  
                cardsPlayableRankSH(sixH,sixH));
       System.out.println(" cardsPlayableRank(aceD,aceD): " +                  
                cardsPlayableRankSH(aceD,aceD));
       System.out.println(" cardsPlayableRank(kingD,aceD): " +                  
                cardsPlayableRankSH(kingD,aceD));
       System.out.println(" cardsPlayableRank(aceS,queenS): " +                  
               cardsPlayableRankSH(aceS,queenS));
       System.out.println("");
      
       System.out.println("cardsPlayableRank - AceHi test: ");
        setAceHi(true);
        
       System.out.println("cardsPlayableRank(sixHAH, aceCAH): "
                + cardsPlayableRankSH(sixHAH,aceCAH));
       System.out.println(" cardsPlayableRank(sixHAH,sixHAH): " +  
               cardsPlayableRankSH(sixHAH,sixHAH));
       System.out.println(" cardsPlayableRank(aceDAH,aceDAH): " +                  
               cardsPlayableRankSH(aceDAH,aceDAH));
       System.out.println(" cardsPlayableRank(kingDAH,aceDAH): " +                  
               cardsPlayableRankSH(kingDAH,aceDAH));
       System.out.println(" cardsPlayableRank(aceSAH,queenSAH): " +                  
               cardsPlayableRankSH(aceSAH,queenSAH));
       System.out.println("");
//************************************************
       System.out.println("scoreCard Test: ");
       System.out.println("scoreCard(AceLo) Test: ");
       setAceHi(false);
       
       System.out.println("scoreCard(sixH): "
               + scoreCard(sixH));
       System.out.println("scoreCard(aceC): "
               + scoreCard(aceC));
       System.out.println("scoreCard(queenS): "
               + scoreCard(queenS));
       System.out.println("");
       
       System.out.println("scoreCard(AceHi) Test: ");
       setAceHi(true);
       
       System.out.println("scoreCard(sixHAH): "
               + scoreCard(sixHAH));
       System.out.println("scoreCard(aceCAH): "
               + scoreCard(aceCAH));
       System.out.println("scoreCard(queenSAH): "
               + scoreCard(queenSAH));
       System.out.println("");
       
    }

}//End TestCard class
