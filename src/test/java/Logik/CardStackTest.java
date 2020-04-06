package Logik;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
        newCards.add(new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE, true));
        newCards.add(new Card());
        newCards.add(new Card());
        newCards.add(new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT, true));
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

        otherStack.addToStack(new Card(E_CardSuit.SPADES, E_CardRank.FIVE, true));
        assertFalse(stack.canMoveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.DIAMONDS, E_CardRank.SIX, true));
        assertFalse(stack.canMoveTo(otherStack));

        otherStack.addToStack(new Card(E_CardSuit.SPADES, E_CardRank.SIX, true));
        assertTrue(stack.canMoveTo(otherStack));
    }

    @Test
    void moveTo() {
    }

    @Test
    void splitAt() {
        stack.addToStack(
                new Card(E_CardSuit.CLUBS, E_CardRank.ACE, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.TWO, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.THREE, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.FOUR, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.FIVE, true)
        );
        CardStack newStack = (CardStack) stack.splitAt(2);

        assertEquals(3, stack.cards.size());
        assertEquals(2, newStack.cards.size());

        stack = new CardStack();
        stack.addToStack(
                new Card(),
                new Card(),
                new Card(E_CardSuit.CLUBS, E_CardRank.THREE, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.FOUR, true),
                new Card(E_CardSuit.CLUBS, E_CardRank.FIVE, true)
        );
        newStack = (CardStack) stack.splitAt(2);

        assertEquals(3, stack.cards.size());
        assertEquals(2, newStack.cards.size());
    }


    @Test
    void getFirst() {
        fillStack(stack);
        assertNotNull(stack.getFirst());
        assertEquals(stack.getFirst().getRank(), E_CardRank.FIVE);
        assertEquals(stack.getFirst().getSuit(), E_CardSuit.DIAMONDS);
    }

    @Test
    void getCardAt() {
    }

    @Test
    void getLast() {
        fillStack(stack);
        assertNotNull(stack.getLast());
        assertEquals(E_CardRank.EIGHT, stack.getLast().getRank());
        assertEquals(E_CardSuit.CLUBS, stack.getLast().getSuit());
    }
}