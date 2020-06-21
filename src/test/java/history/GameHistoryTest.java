package history;

import data.InputSimDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static model.cabal.E_PileID.BUILDSTACK1;
import static model.cabal.E_PileID.BUILDSTACK2;
import static model.cabal.E_PileID.SUITSTACKHEARTS;
import static model.cabal.internals.card.E_CardSuit.CLUBS;
import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {

    private I_BoardModel board;
    private InputSimDTO input;
    private GameHistory hist;

    @Test
    void isRepeatState() {
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
        assertFalse(hist.isRepeatState());
        board.move(SUITSTACKHEARTS, BUILDSTACK2, input.getUsrInput());
        board.move(BUILDSTACK2, SUITSTACKHEARTS, input.getUsrInput());
        assertTrue(hist.isRepeatState());
        board.move(SUITSTACKHEARTS, BUILDSTACK2, input.getUsrInput());
        board.move(BUILDSTACK2, SUITSTACKHEARTS, input.getUsrInput());
        assertTrue( hist.isRepeatState());
    }

    @Test
    void getRepeatStates() {
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
        assertEquals(0, hist.getRepeatStates().size());
        board.move(SUITSTACKHEARTS, BUILDSTACK2, input.getUsrInput());
        board.move(BUILDSTACK2, SUITSTACKHEARTS, input.getUsrInput());
        assertEquals(1, hist.getRepeatStates().size());
        board.move(SUITSTACKHEARTS, BUILDSTACK2, input.getUsrInput());
        board.move(BUILDSTACK2, SUITSTACKHEARTS, input.getUsrInput());
        assertEquals(2, hist.getRepeatStates().size());

    }

    /**
     * Test that a propertyChangeEvent is properly sent to history
     */
    @Test
    void propertyChange(){

        assertEquals(0, hist.history.size());

        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());

        assertEquals(1, hist.history.size());
    }

    @Test
    void hasNext() {
        assertFalse(hist.hasNext());
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
        assertTrue(hist.hasNext());
    }

    @Test
    void next() {
        assertThrows(NoSuchElementException.class, () -> hist.next());
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
        assertEquals(I_GameState.class, hist.next().getClass());
        assertThrows(NoSuchElementException.class, () -> hist.next());
    }

    @BeforeEach
    void makeBoard() {
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
                ));

        input = new InputSimDTO(board);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);
    }
}