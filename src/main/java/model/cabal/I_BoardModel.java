package model.cabal;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

public interface I_BoardModel {

    //Methods for the cardPile and the turnPile

    I_CardModel turnCard();
    I_CardModel getTurnedCard();

    //Methods for the suitPile



    //Methods for the buildStacks



    //Move card methods

    /**
     *
     *
     * @param origin
     * @param originPos
     * @param destination
     * @throws IllegalMoveException
     */
    void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException;

    /**
     *
     * @param origin
     * @param originPos
     * @param destination
     * @throws IllegalMoveException
     */
    void isLegalMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException;

    /**
     *
     * @param origin
     * @param destination
     * @throws IllegalMoveException
     */
    void isLegalMove(E_PileID origin, E_PileID destination) throws IllegalMoveException;

}
