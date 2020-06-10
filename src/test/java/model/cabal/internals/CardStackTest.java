package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CardStackTest {

    @Test
    void popSubset() {

        CardStack stack = new CardStack();

        for (int i = 0; i < 6; i++) {
            I_CardModel card = new Card(E_CardSuit.DIAMONDS,i);
            //stack.push(i);
        }

//        for (int i = 0; i < stack.size(); i++) {
//            System.out.println("id at " + i +" = " + stack.get(i));
//        }

        int x = stack.size();

        System.out.println(x);

        //CardStack<Integer> cards = stack.popSubset(1);
        //int c = (int) cards.get(0);

        //System.out.println(c);

        //Assert.assertEquals(0, c);


    }

    @Test
    void getCard() {
        LinkedList<Integer> l = new LinkedList();

        assertTrue(l.addAll(List.of(1, 2, 3, 4, 5)));
        assertTrue(l.removeAll(List.of(1, 5)));



    }

    @Test
    void contains() {
    }

    @Test
    void canMoveFrom() {
    }

    @Test
    void canMoveTo() {
    }
}