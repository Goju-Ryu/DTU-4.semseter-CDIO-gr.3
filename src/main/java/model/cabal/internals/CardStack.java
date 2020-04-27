package model.cabal.internals;

import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardStack extends A_StackModel {

    public CardStack(List<I_CardModel> cards) {
        this.cards = cards;
    }

    public CardStack(I_CardModel ... cards) {
        this.cards = new ArrayList<>();
        this.cards.addAll(Arrays.asList(cards));
    }

    public boolean canMoveTo(A_StackModel destination) {
        if (destination == null) //TODO dette burde kaste en exception
            throw new NullPointerException("Destination is null!");
        if ( !destination.getLast().isFacedUp() )
            return false;
        if ( destination.size() == 0 && this.getFirst().getRank() != 13)
            return false;
        if ( E_CardSuit.isSameColour(destination.getLast().getSuit(), this.getFirst().getSuit()) )
            return false;
        if (destination.getLast().getRank() - 1 != this.getFirst().getRank() )
            return false;
        return true;
    }

    public void moveTo(A_StackModel stackModel) throws IllegalMoveException {
        if ( !canMoveTo(stackModel) )
            throw
                    new IllegalMoveException(
                            "You tried to move stack \"" +this+ "\" to stack \"" +stackModel+ "\". Maybe "
                                    +this.getFirst()+ " and "
                                    +(stackModel != null && stackModel.cards != null ? stackModel.getLast() : null)+
                                    " don't match?"
                    );

        stackModel.addToStack((I_CardModel) cards); //Todo: figure out why test throws error
        this.cards.removeIf(e -> true);
    }

    public A_StackModel splitAt(int position) {
        if (cards == null)
            return null; //TODO Discuss if we should use Optional to avoid the null
        if (cards.size() < 1)
            return null;
        if (cards.size() < position)
            throw new IndexOutOfBoundsException("Cannot split at index: " + position + " size of stack is: " + cards.size());

        List<I_CardModel> returnable = cards.subList(position + 1, cards.size());
        cards = cards.subList(0, position + 1);
        return new CardStack(returnable);
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
