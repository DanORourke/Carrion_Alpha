package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Leonidas extends Fighter implements Serializable {

    public Leonidas(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Cannot be outnumbered, enemy general regiment bonus capped at his regiment bonus.  Counts for battle, not casualties.";
    }

    @Override
    public String conDescription(){
        return "CON: Max army of ten.";
    }

    @Override
    public String toString() {
        return "Leonidas";
    }

    // pro embedded

    //con

    @Override
    public void addRegiments(int regiments){
        if (this.getIsExposed()){
            this.regiments = this.regiments + regiments;
            if (this.regiments >= 10){
                this.regiments = 10;
            }

        }else if (!this.getIsExposed()){
            this.regiments = this.regiments + regiments;
            if (this.regiments >= 20){
                this.regiments = 20;
            }
        }
    }
    @Override
    public int getMaxRegiments(){
        if (this.getIsExposed()){
            return 9;
        }else if (!this.getIsExposed()){
            return 19;
        }
        System.out.println("Ashoka getMaxRegiments is acting up");
        return 0;
    }
     @Override
    public void exposeFighter(){
         this.isExposed = true;
         if (this.getRegiments() >= 10){
             this.regiments = 10;
         }
     }
}
