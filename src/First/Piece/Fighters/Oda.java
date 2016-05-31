package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Oda extends Fighter implements Serializable {

    public Oda(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Regiments kill like they are one half larger.";
    }

    @Override
    public String conDescription(){
        return "CON: Opposing regiments fight like they are one half larger.";
    }

    @Override
    public String toString() {
        return "Oda";
    }

    //Pro
    @Override
    public int calculatePersonalMainCasualties(){

        return (int) Math.ceil((double)(3 * this.getRegiments()) / 4);

    }
    //Con is embedded in others
}