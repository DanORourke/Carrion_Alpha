package First.Player;

import First.Board.Board;
import First.Board.Tile;
import First.Piece.*;
import First.Piece.Fighters.Askia;
import First.Piece.Fighters.Tecumseh;
import First.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class Player implements Serializable {

    private List<Piece> playerPieces;
    private Alliance playerAlliance;
    private Stage turnStage;

    public Player(final List<Piece> playerPieces, final Alliance playerAlliance, Stage turnStage){
        this.playerPieces = new ArrayList<>();
        //this.playerPieces.addAll(playerPieces);
        this.playerAlliance = playerAlliance;
        this.turnStage = turnStage;
    }

    public List<Piece> getPlayerPieces(){
        return this.playerPieces;
    }

    public void addPlayerPiece(Piece piece){
        this.playerPieces.add(piece);
    }

    public void removePlayerPiece(Piece piece){
        this.playerPieces.remove(piece);
    }

     public List<Fighter> getPlayerFighters(){
         List<Fighter> fighters = new ArrayList<>();
         for (Piece piece : this.getPlayerPieces()){
             if (piece.getClass().getSuperclass() == (Fighter.class)){
                 fighters.add((Fighter)piece);
             }
         }
         return fighters;
     }

    public boolean haveHiddenFighters(){
        for (Fighter fighter : this.getPlayerFighters()){
            if (!fighter.getIsExposed()){
                return true;
            }
        }
        return false;
    }

    public Alliance getPlayerAlliance(){
        return this.playerAlliance;
    }

    public Stage getTurnStage(){
        return this.turnStage;
    }

    public int progressTurnStage(Board gameBoard){
        if (this.turnStage == Stage.IDLE){
            if (this.haveHiddenFighters()){
                this.turnStage = Stage.EXPOSE;
                for (Fighter fighter : this.getPlayerFighters()){
                    fighter.allocateMethod();
                    if (fighter.isGhost()){
                        gameBoard.removePiece(fighter);
                    }
                }
                return 0;
            }else {
                this.turnStage = Stage.ALLOCATE;

                for (Piece piece : this.playerPieces){
                    if(piece.getClass() == Town.class){
                        ((Town) piece).resetHaveRegiment();
                        ((Town) piece).nurhaciMutiny(gameBoard);
                    }else if (piece.getClass() == Capitol.class){
                        ((Capitol) piece).resetRegiments();
                    }else if (piece.getClass().getSuperclass() == (Fighter.class)){
                        ((Fighter) piece).allocateMethod();
                        if (((Fighter)piece).isGhost()){
                            gameBoard.removePiece(piece);
                        }
                    }
                }
                return 1;
            }
        }

        if (this.turnStage == Stage.ALLOCATE){
            this.turnStage = Stage.MOVEFIGHTERS;
            for (Piece piece : this.playerPieces){
                if(piece.getClass().getSuperclass() == (Fighter.class)){
                    ((Fighter) piece).clearAssistance();
                }else if (piece.getClass() == Capitol.class){
                    ((Capitol) piece).clearAssisting();
                }else if (piece.getClass() == Town.class){
                    ((Town) piece).clearAssisting();
                }
            }
            for (Piece piece : gameBoard.getAllPlayerPieces()){
                if(piece.getClass().getSuperclass() == (Fighter.class)){
                    ((Fighter) piece).resetMovementPoints();
                    ((Fighter) piece).resetHasDroppedTown();
                    ((Fighter) piece).resetHasFought();
                }
            }
            return 0;


        }else if (this.turnStage == Stage.MOVEFIGHTERS){
            if ((this.isLegalPosition(gameBoard)) && (this.isBattleNeeded(gameBoard))){
                this.turnStage = Stage.BATTLE;
                for (Piece piece : gameBoard.getAllPlayerPieces()){
                    if(piece.getClass().getSuperclass() == (Fighter.class)){
                        ((Fighter) piece).resetHasDroppedTown();
                        ((Fighter) piece).resetHasFought();
                    }
                }
                return 0;

            }else if (!this.isLegalPosition(gameBoard)){
                return 1;

            }else if ((this.isLegalPosition(gameBoard)) && (!this.isBattleNeeded(gameBoard))){
                this.turnStage = Stage.SETASSIST;
                for (Piece piece : this.playerPieces){
                    if(piece.getClass().getSuperclass() == (Fighter.class)){
                        ((Fighter) piece).clearAssistance();
                    }else if (piece.getClass() == Capitol.class){
                        ((Capitol) piece).clearAssisting();
                    }else if (piece.getClass() == Town.class){
                        ((Town) piece).clearAssisting();
                    }
                }
                return 2;
            }else {
                System.out.println("progressTurnStage is acting funky");
                return 2;
            }

        }else if (this.turnStage == Stage.BATTLE){
            if (this.isBattleNeeded(gameBoard)){
                return 1;
            }else if (!this.isLegalPosition(gameBoard)){
                return 2;
            }else {
                if (gameBoard.getRevenge()) {
                    this.turnStage = Stage.MOVEFIGHTERS;
                    for (Piece piece : this.playerPieces){
                        if(piece.getClass().getSuperclass() == (Fighter.class)){
                            ((Fighter) piece).clearAssistance();
                        }else if (piece.getClass() == Capitol.class){
                            ((Capitol) piece).clearAssisting();
                        }else if (piece.getClass() == Town.class){
                            ((Town) piece).clearAssisting();
                        }
                    }
                    for (Piece piece : gameBoard.getAllPlayerPieces()){
                        if(piece.getClass().getSuperclass() == (Fighter.class)){
                            ((Fighter) piece).resetMovementPoints();
                            ((Fighter) piece).resetHasDroppedTown();
                            ((Fighter) piece).resetHasFought();
                        }
                    }
                    gameBoard.endRevenge();
                    return 3;

                } else
                    this.turnStage = Stage.SETASSIST;
                    for (Piece piece : this.playerPieces) {
                    if (piece.getClass().getSuperclass() == (Fighter.class)) {
                        ((Fighter) piece).clearAssistance();
                        ((Fighter) piece).resetIsFighting();
                    } else if (piece.getClass() == Capitol.class) {
                        ((Capitol) piece).clearAssisting();
                    } else if (piece.getClass() == Town.class) {
                        ((Town) piece).clearAssisting();
                    }
                }
                return 0;

            }

        }else if (this.turnStage == Stage.MOVECOS){
            this.turnStage = Stage.IDLE;
            for (Fighter fighter : this.getPlayerFighters()){
                fighter.idleMethod();
            }
            return 0;

        }else if (this.turnStage == Stage.SETASSIST){
            if (this.hasChiefOfStaff()){
                this.turnStage = Stage.MOVECOS;
                return 0;
            }else if (!this.hasChiefOfStaff()){
                this.turnStage = Stage.IDLE;
                for (Fighter fighter : this.getPlayerFighters()){
                    fighter.idleMethod();
                }
                return 1;
            }else{
                System.out.println("progressTurnStage weird");
                return 0;
            }

        }else if (this.turnStage == Stage.EXPOSE){
            this.turnStage = Stage.ALLOCATE;

            for (Piece piece : this.playerPieces){
                if(piece.getClass() == Town.class){
                    ((Town) piece).resetHaveRegiment();
                    ((Town) piece).nurhaciMutiny(gameBoard);
                }else if (piece.getClass() == Capitol.class){
                    ((Capitol) piece).resetRegiments();
                }
            }
            return 0;

        }else {
            return 0;
        }
    }

    private boolean hasChiefOfStaff() {
        if (this.pieceWithCos() != null) {
            return true;
        }
        return false;
    }


    public boolean isLegalPosition(Board gameBoard){
        List<Tile> playerTiles = playerTiles(gameBoard);
        for (Tile tile : playerTiles){
            if (!tile.getFighters().isEmpty() && (tile.getTown() != null) &&
                    ((tile.getTown().getPieceAlliance()) == (((Fighter)tile.getFighters().get(0)).getPieceAlliance())  ||
                    (tile.getTown().getPieceAlliance()) == Alliance.UNOCCUPIED)){
                return false;
            }else if ((tile.getFighters().size() >=2) &&
                    (((Fighter)tile.getFighters().get(0)).getPieceAlliance() == ((Fighter)tile.getFighters().get(1)).getPieceAlliance())){
                return false;
            }
        }
        return true;
    }

    public boolean isBattleNeeded(Board gameBoard){
        List<Tile> playerTiles = playerTiles(gameBoard);
        for (Tile tile : playerTiles){
            if (!tile.getFighters().isEmpty() && (tile.getTown() != null) &&
                    (tile.getTown().getPieceAlliance() != ((Fighter)tile.getFighters().get(0)).getPieceAlliance())){
                return true;
            }else if ((tile.getFighters().size() >=2) &&
                    (((Fighter)tile.getFighters().get(0)).getPieceAlliance() != ((Fighter)tile.getFighters().get(1)).getPieceAlliance())){
                return true;
            }else if (!tile.getFighters().isEmpty() && (tile.getCapitol() != null) &&
                    (tile.getCapitol().getPieceAlliance() != ((Fighter)tile.getFighters().get(0)).getPieceAlliance())){
                return true;
            }
        }
        return false;
    }

    public List<Tile> playerTiles(Board gameBoard){
        List<Tile> playerTiles = new ArrayList<>();
        for (Piece piece : this.playerPieces){
            Tile tile = gameBoard.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord());
            playerTiles.add(tile);
        }
        return playerTiles;
    }

    public void setDefenseStage(){
        this.turnStage = Stage.SETASSIST;
    }

    public boolean areNeighbors(Piece pieceOne, Piece pieceTwo){
        if (((pieceOne.getPieceQCoord() == pieceTwo.getPieceQCoord()) &&
                ((pieceOne.getPieceRCoord() + 1) == pieceTwo.getPieceRCoord()) &&
                ((pieceOne.getPieceSCoord() - 1) == pieceTwo.getPieceSCoord())) ||
                ((pieceOne.getPieceQCoord() == pieceTwo.getPieceQCoord()) &&
                        ((pieceOne.getPieceRCoord() - 1) == pieceTwo.getPieceRCoord()) &&
                        ((pieceOne.getPieceSCoord() + 1) == pieceTwo.getPieceSCoord())) ||
                (((pieceOne.getPieceQCoord() + 1) == pieceTwo.getPieceQCoord()) &&
                        ((pieceOne.getPieceRCoord() - 1) == pieceTwo.getPieceRCoord()) &&
                        ((pieceOne.getPieceSCoord()) == pieceTwo.getPieceSCoord())) ||
                (((pieceOne.getPieceQCoord() - 1) == pieceTwo.getPieceQCoord()) &&
                        ((pieceOne.getPieceRCoord() + 1) == pieceTwo.getPieceRCoord()) &&
                        ((pieceOne.getPieceSCoord()) == pieceTwo.getPieceSCoord())) ||
                (((pieceOne.getPieceQCoord() - 1) == pieceTwo.getPieceQCoord()) &&
                        ((pieceOne.getPieceRCoord()) == pieceTwo.getPieceRCoord()) &&
                        ((pieceOne.getPieceSCoord() + 1) == pieceTwo.getPieceSCoord())) ||
                (((pieceOne.getPieceQCoord() + 1) == pieceTwo.getPieceQCoord()) &&
                        ((pieceOne.getPieceRCoord()) == pieceTwo.getPieceRCoord()) &&
                        ((pieceOne.getPieceSCoord() - 1) == pieceTwo.getPieceSCoord()))){
            return true;
        }else {
            return false;
        }
    }

    private List<Piece> connectedFullTowns(Fighter fighter){
        List<Piece> connectedPieces = new ArrayList<>();
        for (Piece piece : this.playerPieces) {
            //System.out.println("for loop");

            if (((piece.getClass() == Town.class) && ((Town) piece).getHaveRegiment()) ||
                    ((piece.getClass() == Capitol.class) && (((Capitol) piece).getRegiments() >= 1))) {
                List<Piece> frontier = new ArrayList<>();
                List<Piece> visited = new ArrayList<>();
                visited.add(piece);
                frontier.add(piece);
                //System.out.println("new lists");

                while (!frontier.isEmpty()) {
                    Piece current = frontier.get(frontier.size()-1);
                    //System.out.println("while loop " + (frontier.size()-1));

                    for (Piece fromBigBag : this.playerPieces){
                        //System.out.println("new big bag");
                        if ((!inVisited(visited, fromBigBag))&&(areNeighbors(fromBigBag, current)) ) {
                            //System.out.println("not visited & neighbor, add to frontier");
                            frontier.add(fromBigBag);
                        }
                    }
                    visited.add(current);
                    frontier.remove(current);

                }
                if (inVisited(visited, fighter)){
                    connectedPieces.add(piece);
                }
            }
        }
        return connectedPieces;
    }
    private List<Piece> connectedTowns(Fighter fighter){
        List<Piece> connectedPieces = new ArrayList<>();
        for (Piece piece : this.playerPieces) {
            //System.out.println("for loop");

            if ((piece.getClass() == Town.class) ||
                    (piece.getClass() == Capitol.class)) {
                List<Piece> frontier = new ArrayList<>();
                List<Piece> visited = new ArrayList<>();
                visited.add(piece);
                frontier.add(piece);
                //System.out.println("new lists");

                while (!frontier.isEmpty()) {
                    Piece current = frontier.get(frontier.size()-1);
                    //System.out.println("while loop " + (frontier.size()-1));

                    for (Piece fromBigBag : this.playerPieces){
                        //System.out.println("new big bag");
                        if ((!inVisited(visited, fromBigBag))&&(areNeighbors(fromBigBag, current)) ) {
                            //System.out.println("not visited & neighbor, add to frontier");
                            frontier.add(fromBigBag);
                        }
                    }
                    visited.add(current);
                    frontier.remove(current);

                }
                if (inVisited(visited, fighter)){
                    connectedPieces.add(piece);
                }
            }
        }
        return connectedPieces;
    }

    private boolean inVisited(List<Piece> visited, Piece piece){
        for (Piece option : visited){
            if ((option.getPieceQCoord() == piece.getPieceQCoord()) &&
                    (option.getPieceRCoord() == piece.getPieceRCoord()) &&
                    (option.getPieceSCoord() == piece.getPieceSCoord())){
                return true;
            }

        }
        //System.out.println("inVisited is funky");
        return false;
    }



    public boolean isFighterConnected(Fighter fighter){
        if (this.connectedFullTowns(fighter).isEmpty()){
            System.out.println("isFighterConnected false");
            return false;
        }else if (!this.connectedFullTowns(fighter).isEmpty()){
            System.out.println("isFighterConnected true");
            return true;
        }else{
            System.out.println("isFighterConnected is acting strangely");
            return true;
        }
    }

    public boolean isFighterConnectedDrop(Fighter fighter){
        if (this.connectedTowns(fighter).isEmpty()){
            System.out.println("isFighterConnected false");
            return false;
        }else if (!this.connectedTowns(fighter).isEmpty()){
            System.out.println("isFighterConnected true");
            return true;
        }else if (fighter.getClass() == Tecumseh.class && !fighter.hasChiefOfStaff() && fighter.getIsExposed()){
            if (fighter.getRegiments() <= 19){
                return false;
            }
            return true;
        }else{
            System.out.println("isFighterConnected is acting strangely");
            return true;
        }
    }

    public void takeRegiment(Fighter fighter){
        if (this.connectedFullTowns(fighter).get(0).getClass() == Town.class){
            ((Town) this.connectedFullTowns(fighter).get(0)).takeHaveRegiment();
        }else if (this.connectedFullTowns(fighter).get(0).getClass() == Capitol.class){
            ((Capitol) this.connectedFullTowns(fighter).get(0)).takeRegiment();
        }
    }

    public int getRegimentsLeft(Fighter fighter){
        int regimentsLeft = 0;
        if (!this.isFighterConnected(fighter)){
            return 0;
        }else {
            for (Piece piece : connectedTowns(fighter)){
                if (((piece.getClass() == Town.class) && ((Town) piece).getHaveRegiment())){
                    regimentsLeft = regimentsLeft + 1;
                }else if (((piece.getClass() == Capitol.class) && (((Capitol) piece).getRegiments() >= 1))){
                    regimentsLeft = regimentsLeft + ((Capitol) piece).getRegiments();
                }
            }
        }
        return regimentsLeft;
    }

    public Piece pieceWithCos(){
        for (Piece piece : this.playerPieces){
            if (piece.getClass().getSuperclass() == (Fighter.class)){
                if (((Fighter)piece).hasChiefOfStaff()){
                    return piece;
                }
            }else if (piece.getClass() == Capitol.class) {
                if (((Capitol) piece).hasChiefOfStaff()) {
                    return piece;
                }
            }
        }
        System.out.println("no Chief of Staff");
        return null;
    }

    public boolean isChiefConnected(Tile clickedTile) {

        List<Piece> frontier = new ArrayList<>();
        List<Piece> visited = new ArrayList<>();
        visited.add(this.pieceWithCos());
        frontier.add(this.pieceWithCos());
        //System.out.println("new lists");

        while (!frontier.isEmpty()) {
            Piece current = frontier.get(frontier.size()-1);
            //System.out.println("while loop " + (frontier.size()-1));

            for (Piece fromBigBag : this.playerPieces){
                System.out.println("new big bag");
                if ((!inVisited(visited, fromBigBag))&&(areNeighbors(fromBigBag, current)) ) {
                    //System.out.println("not visited & neighbor, add to frontier");
                    frontier.add(fromBigBag);
                }
            }
            visited.add(current);
            frontier.remove(current);

        }

        if (clickedTile.getCapitol() != null){

            if (inVisited(visited, clickedTile.getCapitol())){
                return true;
            }else {
                return false;
            }
        }else if (!clickedTile.getFighters().isEmpty()){

            if (inVisited(visited, (Fighter)clickedTile.getFighters().get(0))){
                return true;
            }else {
                return false;
            }
        }
        System.out.println("chief connected is weird");
        return false;
    }

    public void giveChiefOfStaff(Piece piece) {
        if (piece.getClass().getSuperclass() == (Fighter.class)){

            for (Piece allPieces : this.playerPieces){
                if (allPieces.getClass().getSuperclass() == Fighter.class){
                    ((Fighter)allPieces).removeChiefOfStaff();
                }else if (allPieces.getClass() == Capitol.class){
                    ((Capitol)allPieces).removeChiefofStaff();
                }
            }

            ((Fighter)piece).giveChiefOfStaff();

        }else if (piece.getClass() == Capitol.class){

            for (Piece allPieces : this.playerPieces){
                if (allPieces.getClass().getSuperclass() == Fighter.class) {
                    ((Fighter) allPieces).removeChiefOfStaff();
                }
            }

            ((Capitol)piece).giveChiefofStaff();

        }
    }

    public boolean exposedAskia(){
        for (Fighter fighter : this.getPlayerFighters()){
            if (fighter.getClass() == Askia.class && !fighter.hasChiefOfStaff() && fighter.getIsExposed()){
                return true;
            }
        }
        return false;
    }

    public void exposeFighters(){
        for (Fighter fighter : this.getPlayerFighters()){
            fighter.exposeFighter();
        }
    }

    public List<Piece> getMainPieces(){
        List <Piece> mainPieces = new ArrayList<>();
        for (Piece piece : this.getPlayerPieces()){
            if (piece.getPieceType() != Piece.PieceType.SUPPLYLINE){
                mainPieces.add(piece);
            }
        }
        return mainPieces;
    }
}
