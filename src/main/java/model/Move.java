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
        String time = " times";
        String Cards =" cards";
        String firstpart = "";
        String secondPart= "";
        if(from == DRAWSTACK) {
            if (depth == 1) {
                Cards = " card";
                time = " time";
            }
            firstpart = "from the drawstack turn " + depth + Cards +"\n" ;
            secondPart = "";
        }
        else {
            boolean acePile = false;
            String name = "";
            switch (from) {
                case SUITSTACKHEARTS:
                    acePile = true;
                    name = "the Heart Ace pile";
                    break;
                case SUITSTACKCLUBS:
                    acePile = true;
                    name = "the Clubs Ace pile";
                    break;
                case SUITSTACKSPADES:
                    acePile = true;
                    name = "the Spades Ace pile";
                    break;
                case SUITSTACKDIAMONDS:
                    acePile = true;
                    name = "the Diamond Ace pile";
                    break;
                case BUILDSTACK1:
                    name = "the first building stack from the left";
                    break;
                case BUILDSTACK2:
                    name = "the second building stack from the left";
                    break;
                case BUILDSTACK3:
                    name = "the third building stack from the left";
                    break;
                case BUILDSTACK4:
                    name = "the fourth building stack from the left";
                    break;
                case BUILDSTACK5:
                    name = "the fith building stack from the left";
                    break;
                case BUILDSTACK6:
                    name = "the sixth building stack from the left";
                    break;
                case BUILDSTACK7:
                    name = "the seventh building stack from the left";
                    break;
            }
            firstpart = "draw from " + name + "\n";
            if (acePile)
                secondPart = "draw the top card from here\n";
            else {
                secondPart = "draw the stack of cards from card number " + depth +"\n" ;
                if(depth == 1)
                    secondPart = "draw the first card\n";

            }
        }

        // TO stacks
        boolean acePile = false;
        String name ="";
        String thirdPart = "";
        switch (to) {
            case SUITSTACKHEARTS:
                acePile = true;
                name = "the Heart Ace pile";
                break;
            case SUITSTACKCLUBS:
                acePile = true;
                name = "the Clubs Ace pile";
                break;
            case SUITSTACKSPADES:
                acePile = true;
                name = "the Spades Ace pile";
                break;
            case SUITSTACKDIAMONDS:
                acePile = true;
                name = "the Diamond Ace pile";
                break;
            case BUILDSTACK1:
                name = "the first building stack from the left";
                break;
            case BUILDSTACK2:
                name = "the second building stack from the left";
                break;
            case BUILDSTACK3:
                name = "the third building stack from the left";
                break;
            case BUILDSTACK4:
                name = "the fourth building stack from the left";
                break;
            case BUILDSTACK5:
                name = "the fith building stack from the left";
                break;
            case BUILDSTACK6:
                name = "the sixth building stack from the left";
                break;
            case BUILDSTACK7:
                name = "the seventh building stack from the left";
                break;
        }
        thirdPart = "and move to "+ name +"\n";

        String a = firstpart + secondPart  + thirdPart;
        //return "from:" + from + ". to:" + to + ". turn " + depth + " cards to find the card";
        return a;
    }
}
