package model.cabal;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

public interface I_BoardModel {

    //Methods for the cardPile and the turnPile

    I_CardModel turnCard();
    I_CardModel getTurnedCard();

    //Methods for the suitPile

    boolean isStackComplete();

    //Move card methods

    /**
     * This function will move a stack of cards from one destination to another
     *
     * @param origin Where the pile is moved from
     * @param originPos The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException
     */
    void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException;

    /**
     * This function will move a stack of cards from one destination to another.
     * It will be used for the turned pile or the ace piles because you can only move the top cards from those piles
     *
     * We dont have to implement this
     *
     * @param origin Where the pile is moved from
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException
     */
    default void move(E_PileID origin, E_PileID destination) throws IllegalMoveException {
        move(origin, 0, destination);
    }

    /**
     * This function will determine if the move is legal when you look at the rules
     *
     * @param origin Where the pile is moved from
     * @param originPos The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException
     * @return true if legal or false if illegal
     */
    boolean isLegalMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException;

    /**
     * This function will do the same as the one above, but you dont have to define the position where you
     * would split the pile.
     *
     * This will be useful when moving from turnPile or suitPiles because you can only take the top card in the pile.
     *
     * We dont have to implement this.
     *
     * @param origin Where the pile is moved from
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException
     * @return true if legal or false if illegal
     */
    default boolean isLegalMove(E_PileID origin, E_PileID destination) throws IllegalMoveException{
        return isLegalMove(origin,0,destination);
    }

}
