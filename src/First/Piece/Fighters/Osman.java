package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Osman extends Fighter implements Serializable {
    private Fighter grudgeMatch;

    public Osman(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.grudgeMatch = null;
    }

    @Override
    public String proDescription(){
        return "PRO: Regiments fight, but do not kill,  as though they are one third larger when attacking a town or capitol.";
    }

    @Override
    public String conDescription(){
        return "CON: After suffering a defeat against an enemy general, will not attack another general until he achieves a victory against that general.";
    }

    @Override
    public String toString() {
        return "Osman";
    }

    //Pro

    @Override
    public int calculateFightBonus(Piece piece){
        if (piece.getPieceType() == PieceType.FIGHTER){
            return standardCasualties(piece);

        }else {
            Fighter enemyFighter = getEnemyFighter(piece);

            if (enemyFighter != null && enemyFighter.getIsExposed()){

                if (enemyFighter.getClass() == Fredrick.class){
                    return this.calculateMainFightBonusFightingTown(enemyFighter);
                }
            }
            int fighterOnlyFightBonus =  this.calculateMainFightBonusFightingTown(enemyFighter);
            int assistingFightBonus = 0;
            for (Fighter fighter : this.getWhoIsHelpingHim()){
                if(!fighter.getIsFighting()){
                    assistingFightBonus = assistingFightBonus + fighter.calculateAssistingFightBonus();
                }
            }
            return fighterOnlyFightBonus + assistingFightBonus;

        }
    }

    public int calculateMainFightBonusFightingTown(Fighter enemyFighter){
        if (enemyFighter != null && enemyFighter.getIsExposed()){
            if (enemyFighter.getClass() == Oda.class &&
                    !enemyFighter.hasChiefOfStaff()){
                return (int) Math.ceil((double)(3 * this.calculatePersonalMainFightBonusFightingTown()) / 2);

            }else if (enemyFighter.getClass() == Leonidas.class){
                int fighterOnlyFightBonus = this.calculatePersonalMainFightBonusFightingTown();
                if (fighterOnlyFightBonus >= 10){
                    fighterOnlyFightBonus = 10;
                }
                return fighterOnlyFightBonus;

            }else if (enemyFighter.getClass() == Washington.class &&
                    !enemyFighter.hasChiefOfStaff()){
                int fighterOnlyFightBonus = 0;

                if (((Washington)enemyFighter).getAttackingUnassisted()){
                    fighterOnlyFightBonus = (int) Math.ceil((double) (3 * this.calculatePersonalMainFightBonusFightingTown()) / 2);

                }else {
                    fighterOnlyFightBonus = this.calculatePersonalMainFightBonusFightingTown();

                }
                return fighterOnlyFightBonus;
            }
        }
        return this.calculatePersonalMainFightBonusFightingTown();
    }

    public int calculatePersonalMainFightBonusFightingTown(){
        return (int) Math.ceil((double)(3 * this.getRegiments()) / 2);
    }

    //con

    @Override
    public boolean canAshokaAttack(Fighter enemyFighter){
        if (this.isExposed){
            if (this.hasChiefOfStaff() ||
                    ((this.grudgeMatch != null) && (enemyFighter != null) && (this.grudgeMatch == enemyFighter)) ||
                    (this.grudgeMatch == null)){
                return true;
            }
            return false;
        }
        return true;
    }


    public void setGrudgeMatch(Fighter fighter){
        this.grudgeMatch = fighter;
    }

    @Override
    public void cyrusMethod(Fighter fighter, int kills){
        if (fighter.getClass() == Cyrus.class && !fighter.hasChiefOfStaff()){
            this.addRegiments(kills);
        }
        if (fighter.getClass() == Osman.class){
            ((Osman)fighter).setGrudgeMatch(this);
        }
        if (this.grudgeMatch == fighter){
            this.grudgeMatch = null;
        }
    }



}
