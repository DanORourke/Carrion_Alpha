package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Ardashir extends Fighter implements Serializable {
    private int countdown;

    public Ardashir(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.countdown = 0;
    }

    @Override
    public String proDescription(){
        return "PRO: All friendly towns gain a +2 to defense (includes capital.)";
    }

    @Override
    public String conDescription(){
        return "CON: After a defensive victory, cannot move the following turn.";
    }

    @Override
    public String toString() {
        return "Ardashir";
    }

    // pro is embedded

    //con

    @Override
    public void allocateMethod(){
        this.countdown = this.countdown - 1;
        if (this.countdown <= 0){
            this.countdown = 0;
        }
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    @Override
    public void resetMovementPoints(){
        if (this.countdown >= 1 && !this.hasChiefOfStaff()){
            this.interactedWithLines = true;
            this.movementPoints = 0;
        }else {
            this.interactedWithLines = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 5;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 2;

            }
        }
    }

    @Override
    public void cyrusMethod(Fighter fighter, int kills){
        if (!this.wasAttacker){
            this.countdown = 2;
            if (fighter.getClass() == Cyrus.class && !fighter.hasChiefOfStaff()){
                this.addRegiments(kills);
            }
        }else {
            if (fighter.getClass() == Cyrus.class && !fighter.hasChiefOfStaff()){
                this.addRegiments(kills);
            }
        }
    }
}