package Main.uno3;

public class SpecialCardsSH {
    
    private static boolean specialCardSH;
    private static boolean twoReset;
    private static boolean threeMirror;
    private static boolean tenBomb;
    private static boolean sevenPlayLowerCard;
    private static boolean fourOfaKindBomb;
    /**
    * SpecialCard true/false    
    * @param card
    * @return true if Rank == 2, 3, 7, or 10.
    */
   public static boolean specialCardSH(Card card) {
       specialCardSH = false;
       int rank = card.getRank();
       if(rank == 2 || rank == 3 || rank == 10 || rank == 7) {
           specialCardSH = true;
       }
       return specialCardSH;
   }
   
   public static boolean twoReset(Card card) {
       twoReset = false;
       if(card.getRank() == 2) {
           return true;
       }
       return twoReset;
   }
   
   public static boolean threeMirror(Card card) {
       threeMirror = false;
       if(card.getRank() == 3) {
           return true;
       }
       return threeMirror;
   }
   
   public static boolean tenBomb(Card card) {
       tenBomb = false;
       if(card.getRank() == 10) {
           return true;
       }
       return tenBomb;
   }
  
   public static boolean sevenPlayLowerCard(Card card) {
       sevenPlayLowerCard = false;
       if(card.getRank() == 7) {
           return true;
       }
       return sevenPlayLowerCard;
   }
   
   /**Four cards must be played in sequence; if so, becomes a Bomb.
 * @param card
 * @return
 */
public static boolean fourOfaKindBomb(Card card) {
       fourOfaKindBomb = false;
       if(ShitHead.isFourOfaKind() == true) {
           return true;
       }
       return fourOfaKindBomb;
   }
   
}
