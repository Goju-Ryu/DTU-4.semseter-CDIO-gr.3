package history;

import model.cabal.internals.card.Card;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static model.cabal.E_PileID.DRAWSTACK;
import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void cloneTest() {
        State state1 = new State(Map.of(DRAWSTACK, List.of(new Card(HEARTS, 1), new Card(HEARTS, 2)))); //todo Use test board when merged
        State state2 = state1.clone();

        assertNotSame(state1, state2);
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state2));
        assertTrue(I_GameHistory.PILE_CONTENT_EQUAL.test(state1, state2));
    }
}