package model.cabal;

import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static model.cabal.E_PileID.*;

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
        assertEquals(24, board.getPile(E_PileID.TURNPILE).size());
        assertEquals(0, board.getPile(E_PileID.HEARTSACEPILE).size());
    }

    @Test
    void isStackComplete() {
        Map<String, I_CardModel> map = new HashMap<>();
        map.put("TURNPILE", new Card(E_CardSuit.SPADES,2));
        for (int i = 2; i < 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);
        //I_BoardModel boardModel = new Board()

        //board.turnCard()

        if (board.canMove(E_PileID.BUILDSTACK1,0,E_PileID.SPADESACEPILE)){
            System.out.println("The move is legal");

            //board.move(E_PileID.BUILDSTACK1,E_PileID.SPADESACEPILE);
        }



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

    private SuitStack createSuitStack(int elements, E_CardSuit suit){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1);
            suitStack.add(card);
        }

        return suitStack;
    }
}