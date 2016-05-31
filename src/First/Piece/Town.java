package First.Piece;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.Fighters.*;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Town extends Piece implements Serializable {

    private boolean haveRegiment;
    private List<Fighter> whoIsHelpingHim;
    private boolean mutiny;
    private Fighter nurhaci;
    private Alliance oldAlliance;


    public Town(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance){
        super(qCoord, rCoord, sCoord, PieceType.TOWN, pieceAlliance);
        this.haveRegiment = true;
        this.whoIsHelpingHim = new ArrayList<>();
        this.mutiny = false;
        this.nurhaci = null;
        this.oldAlliance = null;
    }

    public boolean getHaveRegiment(){
        return this.haveRegiment;
    }

    public void resetHaveRegiment(){
        this.haveRegiment = true;
    }

    public void takeHaveRegiment(){
        this.haveRegiment = false;
    }

    public void clearWhoIsHelpingHim(){
        this.whoIsHelpingHim.clear();
    }

    public void removeFighterAssistance(Fighter fighter){
        this.whoIsHelpingHim.remove(fighter);
    }

    public void addHelpingFighter(Fighter fighter){
        this.whoIsHelpingHim.add(fighter);
    }

    public List<Fighter> getWhoIsHelpingHim(){
        return this.whoIsHelpingHim;
    }

    public void cleanWhoIsHelpingHim(){
        if (!this.whoIsHelpingHim.isEmpty()){
            List<Fighter> attackedFighters = new ArrayList<>();
            for (Fighter fighter : this.whoIsHelpingHim){
                if (fighter.getIsFighting() || fighter.isGhost()){
                    attackedFighters.add(fighter);
                }
            }
            if (!attackedFighters.isEmpty()){
                for (Fighter removeFighter : attackedFighters){
                    this.whoIsHelpingHim.remove(removeFighter);
                }
            }
        }
    }

    private int getStartingFightBonus(){
        int startingBonus = 1;
        for (Fighter fighter : this.getPiecePlayer().getPlayerFighters()){
            if (fighter.getClass() == Ardashir.class && fighter.getIsExposed()){
                return startingBonus + 2;
            }
        }
        return startingBonus;
    }


    public int getFightBonus(Fighter fighter) {
        int startingBonus = this.getStartingFightBonus();
        int mainDefender = 0;
        int others = 0;
        if (this.whoIsHelpingHim.size() == 1){
            if(!this.whoIsHelpingHim.get(0).getIsFighting()){
                if (this.whoIsHelpingHim.get(0).getClass() == Pachacuti.class){
                    mainDefender = (int) Math.ceil((double)(3*this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter))/2);
                }else {
                    mainDefender = this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter);
                }
            }
        }else if (this.whoIsHelpingHim.size() >=2 ){
            if(!this.whoIsHelpingHim.get(0).getIsFighting()){
                if (this.whoIsHelpingHim.get(0).getClass() == Pachacuti.class){
                    mainDefender = (int) Math.ceil((double)(3*this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter))/2);
                }else {
                    mainDefender = this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter);
                }
            }
            if (fighter.getClass() != Fredrick.class){
                for (int i = 1 ; i <= this.whoIsHelpingHim.size() - 1; i++){
                    if (!this.whoIsHelpingHim.get(i).getIsFighting()) {
                        others = others + this.whoIsHelpingHim.get(i).calculateAssistingFightBonus();
                    }
                }
            }
        }
        return startingBonus + mainDefender + others;
    }


    public int getCasualties(Fighter fighter) {
        int startingBonus = 1;
        int mainDefender = 0;
        int others = 0;
        if (this.whoIsHelpingHim.size() == 1){
            if(!this.whoIsHelpingHim.get(0).getIsFighting()){
                mainDefender = this.whoIsHelpingHim.get(0).calculateMainCasualties(fighter);
            }
        }else if (this.whoIsHelpingHim.size() >=2 ){
            if(!this.whoIsHelpingHim.get(0).getIsFighting()){
                mainDefender = this.whoIsHelpingHim.get(0).calculateMainCasualties(fighter);
            }
            if (fighter.getClass() != Fredrick.class){
                for (int i = 1 ; i <= this.whoIsHelpingHim.size() - 1; i++){
                    if (!this.whoIsHelpingHim.get(i).getIsFighting()) {
                        others = others + this.whoIsHelpingHim.get(i).calculateAssistingCasualties();
                    }
                }
            }
        }
        return startingBonus + mainDefender + others;
    }

    public void clearAssisting(){
        if (!this.whoIsHelpingHim.isEmpty()){
            this.whoIsHelpingHim.clear();
        }
    }

    public void exposeHelp() {
        for (Fighter fighter : this.whoIsHelpingHim){
            if (!fighter.getIsFighting()){
                fighter.exposeFighter();
            }
        }
    }
    public int homeAwayBonus(Fighter enemyFighter, Board engineBoard){

        if (enemyFighter.getClass() == Hannibal.class){
            return -1;
        }

        if (engineBoard.inHomeTerritory(this)){
            return 1;
        }
        if (engineBoard.inAwayTerritory(this)){
            return -1;
        }
        return 0;
    }

    public void nurhaciMutiny(Board engineBoard){
        if (this.nurhaci != null && !this.nurhaci.hasChiefOfStaff() && this.mutiny){
            Random random = new Random();
            int chances = random.nextInt(16) + 1;
            if (chances <= 4){
                engineBoard.removePiece(this);
                engineBoard.setPiece(this.getPieceQCoord(), this.getPieceRCoord(), this.getPieceSCoord(), this.oldAlliance, PieceType.TOWN);
            }
        }

    }

    public void setMutiny(Alliance alliance, Fighter nurhaci){
        if (nurhaci.getClass() == Nurhaci.class  && nurhaci.getIsExposed()){
            this.mutiny = true;
            this.nurhaci = nurhaci;
            this.oldAlliance = alliance;
        }
    }

    public void removeMutiny(){
        this.mutiny = false;
        this.nurhaci = null;
        this.oldAlliance = null;
    }

    public boolean getMutiny(){
        return this.mutiny;
    }

    public Fighter getNurhaci(){
        return this.nurhaci;
    }

    public Alliance getoldAlliance(){
        return this.oldAlliance;
    }

    public void transferMutiny(Town oldTown){
        if (oldTown.getMutiny()){
            this.mutiny = true;
            this.nurhaci = oldTown.getNurhaci();
            this.oldAlliance = oldTown.getoldAlliance();
        }
    }
}
