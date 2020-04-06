package Logik;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardStackTest {
    CardStack stack;

    @BeforeEach
    void beforeEach() {
         stack = new CardStack();
    }

    void fillStack() {
        final List<I_CardModel> newCards = new ArrayList<I_CardModel>();
        newCards.add(new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE));
        newCards.add(new Card());
        newCards.add(new Card());
        newCards.add(new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT));
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
    }

    @Test
    void moveTo() {
    }

    @Test
    void splitAt() {
    }


    @Test
    void getFirst() {
        fillStack();
        assertNotNull(stack.getFirst());
        assertEquals(stack.getFirst().getRank(), E_CardRank.FIVE);
        assertEquals(stack.getFirst().getSuit(), E_CardSuit.DIAMONDS);
    }

    @Test
    void getCardAt() {
    }

    @Test
    void getLast() {
        fillStack();
        assertNotNull(stack.getLast());
        assertEquals(E_CardRank.EIGHT, stack.getLast().getRank());
        assertEquals(E_CardSuit.CLUBS, stack.getLast().getSuit());
    }
}