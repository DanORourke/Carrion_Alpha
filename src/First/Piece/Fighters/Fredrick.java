package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Fredrick extends Fighter implements Serializable {

    public Fredrick(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: No opponent general can call for assistance. Town or capital is limited to 1 assisting general.";
    }

    @Override
    public String conDescription(){
        return "CON: Will not accept assistance.";
    }

    @Override
    public String toString(){
        return "Fredrick";
    }

    //TODO  make his con tell world he is not accepting any help

    // Pro is embedded

    //con

    @Override
    public int calculateFightBonus(Piece piece){
        if (this.getIsExposed() && !this.hasChiefOfStaff()){
            Fighter enemyFighter = this.getEnemyFighter(piece);
            return this.calculateMainFightBonus(enemyFighter);

        }else if (!this.getIsExposed() || this.hasChiefOfStaff()) {
            return this.standardFightBonus(piece);
        }
        System.out.println("freddie calc fight bonus funky");
        return 0;
    }

}
