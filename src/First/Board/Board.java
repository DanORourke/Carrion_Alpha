package First.Board;

import First.Piece.*;
import First.Piece.Fighters.*;
import First.Player.*;
import First.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Serializable {
    private List<Tile> gameBoard;
    private List<Player> players;
    private Player redPlayer;
    private Player greenPlayer;
    private Player bluePlayer;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player yellowPlayer;
    private final int mapRadius;
    private Player activePlayer;
    private List<Fighter> allFighters;
    private boolean revenge;
    private boolean justFought;

    public Board() {
        this.mapRadius = 21;
        this.gameBoard = new ArrayList<>();
        this.players = new ArrayList<>();
        this.allFighters = new ArrayList<>();
        this.revenge = false;
        this.justFought = false;
        createBoard();
        createAllFighters();
        setRedPlayer();
        setYellowPlayer();
        setGreenPlayer();
        setWhitePlayer();
        setBluePlayer();
        setBlackPlayer();
        setUnoccupiedTowns();
        setFirstPlayer(redPlayer);
    }

    public Board(int numberOfPlayers){
        this.mapRadius = 21;
        this.gameBoard = new ArrayList<>();
        this.players = new ArrayList<>();
        this.allFighters = new ArrayList<>();
        this.revenge = false;
        this.justFought = false;
        createBoard();
        createAllFighters();
        setUnoccupiedTowns();
        setPlayers(numberOfPlayers);
    }

    private void setPlayers(int numberOfPlayers){
        if (numberOfPlayers == 2){
            setRedPlayer();
            setWhitePlayer();
            setFirstPlayer(redPlayer);

        }else if (numberOfPlayers == 3){
            setRedPlayer();
            setGreenPlayer();
            setBluePlayer();
            setFirstPlayer(redPlayer);

        }else if (numberOfPlayers == 4){
            setYellowPlayer();
            setGreenPlayer();
            setBluePlayer();
            setBlackPlayer();
            setFirstPlayer(yellowPlayer);

        }else if (numberOfPlayers == 5){
            setRedPlayer();
            setYellowPlayer();
            setGreenPlayer();
            setBluePlayer();
            setBlackPlayer();
            setFirstPlayer(redPlayer);

        }else if (numberOfPlayers == 6){
            setRedPlayer();
            setYellowPlayer();
            setGreenPlayer();
            setWhitePlayer();
            setBluePlayer();
            setBlackPlayer();
            setFirstPlayer(redPlayer);

        }else if (numberOfPlayers == 7){
            setRedPlayer();
            setYellowPlayer();
            setFirstPlayer(redPlayer);
        }else if (numberOfPlayers == 8){
            setRedPlayer();
            setGreenPlayer();
            setFirstPlayer(redPlayer);
        }
    }

    public void setRevenge(Boolean revenge) {
        this.revenge = revenge;
    }

    public boolean getRevenge() {
        return this.revenge;
    }

    public void endRevenge() {
        this.revenge = false;
    }

    public void setFirstPlayer(Player player) {
        this.activePlayer = player;
        player.progressTurnStage(this);
    }

    public List getPlayers() {
        return this.players;
    }


    private void createBoard() {

        Tile middleTile = new Tile(0, 0, 0);
        this.gameBoard.add(middleTile);
        System.out.println("made middle tile");

        for (int i = 1; i < mapRadius; i++) {
            Tile cornerTile1 = new Tile(i, -i, 0);

            Tile cornerTile2 = new Tile(-i, i, 0);

            Tile cornerTile3 = new Tile(i, 0, -i);

            Tile cornerTile4 = new Tile(-i, 0, i);

            Tile cornerTile5 = new Tile(0, i, -i);

            Tile cornerTile6 = new Tile(0, -i, i);

            this.gameBoard.add(cornerTile1);
            this.gameBoard.add(cornerTile2);
            this.gameBoard.add(cornerTile3);
            this.gameBoard.add(cornerTile4);
            this.gameBoard.add(cornerTile5);
            this.gameBoard.add(cornerTile6);

            int k;
            for (k = 1; k < i; k++) {
                Tile fillTile1 = new Tile(-i, k, (i - k));

                Tile fillTile2 = new Tile(i, -k, -(i - k));

                Tile fillTile3 = new Tile(k, -i, (i - k));

                Tile fillTile4 = new Tile(-k, i, -(i - k));

                Tile fillTile5 = new Tile(-k, -(i - k), i);

                Tile fillTile6 = new Tile(k, (i - k), -i);

                this.gameBoard.add(fillTile1);
                this.gameBoard.add(fillTile2);
                this.gameBoard.add(fillTile3);
                this.gameBoard.add(fillTile4);
                this.gameBoard.add(fillTile5);
                this.gameBoard.add(fillTile6);
                //System.out.println("made fill tiles");

            }
        }
        System.out.println(this.gameBoard.size());
    }

    public Tile getTile(int qCoord, int rCoord, int sCoord) {
        //System.out.println("tile requested =>" + " qcoord = " + qCoord + " rcoord = " + rCoord + " scoord = " + sCoord);
        for (Tile tile : this.gameBoard) {
            //System.out.println("qcoord = " + tile.getTileQCoord());
            //System.out.println("rcoord = " + tile.getTileRCoord());
            //System.out.println("scoord = " + tile.getTileSCoord());
            if ((tile.getTileQCoord() == qCoord) && (tile.getTileRCoord() == rCoord) && (tile.getTileSCoord() == sCoord)) {
                //System.out.println("tile returned =>" + " qcoord = " + tile.getTileQCoord() + " rcoord = " + tile.getTileRCoord() + " scoord = " + tile.getTileSCoord());
                return tile;
            }
        }
        System.out.println("getTile cant find the tile and returned null");
        return null;
    }

    public List<Tile> getPlayerTiles(Player player) {
        List<Tile> playerTiles = new ArrayList<>();
        for (Tile tile : this.gameBoard) {
            for (Piece piece : tile.getPieces()) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    playerTiles.add(tile);
                    break;
                } else {
                    System.out.println("not a valid coordinate");
                    return null;
                }
            }
        }
        return playerTiles;
    }

    public List<Tile> getTiles() {
        return this.gameBoard;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public Player getPlayer(Alliance alliance) {
        for (Player player : this.players) {
            if (player.getPlayerAlliance() == alliance) {
                return player;
            }
        }
        System.out.println("getPlayer acting up");
        return null;
    }

    public void removePlayer(Alliance alliance) {
        Player player = this.getPlayer(alliance);
        List<Piece> deadPieces = new ArrayList<>();
        for (Piece piece : player.getPlayerPieces()) {
            if (piece.getPieceType() == Piece.PieceType.TOWN) {
                piece.setPieceAlliance(Alliance.UNOCCUPIED);
            } else if (piece.getPieceType() == Piece.PieceType.CAPITOL) {
                deadPieces.add(piece);
                this.setPiece(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord(), Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
            } else if (piece.getPieceType() == Piece.PieceType.FIGHTER) {
                piece.setPieceAlliance(Alliance.UNOCCUPIED);
                ((Fighter)piece).removeChiefOfStaff();
            } else {
                deadPieces.add(piece);
            }
        }
        for (Piece dead : deadPieces) {
            this.removePiece(dead);
        }
        this.players.remove(player);
    }

    public List<Piece> getAllPlayerPieces() {
        List<Piece> allPieces = new ArrayList<>();
        for (Player player : this.players) {
            for (Piece piece : player.getPlayerPieces()) {
                allPieces.add(piece);
            }
        }
        return allPieces;
    }

    public List<Fighter> getAllFighters() {
        List<Fighter> allFighters = new ArrayList<>();
        for (Tile tile : this.gameBoard) {
            allFighters.addAll(tile.getFighters());
        }
        return allFighters;
    }

    public void removeOtherFighters() {
        for (Piece piece : this.getAllFighters()) {
            //System.out.println("removeOtherFighters");
            if ((piece.getPieceAlliance() == Alliance.UNOCCUPIED)) {
                this.removePiece(piece);
                System.out.println("removeOtherFighters2");
            }
        }
    }

    public void removePiece(Piece piece) {

        if (piece.getPieceType() == Piece.PieceType.CAPITOL) {
            this.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord()).removeCapitol();
            for (Player player : this.players) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    player.removePlayerPiece(piece);
                }
            }

        } else if (piece.getPieceType() == Piece.PieceType.TOWN) {
            this.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord()).removeTown();
            for (Player player : this.players) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    player.removePlayerPiece(piece);
                }
            }

        } else if (piece.getPieceType() == Piece.PieceType.SUPPLYLINE) {
            this.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord()).removeSupplyLine();
            for (Player player : this.players) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    player.removePlayerPiece(piece);
                }
            }

        } else if (piece.getPieceType() == Piece.PieceType.FIGHTER) {
            this.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord()).removeFighter((Fighter) piece);
            for (Player player : this.players) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    player.removePlayerPiece(piece);
                }
            }


        } else if (piece.getPieceType() == Piece.PieceType.CHIEFOFSTAFF) {
            this.getTile(piece.getPieceQCoord(), piece.getPieceRCoord(), piece.getPieceSCoord()).removeChiefOfStaff();
            for (Player player : this.players) {
                if (piece.getPieceAlliance() == player.getPlayerAlliance()) {
                    player.removePlayerPiece(piece);
                }
            }


        } else {
            System.out.println("not a recognized piece type");
        }
    }

    public void setPiece(int qCoord, int rCoord, int sCoord, Alliance alliance, Piece.PieceType pieceType) {

        if (pieceType == Piece.PieceType.CAPITOL) {
            Capitol capitol = new Capitol(qCoord, rCoord, sCoord, alliance);
            this.getTile(qCoord, rCoord, sCoord).setCapitol(capitol);
            for (Player player : this.players) {
                if (alliance == player.getPlayerAlliance()) {
                    player.addPlayerPiece(capitol);
                    capitol.setPiecePlayer(player);
                }
            }

        } else if (pieceType == Piece.PieceType.TOWN) {
            Town town = new Town(qCoord, rCoord, sCoord, alliance);
            this.getTile(qCoord, rCoord, sCoord).setTown(town);
            for (Player player : this.players) {
                if (alliance == player.getPlayerAlliance()) {
                    player.addPlayerPiece(town);
                    town.setPiecePlayer(player);

                }
            }

        } else if (pieceType == Piece.PieceType.SUPPLYLINE) {
            SupplyLine supplyLine = new SupplyLine(qCoord, rCoord, sCoord, alliance);
            this.getTile(qCoord, rCoord, sCoord).setSupplyLine(supplyLine);
            for (Player player : this.players) {
                if (alliance == player.getPlayerAlliance()) {
                    player.addPlayerPiece(supplyLine);
                    supplyLine.setPiecePlayer(player);

                }
            }

        } else if (pieceType == Piece.PieceType.FIGHTER) {


        } else if (pieceType == Piece.PieceType.CHIEFOFSTAFF) {
            ChiefOfStaff chiefOfStaff = new ChiefOfStaff(qCoord, rCoord, sCoord, alliance);
            this.getTile(qCoord, rCoord, sCoord).setChiefOfStaff(chiefOfStaff);
            if (this.getTile(qCoord, rCoord, sCoord).getCapitol() != null) {
                this.getTile(qCoord, rCoord, sCoord).getCapitol().giveChiefofStaff();
            }
            if (!this.getTile(qCoord, rCoord, sCoord).getFighters().isEmpty()) {
                ((Fighter) this.getTile(qCoord, rCoord, sCoord).getFighters().get(0)).giveChiefOfStaff();
            }
            for (Player player : this.players) {
                if (alliance == player.getPlayerAlliance()) {
                    player.addPlayerPiece(chiefOfStaff);
                }
            }

        } else {
            System.out.println("not a recognized piece type");
        }
    }

    public void progressActivePlayer() {
        int index = this.players.indexOf(activePlayer);
        int length = this.players.size();
        if (index + 1 == length) {
            this.activePlayer = this.players.get(0);
        } else {
            this.activePlayer = this.players.get(index + 1);
        }
    }

    private Fighter createRandomFighter() {
        int numberOfFighters = 30;
        Random random = new Random();
        int randomFighter = random.nextInt(numberOfFighters) + 1;
        if (randomFighter == 1) {
            return new Alexander(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 2) {
            return new Fredrick(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 3) {
            return new Hannibal(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 4) {
            return new Napoleon(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 5) {
            return new Ramses(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 6) {
            return new Oda(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 7) {
            return new Acampachitli(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 8) {
            return new Ashoka(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 9) {
            return new Pachacuti(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 10) {
            return new Charlemagne(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 11) {
            return new Qin(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 12) {
            return new Tecumseh(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 13) {
            return new Leonidas(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 14) {
            return new Shaka(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 15) {
            return new Ardashir(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 16) {
            return new Gim(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 17) {
            return new Washington(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 18) {
            return new Cyrus(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 19) {
            return new Wellington(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 20) {
            return new Askia(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 21) {
            return new Ragnar(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 22) {
            return new Saladin(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 23) {
            return new Sherman(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 24) {
            return new Musa(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 25) {
            return new Genghis(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 26) {
            return new Shivaji(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 27) {
            return new SunTzu(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 28) {
            return new Osman(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 29) {
            return new Sargon(0, 0, 0, Alliance.UNOCCUPIED);
        } else if (randomFighter == 30) {
            return new Nurhaci(0, 0, 0, Alliance.UNOCCUPIED);
        } else {
            System.out.println("createAllPossibleFighters is wacky");
            return null;
        }
    }

    private void assignRandomFighter(int qCoord, int rCoord, int sCoord, Alliance alliance) {
        Fighter fighter = this.createRandomFighter();
        fighter.setNewCoordinates(qCoord, rCoord, sCoord);
        fighter.setPieceAlliance(alliance);
        this.getTile(qCoord, rCoord, sCoord).addFighter(fighter);
        for (Player player : this.players) {
            if (alliance == player.getPlayerAlliance()) {
                player.addPlayerPiece(fighter);
                fighter.setPiecePlayer(player);
            }
        }
    }

    public void createAllFighters() {
        this.allFighters.clear();
        this.allFighters.add(new Alexander(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Fredrick(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Hannibal(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Napoleon(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Ramses(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Oda(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Acampachitli(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Ashoka(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Pachacuti(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Charlemagne(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Qin(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Tecumseh(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Leonidas(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Shaka(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Ardashir(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Gim(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Washington(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Cyrus(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Wellington(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Askia(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Ragnar(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Saladin(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Sherman(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Musa(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Genghis(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Shivaji(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new SunTzu(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Osman(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Sargon(0, 0, 0, Alliance.UNOCCUPIED));
        this.allFighters.add(new Nurhaci(0, 0, 0, Alliance.UNOCCUPIED));

    }

    private void randomlyAssignFighter(int qCoord, int rCoord, int sCoord, Alliance alliance) {

        Fighter randomlyAssignedFighter = null;

        if (!this.allFighters.isEmpty()) {
            Random random = new Random();
            int pickOne = random.nextInt(this.allFighters.size());
            randomlyAssignedFighter = this.allFighters.get(pickOne);
            this.allFighters.remove(pickOne);
        } else {
            randomlyAssignedFighter = this.createRandomFighter();
        }

        randomlyAssignedFighter.setNewCoordinates(qCoord, rCoord, sCoord);
        randomlyAssignedFighter.setPieceAlliance(alliance);
        this.getTile(qCoord, rCoord, sCoord).addFighter(randomlyAssignedFighter);
        for (Player player : this.players) {
            if (alliance == player.getPlayerAlliance()) {
                player.addPlayerPiece(randomlyAssignedFighter);
                randomlyAssignedFighter.setPiecePlayer(player);
            }
        }
    }

    public Fighter setGhost(int QCoord, int RCoord, int SCoord, Alliance alliance){
        Fighter ghost = new SunTzu(QCoord, RCoord, SCoord, alliance, false);
        this.allFighters.add(ghost);
        this.getTile(QCoord, RCoord, SCoord).addFighter(ghost);
        for (Player player : this.players) {
            if (alliance == player.getPlayerAlliance()) {
                player.addPlayerPiece(ghost);
                ghost.setPiecePlayer(player);
            }
        }
        return ghost;
    }

    private void setUnoccupiedTowns() {
        this.setPiece(-4, -4, 8, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(-8, 4, 4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(-4, 0, 4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(0, -4, 4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(4, -8, 4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(-4, 4, 0, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(4, -4, 0, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(-4, 8, -4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(0, 4, -4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(4, 0, -4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(8, -4, -4, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
        this.setPiece(4, 4, -8, Alliance.UNOCCUPIED, Piece.PieceType.TOWN);
    }

    private void setRedPlayer() {
        this.redPlayer = new Player(null, Alliance.RED, Stage.IDLE);
        this.players.add(redPlayer);
        this.setPiece(8, 8, -16, Alliance.RED, Piece.PieceType.CAPITOL);
        this.setPiece(7, 7, -14, Alliance.RED, Piece.PieceType.TOWN);
        this.setPiece(9, 9, -18, Alliance.RED, Piece.PieceType.TOWN);
        this.setPiece(5, 11, -16, Alliance.RED, Piece.PieceType.TOWN);
        this.setPiece(11, 5, -16, Alliance.RED, Piece.PieceType.TOWN);
        this.setPiece(6, 10, -16, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, 9, -16, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(9, 7, -16, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(10, 6, -16, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(8, 9, -17, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(9, 8, -17, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, 8, -15, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(8, 7, -15, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(6, 7, -13, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, 6, -13, Alliance.RED, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(8, 4, -12, Alliance.RED);
        this.randomlyAssignFighter(7, 5, -12, Alliance.RED);
        this.randomlyAssignFighter(6, 6, -12, Alliance.RED);
        this.randomlyAssignFighter(5, 7, -12, Alliance.RED);
        this.randomlyAssignFighter(4, 8, -12, Alliance.RED);
        //this.setPiece(4, 8, -12, Alliance.RED, Piece.PieceType.FIGHTER);
        //this.setPiece(5, 7, -12, Alliance.RED, Piece.PieceType.FIGHTER);
        //this.setPiece(6, 6, -12, Alliance.RED, Piece.PieceType.FIGHTER);
        //this.setPiece(7, 5, -12, Alliance.RED, Piece.PieceType.FIGHTER);
        //this.setPiece(8, 4, -12, Alliance.RED, Piece.PieceType.FIGHTER);
        //this.setPiece(8, 8, -16, Alliance.RED, Piece.PieceType.CHIEFOFSTAFF);
    }

    private void setBluePlayer() {
        this.bluePlayer = new Player(null, Alliance.BLUE, Stage.IDLE);
        this.players.add(bluePlayer);
        this.setPiece(8, -16, 8, Alliance.BLUE, Piece.PieceType.CAPITOL);
        this.setPiece(7, -14, 7, Alliance.BLUE, Piece.PieceType.TOWN);
        this.setPiece(9, -18, 9, Alliance.BLUE, Piece.PieceType.TOWN);
        this.setPiece(5, -16, 11, Alliance.BLUE, Piece.PieceType.TOWN);
        this.setPiece(11, -16, 5, Alliance.BLUE, Piece.PieceType.TOWN);
        this.setPiece(6, -16, 10, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, -16, 9, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(9, -16, 7, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(10, -16, 6, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(8, -17, 9, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(9, -17, 8, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, -15, 8, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(8, -15, 7, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(6, -13, 7, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(7, -13, 6, Alliance.BLUE, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(4, -12, 8, Alliance.BLUE);
        this.randomlyAssignFighter(5, -12, 7, Alliance.BLUE);
        this.randomlyAssignFighter(6, -12, 6, Alliance.BLUE);
        this.randomlyAssignFighter(7, -12, 5, Alliance.BLUE);
        this.randomlyAssignFighter(8, -12, 4, Alliance.BLUE);
        //this.setPiece(4, -12, 8, Alliance.BLUE, Piece.PieceType.FIGHTER);
        //this.setPiece(5, -12, 7, Alliance.BLUE, Piece.PieceType.FIGHTER);
        //this.setPiece(6, -12, 6, Alliance.BLUE, Piece.PieceType.FIGHTER);
        //this.setPiece(7, -12, 5, Alliance.BLUE, Piece.PieceType.FIGHTER);
        //this.setPiece(8, -12, 4, Alliance.BLUE, Piece.PieceType.FIGHTER);
        //this.setPiece(8, -16, 8, Alliance.BLUE, Piece.PieceType.CHIEFOFSTAFF);
    }

    private void setGreenPlayer() {
        this.greenPlayer = new Player(null, Alliance.GREEN, Stage.IDLE);
        this.players.add(greenPlayer);
        this.setPiece(-16, 8, 8, Alliance.GREEN, Piece.PieceType.CAPITOL);
        this.setPiece(-14, 7, 7, Alliance.GREEN, Piece.PieceType.TOWN);
        this.setPiece(-18, 9, 9, Alliance.GREEN, Piece.PieceType.TOWN);
        this.setPiece(-16, 11, 5, Alliance.GREEN, Piece.PieceType.TOWN);
        this.setPiece(-16, 5, 11, Alliance.GREEN, Piece.PieceType.TOWN);
        this.setPiece(-16, 10, 6, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-16, 9, 7, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-16, 7, 9, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-16, 6, 10, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-17, 9, 8, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-17, 8, 9, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-15, 8, 7, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-15, 7, 8, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-13, 7, 6, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-13, 6, 7, Alliance.GREEN, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(-12, 8, 4, Alliance.GREEN);
        this.randomlyAssignFighter(-12, 7, 5, Alliance.GREEN);
        this.randomlyAssignFighter(-12, 6, 6, Alliance.GREEN);
        this.randomlyAssignFighter(-12, 5, 7, Alliance.GREEN);
        this.randomlyAssignFighter(-12, 4, 8, Alliance.GREEN);
        //this.setPiece(-12, 8, 4, Alliance.GREEN, Piece.PieceType.FIGHTER);
        //this.setPiece(-12, 7, 5, Alliance.GREEN, Piece.PieceType.FIGHTER);
        //this.setPiece(-12, 6, 6, Alliance.GREEN, Piece.PieceType.FIGHTER);
        //this.setPiece(-12, 5, 7, Alliance.GREEN, Piece.PieceType.FIGHTER);
        //this.setPiece(-12, 4, 8, Alliance.GREEN, Piece.PieceType.FIGHTER);
        //this.setPiece(-16, 8, 8, Alliance.GREEN, Piece.PieceType.CHIEFOFSTAFF);
    }

    private void setBlackPlayer() {
        this.blackPlayer = new Player(null, Alliance.BLACK, Stage.IDLE);
        this.players.add(blackPlayer);
        this.setPiece(16, -8, -8, Alliance.BLACK, Piece.PieceType.CAPITOL);
        this.setPiece(14, -7, -7, Alliance.BLACK, Piece.PieceType.TOWN);
        this.setPiece(18, -9, -9, Alliance.BLACK, Piece.PieceType.TOWN);
        this.setPiece(16, -11, -5, Alliance.BLACK, Piece.PieceType.TOWN);
        this.setPiece(16, -5, -11, Alliance.BLACK, Piece.PieceType.TOWN);
        this.setPiece(16, -6, -10, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(16, -7, -9, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(16, -9, -7, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(16, -10, -6, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(17, -8, -9, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(17, -9, -8, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(15, -7, -8, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(15, -8, -7, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(13, -6, -7, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.setPiece(13, -7, -6, Alliance.BLACK, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(12, -4, -8, Alliance.BLACK);
        this.randomlyAssignFighter(12, -5, -7, Alliance.BLACK);
        this.randomlyAssignFighter(12, -6, -6, Alliance.BLACK);
        this.randomlyAssignFighter(12, -7, -5, Alliance.BLACK);
        this.randomlyAssignFighter(12, -8, -4, Alliance.BLACK);
    }

    private void setWhitePlayer() {
        this.whitePlayer = new Player(null, Alliance.WHITE, Stage.IDLE);
        this.players.add(whitePlayer);
        this.setPiece(-8, -8, 16, Alliance.WHITE, Piece.PieceType.CAPITOL);
        this.setPiece(-7, -7, 14, Alliance.WHITE, Piece.PieceType.TOWN);
        this.setPiece(-9, -9, 18, Alliance.WHITE, Piece.PieceType.TOWN);
        this.setPiece(-5, -11, 16, Alliance.WHITE, Piece.PieceType.TOWN);
        this.setPiece(-11, -5, 16, Alliance.WHITE, Piece.PieceType.TOWN);
        this.setPiece(-6, -10, 16, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, -9, 16, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-9, -7, 16, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-10, -6, 16, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-8, -9, 17, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-9, -8, 17, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, -8, 15, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-8, -7, 15, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-6, -7, 13, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, -6, 13, Alliance.WHITE, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(-4, -8, 12, Alliance.WHITE);
        this.randomlyAssignFighter(-5, -7, 12, Alliance.WHITE);
        this.randomlyAssignFighter(-6, -6, 12, Alliance.WHITE);
        this.randomlyAssignFighter(-7, -5, 12, Alliance.WHITE);
        this.randomlyAssignFighter(-8, -4, 12, Alliance.WHITE);
    }

    private void setYellowPlayer() {
        this.yellowPlayer = new Player(null, Alliance.YELLOW, Stage.IDLE);
        this.players.add(yellowPlayer);
        this.setPiece(-8, 16, -8, Alliance.YELLOW, Piece.PieceType.CAPITOL);
        this.setPiece(-7, 14, -7, Alliance.YELLOW, Piece.PieceType.TOWN);
        this.setPiece(-9, 18, -9, Alliance.YELLOW, Piece.PieceType.TOWN);
        this.setPiece(-5, 16, -11, Alliance.YELLOW, Piece.PieceType.TOWN);
        this.setPiece(-11, 16, -5, Alliance.YELLOW, Piece.PieceType.TOWN);
        this.setPiece(-6, 16, -10, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, 16, -9, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-9, 16, -7, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-10, 16, -6, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-8, 17, -9, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-9, 17, -8, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, 15, -8, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-8, 15, -7, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-6, 13, -7, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.setPiece(-7, 13, -6, Alliance.YELLOW, Piece.PieceType.SUPPLYLINE);
        this.randomlyAssignFighter(-4, 12, -8, Alliance.YELLOW);
        this.randomlyAssignFighter(-5, 12, -7, Alliance.YELLOW);
        this.randomlyAssignFighter(-6, 12, -6, Alliance.YELLOW);
        this.randomlyAssignFighter(-7, 12, -5, Alliance.YELLOW);
        this.randomlyAssignFighter(-8, 12, -4, Alliance.YELLOW);
    }

    public List<Fighter> getNearestEnemyFighter(Fighter fighter) {
        List<Fighter> closestFighterList = new ArrayList<>();
        int closestFighterDistance = 50;
        int fighterQCoord = fighter.getPieceQCoord();
        int fighterRCoord = fighter.getPieceRCoord();
        int fighterSCoord = fighter.getPieceSCoord();
        for (Fighter candidateFighter : this.getAllActiveFighters()) {
            if (candidateFighter.getPieceAlliance() != fighter.getPieceAlliance()) {
                int candidateFighterQCoord = candidateFighter.getPieceQCoord();
                int candidateFighterRCoord = candidateFighter.getPieceRCoord();
                int candidateFighterSCoord = candidateFighter.getPieceSCoord();
                int candidateFighterDistance = Math.max(Math.abs(fighterQCoord - candidateFighterQCoord),
                        Math.max(Math.abs(fighterRCoord - candidateFighterRCoord), Math.abs(fighterSCoord - candidateFighterSCoord)));
                System.out.println("candidateFighterDistance " + candidateFighterDistance);
                if (candidateFighterDistance + 1 <= closestFighterDistance) {
                    closestFighterList.clear();
                    closestFighterList.add(candidateFighter);
                    closestFighterDistance = candidateFighterDistance;
                } else if (candidateFighterDistance == closestFighterDistance) {
                    closestFighterList.add(candidateFighter);
                }
            }

        }
        System.out.println("closestFighterList size " + closestFighterList.size() + "\nclosestFighterDistance " + closestFighterDistance);

        return closestFighterList;
    }

    public boolean movingTowardFighter(Fighter movingFighter, List<Fighter> targetFighterList, Tile clickedTile) {
        int fighterQCoord = movingFighter.getPieceQCoord();
        int fighterRCoord = movingFighter.getPieceRCoord();
        int fighterSCoord = movingFighter.getPieceSCoord();

        int tileQCoord = clickedTile.getTileQCoord();
        int tileRCoord = clickedTile.getTileRCoord();
        int tileSCoord = clickedTile.getTileSCoord();

        for (Fighter targetFighter : targetFighterList) {
            int targetFighterQCoord = targetFighter.getPieceQCoord();
            int targetFighterRCoord = targetFighter.getPieceRCoord();
            int targetFighterSCoord = targetFighter.getPieceSCoord();

            int fighterDistance = Math.max(Math.abs(fighterQCoord - targetFighterQCoord),
                    Math.max(Math.abs(fighterRCoord - targetFighterRCoord), Math.abs(fighterSCoord - targetFighterSCoord)));
            int tileDistance = Math.max(Math.abs(tileQCoord - targetFighterQCoord),
                    Math.max(Math.abs(tileRCoord - targetFighterRCoord), Math.abs(tileSCoord - targetFighterSCoord)));
            System.out.println("fighterDistance " + fighterDistance);
            System.out.println("tileDistance " + tileDistance);
            System.out.println("movingTowardFighter checking fighter");


            if ((fighterDistance <= 5) && ((tileDistance) <= fighterDistance)) {
                System.out.println("movingTowardFighter true");
                return true;
            } else if (fighterDistance >= 6) {
                System.out.println("movingTowardFighter true");
                return true;
            }
        }
        if (targetFighterList.isEmpty()) {
            System.out.println("movingTowardFighter true");
            return true;
        }
        System.out.println("movingTowardFighter false");
        return false;
    }

    public List<Fighter> getAllActiveFighters() {
        List<Fighter> allFighters = new ArrayList<>();
        for (Player player : this.players) {
            allFighters.addAll(player.getPlayerFighters());
        }
        return allFighters;
    }

    public boolean inHomeTerritory(Piece piece) {
        System.out.println("inHomeTerritory called");

        int pieceQCoord = piece.getPieceQCoord();
        int pieceRCoord = piece.getPieceRCoord();
        int pieceSCoord = piece.getPieceSCoord();
        for (Player player : this.players) {
            if (player.getPlayerAlliance() == piece.getPieceAlliance() &&
                    player.getPlayerAlliance().inMyTerritory(pieceQCoord, pieceRCoord, pieceSCoord)) {
                return true;
            }
        }
        return false;
    }

    public boolean inAwayTerritory(Piece piece) {
        System.out.println("inHomeTerritory called");

        int pieceQCoord = piece.getPieceQCoord();
        int pieceRCoord = piece.getPieceRCoord();
        int pieceSCoord = piece.getPieceSCoord();
        for (Player player : this.players) {
            if (player.getPlayerAlliance() != piece.getPieceAlliance() &&
                    player.getPlayerAlliance().inMyTerritory(pieceQCoord, pieceRCoord, pieceSCoord)) {
                return true;
            }
        }
        return false;
    }


    public boolean movingTowardSaladin(Fighter movingFighter, List<Fighter> saladinList, Tile clickedTile) {
        int fighterQCoord = movingFighter.getPieceQCoord();
        int fighterRCoord = movingFighter.getPieceRCoord();
        int fighterSCoord = movingFighter.getPieceSCoord();

        int tileQCoord = clickedTile.getTileQCoord();
        int tileRCoord = clickedTile.getTileRCoord();
        int tileSCoord = clickedTile.getTileSCoord();

        for (Fighter targetFighter : saladinList) {
            int targetFighterQCoord = targetFighter.getPieceQCoord();
            int targetFighterRCoord = targetFighter.getPieceRCoord();
            int targetFighterSCoord = targetFighter.getPieceSCoord();

            int fighterDistance = Math.max(Math.abs(fighterQCoord - targetFighterQCoord),
                    Math.max(Math.abs(fighterRCoord - targetFighterRCoord), Math.abs(fighterSCoord - targetFighterSCoord)));
            int tileDistance = Math.max(Math.abs(tileQCoord - targetFighterQCoord),
                    Math.max(Math.abs(tileRCoord - targetFighterRCoord), Math.abs(tileSCoord - targetFighterSCoord)));
            System.out.println("fighterDistance " + fighterDistance);
            System.out.println("tileDistance " + tileDistance);
            System.out.println("movingTowardFighter checking fighter");


            if ((fighterDistance <= 5) && ((tileDistance) <= fighterDistance)) {
                System.out.println("movingTowardFighter true");
                return true;
            } else if (fighterDistance >= 6) {
                System.out.println("movingTowardFighter true");
                return true;
            }
        }
        if (saladinList.isEmpty()) {
            System.out.println("movingTowardFighter true");
            return true;
        }
        System.out.println("movingTowardFighter false");
        return false;
    }


    public List<Piece> getNearestMainPiece(Fighter fighter, List<Piece> otherGuys) {
        List<Piece> closestPieceList = new ArrayList<>();
        int closestFighterDistance = 50;
        int fighterQCoord = fighter.getPieceQCoord();
        int fighterRCoord = fighter.getPieceRCoord();
        int fighterSCoord = fighter.getPieceSCoord();
        for (Piece candidatePiece : otherGuys) {
            int candidatePieceQCoord = candidatePiece.getPieceQCoord();
            int candidatePieceRCoord = candidatePiece.getPieceRCoord();
            int candidatePieceSCoord = candidatePiece.getPieceSCoord();
            int candidateFighterDistance = Math.max(Math.abs(fighterQCoord - candidatePieceQCoord),
                    Math.max(Math.abs(fighterRCoord - candidatePieceRCoord), Math.abs(fighterSCoord - candidatePieceSCoord)));
            System.out.println("candidateFighterDistance " + candidateFighterDistance);
            if (candidateFighterDistance + 1 <= closestFighterDistance) {
                closestPieceList.clear();
                closestPieceList.add(candidatePiece);
                closestFighterDistance = candidateFighterDistance;
            } else if (candidateFighterDistance == closestFighterDistance) {
                closestPieceList.add(candidatePiece);
            }


        }
        System.out.println("closestFighterList size " + closestPieceList.size() + "\nclosestFighterDistance " + closestFighterDistance);

        return closestPieceList;
    }

    public static Board getDeepClone(Board engineBoard) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(engineBoard);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Board)ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean justFought(){
        return this.justFought;
    }

    public void setJustFought(Boolean fight){
        this.justFought = fight;
    }
}
