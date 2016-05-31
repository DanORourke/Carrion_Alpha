package First.Piece.Fighters;

import First.Piece.Fighter;
import First.Piece.Piece;
import First.Player.Alliance;

import java.io.Serializable;

public class Shaka extends Fighter implements Serializable {

    public Shaka(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Regiments fight like they are one half larger against armies he will slaughter.";
    }

    @Override
    public String conDescription(){
        return "CON: Enemy regiments kill like they are one half larger.";
    }

    @Override
    public String toString() {
        return "Shaka Zulu";
    }

    //pro

    @Override
    public int calculateMainFightBonus(Fighter enemyFighter){
        if (enemyFighter != null &&
                (calculatePersonalMainCasualties() >= enemyFighter.getRegiments())) {
            int personalMainFightBonus = (int) Math.ceil((double)(3 * this.calculatePersonalMainFightBonus(this.getRegiments()))/ 2);

            if (enemyFighter.getIsExposed()){

                if (enemyFighter.getClass() == Oda.class &&
                        !enemyFighter.hasChiefOfStaff()){
                    return (int) Math.ceil((double)(3 * personalMainFightBonus) / 2);

                }else if (enemyFighter.getClass() == Leonidas.class && this.getRegiments() >= enemyFighter.getRegiments() +1){

                    return this.calculatePersonalMainFightBonus(enemyFighter.getRegiments());

                }else if (enemyFighter.getClass() == Washington.class &&
                        !enemyFighter.hasChiefOfStaff()){
                    int fighterOnlyFightBonus = 0;

                    if (((Washington)enemyFighter).getAttackingUnassisted()){
                        fighterOnlyFightBonus = (int) Math.ceil((double) (3 * personalMainFightBonus) / 2);

                    }else {
                        fighterOnlyFightBonus = personalMainFightBonus;

                    }
                    return fighterOnlyFightBonus;
                }
            }
            return personalMainFightBonus;

        }
        if (enemyFighter != null && enemyFighter.getIsExposed()){
            if (enemyFighter.getClass() == Oda.class &&
                    !enemyFighter.hasChiefOfStaff()){
                return (int) Math.ceil((double)(3 * this.calculatePersonalMainFightBonus(this.getRegiments())) / 2);

            }else if (enemyFighter.getClass() == Leonidas.class && this.getRegiments() >= enemyFighter.getRegiments() +1){

                return this.calculatePersonalMainFightBonus(enemyFighter.getRegiments());

            }else if (enemyFighter.getClass() == Washington.class &&
                    !enemyFighter.hasChiefOfStaff()){
                int fighterOnlyFightBonus = 0;

                if (((Washington)enemyFighter).getAttackingUnassisted()){
                    fighterOnlyFightBonus = (int) Math.ceil((double) (3 * this.calculatePersonalMainFightBonus(this.getRegiments())) / 2);

                }else {
                    fighterOnlyFightBonus = this.calculatePersonalMainFightBonus(this.getRegiments());

                }
                return fighterOnlyFightBonus;
            }
        }
        return this.calculatePersonalMainFightBonus(this.getRegiments());

    }
    // Con is embedded
}