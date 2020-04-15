package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardStackTest {
    CardStack stack;

    @BeforeEach
    void beforeEach() {
         stack = new CardStack();
    }

    void fillStack(A_StackModel stack) {
        final List<I_CardModel> newCards = new ArrayList<I_CardModel>();
        newCards.add(new Card(E_CardSuit.DIAMONDS, 5, true));
        newCards.add(new Card());
        newCards.add(new Card());
        newCards.add(new Card(E_CardSuit.CLUBS, 8, true));
        stack.addToStack(newCards);
    }


    @Test
    void emptyConstructor() {
        CardStack stack = new CardStack();

        assertNotNull(stack.cards);
        assertEquals(stack.cards.size(), 0);
    }

    @Test
    void canMoveTo() {
        A_StackModel otherStack = new CardStack();
        fillStack(stack);

        otherStack.addToStack(new Card());
        assertFalse(stack.canMoveTo(otherStack)); //TODO: Skal diskuteres om dette bør være en exception i stedet

        otherStack.addToStack(new Card(E_CardSuit.SPADES, 5, true));
        assertFalse(stack.canMoveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.DIAMONDS, 6, true));
        assertFalse(stack.canMoveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.SPADES, 6, true));
        assertTrue(stack.canMoveTo(otherStack));
    }

    @Test
    void moveTo() {
        final A_StackModel otherStack = new CardStack();
        fillStack(stack);

        otherStack.addToStack(new Card());
        assertThrows(IllegalMoveException.class, () -> stack.moveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.SPADES, 5, true));
        assertThrows(IllegalMoveException.class, () -> stack.moveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.DIAMONDS, 6, true));
        assertThrows(IllegalMoveException.class, () -> stack.moveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.SPADES, 6, true));
        stack.moveTo(otherStack);
        //TODO: Test that the stack moved successfully

        //TODO: test edge cases
    }

    @Test
    void splitAt() {
        stack.addToStack(
                new Card(E_CardSuit.CLUBS, 1, true),
                new Card(E_CardSuit.CLUBS, 2, true),
                new Card(E_CardSuit.CLUBS, 3, true),
                new Card(E_CardSuit.CLUBS, 4, true),
                new Card(E_CardSuit.CLUBS, 5, true)
        );
        CardStack newStack = (CardStack) stack.splitAt(2);

        assertEquals(3, stack.cards.size());
        assertEquals(2, newStack.cards.size());

        stack = new CardStack();
        stack.addToStack(
                new Card(),
                new Card(),
                new Card(E_CardSuit.CLUBS, 3, true),
                new Card(E_CardSuit.CLUBS, 4, true),
                new Card(E_CardSuit.CLUBS, 5, true)
        );
        newStack = (CardStack) stack.splitAt(2);

        assertEquals(3, stack.cards.size());
        assertEquals(2, newStack.cards.size());
    }


    @Test
    void getFirst() {
        fillStack(stack);
        assertNotNull(stack.getFirst());
        assertEquals(stack.getFirst().getRank(), 5);
        assertEquals(stack.getFirst().getSuit(), E_CardSuit.DIAMONDS);
    }

    @Test
    void getCardAt() {
        stack.addToStack(
                new Card(E_CardSuit.CLUBS, 1, true),
                new Card(E_CardSuit.CLUBS, 2, true),
                new Card(E_CardSuit.CLUBS, 3, true),
                new Card(E_CardSuit.CLUBS, 4, true),
                new Card(E_CardSuit.CLUBS, 5, true)
        );

        assertEquals(stack.getFirst(), stack.getCardAt(0));
        assertEquals(3, stack.getCardAt(2).getRank());
        assertEquals(stack.getLast(), stack.getCardAt(stack.cards.size()-1));
    }

    @Test
    void getLast() {
        fillStack(stack);
        assertNotNull(stack.getLast());
        assertEquals(8, stack.getLast().getRank());
        assertEquals(E_CardSuit.CLUBS, stack.getLast().getSuit());
    }
}