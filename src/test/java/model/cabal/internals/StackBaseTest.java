package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class StackBaseTest {

    @Test
    void addAll() {

        // BuildStack
        I_CardModel[] cards1 = {
                new Card(E_CardSuit.DIAMONDS,3,true),
                new Card(E_CardSuit.CLUBS,4,true),
                new Card(E_CardSuit.HEARTS,5,true),
                new Card(E_CardSuit.SPADES,6,true)
        };

        I_CardModel[] cards2 = {
                new Card(E_CardSuit.DIAMONDS,8,true),
                new Card(E_CardSuit.CLUBS,9,true),
                new Card(E_CardSuit.HEARTS,10,true),
                new Card(E_CardSuit.SPADES,11,true)
        };

        ArrayList<I_CardModel> list1 = new ArrayList<>(Arrays.asList(cards1));
        ArrayList<I_CardModel> list2 = new ArrayList<>(Arrays.asList(cards2));

        BuildStack stack1 = new BuildStack();
        BuildStack stack2 = new BuildStack();

        stack1.addAll(list1);
        stack2.addAll(list2);

        assertEquals(4, stack1.size());
        assertEquals(4, stack2.size());

        list1.addAll(list2);
        stack1.addAll(stack2);

        Iterator it, it2;

        it = list1.iterator();
        it2 = stack1.iterator();

        while (it.hasNext()){
            assertEquals(it.next(),it2.next());
        }

        //SuitStack
        SuitStack suitStack = createSuitStack(5);
        I_CardModel card = new Card(E_CardSuit.CLUBS,6,true);
        SuitStack suitStack1 = new SuitStack();
        suitStack1.add(card);

        suitStack.addAll(suitStack1);

        assertEquals(6,suitStack.size());

        for (int i = 5; i < suitStack.size() ; i++) {
            assertEquals(card, suitStack.getCard(i));
        }

        //DrawStack
        DrawStack drawStack = createDrawStack(5);
        DrawStack drawStack1 = createDrawStack(6);

        assertEquals(5,drawStack.size());
        assertEquals(6,drawStack1.size());

        drawStack.addAll(drawStack1);

        assertEquals(11, drawStack.size());

        for (int i = 5; i < drawStack.size() ; i++) {
            assertSame(drawStack.getCard(i), drawStack1.getCard(i - 5));
        }
    }

    @Test
    void clear() {

        //BuildStack
        BuildStack buildStack = createBuildStack(4);
        assertEquals(4,buildStack.size());
        buildStack.clear();
        assertEquals(0,buildStack.size());

        //SuitStack
        SuitStack suitStack = createSuitStack(5);
        assertEquals(5,suitStack.size());
        suitStack.clear();
        assertEquals(0,suitStack.size());

        //DrawStack
        DrawStack drawStack = createDrawStack(6);
        assertEquals(6,drawStack.size());
        drawStack.clear();
        assertEquals(0,drawStack.size());
    }

    @Test
    void retainAll() {

        //BuildStack
        BuildStack BuildStack = createBuildStack(4);
        BuildStack buildStack1 = createBuildStack(6);
        assertTrue(buildStack1.retainAll(BuildStack));
        assertEquals(4,buildStack1.size());

        //SuitStack
        SuitStack suitStack = createSuitStack(5);
        SuitStack suitStack1 = createSuitStack(7);
        assertTrue(suitStack1.retainAll(suitStack));
        assertEquals(5,suitStack1.size());

        //DrawStack
        DrawStack drawStack = createDrawStack(6);
        DrawStack drawStack1 = createDrawStack(8);
        assertTrue(drawStack1.retainAll(drawStack));
        assertEquals(6,drawStack1.size());

    }

    @Test
    void removeAll() {

        //BuildStack
        BuildStack buildStack = createBuildStack(13);
        BuildStack buildStack1 = createBuildStack(10);
        assertEquals(13,buildStack.size());
        assertEquals(10,buildStack1.size());
        buildStack.removeAll(buildStack1);
        assertEquals(3,buildStack.size());

        //SuitStack
        SuitStack suitStack = createSuitStack(8);
        SuitStack suitStack1 = createSuitStack(2);
        assertEquals(8,suitStack.size());
        assertEquals(2,suitStack1.size());
        suitStack.removeAll(suitStack1);
        assertEquals(6,suitStack.size());

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        DrawStack drawStack1 = createDrawStack(5);
        assertEquals(10,drawStack.size());
        assertEquals(5,drawStack1.size());
        drawStack.removeAll(drawStack1);
        assertEquals(5,drawStack.size());
    }

    @Test
    void getCard() {

        //BuildStack
        BuildStack buildStack = createBuildStack(10);
        I_CardModel buildCard = new Card(E_CardSuit.HEARTS,6,true);
        assertTrue(buildCard.equals(buildStack.getCard(5)));
        assertEquals(buildCard,buildStack.getCard(5));

        //SuitStack
        SuitStack suitStack = createSuitStack(10);
        I_CardModel suitCard = new Card(E_CardSuit.CLUBS,6,true);
        assertTrue(suitCard.equals(suitStack.getCard(5)));
        assertEquals(suitCard,suitStack.getCard(5));

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        I_CardModel drawCard = new Card(E_CardSuit.DIAMONDS,6,true);
        assertTrue(drawCard.equals(drawStack.getCard(5)));
        assertEquals(drawCard,drawStack.getCard(5));
    }

    @Test
    void getSubset() {

        //BuildStack
        BuildStack buildStack = createBuildStack(13);
        assertEquals(10,buildStack.getSubset(10).size());

        //SuitStack
        SuitStack suitStack = createSuitStack(13);
        assertEquals(8,suitStack.getSubset(8).size());

        //DrawStack
        DrawStack drawStack = createDrawStack(13);
        assertEquals(2,drawStack.getSubset(2).size());
    }

    @Test
    void size() {

        //BuildStack
        BuildStack buildStack = createBuildStack(4);
        assertEquals(4,buildStack.size());

        for (int i = 0; i < 5; i++) {
            I_CardModel card = new Card(E_CardSuit.SPADES,i+1,true);
            buildStack.add(card);
        }

        assertEquals(9,buildStack.size());

        //SuitStack
        SuitStack suitStack = createSuitStack(9);
        assertEquals(9,suitStack.size());

        for (int i = 0; i < 4; i++) {
            I_CardModel card = new Card(E_CardSuit.DIAMONDS,i+2,true);
            suitStack.add(card);
        }

        assertEquals(13,suitStack.size());

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        assertEquals(10,drawStack.size());

        for (int i = 0; i < 3; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i+1,true);
            drawStack.add(card);
        }

        assertEquals(13,drawStack.size());
    }

    @Test
    void isEmpty() {

        //BuildStack
        BuildStack buildStack = createBuildStack(10);
        assertEquals(10,buildStack.size());

        buildStack.clear();

        assertEquals(0,buildStack.size());
        assertTrue(buildStack.isEmpty());

        //SuitStack
        SuitStack suitStack = createSuitStack(8);
        assertEquals(8,suitStack.size());

        suitStack.clear();

        assertEquals(0,suitStack.size());
        assertTrue(suitStack.isEmpty());

        //DrawStack
        DrawStack drawStack = createDrawStack(13);
        assertEquals(13,drawStack.size());

        drawStack.clear();

        assertEquals(0,drawStack.size());
        assertTrue(drawStack.isEmpty());
    }

    @Test
    void contains() {

        //BuildStack
        BuildStack buildStack = createBuildStack(10);
        BuildStack buildStack1 = createBuildStack(10);

        for (I_CardModel card : buildStack){
            assertTrue(buildStack1.contains(card));
        }

        //SuitStack
        SuitStack suitStack = createSuitStack(10);
        SuitStack suitStack1 = createSuitStack(10);

        for (I_CardModel card : suitStack){
            assertTrue(suitStack1.contains(card));
        }

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        DrawStack drawStack1 = createDrawStack(10);

        for (I_CardModel card : drawStack){
            assertTrue(drawStack1.contains(card));
        }
    }

    @Test
    void iterator() {

        int i = 1;
        int j = 1;
        int k = 1;

        //BuildStack
        BuildStack buildStack = createBuildStack(4);
        Iterator iterator = buildStack.iterator();
        while (iterator.hasNext()){
            assertEquals(i++, ((I_CardModel) iterator.next()).getRank());
        }

        //SuitStack
        SuitStack suitStack = createSuitStack(5);
        Iterator iterator1 = suitStack.iterator();
        while (iterator1.hasNext()){
            assertEquals(j++, ((I_CardModel) iterator1.next()).getRank());
        }

        //DrawStack
        DrawStack drawStack = createDrawStack(6);
        Iterator iterator2 = drawStack.iterator();
        while (iterator2.hasNext()){
            assertEquals(k++, ((I_CardModel) iterator2.next()).getRank());
        }
    }

    @Test
    void toArray() {

        int i = 0;
        int j = 0;
        int k = 0;

        //BuildStack
        BuildStack buildStack = createBuildStack(6);
        Object[] array = buildStack.toArray();
        for (I_CardModel card : buildStack){
            assertEquals(array[i++],card);
        }

        //SuitStack
        SuitStack suitStack = createSuitStack(8);
        Object[] array2 = suitStack.toArray();
        for (I_CardModel card : suitStack){
            assertEquals(array2[j++],card);
        }

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        Object[] array3 = drawStack.toArray();
        for (I_CardModel card : drawStack){
            assertEquals(array3[k++],card);
        }
    }

    @Test
    void add() {

        //BuildStack
        BuildStack buildStack = createBuildStack(5);
        I_CardModel buildCard = new Card(E_CardSuit.HEARTS,7,true);
        assertEquals(5,buildStack.size());
        buildStack.add(buildCard);
        assertEquals(6,buildStack.size());

        for(I_CardModel card : buildStack){
            if (card.getRank() == 7 && card.getSuit() == E_CardSuit.HEARTS && card.isFacedUp()){
                assertEquals(buildCard, card);
            }
        }

        //SuitStack
        SuitStack suitStack = createSuitStack(6);
        I_CardModel suitCard = new Card(E_CardSuit.CLUBS,10,true);
        assertEquals(6,suitStack.size());
        suitStack.add(suitCard);
        assertEquals(7,suitStack.size());

        for (I_CardModel card1 : suitStack){
            if (card1.getRank() == 10 && card1.getSuit() == E_CardSuit.CLUBS && card1.isFacedUp()){
                assertEquals(suitCard,card1);
            }
        }

        //DrawStack
        DrawStack drawStack = createDrawStack(7);
        I_CardModel drawCard = new Card(E_CardSuit.DIAMONDS,11,true);
        assertEquals(7,drawStack.size());
        drawStack.add(drawCard);
        assertEquals(8,drawStack.size());

        for (I_CardModel card2 : drawStack){
            if (card2.getRank() == 11 && card2.getSuit() == E_CardSuit.DIAMONDS && card2.isFacedUp()){
                assertEquals(drawCard,card2);
            }
        }

    }

    @Test
    void remove() {

        //BuildStack
        BuildStack buildStack = createBuildStack(5);
        assertEquals(5,buildStack.size());
        buildStack.remove(buildStack.getCard(1));
        assertEquals(4,buildStack.size());

        //SuitStack
        SuitStack suitStack = createSuitStack(6);
        assertEquals(6,suitStack.size());
        suitStack.remove(suitStack.getCard(3));
        assertEquals(5,suitStack.size());

        //DrawStack
        DrawStack drawStack = createDrawStack(7);
        assertEquals(7,drawStack.size());
        drawStack.remove(drawStack.getCard(4));
        assertEquals(6,drawStack.size());
    }

    @Test
    void containsAll() {

        //BuildStack
        BuildStack buildStack = createBuildStack(10);
        BuildStack buildStack1 = createBuildStack(5);
        assertTrue(buildStack.containsAll(buildStack1));
        assertFalse(buildStack1.containsAll(buildStack));

        //SuitStack
        SuitStack suitStack = createSuitStack(10);
        SuitStack suitStack1 = createSuitStack(5);
        assertTrue(suitStack.containsAll(suitStack1));
        assertFalse(suitStack1.containsAll(suitStack));

        //DrawStack
        DrawStack drawStack = createDrawStack(10);
        DrawStack drawStack1 = createDrawStack(5);
        assertTrue(drawStack.containsAll(drawStack1));
        assertFalse(drawStack1.containsAll(drawStack));
    }

    @Test
    void containsCard() {

        //BuildStack
        BuildStack buildStack = createBuildStack(4);
        I_CardModel buildCard = new Card(E_CardSuit.HEARTS,3,true);
        assertTrue(buildStack.containsCard(buildCard));

        //SuitStack
        SuitStack suitStack = createSuitStack(8);
        I_CardModel suitCard = new Card(E_CardSuit.CLUBS,5,true);
        assertTrue(suitStack.containsCard(suitCard));

        //DrawStack
        DrawStack drawStack = createDrawStack(13);
        I_CardModel drawCard = new Card(E_CardSuit.DIAMONDS,10);
        assertTrue(drawStack.containsCard(drawCard));
    }

    private BuildStack createBuildStack(int stacksize){
        BuildStack stack = new BuildStack();

        for (int i = 0; i < stacksize; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i + 1,true);
            stack.add(card);
        }
        return stack;
    }

    private SuitStack createSuitStack(int stacksize) {
        SuitStack stack = new SuitStack();

        for (int i = 0; i < stacksize; i++) {
            I_CardModel card = new Card(E_CardSuit.CLUBS, i + 1, true);
            stack.add(card);
        }
        return stack;
    }

    private DrawStack createDrawStack(int stacksize){
        DrawStack stack = new DrawStack();

        for (int i = 0; i < stacksize; i++) {
            I_CardModel card = new Card(E_CardSuit.DIAMONDS, i + 1, true);
            stack.add(card);
        }
        return stack;
    }
}