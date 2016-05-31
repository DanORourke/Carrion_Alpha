package First.Piece.Fighters;

import First.Board.Tile;
import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Ashoka extends Fighter implements Serializable {

    private int turnCounter;

    public Ashoka(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.turnCounter = 0;
    }

    @Override
    public String proDescription(){
        return "PRO: Max army of 30.  Moves at 1 both normal and slowed when commanding 21-30 regiments.";
    }

    @Override
    public String conDescription(){
        return "CON: Will not attack two turns in a row.";
    }
    //pro

    @Override
    public String toString() {
        return "Ashoka";
    }

    @Override
    public void addRegiments(int regiments){
        if (this.getIsExposed()){
            this.regiments = this.regiments + regiments;
            if (this.regiments >= 30){
                this.regiments = 30;
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
            return 29;
        }else if (!this.getIsExposed()){
            return 19;
        }
        System.out.println("Ashoka getMaxRegiments is acting up");
        return 0;
    }

    @Override
    public void resetMovementPoints(){
        this.interactedWithLines = false;

        if ((this.regiments >= 1) && (this.regiments <= 5)){
            this.movementPoints = 5;

        }else if ((this.regiments >= 6) && (this.regiments <= 10)){
            this.movementPoints = 4;


        }else if ((this.regiments >= 11) && (this.regiments <= 15)){
            this.movementPoints = 3;


        }else if ((this.regiments >= 16) && (this.regiments <= 20)){
            this.movementPoints = 2;

        }else if ((this.regiments >= 21) && (this.regiments <= 30)){
            this.interactedWithLines = true;
            this.movementPoints = 1;
        }
    }

    // con

    @Override
    public void allocateMethod(){
        if (this.turnCounter >= 1){
            this.turnCounter = this.turnCounter - 1;
        }
        this.tileAttackedFrom = null;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    @Override
    public void attack(Tile tileAttackedFrom){
        this.movementPoints = 0;
        this.isFighting = true;
        this.turnCounter = 2;
        this.tileAttackedFrom = tileAttackedFrom;
        this.wasAttacker = true;
    }

    @Override
    public boolean canAshokaAttack(Fighter enemyFighter){
        if (this.isExposed){
            if (this.hasChiefOfStaff() || this.turnCounter == 0){
                return true;
            }
            return false;
        }
        return true;
    }



}
