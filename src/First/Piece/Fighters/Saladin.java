package First.Piece.Fighters;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.List;

public class Saladin extends Fighter implements Serializable {
    int timeSinceLastAttack;

    public Saladin(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        timeSinceLastAttack = 5;
    }

    @Override
    public String proDescription(){
        return "PRO: Any enemy general within 5 spaces of Saladin can only move towards him.";
    }

    @Override
    public String conDescription(){
        return "CON: Must spend the turn before an attack not moving to pray and secure the blessing of Allah.";
    }

    @Override
    public String toString() {
        return "Saladin";
    }

    // pro embedded in canNapoleonMove in Fighter class
    // con

    @Override
    public boolean canAshokaAttack(Fighter enemyFighter){
        if ((this.timeSinceLastAttack >= 2) || this.hasChiefOfStaff()){
            return true;
        }
        return false;
    }

    @Override
    public void allocateMethod(){
        this.timeSinceLastAttack = this.timeSinceLastAttack + 1;
        this.tileAttackedFrom = null;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    @Override
    public void attack(Tile tileAttackedFrom){
        this.movementPoints = 0;
        this.isFighting = true;
        this.timeSinceLastAttack = 0;
        this.tileAttackedFrom = tileAttackedFrom;
        this.wasAttacker = true;

    }


}