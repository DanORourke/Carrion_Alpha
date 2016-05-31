package First.Piece;

import First.Player.Alliance;
import First.Board.Tile;

import java.io.Serializable;

public class SupplyLine extends Piece implements Serializable {

    public SupplyLine(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance){
        super(qCoord, rCoord, sCoord, PieceType.SUPPLYLINE, pieceAlliance);
    }
}
