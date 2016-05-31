package First.Player;

import First.Piece.Piece;
import First.Stage;

import java.util.List;

public class GreenPlayer extends Player{
    GreenPlayer(List<Piece> playerPieces, Stage turnStage) {
        super(playerPieces, Alliance.GREEN, turnStage);
    }
}
