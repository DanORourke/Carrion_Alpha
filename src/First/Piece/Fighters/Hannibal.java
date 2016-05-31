package First.Piece.Fighters;

import First.Board.Board;
import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Hannibal extends Fighter implements Serializable {

    public Hannibal(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Always fights as though he is on home territory and enemy fights as though away.";
    }

    @Override
    public String conDescription(){
        return "CON: Will not attack enemy home capital.";
    }

    @Override
    public String toString(){
        return "Hannibal";
    }

    // pro

    @Override
    public int homeAwayBonus(Piece piece, Board engineBoard){
        Fighter enemyFighter = getEnemyFighter(piece);
        if (piece.getClass().getSuperclass() == Fighter.class){
            if (enemyFighter.getClass() == Hannibal.class){
                return 0;
            }
        }
        return 1;
    }
    //con
    @Override
    public boolean canHannibalAttack(){
        if (!this.hasChiefOfStaff()){
            return false;
        }
        return true;
    }
}
