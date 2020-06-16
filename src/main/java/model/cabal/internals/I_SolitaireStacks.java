package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface I_SolitaireStacks extends Collection<I_CardModel> {

    /**
     * Adds a Collection to another Collection
     *
     * @param c the Collection we want to add to another collection
     * @return If the Collection was added return true, otherwise return false
     * @throws IllegalMoveException If the canMoveTo(c) method would return false
     */
    @Override
    boolean addAll(@Nonnull Collection<? extends I_CardModel> c) throws IllegalMoveException;

    /**
     * Removes a subset of the solitaire stack. all the cards must be face up
     *
     * @param range The position in the list where you would like to split it to make the new subset of the list
     * @return The new sublist
     * @throws IllegalMoveException If the canMoveFrom(range) method would return false
     */
    Collection<I_CardModel> popSubset(int range) throws IllegalMoveException;

    /**
     * Returns a card from the list on a specified position.
     *
     * @param position The position in the list
     * @return The card
     */
    I_CardModel getCard(int position); //TODO should this be copies to avoid outside interference?

    /**
     * Returns a subset of the solitaire stack.
     *
     * @param range The position in the list where you would like to split it to make the new subset of the list
     * @return The new sublist
     */
    Collection<I_CardModel> getSubset(int range);


    /**
     * Checks if you can move a Stack of the CardStack from that position in the card stack
     *
     * - If some of the cards in the Stack are face down then you cant move the stack
     *
     * @param range the range that will be moved from.
     * @return true if the stack can be moved and false if it can't.
     */
    boolean canMoveFrom(int range);


    /**
     * Check if the card stack can be moved away from this stack
     *
     * - If you have a facedown card in the stack at the range because it is a build stack then we cant move the
     *   card.
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
    boolean canMoveTo(@NonNullType Collection<I_CardModel> cards);

    /**
     * Checks if the list contains a specific card
     *
     * @param card The card in the list
     * @return true if it does contain the card and false if it doesent
     */
    boolean containsCard(I_CardModel card);

}
