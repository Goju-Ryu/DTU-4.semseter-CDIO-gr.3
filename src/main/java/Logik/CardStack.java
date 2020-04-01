package Logik;

import Logik.error.IllegalMoveException;

import java.util.ArrayList;
import java.util.List;

public class CardStack implements I_StackModel {

    private List<I_CardModel> cards;

    public CardStack(List<I_CardModel> cards) {
        this.cards = cards;
    }

    public CardStack(I_CardModel ... cards) {
        this.cards = List.of(cards);
    }




    public boolean canMoveTo(I_StackModel cardModel) {
        return false;
    }

    public void moveTo(I_StackModel stackModel) throws IllegalMoveException {

    }

    public I_StackModel splitAt(int position) {
        if (cards == null)
            return null; //TODO Discuss if we should use Optional to avoid the null
        if (cards.size() < 1)
            return null;
        if (cards.size() < position)
            throw new IndexOutOfBoundsException("Cannot split at index: " + position + " size of stack is: " + cards.size());

        return null;
    }



    //--------------------------------------------------//
    //                    Getters                       //
    //--------------------------------------------------//

    /**
     * @return the card at the top of the stack or null if not found
     */
    public I_CardModel getFirst() {
        return getCardAt(0);
    }

    /**
     * @return the card with index of position in the stack or null if not found
     */
    public I_CardModel getCardAt(int position) {
        if (cards == null)
            return null; //TODO Discuss if we should use Optional to avoid the null
        if (cards.size() < 1)
            return null;
        if (cards.size() < position)
            throw new IndexOutOfBoundsException("Cannot get at index: " + position + " size of stack is: " + cards.size());

        return cards.get(position);
    }

    /**
     * @return the card at the bottom of the stack or null if not found
     */
    public I_CardModel getLast() {
        return getCardAt(cards.size() - 1);
    }
}
