package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Tecumseh extends Fighter implements Serializable {

    public Tecumseh(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Can raises one regiment at the beginning of every turn that only he can use.  ";
    }

    @Override
    public String conDescription(){
        return "CON: Must raise and accept the regiment.  This does not preclude him from accepting other regiments, but does preclude him from cutting any regiments.";
    }

    @Override
    public String toString() {
        return "Tecumseh";
    }

    //pro

    @Override
    public void allocateMethod(){
        if (this.getIsExposed() && !this.hasChiefOfStaff()){
            this.addRegiments(1);
        }
        this.tileAttackedFrom = null;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    //con embedded in player isFighterConnectedDrop
}
