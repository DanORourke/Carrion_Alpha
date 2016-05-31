package First.Piece;

import First.Board.Board;
import First.Piece.Fighters.*;
import First.Player.Alliance;
import First.Board.Tile;
import First.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Fighter extends Piece implements Serializable{
    protected int regiments;
    protected int movementPoints;
    private boolean hasChiefOfStaff;
    protected boolean interactedWithLines;
    protected boolean hasCutLine;
    protected boolean hasDroppedLine;
    private boolean hasDroppedTown;
    protected boolean hasFought;
    private boolean isHelping;
    protected boolean isFighting;
    protected boolean isExposed;
    private List<Fighter> whoIsHelpingHim;
    protected Fighter fighterHeisHelping;
    protected Town townHeisHelping;
    protected Capitol capitolHeisHelping;
    protected Tile tileAttackedFrom;
    protected boolean wasAttacker;
    protected boolean justBeatTown;
    protected int scaredOfSargon;


    public Fighter(int qCoord, int rCoord, int sCoord, Alliance pieceAlliance) {
        super(qCoord, rCoord, sCoord, PieceType.FIGHTER, pieceAlliance);
        this.regiments = 1;
        this.hasChiefOfStaff = false;
        this.interactedWithLines = false;
        this.hasDroppedTown = false;
        this.hasFought = false;
        this.whoIsHelpingHim = new ArrayList<>();
        this.fighterHeisHelping = null;
        this.townHeisHelping = null;
        this.capitolHeisHelping = null;
        this.isHelping = false;
        this.isFighting = false;
        this.isExposed = false;
        this.tileAttackedFrom = null;
        this.wasAttacker = false;
        this.justBeatTown = false;
        this.scaredOfSargon = 0;

    }

    public Fighter() {
        super(0, 0, 0,PieceType.FIGHTER ,Alliance.UNOCCUPIED);
        this.regiments=1;
        this.hasChiefOfStaff=false;
        this.interactedWithLines=false;
        this.hasDroppedTown=false;
        this.hasFought=false;
        this.whoIsHelpingHim=new ArrayList<>();
        this.fighterHeisHelping=null;
        this.townHeisHelping=null;
        this.capitolHeisHelping=null;
        this.isHelping=false;
        this.isFighting=false;
        this.isExposed=false;
        this.tileAttackedFrom = null;
        this.wasAttacker = false;
        this.justBeatTown = false;
        this.scaredOfSargon = 0;
    }

    public int getRegiments(){
        return  this.regiments;
    }
    public void addRegiments(final int i){
        this.regiments = this.regiments + i;
        if (this.regiments >= 20){
            this.regiments = 20;
        }
    }
    public void releaseRegiments(final int i){
        this.regiments = this.regiments - i;
        if (this.regiments <= 0){
            this.regiments = 0;
        }
    }

    public void dropRegiment(){
        this.regiments = this.regiments - 1;
        if (this.regiments <= 0){
            this.regiments = 0;
        }
    }

    public boolean hasChiefOfStaff(){
        return this.hasChiefOfStaff;
    }
    public void giveChiefOfStaff(){
        this.hasChiefOfStaff = true;
    }
    public void removeChiefOfStaff(){
        this.hasChiefOfStaff = false;
    }

    public int getMovementPoints(){
        return this.movementPoints;
    }

    public int getMaxRegiments(){
        return 19;
    }

    public void resetMovementPoints(){
        this.interactedWithLines = false;
        this.hasDroppedLine = false;
        this.hasCutLine = false;

        if ((this.regiments >= 1) && (this.regiments <= 5)){
            this.movementPoints = 5;

        }else if ((this.regiments >= 6) && (this.regiments <= 10)){
            this.movementPoints = 4;


        }else if ((this.regiments >= 11) && (this.regiments <= 15)){
            this.movementPoints = 3;


        }else if ((this.regiments >= 16) && (this.regiments <= 20)){
            this.movementPoints = 2;

        }
    }

    public void lowerMovementPoints(Boolean cutLine, Boolean dropLine){
        if (!this.interactedWithLines && !(cutLine || dropLine)){
            this.movementPoints = this.movementPoints - 1;
        }else if (!this.interactedWithLines && (cutLine || dropLine)){
            this.movementPoints = this.movementPoints - 2;
        }else if (this.interactedWithLines && !(cutLine || dropLine)){
            this.movementPoints = this.movementPoints - 1;
        }else if (this.interactedWithLines && (cutLine || dropLine)){
            this.movementPoints = this.movementPoints - 1;
        }
        if (cutLine){
            this.hasCutLine = true;
            this.interactedWithLines = true;
        }
        if (dropLine){
            this.hasDroppedLine = true;
            this.interactedWithLines = true;
        }
    }

    public void setAfterBattleMovementPoints(){
        this.movementPoints = 1;
        this.interactedWithLines = true;
        this.hasFought = true;
    }

    public void trueDroppedLine(Boolean lowerMovementPoints){
        this.interactedWithLines = true;
        this.hasDroppedLine = true;
        if (lowerMovementPoints){
            this.movementPoints = this.movementPoints -1;
        }
    }

    public void trueCutLine(){
        this.interactedWithLines = true;
        this.hasCutLine = true;
    }
    public boolean getInteractedWithLines(){
        return this.interactedWithLines;
    }

    public boolean getHasDroppedTown(){
        return this.hasDroppedTown;
    }

    public void dropTown(){
        this.hasDroppedTown = true;
        this.regiments = this.regiments - 1;
    }


    public void resetHasDroppedTown(){
        this.hasDroppedTown = false;
    }
    public void attack(Tile tileAttackedFrom){
        this.movementPoints = 0;
        this.isFighting = true;
        this.tileAttackedFrom = tileAttackedFrom;
        this.wasAttacker = true;
    }

    public void attackUnoccupiedTown(){
        this.movementPoints = 1;
    }
    public void fightHasFought(){
        this.hasFought = true;
    }
    public boolean getHasFought(){
        return this.hasFought;
    }
    public void resetHasFought(){
        this.hasFought = false;
    }

    public boolean getIsExposed(){
        return this.isExposed;
    }

    public void exposeFighter(){
        this.isExposed = true;
    }

    public void exposeAttackFighter(){
        this.exposeFighter();
        for (Fighter fighter : this.whoIsHelpingHim){
            fighter.exposeFighter();
            if (fighter.getClass() == Askia.class && !fighter.hasChiefOfStaff()){
                this.exposeAllFighters();
            }
        }
    }

    public void exposeDefenseFighter(){
        this.exposeFighter();
        for (Fighter fighter : this.whoIsHelpingHim){
            if (!fighter.isFighting){
                fighter.exposeFighter();
                if (fighter.getClass() == Askia.class && !fighter.hasChiefOfStaff()){
                    this.exposeAllFighters();
                }
            }
        }
    }

    public void exposeAllFighters(){
        for (Fighter fighter : this.getPiecePlayer().getPlayerFighters()){
            fighter.exposeFighter();
        }
    }

    public void hideFighter(){
        this.isExposed = false;
    }


    public boolean isLastFighter(Board gameBoard, Alliance alliance){
        Player player = gameBoard.getPlayer(alliance);
        if (player.getPlayerFighters().size() == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isFighterTrapped(Board gameBoard){
        for (Tile tile : gameBoard.getTiles()){
            if (this.isNextTo(tile)) {
                System.out.println("fightertrapped is next to " + tile.getTileQCoord()  + " " + tile.getTileRCoord() + " " + tile.getTileSCoord());
                if (tile.getFighters().isEmpty() &&
                        tile.getTown() == null &&
                        tile.getCapitol() == null &&
                        tile.getSupplyLine() == null) {
                    System.out.println("fightertrapped false");
                    return false;
                } else if ((tile.getSupplyLine() != null) &&
                        (tile.getSupplyLine().getPieceAlliance() == this.getPieceAlliance()) &&
                        (tile.getFighters().isEmpty())) {
                    System.out.println("fightertrapped false supply line");
                    return false;
                }
            }
        }
        System.out.println("isFighterTrapped true");
        return true;
    }

    public boolean isNextTo(Tile tile){
        if  (((tile.getTileQCoord() == this.getPieceQCoord()) &&
                ((tile.getTileRCoord() + 1) == this.getPieceRCoord()) &&
                ((tile.getTileSCoord() - 1) == this.getPieceSCoord())) ||
                ((tile.getTileQCoord() == this.getPieceQCoord()) &&
                        ((tile.getTileRCoord() - 1) == this.getPieceRCoord()) &&
                        ((tile.getTileSCoord() + 1) == this.getPieceSCoord())) ||
                (((tile.getTileQCoord() + 1) == this.getPieceQCoord()) &&
                        ((tile.getTileRCoord() - 1) == this.getPieceRCoord()) &&
                        ((tile.getTileSCoord()) == this.getPieceSCoord())) ||
                (((tile.getTileQCoord() - 1) == this.getPieceQCoord()) &&
                        ((tile.getTileRCoord() + 1) == this.getPieceRCoord()) &&
                        ((tile.getTileSCoord()) == this.getPieceSCoord())) ||
                (((tile.getTileQCoord() - 1) == this.getPieceQCoord()) &&
                        ((tile.getTileRCoord()) == this.getPieceRCoord()) &&
                        ((tile.getTileSCoord() + 1) == this.getPieceSCoord())) ||
                (((tile.getTileQCoord() + 1) == this.getPieceQCoord()) &&
                        ((tile.getTileRCoord()) == this.getPieceRCoord()) &&
                        ((tile.getTileSCoord() - 1) == this.getPieceSCoord()))){
            return true;
        }else {
            return false;
        }
    }

    public boolean isNextTo(Piece piece){
        if  (((piece.getPieceQCoord() == this.getPieceQCoord()) &&
                ((piece.getPieceRCoord() + 1) == this.getPieceRCoord()) &&
                ((piece.getPieceSCoord() - 1) == this.getPieceSCoord())) ||
                ((piece.getPieceQCoord() == this.getPieceQCoord()) &&
                        ((piece.getPieceRCoord() - 1) == this.getPieceRCoord()) &&
                        ((piece.getPieceSCoord() + 1) == this.getPieceSCoord())) ||
                (((piece.getPieceQCoord() + 1) == this.getPieceQCoord()) &&
                        ((piece.getPieceRCoord() - 1) == this.getPieceRCoord()) &&
                        ((piece.getPieceSCoord()) == this.getPieceSCoord())) ||
                (((piece.getPieceQCoord() - 1) == this.getPieceQCoord()) &&
                        ((piece.getPieceRCoord() + 1) == this.getPieceRCoord()) &&
                        ((piece.getPieceSCoord()) == this.getPieceSCoord())) ||
                (((piece.getPieceQCoord() - 1) == this.getPieceQCoord()) &&
                        ((piece.getPieceRCoord()) == this.getPieceRCoord()) &&
                        ((piece.getPieceSCoord() + 1) == this.getPieceSCoord())) ||
                (((piece.getPieceQCoord() + 1) == this.getPieceQCoord()) &&
                        ((piece.getPieceRCoord()) == this.getPieceRCoord()) &&
                        ((piece.getPieceSCoord() - 1) == this.getPieceSCoord()))){
            return true;
        }else {
            return false;
        }
    }

    public Fighter getEnemyFighter(Piece piece){
        Fighter enemyFighter = null;

        if (piece.getClass() == Town.class){
            if (!((Town)piece).getWhoIsHelpingHim().isEmpty()){
                enemyFighter = ((Town)piece).getWhoIsHelpingHim().get(0);

            }else if (((Town)piece).getWhoIsHelpingHim().isEmpty()){
                enemyFighter = null;
            }

        }else if (piece.getClass()== Capitol.class){
            if (!((Capitol)piece).getWhoIsHelpingHim().isEmpty()){
                enemyFighter = ((Capitol)piece).getWhoIsHelpingHim().get(0);

            }else if (((Capitol)piece).getWhoIsHelpingHim().isEmpty()){
                enemyFighter = null;
            }

        }else if (piece.getClass().getSuperclass() == Fighter.class){
            enemyFighter = (Fighter)piece;
        }

        return enemyFighter;
    }

    public int calculateFightBonus(Piece piece){
        return this.standardFightBonus(piece);
    }

    public int standardFightBonus(Piece piece){
        Fighter enemyFighter = this.getEnemyFighter(piece);

        if (enemyFighter != null && enemyFighter.getIsExposed()){

            if (enemyFighter.getClass() == Fredrick.class){
                return this.calculateMainFightBonus(enemyFighter);
            }
        }

        int fighterOnlyFightBonus = this.calculateMainFightBonus(enemyFighter);
        int assistingFightBonus = 0;
        for (Fighter fighter : this.whoIsHelpingHim){
            if(!fighter.isFighting){
                assistingFightBonus = assistingFightBonus + fighter.calculateAssistingFightBonus();
            }
        }
        return fighterOnlyFightBonus + assistingFightBonus;
    }

    public int calculateCasualties(Piece piece){
        return standardCasualties(piece);
    }

    public int standardCasualties(Piece piece){
        Fighter enemyFighter = getEnemyFighter(piece);

        if (enemyFighter != null && enemyFighter.getIsExposed()){

            if (enemyFighter.getClass() == Fredrick.class){
                return this.calculateMainCasualties(enemyFighter);
            }
        }
        int fighterOnlyCasualties =  this.calculateMainCasualties(enemyFighter);
        int assistingCasualties = 0;
        for (Fighter fighter : this.whoIsHelpingHim){
            if(!fighter.isFighting){
                assistingCasualties = assistingCasualties + fighter.calculateAssistingCasualties();
            }
        }
        return fighterOnlyCasualties + assistingCasualties;
    }


    public int calculateMainFightBonus(Fighter enemyFighter){
        if (enemyFighter != null && enemyFighter.getIsExposed()){
            if (enemyFighter.getClass() == Oda.class &&
                    !enemyFighter.hasChiefOfStaff()){
                return (int) Math.ceil((double)(3 * this.calculatePersonalMainFightBonus(this.getRegiments())) / 2);

            }else if (enemyFighter.getClass() == Leonidas.class && this.getRegiments() >= enemyFighter.getRegiments() +1){

                return this.calculatePersonalMainFightBonus(enemyFighter.getRegiments());

            }else if (enemyFighter.getClass() == Washington.class &&
                    !enemyFighter.hasChiefOfStaff()){
                int fighterOnlyFightBonus = 0;

                if (((Washington)enemyFighter).getAttackingUnassisted()){
                    fighterOnlyFightBonus = (int) Math.ceil((double) (3 * this.calculatePersonalMainFightBonus(this.getRegiments())) / 2);

                }else {
                    fighterOnlyFightBonus = this.calculatePersonalMainFightBonus(this.getRegiments());

                }
                return fighterOnlyFightBonus;
            }
        }
        return this.calculatePersonalMainFightBonus(this.getRegiments());
    }

    public int calculatePersonalMainFightBonus(int regiments){
        return regiments;
    }


    public int calculateAssistingFightBonus(){
        return (int) Math.ceil((double)this.getRegiments() / 2);
    }

    public int calculateMainCasualties(Fighter enemyFighter){
        if (enemyFighter != null && enemyFighter.getIsExposed()){

            if (enemyFighter.getClass() == Charlemagne.class){
                return (int) Math.ceil((double)(2 * this.calculatePersonalMainCasualties()) / 3);
            }else if (enemyFighter.getClass() == Shaka.class &&
                    !enemyFighter.hasChiefOfStaff()){
                return (int) Math.ceil((double)(3 * this.calculatePersonalMainCasualties()) / 2);
            }
        }
        return this.calculatePersonalMainCasualties();
    }

    public int calculatePersonalMainCasualties(){
        return (int) Math.ceil((double)this.getRegiments() / 2);
    }

    public int calculateAssistingCasualties(){
        return (int) Math.ceil((double)this.getRegiments() / 4);
    }


    public String fightsAndKills(){
        return "\nfight bonus = " + this.calculatePersonalMainFightBonus(this.getRegiments()) + "\nkills = " + this.calculatePersonalMainCasualties();
    }


    public void addHelpingFighter(Fighter fighter){
        this.whoIsHelpingHim.add(fighter);

    }

    public void addAllHelpingFighters(List<Fighter> helpingFighters){
        this.whoIsHelpingHim.addAll(helpingFighters);
    }

    public void addFighterHeisHelping(Fighter fighter){
        this.fighterHeisHelping = fighter;
        this.isHelping = true;
    }
    public void addTownHeisHelping(Town town){
        this.townHeisHelping = town;
        this.isHelping = true;

    }
    public void addCapitolHeisHelping(Capitol capitol){
        this.capitolHeisHelping = capitol;
        this.isHelping = true;

    }
     public Fighter getFighterHeisHelping(){
         return this.fighterHeisHelping;
     }

    public List<Fighter> getWhoIsHelpingHim(){
        return this.whoIsHelpingHim;
    }

    public void removeFighterAssistance(Fighter fighter){
        this.whoIsHelpingHim.remove(fighter);
    }

    public void helpFighter(Fighter assistedFighter) {
        if (this.fighterHeisHelping != assistedFighter) {
            if (this.fighterHeisHelping != null) {
                this.fighterHeisHelping.removeFighterAssistance(this);
            }
            if (this.townHeisHelping != null){
                this.townHeisHelping.removeFighterAssistance(this);
            }
            if (this.capitolHeisHelping != null){
                this.capitolHeisHelping.removeFighterAssistance(this);
            }
            this.addFighterHeisHelping(assistedFighter);
            assistedFighter.addHelpingFighter(this);
        }
    }

    public void helpTown(Town assistedTown) {
        if (this.townHeisHelping != assistedTown) {
            if (this.fighterHeisHelping != null) {
                this.fighterHeisHelping.removeFighterAssistance(this);
            }
            if (this.townHeisHelping != null){
                this.townHeisHelping.removeFighterAssistance(this);
            }
            if (this.capitolHeisHelping != null){
                this.capitolHeisHelping.removeFighterAssistance(this);
            }
            this.addTownHeisHelping(assistedTown);
            assistedTown.addHelpingFighter(this);
        }
    }

    public void helpCapitol(Capitol assistedCapitol) {
        if (this.capitolHeisHelping != assistedCapitol) {
            if (this.fighterHeisHelping != null) {
                this.fighterHeisHelping.removeFighterAssistance(this);
            }
            if (this.townHeisHelping != null){
                this.townHeisHelping.removeFighterAssistance(this);
            }
            if (this.capitolHeisHelping != null){
                this.capitolHeisHelping.removeFighterAssistance(this);
            }
            this.addCapitolHeisHelping(assistedCapitol);
            assistedCapitol.addHelpingFighter(this);
        }
    }

    public void clearAssistance(){
        this.fighterHeisHelping = null;
        this.townHeisHelping = null;
        this.capitolHeisHelping = null;
        this.isHelping = false;
        this.isFighting = false;
        if (!this.whoIsHelpingHim.isEmpty()){
            this.whoIsHelpingHim.clear();
        }
    }

    public boolean getIsFighting(){
        return this.isFighting;
    }

    public void fightIsFighting(){
        this.isFighting = true;
    }
    public void resetIsFighting(){
        this.isFighting = false;
    }

    public void zeroMovementPoints(){
        this.movementPoints = 0;
    }

    public void allocateMethod(){
        this.scaredOfSargon = this.scaredOfSargon - 1;
        if (this.scaredOfSargon <= 0){
            this.scaredOfSargon = 0;
        }
    }

    public void cyrusMethod(Fighter fighter, int kills){
        if (fighter.getClass() == Cyrus.class && !fighter.hasChiefOfStaff()){
            this.addRegiments(kills);
        }
        if (fighter.getClass() == Osman.class){
            ((Osman)fighter).setGrudgeMatch(this);
        }
    }

    public Fighter wellingtonMethod(Fighter fighter, Tile tile){
        return null;
    }

    public void setwasAttacker(Boolean attacker){
        this.wasAttacker = attacker;
    }

    public boolean isExposedRagnar(){
        return false;
    }

    public boolean killRagnar(){
        return false;
    }

    public boolean canHannibalAttack(){
        return true;
    }

    public boolean canAshokaAttack(Fighter enemyFighter){
        return true;
    }

    public boolean canNapoleonMove(Board engineBoard, Tile clickedTile){
        List<Fighter> saladinList= new ArrayList<>();
        List<Fighter> oneSaladinList= new ArrayList<>();
        for (Fighter fighter : engineBoard.getAllActiveFighters()){
            if (fighter.getPieceAlliance() != this.getPieceAlliance() && fighter.getClass() == Saladin.class &&
                    fighter.getIsExposed()){
                saladinList.add(fighter);
            }
        }
        if (!saladinList.isEmpty()){
            for (Fighter fighter : saladinList){
                if (engineBoard.getNearestEnemyFighter(fighter).contains(this)){
                    oneSaladinList.add(fighter);
                    if (!engineBoard.movingTowardSaladin(this, oneSaladinList, clickedTile)){
                        return false;
                    }
                    oneSaladinList.clear();
                }
            }
        }
        if (this.scaredOfSargon == 1){
            return false;
        }
        return true;
    }

    public int homeAwayBonus(Piece piece, Board engineBoard){
        System.out.println("homeAwayBonus called");
        Fighter enemyFighter = getEnemyFighter(piece);
        if (piece.getClass().getSuperclass() == Fighter.class){
            if (enemyFighter.getClass() == Hannibal.class){
                return -1;
            }
        }
        if (engineBoard.inHomeTerritory(this)){
            return 1;
        }
        if (engineBoard.inAwayTerritory(this)){
            return -1;
        }
        return 0;
    }

    public void getWhereAttackedFrom(Fighter fighter){
        this.tileAttackedFrom = fighter.tileAttackedFrom;
    }

    public boolean isFighterCornered(Board engineBoard){

        for (Tile tile : engineBoard.getTiles()) {
            if (this.isNextTo(tile)){
                if (this.canPostBattleFighterEscapeHere(tile)) {
                    System.out.println("isFighterCornered false");

                    return false;
                }
            }
        }
        System.out.println("isFighterCornered true");

        return true;
    }

    public void setTileAttackedFrom(Tile tile){
        this.tileAttackedFrom = tile;
    }


    public boolean canPostBattleFighterEscapeHere(Tile tile){
        System.out.println("canPostBattleFighterEscapeHere called");

        if (this.wasAttacker && !this.justBeatTown){
            System.out.println("wasAttacker called");
            if (((this.isNextTo(tile)  && this.tileAttackedFrom.isNextTo(tile)) || (this.tileAttackedFrom == tile))){
                if (!tile.getPieces().isEmpty()){
                    if (tile.getSupplyLine() != null && tile.getSupplyLine().getPieceAlliance() != this.getPieceAlliance()){
                        return false;
                    }else if (!tile.getFighters().isEmpty()){
                        return false;
                    }else if (tile.getTown() != null){
                        return false;
                    }else if (tile.getCapitol() != null){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }else if (this.wasAttacker && this.justBeatTown){
            if (this.isNextTo(tile)){
                if (!tile.getPieces().isEmpty()){
                    if (tile.getSupplyLine() != null && tile.getSupplyLine().getPieceAlliance() != this.getPieceAlliance()){
                        return false;
                    }else if (!tile.getFighters().isEmpty()){
                        return false;
                    }else if (tile.getTown() != null){
                        return false;
                    }else if (tile.getCapitol() != null){
                        return false;
                    }
                }
                return true;
            }

        }else if (!this.wasAttacker){
            System.out.println("!wasAttacker called");

            if ((this.isNextTo(tile)  && !this.tileAttackedFrom.isNextTo(tile)) && this.tileAttackedFrom != tile){
                if (!tile.getPieces().isEmpty()){
                    if (tile.getSupplyLine() != null && tile.getSupplyLine().getPieceAlliance() != this.getPieceAlliance()){
                        return false;
                    }else if (!tile.getFighters().isEmpty()){
                        return false;
                    }else if (tile.getTown() != null){
                        return false;
                    }else if (tile.getCapitol() != null){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public void idleMethod(){
        this.wasAttacker = false;
        this.tileAttackedFrom = null;
        this.justBeatTown = false;
    }

    public void giveAcamaRegiment(){
    this.justBeatTown = true;
    }

    public boolean canHelpFighter(Fighter fighter){
        if (fighter.getClass() == Fredrick.class && !fighter.hasChiefOfStaff()){
            return false;
        }else if (fighter.getClass() == SunTzu.class && fighter.isGhost()){
            return false;
        }
        return true;
    }

    public boolean shermanCheck(){
        return true;
    }

    public String proDescription(){
        return null;
    }

    public String conDescription(){
        return null;
    }
    public boolean willBribe(Fighter fighter, Boolean willDie){
        return false;
    }

    public void musaMethod(Fighter fighter){

    }
    public boolean shermanCut(){
        return false;
    }

    public boolean qinDrop(){
        return false;
    }

    public String exposedString(){
        if (this.isExposed){
            return "\nExposed";
        }else {
            return "\nConcealed";
        }
    }
    public void genghisMethod(){

    }

    public void getBattleField(Tile battleField){
    }

    public void shivajiMethod(){

    }
    public boolean genghisOrShivaji(){
        return false;
    }

    public boolean isGhost(){
        return false;
    }

    public boolean sunTzuMethod(){
        return false;
    }

    public String ghostName(){
        return null;
    }

    public void haveGhosted(){

    }

    public boolean hasGhost(){
        return false;
    }

    public Fighter getGhost(){
        return null;
    }

    public void setGhost(Fighter ghost){
    }

    public void setScaredOfSargon(Fighter fighter){
        if (fighter.getClass() == Sargon.class){
            this.scaredOfSargon = 2;
        }
    }

    public void sargonMethod(){

    }

    public boolean nurhaciHelp(Tile clickedTile){
        return false;
    }
}
