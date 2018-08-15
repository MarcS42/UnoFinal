package TestClasses;

import java.util.ArrayList;
import static Main.uno3.SpecialCardsSH.*;
import static Main.uno3.Card.*;
import static Main.uno3.CardHand.*;

import Main.uno3.Card;
import Main.uno3.CardDeck.*;

public class TestPlayerSH {
    static ArrayList<Card> cardsPlayable = new ArrayList<>();
    static ArrayList<Integer> workingSet = new ArrayList<>();
public TestPlayerSH() {
    Card fourC = new Card(4,0,true);
    Card fiveD = new Card(5,1,true);
    Card sixD = new Card(6,1,true);
    Card sixS = new Card(6,3,true);
    Card sevenC = new Card(7,0,true);
    Card eightD = new Card(8,1,true);
    Card eightS = new Card(8,3,true);
    Card tenD = new Card(10,1,true);
    Card tenH = new Card(10,2,true);
    Card tenS = new Card(10,3,true);
    Card tenC = new Card(10,0,true);
    Card jackC = new Card(11,0,true);
    Card jackD = new Card(11,1,true);
    Card jackH = new Card(11,2,true);

    cardsPlayable.add(fourC);
    cardsPlayable.add(fiveD);
    cardsPlayable.add(sixD);
    cardsPlayable.add(sixS);
    cardsPlayable.add(sevenC);
    cardsPlayable.add(eightD);
    cardsPlayable.add(eightS);
    cardsPlayable.add(tenD);
    cardsPlayable.add(tenH);
    cardsPlayable.add(tenS);
    cardsPlayable.add(tenC);
    cardsPlayable.add(jackC);
    cardsPlayable.add(jackD);
    cardsPlayable.add(jackH);
}

public ArrayList<Card> cardsPlayableAnalyzer(ArrayList<Card> cardsPlayable){
    ArrayList<Card> singles2 = new ArrayList<>();
    ArrayList<Card> doubles2 = new ArrayList<>();
    ArrayList<Card> triples2 = new ArrayList<>();
    ArrayList<Card> quads2 = new ArrayList<>();
    ArrayList<ArrayList<Card>> sdtq2 = new ArrayList<>();
  
  ArrayList<Card> cardsToBePlayed = new ArrayList<>();
  
  ArrayList<Integer> workingSet = cardsPlayableXRay(cardsPlayable);
    
 /********workingSet-to-cardsPlayable index position converter***/
  sdtq2 = workingSetCdsPlayableIndexConverter(cardsPlayable,workingSet);
  singles2 = sdtq2.get(0);
  doubles2 = sdtq2.get(1);
  triples2 = sdtq2.get(2);
  quads2 = sdtq2.get(3);
  
  System.out.println("Singles2: \n" + singles2 + "\nDoubles2: \n" + doubles2 +
          "\nTriples2: \n" + triples2 + "\nFour-of-a-Kind: \n" + quads2);

  cardsToBePlayed = quads2;  
  System.out.println("Cards to be Played: \n" + cardsToBePlayed);  
  
  return cardsToBePlayed;
}// End cardsPlayableAnalyzer

/**Takes a cardsPlayable AL and analyzes it for
 * four-of-a-kind, triples, doubles, and single
 * @param cardsPlayable
 * @return integer AL with numbers from 4 to 1
 * The position of the integers in the array is
 * linked to their original position in cardsPlayable.
 */
public ArrayList<Integer> cardsPlayableXRay(ArrayList<Card> cardsPlayable){
    ArrayList<Integer> workingSet = new ArrayList<>();
    if(cardsPlayable.size() > 1) {
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
    System.out.println("WorkingSet: \n" + workingSet);
return workingSet;    
}

    /********workingSet-to-cardsPlayable index position converter***/
    public ArrayList<ArrayList<Card>> workingSetCdsPlayableIndexConverter(ArrayList<Card> cardsPlayable, ArrayList<Integer> workingSet ) {
        ArrayList<Card> singles = new ArrayList<>();
        ArrayList<Card> doubles = new ArrayList<>();
        ArrayList<Card> triples = new ArrayList<>();
        ArrayList<Card> quads = new ArrayList<>();
        ArrayList<ArrayList<Card>> sdtq = new ArrayList<>();
        
    int cPSize = cardsPlayable.size(), wSVal, j, cPIndex;
//    int count = 0;
    for(int wSPosit=0; wSPosit < workingSet.size(); wSPosit++){
        if(wSPosit != 0) {
        cPSize -= workingSet.get(wSPosit-1);
        }
        wSVal = workingSet.get(wSPosit);
        for(j=0; j<wSVal; j++){
//            count++;
            cPIndex=cPSize-1-j;

            switch(wSVal) {
              case 1: singles.add(cardsPlayable.get(cPIndex));
                  break;
              case 2: doubles.add(cardsPlayable.get(cPIndex));
                  break;
              case 3: triples.add(cardsPlayable.get(cPIndex));
                  break;
              case 4: quads.add(cardsPlayable.get(cPIndex));
                  break;
              default :
                  System.out.println("There is a problem in the Switch code block");
            }// End Switch block
        }//End for(j=0; j<wSVal; j++)
    }//End for(int wSPosit=0; wSPosit < workingSet.size(); wSPosit++)
    
    System.out.println("Four-of-A-Kind: \n" + quads + "\nTriples: \n" + triples +
            "\nDoubles: \n" + doubles + "\nSingles: \n" + singles);
    
    sdtq.add(singles);
    sdtq.add(doubles);
    sdtq.add(triples);
    sdtq.add(quads);
    
    return sdtq;
  }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TestPlayerSH tPSH = new TestPlayerSH();
//        workingSet=tPSH.cardsPlayableXRay(cardsPlayable);
        tPSH.cardsPlayableAnalyzer(cardsPlayable);

        


    }

}
