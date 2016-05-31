package First.Piece;

import First.Player.Alliance;
import First.Player.Player;

import java.awt.*;
import java.io.Serializable;

public abstract class Piece implements Serializable {

    private int qCoord;
    private int rCoord;
    private int sCoord;
    //private Tile pieceTile;
    private PieceType pieceType;
    private Alliance pieceAlliance;
    private Player player;

    Piece(int qCoord, int rCoord,int sCoord, PieceType pieceType, Alliance pieceAlliance){
        this.qCoord = qCoord;
        this.rCoord = rCoord;
        this.sCoord = sCoord;
        //this.pieceTile = pieceTile;
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.player = null;
    }

    public int getPieceQCoord() {
        return this.qCoord;
    }
    public int getPieceRCoord() {
        return this.rCoord;
    }
    public int getPieceSCoord() {
        return this.sCoord;
    }
    public void setNewCoordinates(int qCoord, int rCoord, int sCoord){
        this.qCoord = qCoord;
        this.rCoord = rCoord;
        this.sCoord = sCoord;

    }


    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public void setPieceAlliance(Alliance alliance){
        this.pieceAlliance = alliance;
    }

    public void setPiecePlayer(Player player){
        this.player = player;
    }

    public Player getPiecePlayer(){
        return this.player;
    }

    public Color getPieceColor() {
        if (this.pieceAlliance == Alliance.RED){
            Color playerColor = new Color(190,18,12);
            return playerColor;
        } else if (this.pieceAlliance == Alliance.BLUE){
            Color playerColor = new Color(10,18,190);
            return playerColor;
        }else if (this.pieceAlliance == Alliance.GREEN){
            Color playerColor = new Color(0,100,0);
            return playerColor;
        }else if (this.pieceAlliance == Alliance.UNOCCUPIED){
            Color playerColor = new Color(100,100,100);
            return playerColor;
        }else if (this.pieceAlliance == Alliance.BLACK){
            Color playerColor = new Color(0,0,0);
            return playerColor;
        }else if (this.pieceAlliance == Alliance.WHITE){
            Color playerColor = new Color(250,250,250);
            return playerColor;
        }else if (this.pieceAlliance == Alliance.YELLOW){
            Color playerColor = new Color(255,255,10);
            return playerColor;
        }else{
            System.out.println("no such color");
            return null;
        }
    }

    /*public Tile getPieceTile() {
        return this.pieceTile;
    }*/



    public enum PieceType {
        CAPITOL("Capitol"){


        },

        SUPPLYLINE("Supply Line"){

        },

        TOWN("Town") {

        },

        FIGHTER("General") {

        },

        CHIEFOFSTAFF("Chief of Staff"){

        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

    }



}
