package model;

import model.cabal.Board;
import model.cabal.E_PileID;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

public class Move implements I_Move {

    E_PileID to;
    E_PileID from;
    int depth;
    boolean improveAce = false;
    boolean improveShow= false;
    String toStr = "";

    public Move(E_PileID to, E_PileID from, int depth, boolean improveAce, boolean improveShow, String toStr) {
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
    public E_PileID moveFromStack() {
        return from;
    }

    @Override
    public E_PileID moveToStack() {
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
        return "from:" + from + ". to:" + to + ". depth:" + depth;
    }
}
