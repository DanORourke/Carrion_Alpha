package First.Piece;

import First.Player.Alliance;
import First.Board.Tile;

public class ChiefOfStaff extends Piece {

    public ChiefOfStaff(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance){
        super(qCoord, rCoord, sCoord, PieceType.CHIEFOFSTAFF, pieceAlliance);
    }
}
