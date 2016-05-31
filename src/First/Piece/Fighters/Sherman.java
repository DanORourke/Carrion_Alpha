package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Sherman extends Fighter implements Serializable {
    private boolean hasDroppedLine;

    public Sherman(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Does not suffer movement penalty for destroying lines.";
    }

    @Override
    public String conDescription(){
        return "CON: If takes a town, cannot occupy it by dropping any regiments in it.";
    }

    @Override
    public String toString() {
        return "Sherman";
    }

    //pro

    @Override
    public void lowerMovementPoints(Boolean cutLine, Boolean dropLine){
        if (this.isExposed){
            if (!this.interactedWithLines && !(dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (!this.interactedWithLines && (dropLine)){
                this.movementPoints = this.movementPoints - 2;
                this.interactedWithLines = true;
            }else if (this.interactedWithLines && !(dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (this.interactedWithLines && (dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }

        }else{
            if (!this.interactedWithLines && !(cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (!this.interactedWithLines && (cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 2;
            }else if (this.interactedWithLines && !(cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }else if (this.interactedWithLines && (cutLine || dropLine)){
                this.movementPoints = this.movementPoints - 1;
            }
            if (cutLine){
                this.hasCutLine = true;
                this.interactedWithLines = true;
            }
            if (dropLine){
                this.hasDroppedLine = true;
                this.interactedWithLines = true;
            }
        }
    }
    @Override
    public boolean shermanCut(){
        if (this.isExposed && !this.hasDroppedLine){
            return true;
        }
        return false;
    }

    //con

    @Override
    public boolean shermanCheck(){
        if (this.isExposed && !this.hasChiefOfStaff()){
            return false;
        }
        return true;
    }
}
