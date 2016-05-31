package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Cyrus extends Fighter implements Serializable {

    public Cyrus(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: If he wins a battle, will convert regiments instead of killing them.  If he loses, his troops will betray him to the enemy general instead of die.";
    }

    @Override
    public String conDescription(){
        return "CON: Pro happens.";
    }

    @Override
    public String toString() {
        return "Cyrus";
    }

    // Only works in general on general fight, not when towns or capitols involved.  could add if wanted
    @Override
    public void cyrusMethod(Fighter fighter, int kills){
        if (!this.hasChiefOfStaff()){
            this.addRegiments(kills);
        }
    }
}
