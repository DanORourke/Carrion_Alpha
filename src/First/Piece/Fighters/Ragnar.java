package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Ragnar extends Fighter implements Serializable {

    public Ragnar(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: If dies during an attack on a general, all friendly generals gain a free movement and battle phase after all current battles are resolved.";
    }

    @Override
    public String conDescription(){
        return "CON: If he loses a battle with a general, he and his entire army are slaughtered.";
    }

    @Override
    public String toString() {
        return "Ragnar";
    }
    // kinda has both, pro only if he was the attacker, both only in general on general fights

    @Override
    public boolean isExposedRagnar(){
        if (this.isExposed && !this.hasChiefOfStaff()){
            return true;
        }
        return false;
    }

    @Override
    public boolean killRagnar(){
        return true;
    }
}
