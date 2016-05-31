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

public class Sargon extends Fighter implements Serializable {
    private int haveCapturedTown;


    public Sargon(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.haveCapturedTown = 0;
    }

    @Override
    public String proDescription(){
        return "PRO: Opposing general can not move the turn after losing a direct battle with Sargon.";
    }

    @Override
    public String conDescription(){
        return "CON: Can not move the turn after building a town.";
    }

    @Override
    public String toString() {
        return "Sargon";
    }

    //pro embedded in can nappy move and allocate method

    //con

    @Override
    public void sargonMethod(){
        if (this.getIsExposed() && !this.hasChiefOfStaff()){
            this.haveCapturedTown = 2;
        }
    }

    @Override
    public void allocateMethod(){
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
        this.haveCapturedTown = this.haveCapturedTown - 1;
        if (this.haveCapturedTown <= 0){
            this.haveCapturedTown = 0;
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
        if (this.haveCapturedTown == 1){
            return false;
        }
        return true;
    }

}
