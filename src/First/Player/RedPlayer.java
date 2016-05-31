package First.Player;

import First.Board.Board;
import First.Piece.Piece;
import First.Stage;

import java.util.List;

public class RedPlayer extends Player{

    public RedPlayer(List<Piece> playerPieces, Stage turnStage) {
        super(playerPieces, Alliance.RED, turnStage);
    }

}
