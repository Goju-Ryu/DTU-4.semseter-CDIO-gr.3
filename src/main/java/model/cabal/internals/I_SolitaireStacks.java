package model.cabal.internals;

import model.error.IllegalMoveException;

import java.util.Collection;

public interface I_SolitaireStacks<I_CardModel> extends Collection<I_CardModel> {

    /**
     * Adds a Collection to another Collection
     *
     * @param c the Collection we want to add to another collection
     * @return If the Collection was added return true, otherwise return false
     * @throws IllegalMoveException
     */
    @Override
    boolean addAll(Collection<? extends I_CardModel> c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. all the cards must be face up
     *
     * @param range The position in the list where you would like to split it to make the new subset of the list
     * @return The new sublist
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
     * Checks if you can move a Stack of the CardStack from that position in the Build stack
     *
     *
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
     * @param cards
     * @return
     */
    boolean canMoveTo(Collection<I_CardModel> cards);

}
