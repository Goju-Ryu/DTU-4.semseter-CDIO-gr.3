package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SuitStackTest {

    @Test
    void popSubset() {
        SuitStack suitStack = createSuitStack(4,E_CardSuit.HEARTS,true);

        for (I_CardModel card : suitStack){
            System.out.println("The old suit stack: "+card);
        }

        I_CardModel card = new Card(E_CardSuit.HEARTS,4,true);

        if (suitStack.canMoveFrom(1)){
            System.out.println("passed canMoveFrom test");

            Collection<I_CardModel> suitStack1 = suitStack.popSubset(1);

            for (I_CardModel card1 : suitStack){
                System.out.println("The new suit stack: "+card1);
            }

            System.out.println("Card in that is popped from the drawstack: "+suitStack1.iterator().next());
            System.out.println("Card to compare with: "+card);

            assertThrows(IllegalMoveException.class,() -> suitStack.popSubset(2));
            assertEquals(card,suitStack1.iterator().next());

        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {

        SuitStack suitStack = createSuitStack(10,E_CardSuit.CLUBS,true);
        SuitStack suitStack1 = createSuitStack(4,E_CardSuit.HEARTS,false);
        SuitStack suitStack2 = new SuitStack();

        assertTrue(suitStack.canMoveFrom(1));
        assertThrows(IllegalArgumentException.class, () -> suitStack.canMoveFrom(2));
        assertFalse(suitStack1.canMoveFrom(1));

        if (suitStack2.isEmpty()){
            System.out.println("isEmpty test passed");
            assertThrows(IndexOutOfBoundsException.class,() -> suitStack2.canMoveFrom(0));
        }else {
            fail();
        }
    }

    @Test
    void canMoveTo() {
    }

    private SuitStack createSuitStack(int elements, E_CardSuit suit, boolean isFaceUp){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1,isFaceUp);
            suitStack.add(card);
        }

        return suitStack;
    }
}