package First.GUI;

import First.Board.Board;

import java.util.ArrayList;
import java.util.List;

public class MoveList {
    private List<Board> undoList;
    private List<Board> redoList;

    public MoveList(){
        this.undoList = new ArrayList<>();
        this.redoList = new ArrayList<>();

    }

    public Board undo() {
        if (this.undoList.size() == 1){
            return this.undoList.get(this.undoList.size() - 1);
        }else if (this.undoList.get(this.undoList.size() - 1).justFought()){
            return (this.undoList.get(this.undoList.size() - 1));

        }else {
            this.redoList.add(this.undoList.get(this.undoList.size() - 1));
            this.undoList.remove(this.undoList.size() - 1);
            return this.undoList.get(this.undoList.size() - 1);
        }

    }
    public Board redo() {
        if (this.redoList.size() == 1){
            return this.redoList.get(this.redoList.size() - 1);
        }else {
            this.undoList.add(this.redoList.get(this.redoList.size() - 1));
            this.redoList.remove(this.redoList.size() - 1);
            return this.redoList.get(this.redoList.size() - 1);
        }

    }

    public void addMove(Board engineBoard, Boolean fight){

        engineBoard.setJustFought(fight);
        this.undoList.add(engineBoard);
        this.redoList.clear();
    }
}
