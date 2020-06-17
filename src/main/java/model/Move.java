package model;

import model.cabal.Board;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

public class Move implements I_Move {

    int to = 0;
    int from = 0;
    int depth = 0;
    public Move(int to, int from, int depth) {
        this.to = to;
        this.from = from;
        this.depth = depth;
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
}
