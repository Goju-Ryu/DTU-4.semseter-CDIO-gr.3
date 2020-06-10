package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.beans.PropertyEditor;
import java.util.Collection;

public interface I_SolitaireStacks<cardType extends I_CardModel> extends Collection<cardType>, PropertyEditor {

    /**
     * Adds a Collection to another Collection
     *
     * @param c the Collection we want to add to another collection
     * @return If the Collection was added return true, otherwise return false
     * @throws IllegalMoveException If the canMoveTo(c) method would return false
     */
    @Override
    boolean addAll(@NonNullType Collection<? extends cardType> c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. all the cards must be face up
     *
     * @param range The position in the list where you would like to split it to make the new subset of the list
     * @return The new sublist
     * @throws IllegalMoveException If the canMoveFrom(range) method would return false
     */
    Collection<cardType> popSubset(int range) throws IllegalMoveException;

    /**
     * Returns a card from the list on a specified position.
     *
     * @param position The position in the list
     * @return The card
     */
    cardType getCard(int position); //TODO should this be copies to avoid outside interference?


    /**
     * Checks if you can move a Stack of the CardStack from that position in the Build stack
     *
     * - If some of the cards in the Stack are face down then you cant move the stack
     *
     * @param range the range that will be moved from.
     * @return true if the stack can be moved and false if it can't.
     */
    boolean canMoveFrom(int range);


    /**
     * Check if the top card can be moved away from this stack
     *
     * @return true if the card can be moved and false if it can't.
     */
    default boolean canMoveFrom(){
        return canMoveFrom(0);
    }

    /**
     * Check if you can move a stack of cards to this stack.
     *
     * @param cards The stack that wants to be moved to this stack. Can't be null.
     * @return true if it is legal and false if otherwise.
     */
    boolean canMoveTo(@NonNullType Collection<cardType> cards);

}
