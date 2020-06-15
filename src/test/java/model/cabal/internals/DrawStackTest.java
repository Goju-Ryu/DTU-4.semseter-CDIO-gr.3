package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DrawStackTest {

    @Test
    void popSubset() {
        DrawStack drawStack = createDrawstack(4,E_CardSuit.CLUBS);
        I_CardModel card = new Card(E_CardSuit.CLUBS,1,true);

        // You need to turn a Card before you can pop it from the pile
        drawStack.turnCard();

        if (drawStack.canMoveFrom(0)){
            Collection<I_CardModel> drawStack1 = drawStack.popSubset();

            System.out.println("Card in that is popped from the drawstack: "+drawStack1);
            System.out.println("Card to compare with: "+card);

            assertEquals(card,drawStack1.iterator().next());

            // If you try to pop more than 1 element from the draw stack, an IllegalMoveException will be thrown
            assertThrows(IllegalMoveException.class,
                    ()-> drawStack.popSubset(1),
                    "You cannot pop more than 1 card at a time, so range should be 0");
        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {

    }

    @Test
    void canMoveTo() {
        DrawStack drawStack = new DrawStack();


    }

    @Test
    void turnCard() {
    }

    @Test
    void getTopCard() {
    }

    private DrawStack createDrawstack(int elements, E_CardSuit suit) {
        DrawStack drawStack = new DrawStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i + 1,true);
            drawStack.add(card);
        }

        return drawStack;
    }
}