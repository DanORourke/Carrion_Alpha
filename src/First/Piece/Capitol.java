package First.Piece;

import First.Board.Board;
import First.Piece.Fighters.Ardashir;
import First.Piece.Fighters.Fredrick;
import First.Piece.Fighters.Hannibal;
import First.Player.Alliance;
import First.Board.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Capitol extends Piece implements Serializable {

    private boolean hasChiefOfStaff;
    private int regiments;
    private List<Fighter> whoIsHelpingHim;

    public Capitol(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance){
        super(qCoord, rCoord, sCoord, PieceType.CAPITOL, pieceAlliance);
        this.hasChiefOfStaff = true;
        this.regiments = 2;
        this.whoIsHelpingHim = new ArrayList<>();
    }

    public int getRegiments(){
        return this.regiments;
    }

    public void resetRegiments(){
        this.regiments = 2;
    }

    public void takeRegiment(){
        this.regiments = this.regiments - 1;
    }

    public boolean hasChiefOfStaff(){
        return this.hasChiefOfStaff;
    }

    public void giveChiefofStaff(){
        this.hasChiefOfStaff = true;
    }

    public void removeChiefofStaff(){
        this.hasChiefOfStaff = false;
    }

    public List<Fighter> getWhoIsHelpingHim(){
        return this.whoIsHelpingHim;
    }

    public void removeFighterAssistance(Fighter fighter){
        this.whoIsHelpingHim.remove(fighter);
    }

    public void addHelpingFighter(Fighter fighter){
        this.whoIsHelpingHim.add(fighter);
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
        if (this.hasChiefOfStaff){
            startingBonus = startingBonus + 2;
        }
        for (Fighter fighter : this.getPiecePlayer().getPlayerFighters()){
            if (fighter.getClass() == Ardashir.class && fighter.getIsExposed()){
                startingBonus = startingBonus + 2;
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
                mainDefender = this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter);
            }
        }else if (this.whoIsHelpingHim.size() >=2 ){
            if(!this.whoIsHelpingHim.get(0).getIsFighting()){
                mainDefender = this.whoIsHelpingHim.get(0).calculateMainFightBonus(fighter);
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
}
