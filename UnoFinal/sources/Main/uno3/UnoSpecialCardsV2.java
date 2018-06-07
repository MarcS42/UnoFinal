package Main.uno3;



import java.util.Random;

public class UnoSpecialCardsV2 {
        private static boolean specialCard;
        private static boolean skip;
        private static boolean reverse;
        private static boolean drawTwo;
        private static boolean specialNotWildWD4;
        private static boolean wildWD4;
        private static boolean wild;
        private static boolean wildDrawFour;
        
        /**
         * SpecialCard true/false    
         * @param unocard
         * @return true if Rank > 18
         */
        public static boolean unoSpecialCard(UnoCard unocard) {
            specialCard = false;
            if(unocard.getRank() > 18) {
                specialCard = true;
            }
            return specialCard;
        }
        
        /**
         * skip true/false
         * @param unocard
         * @return True if 18 < Rank < 21
         */
        public static boolean unoCardSkip(UnoCard unocard){
            skip = false;
            if (unocard.getRank() > 18 && unocard.getRank()<21) {
                skip=true;
            }
            return skip;
        }
        
        /**
         * Reverse = reverse+skip
         * @param unocard
         * @return true if 20 < Rank < 23
         */
        public static boolean unoCardReverse(UnoCard unocard){
            reverse = false;
            if (unocard.getRank() > 20 && unocard.getRank()<23) {
                reverse = true;
            }
            return reverse;
        }

        /**
         * DrawTwo = drawTwo
         * @param unocard
         * @return true if 22 < Rank < 25
         */
        public static boolean unoCardDrawTwo(UnoCard unocard){
            drawTwo = false;
            if (unocard.getRank() > 22 && unocard.getRank()<25) {
                drawTwo=true;
            }
            return drawTwo;
        }
        
        /**Simulates Player Picking a Color when playing 
         * a wild card or WD4
         * @return int representing index of COLORS[Y, B, G, R]
         */
        public static int randomColor() {
            Random randomColor = new Random();
            return randomColor.nextInt(4);
        }
        
        /**
         * SpecialNotWild
         * @param unocard
         * @return true if 18 < Rank < 25
         */
        public static boolean specialNotWild(UnoCard unocard){
            specialNotWildWD4 = false;
            if (unocard.getRank() > 18 && 
                    unocard.getRank() < 25) 
            {
                specialNotWildWD4 = true; 
            }
            return specialNotWildWD4;
        }

        /**
         * Wild or WD4
         * @param unocard
         * @return true if 24 < Rank < 33
         */
        public static boolean uCardWldorWD4(UnoCard unocard){
            wildWD4 = false;
            if (unocard.getRank() > 24 && unocard.getRank() < 33) {
                wildWD4 = true; 
            }
            return wildWD4;
        }
        
        /**
         * Wild = wild + user input color
         * @param unocard
         * @return true if 24 < Rank < 29
         */
        public static boolean unoCardWild(UnoCard unocard){
            wild = false;
            if (unocard.getRank() > 24 && 
                    unocard.getRank()<29) {
                wild = true; 
            }
            return wild;
        }
        
    /**
     * WildDrawFour=wild+user input color+drawFour+skip
     * @param unocard
     * @return true if 28 < Rank <= 32
     */
        public static boolean unoCardWildDrawFour(UnoCard unocard){
            wildDrawFour = false;
            if (unocard.getRank() > 28 && unocard.getRank()<=32) {
                wildDrawFour = true;  
            }
            return wildDrawFour;
        }
} // end UnoSpecialCardsV2 Class


