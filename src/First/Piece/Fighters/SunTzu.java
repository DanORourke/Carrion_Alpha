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

public class SunTzu extends Fighter implements Serializable {
    public boolean realSunTzu;
    public boolean haveMadeGhost;
    public Fighter ghost;

    public SunTzu(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.realSunTzu = true;
        this.haveMadeGhost = false;
        this.ghost = null;
    }
    public SunTzu(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance, Boolean realSunTzu) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
        this.realSunTzu = realSunTzu;
        this.haveMadeGhost = true;
        this.ghost = null;
    }


    @Override
    public String proDescription(){
        return "PRO: Can create shadow army in neighboring territory at end of " +
                "movement phase that disappears at the beginning of his next turn.  " +
                "If one of the two is attacked, the fake one disappears.";
    }

    @Override
    public String conDescription(){
        return "CON: Will only attack a general if he alone outnumbers the enemy by at least three regiments. Will not attack Leonidas if he knows it's Leonidas.";
    }

    @Override
    public String toString() {
        if (realSunTzu){
            return "Sun Tzu";
        }
        return "Ghost Sun Tzu";
    }
    @Override
    public String ghostName(){
        return "Sun Tzu";
    }

    //pro

    @Override
    public boolean isGhost(){
        if (realSunTzu){
            return false;
        }
        return true;
    }
    @Override
    public boolean sunTzuMethod(){
        if (this.isExposed && this.getMovementPoints() == 0 && !this.haveMadeGhost){
            return true;
        }
        return false;
    }

    @Override
    public void allocateMethod(){
        this.haveMadeGhost = false;
        this.ghost = null;
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    @Override
    public boolean hasGhost(){
        if (this.haveMadeGhost){
            return true;
        }
        return false;
    }

    @Override
    public void haveGhosted(){
        this.haveMadeGhost = true;
    }

    @Override
    public Fighter getGhost(){
        return this.ghost;
    }

    @Override
    public void setGhost(Fighter ghost){
        this.ghost = ghost;
    }

    @Override
    public boolean canHelpFighter(Fighter fighter){
        if (this.isGhost()){
            return false;
        }
        if (fighter.getClass() == Fredrick.class && !fighter.hasChiefOfStaff()){
            return false;
        }else if (fighter.getClass() == SunTzu.class && fighter.isGhost()){
            return false;
        }
        return true;
    }
    //con

    @Override
    public boolean canAshokaAttack(Fighter enemyFighter){
        if (this.isExposed &&
                (enemyFighter != null && ((this.getRegiments() + 3 <= enemyFighter.getRegiments()) || (enemyFighter.getClass() == Leonidas.class)))
                && !this.hasChiefOfStaff()){
            return false;
        }
        return true;
    }
}
