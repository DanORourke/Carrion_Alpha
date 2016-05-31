package First.Piece.Fighters;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Napoleon extends Fighter implements Serializable {

    public Napoleon(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Friendly generals who assist him fight and kill like they are a main and not assisting.";
    }

    @Override
    public String conDescription(){
        return "CON: If an enemy general is within 5 spaces, he can only move towards him.";
    }

    @Override
    public String toString(){
        return "Napoleon";
    }

    //pro

    @Override
    public int calculateFightBonus(Piece piece){


        Fighter enemyFighter = this.getEnemyFighter(piece);

        if (enemyFighter != null && enemyFighter.getIsExposed()){

            if (enemyFighter.getClass() == Fredrick.class){
                return this.calculateMainFightBonus(enemyFighter);
            }
        }

        int fighterOnlyFightBonus = this.calculateMainFightBonus(enemyFighter);
        int assistingFightBonus = 0;
        for (Fighter fighter : this.getWhoIsHelpingHim()){
            if(!fighter.getIsFighting()){
                assistingFightBonus = assistingFightBonus + fighter.calculatePersonalMainFightBonus(fighter.getRegiments());
            }
        }
        return fighterOnlyFightBonus + assistingFightBonus;

    }

    @Override
    public int calculateCasualties(Piece piece){
        Fighter enemyFighter = getEnemyFighter(piece);

        if (enemyFighter != null && enemyFighter.getIsExposed()){

            if (enemyFighter.getClass() == Fredrick.class){
                return this.calculateMainCasualties(enemyFighter);
            }
        }
        int fighterOnlyCasualties =  this.calculateMainCasualties(enemyFighter);
        int assistingCasualties = 0;
        for (Fighter fighter : this.getWhoIsHelpingHim()){
            if(!fighter.getIsFighting()){
                assistingCasualties = assistingCasualties + fighter.calculatePersonalMainCasualties();
            }
        }
        return fighterOnlyCasualties + assistingCasualties;
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

        List<Fighter> enemyFighterList = engineBoard.getNearestEnemyFighter(this);
        System.out.println("canNapoleonMove is called");
        if (this.getIsExposed()){
            if (this.hasChiefOfStaff() || engineBoard.movingTowardFighter(this, enemyFighterList, clickedTile)){
                System.out.println("canNapoleonMove true");
                return true;
            }
            System.out.println("canNapoleonMove false");
            return false;
        }
        System.out.println("canNapoleonMove true");
        return true;
    }



}