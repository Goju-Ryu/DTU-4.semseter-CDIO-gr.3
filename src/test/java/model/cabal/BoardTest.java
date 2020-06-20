package model.cabal;

import data.InputSimDTO;
import model.GameCardDeck;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void constructor() {
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);

        assertEquals(7, board.getPile(E_PileID.BUILDSTACK7).size());
        assertEquals(1, board.getPile(E_PileID.BUILDSTACK1).size());
        assertEquals(24, board.getPile(E_PileID.DRAWSTACK).size());
        assertEquals(0, board.getPile(E_PileID.SUITSTACKHEARTS).size());
    }

    @Test
    void isStackComplete2() { //TODO Actually test the method it says it does
        Map<String, I_CardModel> map = new HashMap<>();

        int i = 1;
        for (E_PileID e: E_PileID.values()) {

            if(i > 1 && i < 6) {
                i++;
                continue;
            }

            map.put( e.toString() , new Card(E_CardSuit.SPADES, i++) );
        }

        I_BoardModel board = new Board(map);

        for (E_PileID e: E_PileID.values()) {
            List<I_CardModel> stack = board.getPile(e);
            int size = stack.size();

            // specificly for the ace piles
            if (size == 0){
                boolean acePile = false;
                if(e == E_PileID.SUITSTACKCLUBS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKDIAMONDS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKHEARTS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKSPADES)
                    acePile = true;

                assertTrue(acePile);
                continue;
            }

            I_CardModel c = board.getPile(e).get(size-1);
            assertEquals(map.get(e.toString()).getRank(),c.getRank());
            String mapsSuit = map.get(e.toString()).getSuit().toString();
            boolean SuitMatches = mapsSuit.equals( c.getSuit().toString() );
            assertTrue(SuitMatches);
        }

        for (E_PileID e: E_PileID.values()) {
            switch(e){
                case DRAWSTACK:
                    assertEquals(25,board.getPile(e).size());
                    break;
                case SUITSTACKCLUBS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKDIAMONDS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKHEARTS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKSPADES:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case BUILDSTACK1:
                    assertEquals(1,board.getPile(e).size());
                    break;
                case BUILDSTACK2:
                    assertEquals(2,board.getPile(e).size());
                    break;
                case BUILDSTACK3:
                    assertEquals(3,board.getPile(e).size());
                    break;
                case BUILDSTACK4:
                    assertEquals(4,board.getPile(e).size());
                    break;
                case BUILDSTACK5:
                    assertEquals(5,board.getPile(e).size());
                    break;
                case BUILDSTACK6:
                    assertEquals(6,board.getPile(e).size());
                    break;
                case BUILDSTACK7:
                    assertEquals(7,board.getPile(e).size());
            }
        }


        System.out.println("hej");
    }

    @Test
    void isStackComplete() { //TODO Actually test the method it says it does
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 0; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);
        InputSimDTO inputSim = new InputSimDTO(board);
        //Todo: the error is probably here, try and figure out why it does not print
        System.out.println("sflvknsflvk"+map);
        board.turnCard(inputSim.getUsrInput());

        if (board.canMove(E_PileID.BUILDSTACK1, E_PileID.SUITSTACKSPADES)) {
            System.out.println("The move is legal");
        }
        var testData = inputSim.getUsrInput();
        board.move(E_PileID.BUILDSTACK1, E_PileID.SUITSTACKSPADES, testData);

        System.out.println(board.getPile(E_PileID.BUILDSTACK1));
    }

    @Test
    void getPile() {
    }

    @Test
    void getPiles() {
    }

    @Test
    void turnCard() {
    }

    @Test
    void getTurnedCard() {
    }

    @Test
    void move() {
    }

    @Test
    void canMove() {
    }

    @Test
    void addPropertyChangeListener() {
    }

    @Test
    void removePropertyChangeListener() {
    }

    // todo note that this only tests heartstack, this should be exended to test all implementations of suitstack, even if they are identical
    private SuitStack createSuitStack(int elements){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i+1);
            suitStack.add(card);
        }

        return suitStack;
    }
}