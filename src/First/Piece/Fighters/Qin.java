package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Qin extends Fighter implements Serializable {

    public Qin(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Does not suffer a movement penalty when laying lines.";
    }

    @Override
    public String conDescription(){
        return "CON: If lays at least one supply line and moves all spaces he would be allowed to move without dropping any supply lines, loses one extra regiment.";
    }

    @Override
    public String toString() {
        return "Qin";
    }

    //pro and con

    @Override
    public void lowerMovementPoints(Boolean cutLine, Boolean dropLine){

        if (this.isExposed){
            if (!this.interactedWithLines && !(cutLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (!this.interactedWithLines && (cutLine)){
                this.movementPoints = this.movementPoints - 2;
                this.interactedWithLines = true;
            }else if (this.interactedWithLines && !(cutLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (this.interactedWithLines && (cutLine)){
                this.movementPoints = this.movementPoints - 1;
            }
            if (!this.hasChiefOfStaff() && this.getMovementPoints() == 0 && this.hasDroppedLine){
                this.regiments = this.regiments - 1;
                if (this.regiments <= 1){
                    this.regiments = 1;
                }
            }
        }else{
            if (!this.interactedWithLines && !(cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (!this.interactedWithLines && (cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 2;
                this.interactedWithLines = true;
            }else if (this.interactedWithLines && !(cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (this.interactedWithLines && (cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }
        }
        if (dropLine){
            this.hasDroppedLine = true;
        }
        if (cutLine){
            this.hasCutLine = true;
            this.interactedWithLines = true;
        }
    }

    @Override
    public void idleMethod(){
        this.wasAttacker = false;
        this.tileAttackedFrom = null;
        this.justBeatTown = false;
        this.hasDroppedLine = false;
    }


    @Override
    public void trueDroppedLine(Boolean lowerMovementPoints){
        if (!this.getIsExposed()){
            this.hasDroppedLine = true;
            this.interactedWithLines = true;
            this.movementPoints = this.movementPoints - 1;

        }else{
            this.hasDroppedLine = false;
        }
    }
    @Override
    public boolean qinDrop(){
        if (this.isExposed){
            return true;
        }
        return false;    }
}
