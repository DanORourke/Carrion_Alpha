package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;
import First.Player.Player;

import java.io.Serializable;

public class Pachacuti extends Fighter implements Serializable {

    public Pachacuti(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: When main defender of a town under attack, regiments fight like they are one half larger.";
    }

    @Override
    public String conDescription(){
        return "CON: Cannot move if he starts his turn unconnected to a supply line.  He can drop a supply line directly underneath himself without moving.";
    }

    @Override
    public String toString() {
        return "Pachacuti";
    }

    //pro embedded in town calc fight bonus

    //con

    @Override
    public void resetMovementPoints(){
        if (!this.hasChiefOfStaff() && !this.connectedToSupplyLine() && this.isExposed){
            this.interactedWithLines = true;
            this.movementPoints = 0;
        }else {
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
    public boolean connectedToSupplyLine(){
        return this.getPiecePlayer().isFighterConnectedDrop(this);
    }
}
