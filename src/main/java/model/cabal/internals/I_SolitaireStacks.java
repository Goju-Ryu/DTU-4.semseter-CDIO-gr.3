package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Collection;

public interface I_SolitaireStacks extends Collection {

    @Override
    boolean addAll(Collection c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. The top card must be face up.
     *
     * @param range
     * @return
     * @throws IllegalMoveException
     */
    Collection<I_CardModel> popSubset(int range) throws IllegalMoveException;

    /**
     *
     * @param position
     * @return
     */
    I_CardModel getCard(int position);

    /**
     * returns
     */
    int size();

    /**
     *
     * @param card
     * @return
     */
    boolean contains(I_CardModel card);

    /**
     *
     * @return
     */
    boolean canMoveFrom(int range);


    default boolean canMoveFrom(){
        return canMoveFrom(0);
    }

    /**
     *
     * @return
     */
    boolean canMoveTo(Collection cards);

}
