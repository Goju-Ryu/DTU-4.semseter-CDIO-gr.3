package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class SuitStackTest {

    @Test
    void canMoveFrom() {
        canMoveFromSmallList();
        canMoveFromList();
    }

    void canMoveFromSmallList() {
        SuitStack suitStack = createSuitStack(2, true);

        suitStack.popSubset(suitStack.size() - 1);
        suitStack.popSubset(suitStack.size() - 1);
        assertThrows(IndexOutOfBoundsException.class, () -> suitStack.popSubset(suitStack.size() - 1));
    }

    void canMoveFromList() {
        SuitStack suitStack = createSuitStack(10,true);

        //Check that a card cannot be moved from the bottom of the stack
        assertFalse(suitStack.canMoveFrom(1));
        assertFalse(suitStack.canMoveFrom(2));

        // should be able to move top card
        assertTrue(suitStack.canMoveFrom(suitStack.size() - 1));

        //when moved stack shrinks
        assertEquals(10, suitStack.size());
        suitStack.popSubset(suitStack.size() - 1);
        assertEquals(9, suitStack.size());
        suitStack.popSubset(suitStack.size() - 1);
        suitStack.popSubset(suitStack.size() - 1);
        suitStack.popSubset(suitStack.size() - 1);
        assertEquals(6, suitStack.size());

    }

    private SuitStack createSuitStack(int elements, boolean isFaceUp){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(HEARTS, i + 1, isFaceUp);
            suitStack.add(card);
        }

        return suitStack;
    }
}