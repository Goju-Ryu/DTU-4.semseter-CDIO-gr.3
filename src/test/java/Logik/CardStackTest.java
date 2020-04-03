package Logik;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardStackTest {

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
        CardStack stack = new CardStack();
        stack.addToStack(new ArrayList<I_CardModel>(){{
                new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE);
                        new Card();
                        new Card();
                        new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT);
            }}
        );
        assertNotNull(stack.getFirst());
        assertEquals(stack.getFirst().getRank(), E_CardRank.FIVE);
        assertEquals(stack.getFirst().getSuit(), E_CardSuit.DIAMONDS);
    }

    @Test
    void getCardAt() {
    }

    @Test
    void getLast() {
        CardStack stack = new CardStack();
        stack.addToStack(new ArrayList<I_CardModel>(){{
                             new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE);
                             new Card();
                             new Card();
                             new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT);
                         }}
        );
        assertNotNull(stack.getLast());
        assertEquals(E_CardRank.EIGHT, stack.getLast().getRank());
        assertEquals(E_CardSuit.CLUBS, stack.getLast().getSuit());
    }
}