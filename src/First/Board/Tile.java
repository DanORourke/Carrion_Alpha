package First.Board;

import First.Piece.*;
import First.Player.Alliance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tile implements Serializable {

    private final int qCoord;
    private final int rCoord;
    private final int sCoord;
    private Capitol capitol;
    private Town town;
    private SupplyLine supplyLine;
    private  List<Fighter> fighters;
    private ChiefOfStaff chiefOfStaff;
    private Piece piece;

    protected Tile(final int qCoord, final int rCoord, final int sCoord,
                   Capitol capitol, Town town, SupplyLine supplyLine,
                   List<Fighter> fighters, ChiefOfStaff chiefOfStaff) {


        this.qCoord = qCoord;
        this.rCoord = rCoord;
        this.sCoord = sCoord;
        this.capitol = capitol;
        this.town = town;
        this.supplyLine = supplyLine;
        this.fighters = new ArrayList<>();
        this.fighters.addAll(fighters);
        this.chiefOfStaff = chiefOfStaff;
    }

    protected Tile (final int qCoord,final int rCoord, final int sCoord ){
        this.qCoord = qCoord;
        this.rCoord = rCoord;
        this.sCoord = sCoord;
        this.capitol = null;
        this.town = null;
        this.supplyLine = null;
        this.fighters = new ArrayList<>();
        this.chiefOfStaff = null;
    }


    public int getTileQCoord() {
        return this.qCoord;
    }
    public int getTileRCoord() {
        return this.rCoord;
    }
    public int getTileSCoord() {
        return this.sCoord;
    }


    public boolean isTileEmpty(){
        if((this.capitol == null) && (this.town == null) && (this.supplyLine == null) &&
                (this.fighters == null) && (this.chiefOfStaff == null)){
            return true;
        } else {
            return false;
        }
    }


    public boolean isTileOccupiedbySupplyLine(){
        if (this.supplyLine != null){
            return true;
        }else{
            return false;
        }
    }
    public SupplyLine getSupplyLine(){
        return this.supplyLine;
    }

    public void setSupplyLine(SupplyLine supplyLine){
        this.supplyLine = supplyLine;
    }

    public void removeSupplyLine(){
        this.supplyLine = null;
    }

    public boolean isTileOccupiedbyCapitol(){
        if (this.capitol != null){
            return true;
        }else{
            return false;
        }
    }
    public Capitol getCapitol(){
        return this.capitol;
    }
    public void setCapitol(Capitol capitol){
        this.capitol = capitol;
    }




    public boolean isTileOccupiedbyTown(){
        if (this.town != null){
            return true;
        }else{
            return false;
        }
    }
    public Town getTown(){
        return this.town;
    }
    public void setTown(Town town){
        this.town = town;
    }
    public void removeTown(){
        this.town = null;
    }



    public boolean isTileOccupiedbyFighter(){
        if (this.fighters != null){
            return true;
        }else{
            return false;
        }
    }
    public List getFighters(){
        return this.fighters;
    }
    public void addFighter(Fighter fighter){
        this.fighters.add(fighter);

    }
    public void removeFighter(Fighter fighter){
        this.fighters.remove(fighter);
    }

    public boolean isTileOccupiedbyChiefOfStaff(){
        if (this.chiefOfStaff != null){
            return true;
        }else{
            return false;
        }
    }
    public ChiefOfStaff getChiefOfStaff(){
        return this.chiefOfStaff;
    }
    public void setChiefOfStaff(ChiefOfStaff chiefOfStaff){
        this.chiefOfStaff = chiefOfStaff;
    }
    public void removeChiefOfStaff(){
        this.chiefOfStaff = null;
    }

    public Fighter getBattlefieldAlly(Alliance alliance){
        if (!this.getFighters().isEmpty()){
            for (Fighter fighter : this.fighters){
                if (fighter.getPieceAlliance() == alliance){
                    return fighter;
                }
            }
        }
        System.out.println("getBattlefieldAlly cant find anyone");
        return null;
    }


    public boolean isTileHomeTerritory(){
        return false;
    }
    public boolean isTileAwayTerritiry(){
        return false;
    }

    public boolean isTileBattlefield(){
        if (this.fighters.size() >= 2){
            return true;
        }else if ((!this.fighters.isEmpty()) && (((this.getTown() != null) && (this.getTown().getPieceAlliance() != Alliance.UNOCCUPIED)) ||
                ((this.getCapitol() != null)))){
            return true;
        }else {
            return false;
        }
    }

    public List<Piece> getPieces() {
        List<Piece> tilePieces = new ArrayList<>();
        tilePieces.add(this.capitol);
        tilePieces.add(this.town);
        tilePieces.add(this.supplyLine);
        tilePieces.add(this.chiefOfStaff);
        tilePieces.addAll(this.fighters);
        return tilePieces;
    }


    public void shuffleFighters() {
        Fighter oldZero = (Fighter) this.getFighters().get(0);
        Fighter oldOne = (Fighter) this.getFighters().get(1);
        this.getFighters().set(0, oldOne);
        this.getFighters().set(1, oldZero);
    }

    public void removeCapitol() {
        this.capitol = null;
    }

    public boolean isNextTo(Tile tile) {
        if (((tile.getTileQCoord() == this.getTileQCoord()) &&
                ((tile.getTileRCoord() + 1) == this.getTileRCoord()) &&
                ((tile.getTileSCoord() - 1) == this.getTileSCoord())) ||
                ((tile.getTileQCoord() == this.getTileQCoord()) &&
                        ((tile.getTileRCoord() - 1) == this.getTileRCoord()) &&
                        ((tile.getTileSCoord() + 1) == this.getTileSCoord())) ||
                (((tile.getTileQCoord() + 1) == this.getTileQCoord()) &&
                        ((tile.getTileRCoord() - 1) == this.getTileRCoord()) &&
                        ((tile.getTileSCoord()) == this.getTileSCoord())) ||
                (((tile.getTileQCoord() - 1) == this.getTileQCoord()) &&
                        ((tile.getTileRCoord() + 1) == this.getTileRCoord()) &&
                        ((tile.getTileSCoord()) == this.getTileSCoord())) ||
                (((tile.getTileQCoord() - 1) == this.getTileQCoord()) &&
                        ((tile.getTileRCoord()) == this.getTileRCoord()) &&
                        ((tile.getTileSCoord() + 1) == this.getTileSCoord())) ||
                (((tile.getTileQCoord() + 1) == this.getTileQCoord()) &&
                        ((tile.getTileRCoord()) == this.getTileRCoord()) &&
                        ((tile.getTileSCoord() - 1) == this.getTileSCoord()))) {
            return true;
        } else {
            return false;
        }
    }
}
