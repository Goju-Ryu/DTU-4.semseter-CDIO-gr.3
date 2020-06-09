package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Collection;
import java.util.List;

public interface I_SolitaireStacks<T> extends List {

    @Override
    boolean addAll(Collection c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. all the cards must be face up
     *
     * @param range The position in the list where you would like to split it to make the new subset of the list
     * @return The new sublist
     * @throws IllegalMoveException
     */
    List<T> popSubset(int range) throws IllegalMoveException;

    /**
     * Returns a card from the list on a specified position.
     *
     * @param position The position in the list
     * @return The card
     */
    T getCard(int position);

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
