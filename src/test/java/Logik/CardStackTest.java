package Logik;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardStackTest {

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
        stack.addToStack(List.of(
                new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE),
                new Card(),
                new Card(),
                new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT)
                )
        );
        assertEquals(stack.getLast().getRank(), E_CardRank.FIVE);
        assertEquals(stack.getLast().getSuit(), E_CardSuit.DIAMONDS);
    }

    @Test
    void getCardAt() {
    }

    @Test
    void getLast() {
        CardStack stack = new CardStack();
        stack.addToStack(List.of(
                new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE),
                new Card(),
                new Card(),
                new Card(E_CardSuit.CLUBS, E_CardRank.EIGHT)
                )
        );
        assertEquals(stack.getLast().getRank(), E_CardRank.EIGHT);
        assertEquals(stack.getLast().getSuit(), E_CardSuit.CLUBS);
    }
}