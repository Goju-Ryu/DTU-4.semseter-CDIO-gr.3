package model;

import model.cabal.Board;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

public class Move implements I_Move {

    int to = 0;
    int from = 0;
    int depth = 0;
    boolean improveAce = false;
    boolean improveShow= false;
    String toStr = "";

    public Move(int to, int from, int depth, boolean improveAce, boolean improveShow, String toStr) {
        this.to = to;
        this.from = from;
        this.depth = depth;
        this.improveAce = improveAce;
        this.improveShow = improveShow;
        this.toStr = toStr;
    }

    @Override
    public boolean improvesByTurningCard() {
        return improveShow;
    }

    @Override
    public boolean improvesAceCondition() {
        return improveAce;
    }

    @Override
    public int moveFromStack_index() {
        return from;
    }

    @Override
    public int moveToStack_index() {
        return to;
    }

    @Override
    public int moveFromRange() {
        return depth;
    }

    @Override
    public Board move(Board board){
       // board.move(from, depth, to);
        return null;
    }

    @Override
    public String toString() {
        return toStr;
    }
}
