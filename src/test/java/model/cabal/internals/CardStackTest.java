package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

class CardStackTest {

    @Test
    void popSubset() {

        CardStack<I_CardModel> stack = new CardStack<>();

        for (int i = 0; i < 6; i++) {
            I_CardModel card = new Card(E_CardSuit.DIAMONDS,i);
            stack.add(card);
        }

        for (int i = 0; i < stack.size(); i++) {
            System.out.println("id at " + i +" = " + stack.get(i));
        }

        int x = stack.size();

        System.out.println(x);

        CardStack<I_CardModel> cards = (CardStack<I_CardModel>) stack.popSubset(1);
        int c = cards.size();

        System.out.println(c);

        //Assert.assertEquals(0, c);


    }

    @Test
    void getCard() {
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