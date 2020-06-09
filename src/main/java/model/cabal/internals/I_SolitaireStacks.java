package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Collection;

public interface I_SolitaireStacks extends Collection {

    @Override
    boolean addAll(Collection c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. all the cards must be face up
     *
     * @param range
     * @return
     * @throws IllegalMoveException
     */
    Collection<I_CardModel> popSubset(int range) throws IllegalMoveException;

    /**
     * Returns a card from the list on a specified position.
     *
     * @param position The position in the list
     * @return The card
     */
    I_CardModel getCard(int position);

    /**
     * returns the size of the list
     */
    int size();

    /**
     * Checks if the list contains a certain card
     *
     * @param card The card we are looking for
     * @return true if the list contains the card and false if it dosent
     */
    boolean contains(I_CardModel card);


    /**
     *
     * @param range
     * @return
     */
    boolean canMoveFrom(int range);


    default boolean canMoveFrom(){
        return canMoveFrom(0);
    }

    /**
     *
     *
     * @return
     */
    boolean canMoveTo(Collection cards);

}
