package history;

import model.cabal.E_PileID;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static model.cabal.E_PileID.*;
import static util.TestUtil.getTestReadyBoard;
import static model.cabal.internals.card.E_CardSuit.CLUBS;
import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {

    @Test
    void addState() {
        var hist = new GameHistory();
        var stateMap = new EnumMap<E_PileID, List<I_CardModel>>( Map.of(
                BUILDSTACK_1, List.of(new Card(HEARTS, 1)),
                BUILDSTACK_2, List.of(new Card(CLUBS, 2)))
        );

        hist.currentState = new State(stateMap);
        assertEquals(1, hist.history.size());

        hist.addGameState();
        assertEquals(2, hist.history.size());

        stateMap.put(BUILDSTACK_3, List.of(new Card(), new Card(HEARTS, 3)));
        hist.currentState = new State(stateMap);
        hist.addGameState();
        assertEquals(3, hist.history.size());
    }

    @Test
    void getRepeatStates() {
        var hist = new GameHistory();
        var stateMap = new EnumMap<E_PileID, List<I_CardModel>>( Map.of(
                BUILDSTACK_1, List.of(new Card(HEARTS, 1)),
                BUILDSTACK_2, List.of(new Card(CLUBS, 2)))
        );


        assertEquals(0, hist.getRepeatStates().size());
        hist.currentState = new State(stateMap);
        assertEquals(0, hist.getRepeatStates().size());

        hist.addGameState();
        assertEquals(0, hist.getRepeatStates().size());

        hist.addGameState();
        assertEquals(1, hist.getRepeatStates().size());

        stateMap.put(SUITSTACK_HEARTS, List.of(new Card(HEARTS, 1)));
        stateMap.put(BUILDSTACK_1, List.of());
        hist.currentState = new State(stateMap);
        hist.addGameState();
        assertEquals(0, hist.getRepeatStates().size());


        stateMap.put(BUILDSTACK_2, List.of(new Card(CLUBS, 2), new Card(HEARTS, 1)));
        stateMap.put(SUITSTACK_HEARTS, List.of());
        hist.currentState = new State(stateMap);
        hist.addGameState();
        assertEquals(0, hist.getRepeatStates().size());


        stateMap.put(SUITSTACK_HEARTS, List.of(new Card(HEARTS, 1)));
        stateMap.put(BUILDSTACK_2, List.of(new Card(CLUBS, 2)));
        hist.currentState = new State(stateMap);
        hist.addGameState();
        assertEquals(1, hist.getRepeatStates().size());
    }



    /**
     * Test that a propertyChangeEvent is properly sent to history
     */
    @Test
    void propertyChange(){

        var game = getTestReadyBoard(
                Map.of(
                        BUILDSTACK_1, new Card(HEARTS, 1),
                        BUILDSTACK_2, new Card(CLUBS, 2)
                )
        );

        var board = game.getKey();
        var input = game.getValue();
        var hist = new GameHistory(board);


        assertEquals(1, hist.history.size());

        board.move(BUILDSTACK_1, SUITSTACK_HEARTS, input.getUsrInput());
        assertEquals(2, hist.history.size());
        board.move(SUITSTACK_HEARTS, BUILDSTACK_2, input.getUsrInput());
        assertEquals(3, hist.history.size());
    }

    @Test
    void boardIntegrationTest() {
        var game = getTestReadyBoard(
                Map.of(
                        BUILDSTACK_1, new Card(HEARTS, 1),
                        BUILDSTACK_2, new Card(CLUBS, 2)
                )
        );

        var board = game.getKey();
        var input = game.getValue();
        var hist = new GameHistory(board);

        board.move(BUILDSTACK_1, SUITSTACK_HEARTS, input.getUsrInput());
        assertEquals(0, hist.getRepeatStates().size());
        board.move(SUITSTACK_HEARTS, BUILDSTACK_2, input.getUsrInput());
        assertEquals(0, hist.getRepeatStates().size());
        board.move(BUILDSTACK_2, SUITSTACK_HEARTS, input.getUsrInput());
        assertEquals(1, hist.getRepeatStates().size());
        board.move(SUITSTACK_HEARTS, BUILDSTACK_2, input.getUsrInput());
        assertEquals(1, hist.getRepeatStates().size());
        board.move(BUILDSTACK_2, SUITSTACK_HEARTS, input.getUsrInput());
        assertEquals(2, hist.getRepeatStates().size());

    }

}