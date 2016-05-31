package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Alexander extends Fighter implements Serializable {

    public Alexander(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Regiments fight like they are one half larger.";
    }

    @Override
    public String conDescription(){
        return "Con: Loses 1 regiment at the beginning of every turn, unless at 1.\nIf loses and chief of staff is with him, kills chief of staff.";
    }

    @Override
    public String toString(){
        return "Alexander";
    }

    @Override
    public int calculatePersonalMainFightBonus(int regiments){

        return (int) Math.ceil((double)(3 * regiments) / 2);

    }

    @Override
    public void allocateMethod(){
        if (this.getIsExposed() && !this.hasChiefOfStaff()){
            if (this.getRegiments() >= 2){
                this.releaseRegiments(1);
            }
        }
        this.tileAttackedFrom = null;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    @Override
    public void releaseRegiments(int i){
        this.regiments = this.regiments - i;
        if (this.regiments <= 0){
            this.regiments = 0;
        }
        if (this.hasChiefOfStaff()){
            this.removeChiefOfStaff();
        }
    }
}
