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

        SuitStack suitStack = createSuitStack(10,true);
        SuitStack suitStack1 = createSuitStack(4,false);
        SuitStack suitStack2 = new SuitStack();

        //Check that a card can be moved from the top of the stack
        assertTrue(suitStack.canMoveFrom(1));

        // Check that an exception will be thrown if range is greater than 1
        assertFalse(suitStack.canMoveFrom(2));

        // Check if the function returns false if the card to be moved is face down
        assertFalse(suitStack1.canMoveFrom(1));

        if (suitStack2.isEmpty()){
            System.out.println("isEmpty test passed");

            // Check if an exception will be thrown if the suit stack is empty an we want to know if we can move the top card
            assertFalse(suitStack2.canMoveFrom(1));
        }else {
            fail();
        }
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