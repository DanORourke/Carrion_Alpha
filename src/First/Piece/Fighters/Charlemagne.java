package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Charlemagne extends Fighter implements Serializable {

    public Charlemagne(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Enemy main generals kill him like they are one third smaller";
    }

    @Override
    public String conDescription(){
        return "CON: Moves as though he is one tier larger. Moves at 1 both normal and slowed when commanding 16-20 regiments.";
    }

    @Override
    public String toString() {
        return "Charlemagne";
    }

    // charlie has pro embedded

    //con

    @Override
    public void resetMovementPoints(){
        if (this.getIsExposed() && !this.hasChiefOfStaff()){
            this.interactedWithLines = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 4;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 2;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.interactedWithLines = true;
                this.movementPoints = 1;
            }
        }else if (!this.getIsExposed() || this.hasChiefOfStaff()){
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
}
