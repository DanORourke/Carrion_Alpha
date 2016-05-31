package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Acampachitli extends Fighter implements Serializable {

    public Acampachitli(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }
    @Override
    public String proDescription(){
        return "PRO: Whenever he defeats an enemy town, gains 1 regiment.";
    }

    @Override
    public String conDescription(){
        return "CON: When he loses a battle, sacrifices 1 extra regiment to appease the gods.  Will not sacrifice himself.";
    }

    @Override
    public String toString() {
        return "Acampachitli";
    }

    //pro
    @Override
    public void giveAcamaRegiment(){
        if (this.isExposed){
            this.addRegiments(1);
        }
        this.justBeatTown = true;
    }

    @Override
    public void attackUnoccupiedTown(){
        this.movementPoints = 1;
        this.addRegiments(1);
    }

    //con

     @Override
    public void releaseRegiments(final int i){
         if (this.getIsFighting() && !this.hasChiefOfStaff() && (i <= (this.regiments + 2))){
             this.regiments = this.regiments - i - 1;
             if (this.regiments <= 0) {
                 this.regiments = 0;
             }
         }else {
             this.regiments = this.regiments - i;
             if (this.regiments <= 0) {
                 this.regiments = 0;
             }
         }
     }
}
