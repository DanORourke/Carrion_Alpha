package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Ramses extends Fighter implements Serializable {

    public Ramses(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Can always move one space more than normal.";
    }

    @Override
    public String conDescription(){
        return "CON: Regiments kill as though they are one third smaller.";
    }

    @Override
    public String toString(){
        return "Ramses";
    }

    //pro

    @Override
    public void resetMovementPoints(){
        if (this.getIsExposed()){
            this.interactedWithLines = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 6;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 5;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 3;

            }

        }else if (!this.getIsExposed()){
            this.interactedWithLines = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 5;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 2;

            }
        }
    }

    //con

    @Override
    public int calculatePersonalMainCasualties(){
        if (!this.hasChiefOfStaff()){
            return (int) Math.ceil((double)this.getRegiments() / 3);

        }else if (this.hasChiefOfStaff()){
            return (int) Math.ceil((double)this.getRegiments() / 2);
        }
        System.out.println("Ramses con not working right");
        return 0;
    }
}
