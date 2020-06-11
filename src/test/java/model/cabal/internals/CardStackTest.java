package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class CardStackTest {

    @Test
    void addAll() {

        Card[] cards1 = {
                new Card(E_CardSuit.DIAMONDS,3,true),
                new Card(E_CardSuit.CLUBS,4,true),
                new Card(E_CardSuit.HEARTS,5,true),
                new Card(E_CardSuit.SPADES,6,true)
        };

        Card[] cards2 = {
                new Card(E_CardSuit.DIAMONDS,8,true),
                new Card(E_CardSuit.CLUBS,9,true),
                new Card(E_CardSuit.HEARTS,10,true),
                new Card(E_CardSuit.SPADES,11,true)
        };

        ArrayList<I_CardModel> list1 = new ArrayList<>(Arrays.asList(cards1));

        ArrayList<I_CardModel> list2 = new ArrayList<>(Arrays.asList(cards2));

        CardStack<I_CardModel> stack1 = new CardStack<>();
        CardStack<I_CardModel> stack2 = new CardStack<>();

        stack1.addAll(list1);
        stack2.addAll(list2);

        Assert.assertEquals(4, stack1.size());
        Assert.assertEquals(4, stack2.size());

        list1.addAll(list2);
        stack1.addAll(stack2);

        Iterator it, it2;

        it = list1.iterator();
        it2 = stack1.iterator();

        while (it.hasNext()){
            Assert.assertEquals(it.next(),it2.next());
        }
    }

    @Test
    void clear() {

        Card[] cards1 = {
                new Card(E_CardSuit.DIAMONDS,3,true),
                new Card(E_CardSuit.CLUBS,4,true),
                new Card(E_CardSuit.HEARTS,5,true),
                new Card(E_CardSuit.SPADES,6,true)
        };

        CardStack<I_CardModel> stack = new CardStack<I_CardModel>(Arrays.asList(cards1));

        System.out.println(stack.size());

        Assert.assertEquals(4,stack.size());

        stack.clear();

        System.out.println(stack.size());

        Assert.assertEquals(0,stack.size());
    }

    @Test
    void retainAll() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void popSubset() {
    }

    @Test
    void getCard() {
    }

    @Test
    void size() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void contains() {
    }

    @Test
    void iterator() {
    }

    @Test
    void toArray() {
    }

    @Test
    void testToArray() {
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void containsAll() {
    }

    @Test
    void canMoveFrom() {
    }

    @Test
    void canMoveTo() {
    }
}