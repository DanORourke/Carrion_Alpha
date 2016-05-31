package First.Player;

import java.io.Serializable;

public enum Alliance implements Serializable {

    RED {

        @Override
        public String toString() {
            return "Red";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory Red called");

            if (((pieceQCoord >= 2 && pieceQCoord <= 10) && (pieceRCoord >= 2 && pieceRCoord <= 10) && (pieceSCoord == -12)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 11) && (pieceRCoord >= 2 && pieceRCoord <= 11) && (pieceSCoord == -13)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 12) && (pieceRCoord >= 2 && pieceRCoord <= 12) && (pieceSCoord == -14)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 13) && (pieceRCoord >= 2 && pieceRCoord <= 13) && (pieceSCoord == -15)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 14) && (pieceRCoord >= 2 && pieceRCoord <= 14) && (pieceSCoord == -16)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 15) && (pieceRCoord >= 2 && pieceRCoord <= 15) && (pieceSCoord == -17)) ||
                    ((pieceQCoord >= 3 && pieceQCoord <= 10) && (pieceRCoord >= 3 && pieceRCoord <= 10) && (pieceSCoord == -18)) ||
                    ((pieceQCoord >= 4 && pieceQCoord <= 10) && (pieceRCoord >= 4 && pieceRCoord <= 10) && (pieceSCoord == -19)) ||
                    ((pieceQCoord >= 5 && pieceQCoord <= 10) && (pieceRCoord >= 5 && pieceRCoord <= 10) && (pieceSCoord == -20))){
                //System.out.println("true");

                return true;

            }
            //System.out.println("false");

            return false;
        }
    },

    BLUE {
        @Override
        public String toString() {
            return "Blue";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory Blue called");

            if (((pieceQCoord >= 2 && pieceQCoord <= 10) && (pieceSCoord >= 2 && pieceSCoord <= 10) && (pieceRCoord == -12)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 11) && (pieceSCoord >= 2 && pieceSCoord <= 11) && (pieceRCoord == -13)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 12) && (pieceSCoord >= 2 && pieceSCoord <= 12) && (pieceRCoord == -14)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 13) && (pieceSCoord >= 2 && pieceSCoord <= 13) && (pieceRCoord == -15)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 14) && (pieceSCoord >= 2 && pieceSCoord <= 14) && (pieceRCoord == -16)) ||
                    ((pieceQCoord >= 2 && pieceQCoord <= 15) && (pieceSCoord >= 2 && pieceSCoord <= 15) && (pieceRCoord == -17)) ||
                    ((pieceQCoord >= 3 && pieceQCoord <= 10) && (pieceSCoord >= 3 && pieceSCoord <= 10) && (pieceRCoord == -18)) ||
                    ((pieceQCoord >= 4 && pieceQCoord <= 10) && (pieceSCoord >= 4 && pieceSCoord <= 10) && (pieceRCoord == -19)) ||
                    ((pieceQCoord >= 5 && pieceQCoord <= 10) && (pieceSCoord >= 5 && pieceSCoord <= 10) && (pieceRCoord == -20))){
                //System.out.println("true");

                return true;

            }
            //System.out.println("false");

            return false;
        }
    },

    GREEN {
        @Override
        public String toString() {
            return "Green";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory Green called");

            if (((pieceSCoord >= 2 && pieceSCoord <= 10) && (pieceRCoord >= 2 && pieceRCoord <= 10) && (pieceQCoord == -12)) ||
                    ((pieceSCoord >= 2 && pieceSCoord <= 11) && (pieceRCoord >= 2 && pieceRCoord <= 11) && (pieceQCoord == -13)) ||
                    ((pieceSCoord >= 2 && pieceSCoord <= 12) && (pieceRCoord >= 2 && pieceRCoord <= 12) && (pieceQCoord == -14)) ||
                    ((pieceSCoord >= 2 && pieceSCoord <= 13) && (pieceRCoord >= 2 && pieceRCoord <= 13) && (pieceQCoord == -15)) ||
                    ((pieceSCoord >= 2 && pieceSCoord <= 14) && (pieceRCoord >= 2 && pieceRCoord <= 14) && (pieceQCoord == -16)) ||
                    ((pieceSCoord >= 2 && pieceSCoord <= 15) && (pieceRCoord >= 2 && pieceRCoord <= 15) && (pieceQCoord == -17)) ||
                    ((pieceSCoord >= 3 && pieceSCoord <= 10) && (pieceRCoord >= 3 && pieceRCoord <= 10) && (pieceQCoord == -18)) ||
                    ((pieceSCoord >= 4 && pieceSCoord <= 10) && (pieceRCoord >= 4 && pieceRCoord <= 10) && (pieceQCoord == -19)) ||
                    ((pieceSCoord >= 5 && pieceSCoord <= 10) && (pieceRCoord >= 5 && pieceRCoord <= 10) && (pieceQCoord == -20))){
                //System.out.println("true");

                return true;

            }
            //System.out.println("false");

            return false;
        }
    },

    UNOCCUPIED {
        @Override
        public String toString() {
            return "Unoccupied";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            return false;
        }
    },

    WHITE {
        @Override
        public String toString() {
            return "White";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory White called");

            if (((pieceQCoord <= -2 && pieceQCoord >= -10) && (pieceRCoord <= -2 && pieceRCoord >= -10) && (pieceSCoord == 12)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -11) && (pieceRCoord <= -2 && pieceRCoord >= -11) && (pieceSCoord == 13)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -12) && (pieceRCoord <= -2 && pieceRCoord >= -12) && (pieceSCoord == 14)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -13) && (pieceRCoord <= -2 && pieceRCoord >= -13) && (pieceSCoord == 15)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -14) && (pieceRCoord <= -2 && pieceRCoord >= -14) && (pieceSCoord == 16)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -15) && (pieceRCoord <= -2 && pieceRCoord >= -15) && (pieceSCoord == 17)) ||
                    ((pieceQCoord <= -3 && pieceQCoord >= -10) && (pieceRCoord <= -3 && pieceRCoord >= -10) && (pieceSCoord == 18)) ||
                    ((pieceQCoord <= -4 && pieceQCoord >= -10) && (pieceRCoord <= -4 && pieceRCoord >= -10) && (pieceSCoord == 19)) ||
                    ((pieceQCoord <= -5 && pieceQCoord >= -10) && (pieceRCoord <= -5 && pieceRCoord >= -10) && (pieceSCoord == 20))){
               // System.out.println("true");

                return true;

            }
            //System.out.println("false");

            return false;
        }
    },

    YELLOW {
        @Override
        public String toString() {
            return "Yellow";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory Yellow called");
            System.out.println(pieceQCoord);
            System.out.println(pieceRCoord);
            System.out.println(pieceSCoord);

            if (((pieceQCoord <= -2 && pieceQCoord >= -10) && (pieceSCoord >= -2 && pieceSCoord <= -10) && (pieceRCoord == 12)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -11) && (pieceSCoord <= -2 && pieceSCoord >= -11) && (pieceRCoord == 13)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -12) && (pieceSCoord <= -2 && pieceSCoord >= -12) && (pieceRCoord == 14)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -13) && (pieceSCoord <= -2 && pieceSCoord >= -13) && (pieceRCoord == 15)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -14) && (pieceSCoord <= -2 && pieceSCoord >= -14) && (pieceRCoord == 16)) ||
                    ((pieceQCoord <= -2 && pieceQCoord >= -15) && (pieceSCoord <= -2 && pieceSCoord >= -15) && (pieceRCoord == 17)) ||
                    ((pieceQCoord <= -3 && pieceQCoord >= -10) && (pieceSCoord <= -3 && pieceSCoord >= -10) && (pieceRCoord == 18)) ||
                    ((pieceQCoord <= -4 && pieceQCoord >= -10) && (pieceSCoord <= -4 && pieceSCoord >= -10) && (pieceRCoord == 19)) ||
                    ((pieceQCoord <= -5 && pieceQCoord >= -10) && (pieceSCoord <= -5 && pieceSCoord >= -10) && (pieceRCoord == 20))){
                //System.out.println("true");

                return true;

            }
            //System.out.println("false");

            return false;
        }
    },

    BLACK {
        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord){
            //System.out.println("inMyTerritory Black called");

            if (((pieceSCoord <= -2 && pieceSCoord >= -10) && (pieceRCoord <= -2 && pieceRCoord >= -10) && (pieceQCoord == 12)) ||
                    ((pieceSCoord <= -2 && pieceSCoord >= -11) && (pieceRCoord <= -2 && pieceRCoord >= -11) && (pieceQCoord == 13)) ||
                    ((pieceSCoord <= -2 && pieceSCoord >= -12) && (pieceRCoord <= -2 && pieceRCoord >= -12) && (pieceQCoord == 14)) ||
                    ((pieceSCoord <= -2 && pieceSCoord >= -13) && (pieceRCoord <= -2 && pieceRCoord >= -13) && (pieceQCoord == 15)) ||
                    ((pieceSCoord <= -2 && pieceSCoord >= -14) && (pieceRCoord <= -2 && pieceRCoord >= -14) && (pieceQCoord == 16)) ||
                    ((pieceSCoord <= -2 && pieceSCoord >= -15) && (pieceRCoord <= -2 && pieceRCoord >= -15) && (pieceQCoord == 17)) ||
                    ((pieceSCoord <= -3 && pieceSCoord >= -10) && (pieceRCoord <= -3 && pieceRCoord >= -10) && (pieceQCoord == 18)) ||
                    ((pieceSCoord <= -4 && pieceSCoord >= -10) && (pieceRCoord <= -4 && pieceRCoord >= -10) && (pieceQCoord == 19)) ||
                    ((pieceSCoord <= -5 && pieceSCoord >= -10) && (pieceRCoord <= -5 && pieceRCoord >= -10) && (pieceQCoord == 20))){
                //System.out.println("true");
                return true;

            }
            //System.out.println("false");

            return false;
        }
    },;

    public abstract boolean inMyTerritory(int pieceQCoord, int pieceRCoord, int pieceSCoord);
}
