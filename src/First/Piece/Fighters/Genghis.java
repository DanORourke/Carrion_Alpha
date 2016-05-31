package First.Piece.Fighters;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Genghis extends Fighter implements Serializable {
    private int justLost;
    private Tile battleField;
    private boolean haveBounced;

    public Genghis(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.justLost = 0;
        this.battleField = null;
        this.haveBounced = false;
    }
    @Override
    public String proDescription(){
        return "PRO: If he attacks and takes a town, Can move again.";
    }

    @Override
    public String conDescription(){
        return "CON: If ever loses a battle, must spend next turn moving in opposite direction.  He will stay put if he can’t move in the opposite direction.";
    }

    @Override
    public String toString() {
        return "Genghis Khan";
    }

    //pro
    @Override
    public boolean canAshokaAttack(Fighter enemyFighter){
        if (this.isExposed && this.haveBounced){
            return false;
        }
        return true;
    }

    @Override
    public void attackUnoccupiedTown(){
        if (this.getIsExposed()){
            this.interactedWithLines = false;
            this.hasDroppedLine = false;
            this.hasCutLine = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 5;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 2;

            }
            this.haveBounced = true;
        }else{
            this.movementPoints = 1;
        }
    }

    @Override
    public void giveAcamaRegiment(){
        if (this.isExposed){
            this.interactedWithLines = false;
            this.hasDroppedLine = false;
            this.hasCutLine = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 5;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 2;

            }
            this.haveBounced = true;
        }
        this.justBeatTown = true;
    }
    @Override
    public void genghisMethod(){
        if (this.isExposed){
            this.interactedWithLines = false;
            this.hasDroppedLine = false;
            this.hasCutLine = false;

            if ((this.regiments >= 1) && (this.regiments <= 5)){
                this.movementPoints = 5;

            }else if ((this.regiments >= 6) && (this.regiments <= 10)){
                this.movementPoints = 4;


            }else if ((this.regiments >= 11) && (this.regiments <= 15)){
                this.movementPoints = 3;


            }else if ((this.regiments >= 16) && (this.regiments <= 20)){
                this.movementPoints = 2;

            }
            this.haveBounced = true;
        }
    }
    @Override
    public boolean genghisOrShivaji(){
        if (this.getIsExposed()){
            return true;
        }
        return false;
    }

    //con
    @Override
    public void releaseRegiments(final int i){
        if (this.getIsFighting() && !this.hasChiefOfStaff()){
            this.justLost = 2;
        }
        this.regiments = this.regiments - i;
        if (this.regiments <= 0) {
            this.regiments = 0;
        }
    }
    @Override
    public void allocateMethod(){
        this.justLost = this.justLost - 1;
        if (this.justLost <= 0){
            this.justLost = 0;
            this.battleField = null;
        }
        this.tileAttackedFrom = null;
        this.haveBounced = false;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }
    @Override
    public boolean canNapoleonMove(Board engineBoard, Tile clickedTile){
        List<Fighter> saladinList= new ArrayList<>();
        List<Fighter> oneSaladinList= new ArrayList<>();
        for (Fighter fighter : engineBoard.getAllActiveFighters()){
            if (fighter.getPieceAlliance() != this.getPieceAlliance() && fighter.getClass() == Saladin.class &&
                    fighter.getIsExposed()){
                saladinList.add(fighter);
            }
        }
        if (!saladinList.isEmpty()){
            for (Fighter fighter : saladinList){
                if (engineBoard.getNearestEnemyFighter(fighter).contains(this)){
                    oneSaladinList.add(fighter);
                    if (!engineBoard.movingTowardSaladin(this, oneSaladinList, clickedTile)){
                        return false;
                    }
                    oneSaladinList.clear();
                }
            }
        }
        if (this.scaredOfSargon == 1){
            return false;
        }
        if (!this.hasChiefOfStaff() && this.battleField != null && this.justLost >= 1){
            System.out.println("canNapoleonMove applies");
            if (!this.runningAway(clickedTile)){
                System.out.println("canNapoleonMove false");
                return false;
            }
        }
        System.out.println("canNapoleonMove true");
        return true;
    }

    @Override
    public void getBattleField(Tile battleField){
        this.battleField = battleField;
    }

    private boolean runningAway(Tile clickedTile){
        int fighterQCoord = this.getPieceQCoord();
        int fighterRCoord = this.getPieceRCoord();
        int fighterSCoord = this.getPieceSCoord();
        int tileQCoord = clickedTile.getTileQCoord();
        int tileRCoord = clickedTile.getTileRCoord();
        int tileSCoord = clickedTile.getTileSCoord();
        int battleQCoord = this.battleField.getTileQCoord();
        int battleRCoord = this.battleField.getTileRCoord();
        int battleSCoord = this.battleField.getTileSCoord();
        int fighterDistance = Math.max(Math.abs(fighterQCoord - battleQCoord ),
                Math.max(Math.abs(fighterRCoord - battleRCoord), Math.abs(fighterSCoord - battleSCoord)));
        int tileDistance = Math.max(Math.abs(tileQCoord - battleQCoord ),
                Math.max(Math.abs(tileRCoord - battleRCoord), Math.abs(tileSCoord - battleSCoord)));
        System.out.println("fighterDistance " + fighterDistance);
        System.out.println("tileDistance " + tileDistance);
        if (tileDistance >= fighterDistance + 1){
            return true;
        }
        return false;
    }
}
