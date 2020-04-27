package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class A_StackModelTest {

    @Test
    void addToStack() {
        A_StackModel stack = new A_StackModel() {
            @Override
            public boolean canMoveTo(A_StackModel stackModel) {
                return false;
            }

            @Override
            public void moveTo(A_StackModel stackModel) throws IllegalMoveException {

            }

            @Override
            public A_StackModel splitAt(int position) {
                return null;
            }

            @Override
            public I_CardModel getFirst() {
                return null;
            }

            @Override
            public I_CardModel getCardAt(int position) {
                return null;
            }

            @Override
            public I_CardModel getLast() {
                return null;
            }
        };
        stack.cards = new ArrayList<>();

        stack.addToStack(    new Card(E_CardSuit.DIAMONDS, 5),
                             new Card(),
                             new Card(),
                             new Card(E_CardSuit.CLUBS, 8)
        );

        assertNotNull(stack.cards);
        assertEquals(4, stack.size());
    }
}