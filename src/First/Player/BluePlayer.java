package First.Player;

import First.Piece.Piece;
import First.Stage;

import java.util.List;

public class BluePlayer extends Player{
    BluePlayer(List<Piece> playerPieces, Stage turnStage) {
        super(playerPieces, Alliance.BLUE, turnStage);
    }
}
