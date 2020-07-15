package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void constructor() {
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put(E_PileID.valueOf("BUILDSTACK_" + i), new Card(E_CardSuit.SPADES, i+1));
        }
        map.put(E_PileID.valueOf("BUILDSTACK_1"), new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map, new GameCardDeck());

        assertEquals(7, board.getPile(E_PileID.BUILDSTACK_7).size());
        assertEquals(1, board.getPile(E_PileID.BUILDSTACK_1).size());
        assertEquals(24, board.getPile(E_PileID.DRAWSTACK).size());
        assertEquals(0, board.getPile(E_PileID.SUITSTACK_HEARTS).size());
    }

    @Test
    void isStackComplete() { //TODO Actually test the method it says it does

        Map<E_PileID, I_CardModel> map = new HashMap<>();

        int i = 1;
        for (E_PileID e: E_PileID.values()) {

            if(i > 1 && i < 6) {
                i++;
                continue;
            }

            map.put( e , new Card(E_CardSuit.SPADES, i++) );
        }
        var deck = new GameCardDeck();
        var test = TestUtil.getTestReadyBoard(map);
        I_BoardModel board = test.getKey();

        for (E_PileID e: E_PileID.values()) {
            List<I_CardModel> stack = board.getPile(e);
            int size = stack.size();

            // specificly for the ace piles
            if (size == 0){
                boolean acePile = false;
                if(e == E_PileID.SUITSTACK_CLUBS)
                    acePile = true;
                if(e == E_PileID.SUITSTACK_DIAMONDS)
                    acePile = true;
                if(e == E_PileID.SUITSTACK_HEARTS)
                    acePile = true;
                if(e == E_PileID.SUITSTACK_SPADES)
                    acePile = true;

                assertTrue(acePile);
                continue;
            }

            I_CardModel c = board.getPile(e).get(board.getPile(e).size() - 1);
            assertEquals(map.get(e).getRank(),c.getRank());
            String mapsSuit = map.get(e).getSuit().toString();
            boolean SuitMatches = mapsSuit.equals( c.getSuit().toString() );
            assertTrue(SuitMatches);
        }

        for (E_PileID e: E_PileID.values()) {
            switch(e){
                case DRAWSTACK:
                    assertEquals(25,board.getPile(e).size());
                    break;
                case SUITSTACK_CLUBS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACK_DIAMONDS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACK_HEARTS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACK_SPADES:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case BUILDSTACK_1:
                    assertEquals(1,board.getPile(e).size());
                    break;
                case BUILDSTACK_2:
                    assertEquals(2,board.getPile(e).size());
                    break;
                case BUILDSTACK_3:
                    assertEquals(3,board.getPile(e).size());
                    break;
                case BUILDSTACK_4:
                    assertEquals(4,board.getPile(e).size());
                    break;
                case BUILDSTACK_5:
                    assertEquals(5,board.getPile(e).size());
                    break;
                case BUILDSTACK_6:
                    assertEquals(6,board.getPile(e).size());
                    break;
                case BUILDSTACK_7:
                    assertEquals(7,board.getPile(e).size());
            }
        }

        System.out.println("hej");
    }


}