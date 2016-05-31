package First.Piece.Fighters;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;
import First.Player.Player;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Shivaji extends Fighter implements Serializable {
    private boolean haveBounced;


    public Shivaji(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.haveBounced = false;
    }

    @Override
    public String proDescription(){
        return "PRO: After he attacks and wins, can take a movement phase again as long as no battle or interaction with lines required.";
    }

    @Override
    public String conDescription(){
        return "CON: Can not move more than 10 spaces from a friendly general, town, or capitol.";
    }

    @Override
    public String toString() {
        return "Shivaji";
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
    public void shivajiMethod(){
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

    @Override
    public void allocateMethod(){
        this.haveBounced = false;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    //con

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

        if (this.isExposed || !this.hasChiefOfStaff()){
            List<Piece> otherGuys = new ArrayList<>();
            for (Piece piece : this.getPiecePlayer().getMainPieces()){
                if (piece != this){
                    otherGuys.add(piece);
                }
            }
            List<Piece> nearestOtherGuy = engineBoard.getNearestMainPiece(this, otherGuys);
            if (this.runningAway(nearestOtherGuy, clickedTile)){
                return false;
            }

        }
        return true;
    }

    private boolean runningAway(List<Piece> nearestOtherGuy, Tile clickedTile){
        int fighterQCoord = this.getPieceQCoord();
        int fighterRCoord = this.getPieceRCoord();
        int fighterSCoord = this.getPieceSCoord();
        int tileQCoord = clickedTile.getTileQCoord();
        int tileRCoord = clickedTile.getTileRCoord();
        int tileSCoord = clickedTile.getTileSCoord();

        for (Piece piece : nearestOtherGuy){
            int pieceQCoord = piece.getPieceQCoord();
            int pieceRCoord = piece.getPieceRCoord();
            int pieceSCoord = piece.getPieceSCoord();

            int fighterDistance = Math.max(Math.abs(fighterQCoord - pieceQCoord ),
                    Math.max(Math.abs(fighterRCoord - pieceRCoord), Math.abs(fighterSCoord - pieceSCoord)));
            int tileDistance = Math.max(Math.abs(tileQCoord - pieceQCoord ),
                    Math.max(Math.abs(tileRCoord - pieceRCoord), Math.abs(tileSCoord - pieceSCoord)));
            System.out.println("fighterDistance " + fighterDistance);
            System.out.println("tileDistance " + tileDistance);
            if (tileDistance >= 11){
                return true;
            }
        }

        return false;
    }
}
