package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SuitStackTest {

    @Test
    void popSubset() {
        SuitStack suitStack = createSuitStack(4,E_CardSuit.HEARTS);

        for (I_CardModel card : suitStack){
            System.out.println(card);
        }

        I_CardModel card = new Card(E_CardSuit.HEARTS,4,true);

        if (suitStack.canMoveFrom(1)){
            System.out.println("passed canMoveFrom test");

            Collection<I_CardModel> suitStack1 = suitStack.popSubset(1);

            System.out.println("Card in that is popped from the drawstack: "+suitStack1.iterator().next());
            System.out.println("Card to compare with: "+card);

            assertEquals(card,suitStack1.iterator().next());
        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {
    }

    @Test
    void canMoveTo() {
    }

    private SuitStack createSuitStack(int elements, E_CardSuit suit){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1,true);
            suitStack.add(card);
        }

        return suitStack;
    }
}