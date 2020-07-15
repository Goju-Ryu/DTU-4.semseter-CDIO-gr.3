package history;

import model.cabal.internals.card.Card;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.*;

class I_GameHistoryTest {

    @Test
    void identityEqual() {
        State state1 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state2 = state1;
        State state3 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));

        assertTrue(I_GameHistory.IDENTITY_EQUAL.test(state1, state2));
        assertFalse(I_GameHistory.IDENTITY_EQUAL.test(state1, state3));
        assertTrue(I_GameHistory.IDENTITY_EQUAL.test(state1, state1.clone()));
        assertTrue(I_GameHistory.IDENTITY_EQUAL.test(state1, new State(state1)));
        assertTrue(I_GameHistory.IDENTITY_EQUAL.test(state1, new State(Map.copyOf(state1))));
    }

    @Test
    void sizeEqual() {
        State state1 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state2 = state1;
        State state3 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state4 = new State(Map.of(DRAWSTACK, List.of(new Card(CLUBS, 1), new Card(HEARTS, 2))));
        State state5 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2), new Card(HEARTS, 3))));
        State state6 = new State(
                Map.of(
                        DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(CLUBS, 2)),
                        BUILDSTACK_1, List.of(new Card(HEARTS, 3)))
        );

        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state2));
        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state3));
        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state1.clone()));
        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, new State(state1)));
        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, new State(Map.copyOf(state1))));
        assertTrue(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state4));
        assertFalse(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state5));
        assertFalse(I_GameHistory.PILE_SIZE_EQUAL.test(state1, state6));
    }

    @Test
    void contentEqual() {
        State state1 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state2 = state1;
        State state3 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state4 = new State(Map.of(DRAWSTACK, List.of(new Card(CLUBS, 1), new Card(HEARTS, 2))));
        State state5 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2), new Card(HEARTS, 3))));
        State state6 = new State(
                Map.of(
                        DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(CLUBS, 2)),
                        BUILDSTACK_1, List.of(new Card(HEARTS, 3)))
        );

        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state2));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state3));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state1.clone()));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, new State(state1)));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, new State(Map.copyOf(state1))));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state4));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state5));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state6));
    }

    @Test
    void fullEqual() {
        State state1 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state2 = state1;
        State state3 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2))));
        State state4 = new State(Map.of(DRAWSTACK, List.of(new Card(CLUBS, 1), new Card(HEARTS, 2))));
        State state5 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2), new Card(HEARTS, 3))));
        State state6 = new State(
                Map.of(
                        DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(CLUBS, 2)),
                        BUILDSTACK_1, List.of(new Card(HEARTS, 3)))
        );

        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state2));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state3));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state1.clone()));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, new State(state1)));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, new State(Map.copyOf(state1))));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state4));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state5));
        assertFalse(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state6));
    }

}