package history;

import data.InputSimDTO;
import model.GameCardDeck;
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

    @Test
    void isRepeatState() {
        I_BoardModel board;
        InputSimDTO input;
        GameHistory hist;

        GameCardDeck deck = new GameCardDeck();
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
        ), deck);

        input = new InputSimDTO(board, deck);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);

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
        I_BoardModel board;
        InputSimDTO input;
        GameHistory hist;
        GameCardDeck deck = new GameCardDeck();
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
        ), deck);

        input = new InputSimDTO(board, deck);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);

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
        I_BoardModel board;
        InputSimDTO input;
        GameHistory hist;
        GameCardDeck deck = new GameCardDeck();
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
        ), deck);

        input = new InputSimDTO(board, deck);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);


        assertEquals(0, hist.history.size());

        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());

        assertEquals(1, hist.history.size());
    }

    @Test
    void hasNext() {
        I_BoardModel board;
        InputSimDTO input;
        GameHistory hist;
        GameCardDeck deck = new GameCardDeck();
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
        ), deck);

        input = new InputSimDTO(board, deck);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);

        assertFalse(hist.hasNext());
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
//        board.move(SUITSTACKHEARTS, BUILDSTACK2, input.getUsrInput());
        assertTrue(hist.hasNext());
    }

    @Test
    void next() {
        I_BoardModel board;
        InputSimDTO input;
        GameHistory hist;
        GameCardDeck deck = new GameCardDeck();
        board = new Board(Map.of(
                BUILDSTACK1.name(), new Card(HEARTS, 1),
                BUILDSTACK2.name(), new Card(CLUBS, 2)
        ), deck);

        input = new InputSimDTO(board, deck);
        hist = new GameHistory();

        board.addPropertyChangeListener(hist);

        assertThrows(NoSuchElementException.class, () -> hist.next());
        board.move(BUILDSTACK1, SUITSTACKHEARTS, input.getUsrInput());
        assertEquals(I_GameState.class, hist.next().getClass());
        assertThrows(NoSuchElementException.class, () -> hist.next());
    }
}