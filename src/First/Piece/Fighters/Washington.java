package First.Piece.Fighters;

import First.Board.Tile;
import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Washington extends Fighter implements Serializable {
    private boolean attackingUnassisted;
    private boolean hasMoved;

    public Washington(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.attackingUnassisted = false;
        this.hasMoved = false;
    }

    @Override
    public String proDescription(){
        return "PRO: If does not move, gains 25% more regiments at the end of players turn.";
    }

    @Override
    public String conDescription(){
        return "CON: If attacks unsupported, enemy regiments fight like they are one half larger.";
    }

    @Override
    public String toString() {
        return "George Washington";
    }

    //pro

    @Override
    public void lowerMovementPoints(Boolean cutLine, Boolean dropLine) {
        if (!this.interactedWithLines && !(cutLine || dropLine)) {
            this.movementPoints = this.movementPoints - 1;
        } else if (!this.interactedWithLines && (cutLine || dropLine)) {
            this.movementPoints = this.movementPoints - 2;
            this.interactedWithLines = true;
        } else if (this.interactedWithLines && !(cutLine || dropLine)) {
            this.movementPoints = this.movementPoints - 1;
        } else if (this.interactedWithLines && (cutLine || dropLine)) {
            this.movementPoints = this.movementPoints - 1;
        }
        this.hasMoved = true;
    }


    @Override
    public void idleMethod(){
        this.wasAttacker = false;
        this.tileAttackedFrom = null;
        this.justBeatTown = false;
        if (!this.hasMoved && this.isExposed){
            this.addRegiments((int) Math.ceil((double)this.getRegiments() / 4));
        }
        this.hasMoved = false;
    }

    // con
    @Override
    public void attack(Tile tileAttackedFrom){
        this.movementPoints = 0;
        this.isFighting = true;
        if (!this.hasChiefOfStaff()){
            this.attackingUnassisted = true;
        }
        this.tileAttackedFrom = tileAttackedFrom;
        this.wasAttacker = true;

    }

    public boolean getAttackingUnassisted(){
        boolean yup =  this.attackingUnassisted;
        this.attackingUnassisted = false;
        return yup;
    }
}
