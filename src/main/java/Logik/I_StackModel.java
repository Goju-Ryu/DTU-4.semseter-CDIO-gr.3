package Logik;

import Logik.error.IllegalMoveException;

/**
 * This Interface describes the behavior of a stack of cards.
 */
public interface I_StackModel {

    /**
     * Checks if a move operation is valid
     * @param stackModel The stack you want to move to
     * @return true if it is a valid move, false otherwise
     */
    boolean canMoveTo(I_StackModel stackModel);

    /**
     * Moves one stack to be part of another
     * @param stackModel The stack you want to move to
     * @throws IllegalMoveException If a stack cannot be moved due to rules constraints
     */
    void moveTo(I_StackModel stackModel) throws IllegalMoveException;

    /**
     * Splits a stack into two objects.
     * @param position index at which the stack is split. The first stack contains all elements up to and including position.
     * @return returns an array of 2 stacks or null.
     */
    I_StackModel[] splitAt(int position);


    /**
     * @return the card at the top of the stack or null if not found
     */
    I_CardModel getFirst();

    /**
     * @return the card with index of position in the stack or null if not found
     */
    I_CardModel getCardAt(int position);

    /**
     * @return the card at the bottom of the stack or null if not found
     */
    I_CardModel getLast();


}
