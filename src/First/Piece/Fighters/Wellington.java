package First.Piece.Fighters;

import First.Board.Tile;
import First.Piece.Fighter;
import First.Player.Alliance;
import First.Player.Player;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Wellington extends Fighter implements Serializable {

    public Wellington(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Takes the pros and cons of enemy general in a direct fight.  They stay with him forever.";
    }

    @Override
    public String conDescription(){
        return "CON: Pro happens.";
    }

    @Override
    public String toString() {
        return "Wellington";
    }

    //permaswitch

    @Override
    public Fighter wellingtonMethod(Fighter fighter, Tile tile){
        if (!this.hasChiefOfStaff()){
            Class<?> fighterClass = fighter.getClass();
            Constructor[] ctors = fighterClass.getDeclaredConstructors();
            Constructor ctor = null;
            for (int i = 0; i < ctors.length; i++) {
                ctor = ctors[i];
                if (ctor.getGenericParameterTypes().length == 0)
                    break;
            }
            Object newFighter = null;
            try {
                System.out.println(ctor.getGenericParameterTypes().length);
                newFighter = ctor.newInstance(this.getPieceQCoord(), this.getPieceRCoord(), this.getPieceSCoord(),this.getPieceAlliance() );
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //((Fighter)newFighter).setNewCoordinates(this.getPieceQCoord(), this.getPieceRCoord(), this.getPieceSCoord());
            //((Fighter)newFighter).setPieceAlliance(this.getPieceAlliance());
            ((Fighter)newFighter).addRegiments(this.getRegiments() - 1);
            ((Fighter)newFighter).addAllHelpingFighters(this.getWhoIsHelpingHim());
            ((Fighter)newFighter).setTileAttackedFrom(this.tileAttackedFrom);
            ((Fighter)newFighter).setwasAttacker(this.wasAttacker);
            tile.addFighter((Fighter)newFighter);
            this.getPiecePlayer().addPlayerPiece((Fighter)newFighter);
            ((Fighter)newFighter).setPiecePlayer(this.getPiecePlayer());
            tile.removeFighter(this);
            this.getPiecePlayer().removePlayerPiece(this);
            System.out.println(newFighter.toString());
            return (Fighter)newFighter;
        }
        System.out.println("I don't understand reflection");
        return null;
    }
}
