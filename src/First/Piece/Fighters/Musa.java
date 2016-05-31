package First.Piece.Fighters;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.List;

public class Musa extends Fighter implements Serializable {
    int bribeAmount;

    public Musa(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.bribeAmount = 3;
    }

    @Override
    public String proDescription(){
        return "PRO: Pays 3 regiments to auto win a general fight if no one is trapped.";
    }

    @Override
    public String conDescription(){
        return "CON: Pro happens";
    }

    @Override
    public String toString() {
        return "Musa";
    }

    @Override
    public boolean willBribe(Fighter fighter, Boolean willDie){
        if (!willDie && this.getRegiments() >= bribeAmount + 1 && !this.hasChiefOfStaff()){
            return true;
        }
        return false;
    }

    @Override
    public void musaMethod(Fighter fighter){
        this.regiments = this.regiments - bribeAmount;
        fighter.addRegiments(bribeAmount);
    }
}
