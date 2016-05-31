package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Gim extends Fighter implements Serializable {
    private Piece pieceHeHelpedLastTime;

    public Gim(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.pieceHeHelpedLastTime = null;
    }

    @Override
    public String proDescription(){
        return "PRO: Always fights and kills like he is the main general, even when assisting.";
    }

    @Override
    public String conDescription(){
        return "CON: Will not assist the same general two battles in a row.";
    }

    @Override
    public String toString() {
        return "Gim Yu-sin";
    }

    //pro
    @Override
    public int calculateAssistingFightBonus(){

        return this.getRegiments();
    }
    @Override
    public int calculateAssistingCasualties(){

        return (int) Math.ceil((double)this.getRegiments() / 2);
    }

    //con
    @Override
    public void helpFighter(Fighter assistedFighter) {
        if (this.fighterHeisHelping != assistedFighter && ((this.pieceHeHelpedLastTime != null &&
                this.pieceHeHelpedLastTime != assistedFighter) || (this.pieceHeHelpedLastTime == null) || this.hasChiefOfStaff()) ) {
            if (this.fighterHeisHelping != null) {
                this.pieceHeHelpedLastTime = this.getFighterHeisHelping();
                this.fighterHeisHelping.removeFighterAssistance(this);
            }
            if (this.townHeisHelping != null){
                this.townHeisHelping.removeFighterAssistance(this);
            }
            if (this.capitolHeisHelping != null){
                this.capitolHeisHelping.removeFighterAssistance(this);
            }
            this.addFighterHeisHelping(assistedFighter);
            assistedFighter.addHelpingFighter(this);
        }
    }
    @Override
    public boolean canHelpFighter(Fighter fighter){
        if (fighter.getClass() == Fredrick.class && !fighter.hasChiefOfStaff()){
            return false;
        }else if (this.pieceHeHelpedLastTime != null && this.pieceHeHelpedLastTime == fighter && !this.hasChiefOfStaff()){
            return false;
        }
        return true;
    }
}
