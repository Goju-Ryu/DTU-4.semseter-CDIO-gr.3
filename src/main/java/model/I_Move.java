package model;

import model.cabal.Board;

public interface I_Move {


    /**
     * @return a true or false value, if the move reveals a new card in the pile. where the cards are moved from.
     * */
    boolean improvesByTurningCard();

    /**
     * @return a true or false value, in the first priority it will return true if it moves a card into an Ace pile.
     * */
    boolean improvesAceCondition();

    /**
     * @return an index that reveals what stack is being moved from
     * */
    int moveFromStack_index();

    /**
     * @return an index that reveals what stack is being moved to
     * */
    int moveToStack_index();

    /**
     * @return an index that reveals how deep the move is moving from a stack.
     * */
    int moveFromRange();

    /**
     * @return a Board where the move has been executed.
     * */
    Board move(Board board);

}