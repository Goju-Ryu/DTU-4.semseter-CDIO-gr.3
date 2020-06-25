package model;

import model.cabal.Board;
import model.cabal.E_PileID;

import static model.cabal.E_PileID.DRAWSTACK;

public class Move implements I_Move {

    E_PileID to;
    E_PileID from;
    int index;
    int depth;
    boolean improveAce = false;
    boolean improveShow= false;
    String toStr = "";

    public Move( E_PileID to, E_PileID from, int index, boolean improveAce, boolean improveShow, String toStr) {
        this.to = to;
        this.from = from;
        this.index = index;
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
        return index;
    }

    @Override
    public Board move(Board board){
       // board.move(from, depth, to);
        return null;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        String Cards =" cards";
        String firstpart = "";
        if(from == DRAWSTACK)
            if(depth == 1)
                Cards = " card";
            firstpart = "from the drawstack turn " + depth + Cards + " to get to the card.";

        return "from:" + from + ". to:" + to + ". turn " + depth + " cards to find the card";
    }
}
