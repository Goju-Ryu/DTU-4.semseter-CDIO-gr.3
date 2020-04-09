package Logik.cabal.internals;

import Logik.cabal.internals.card.Card;
import Logik.cabal.internals.card.E_CardRank;
import Logik.cabal.internals.card.E_CardSuit;
import Logik.cabal.internals.card.I_CardModel;
import Logik.error.IllegalMoveException;
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

        stack.addToStack(    new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE),
                             new Card(),
                             new Card(),
                             new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT)
        );

        assertNotNull(stack.cards);
        assertEquals(5, stack.cards.size());
    }
}