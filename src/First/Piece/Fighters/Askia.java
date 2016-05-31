package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Askia extends Fighter implements Serializable {

    public Askia(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: When he is voluntarily revealed, exposes the identity of all of another player's generals.";
    }

    @Override
    public String conDescription(){
        return "CON: If his identity is known, will reveal all friendly generals.";
    }

    @Override
    public String toString() {
        return "Askia";
    }


    @Override
    public void exposeAttackFighter(){
        this.exposeFighter();
        if (!this.hasChiefOfStaff()){
            this.exposeAllFighters();
        }
        for (Fighter fighter : this.getWhoIsHelpingHim()){
            fighter.exposeFighter();
            if (fighter.getClass() == Askia.class && !fighter.hasChiefOfStaff()){
                this.exposeAllFighters();
            }
        }
    }
    @Override
    public void exposeDefenseFighter(){
        this.exposeFighter();
        if (!this.hasChiefOfStaff()){
            this.exposeAllFighters();
        }
        for (Fighter fighter : this.getWhoIsHelpingHim()){
            if (!fighter.getIsFighting()){
                fighter.exposeFighter();
                if (fighter.getClass() == Askia.class && !fighter.hasChiefOfStaff()){
                    this.exposeAllFighters();
                }
            }
        }
    }
}
