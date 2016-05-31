package First.Piece.Fighters;

import First.Board.Tile;
import First.Piece.Fighter;
import First.Player.Alliance;

import java.io.Serializable;

public class Nurhaci extends Fighter implements Serializable {

    public Nurhaci(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, pieceAlliance);
    }

    @Override
    public String proDescription(){
        return "PRO: Can assist on defense from two spaces away.";
    }

    @Override
    public String conDescription(){
        return "CON: Any village Nurhaci captures has a 25% change of reverting to previous owner at the beginning of every turn.";
    }

    @Override
    public String toString() {
        return "Nurhaci";
    }

    @Override
    public boolean nurhaciHelp(Tile clickedTile){
        if (this.getIsExposed() && this.withinTwoOf(clickedTile)){
            return true;
        }
        return false;
    }

    private boolean withinTwoOf(Tile clickedTile){
        int thisQCoord = this.getPieceQCoord();
        int thisRCoord = this.getPieceRCoord();
        int thisSCoord = this.getPieceSCoord();

        int tileQCoord = clickedTile.getTileQCoord();
        int tileRCoord = clickedTile.getTileRCoord();
        int tileSCoord = clickedTile.getTileSCoord();

        int tileDistance  = Math.max(Math.abs(thisQCoord - tileQCoord),
                Math.max(Math.abs(thisRCoord - tileRCoord), Math.abs(thisSCoord - tileSCoord)));

        if (tileDistance <= 2){
            return true;
        }

        return false;
    }
}
