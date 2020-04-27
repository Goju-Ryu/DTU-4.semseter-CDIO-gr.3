package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Arrays;
import java.util.List;

/**
 * This Interface describes the behavior of a stack of cards.
 */
public abstract class A_StackModel {

    protected List<I_CardModel> cards = List.of();

    /**
     * Checks if a move operation is valid
     * @param stackModel The stack you want to move to
     * @return true if it is a valid move, false otherwise
     */
    abstract public boolean canMoveTo(A_StackModel stackModel);

    /**
     * Moves one stack to be part of another
     * @param stackModel The stack you want to move to
     * @throws IllegalMoveException If a stack cannot be moved due to rules constraints
     */
    abstract public void moveTo(A_StackModel stackModel) throws IllegalMoveException;

    /**
     * Splits a stack into two objects.
     * @param position index at which the stack is split. The first stack contains all elements up to and including position.
     * @return returns the stack split off from this object
     * @throws IndexOutOfBoundsException if trying to split at index not within 0 to size.
     */
    abstract public A_StackModel splitAt(int position);


    /**
     * @return the card at the top of the stack or null if not found
     */
    abstract public I_CardModel getFirst();

    /**
     * @return the card with index of position in the stack or null if not found
     */
    abstract public I_CardModel getCardAt(int position);

    /**
     * @return the card at the bottom of the stack or null if not found
     */
    abstract public I_CardModel getLast();

    /**
     * @return number of cards in the stack
     */
    public int size() {
        return cards.size();
    }



    //--------------------------------------------------//
    //                    Helpers                       //
    //--------------------------------------------------//

    /**
     * Adds a list of cards to the stack.
     * This is a method is meant to only be called from other stacks to enable moving between them without a
     * function to add cards from the outside
     * @param newCards The cards to be added to this stack.
     * @throws RuntimeException if the add
     */
    private void addToStack(List<I_CardModel> newCards) {
        //if the addAll method is not implemented in the list type then make new list manually
        if ( !cards.addAll(newCards) ) {
            int newSize = cards.size() + newCards.size();
            I_CardModel[] newList = new I_CardModel[newSize];

            //Copy lists into array
            for (int i = 0; i < newSize; i++) {
                if (i < cards.size()) {
                    newList[i] = cards.get(i);
                } else {
                    newList[i] = newCards.get( i % cards.size() );
                }
            }

            cards = Arrays.asList(newList);
        }
    }

    /**
     * Method is for ease of use and is equivalent to the list implementation of the same name
     * @param newCards an array of cards to be added to the stack
     */
    protected void addToStack(I_CardModel ... newCards) {
        addToStack(Arrays.asList(newCards));
    }
}
