/**
 * 
 */
package TestClasses;

import Main.uno3.UnoCard;

/**
 * @author MarcSherman
 *
 */
public class TestUnoCard {
    public UnoCard card000;
    public UnoCard card0110;
    public UnoCard card0220;
    
 public TestUnoCard() {  
    for(int i=0; i<4; i++) {
        for(int j= 19; j< 25; j++) {
            System.out.println(new UnoCard(i,j));
        }
    }
    for(int j= 25; j< 33; j++) {
        System.out.println(new UnoCard(4,j));
    }
    System.out.println();
    
    UnoCard card0000 = new UnoCard();
    System.out.println(card0000);
    UnoCard card0110 = new UnoCard(1,10);
    System.out.println(card0110.getColor() + " " + card0110.getRank());
    UnoCard card0220 = new UnoCard(2,20);
    System.out.println("Uno card equals card0110"  +" "+ card0110);
    System.out.println("Uno card equals card0220"  +" "+ card0220);
    System.out.println(UnoCard.compareCards(card0110,card0220));
    System.out.println();
    System.out.println(compCardsResult(card0110, card0220));
    
 } 

 public String compCardsResult(UnoCard C1, UnoCard C2) {
     if(UnoCard.compareCards(C1, C2) >0) 
     {
         return "card0110 is greater than card0220";
     }
     if(UnoCard.compareCards(C1, C2) <0) 
     {
         return "card0220 is greater than card0110";
     }
     return "card0110 is equal to card0220";
 }
 
    /**
     * @param args
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
       TestUnoCard test1 = new TestUnoCard();
//       System.out.println(UnoCard.scoreCard((Card) new UnoCard(1,10)));
    }

}
