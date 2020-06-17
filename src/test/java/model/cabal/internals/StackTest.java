package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

//TODO REMOVE this test class

class StackTest {

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
    }

    @Test
    void clear() {

        BuildStack stack = createCardStack(4);

        assertEquals(4,stack.size());

        stack.clear();

        assertEquals(0,stack.size());
    }

    //Test will fail
    @Test
    void retainAll() {

        //TODO: when removeAll is implemented correct this should work.

        BuildStack stack = createCardStack(4);
        BuildStack stack2 = createCardStack(6);

        System.out.println(stack.size());
        System.out.println(stack2.size());

        assertTrue(stack2.retainAll(stack));

        System.out.println(stack2.size());

        assertEquals(4,stack2.size());

    }

    //Test will fail
    @Test
    void removeAll() {

        //TODO: when removeAll is implemented correct this should work.
        // It says that removeAll should not be implemented in the class. should this test test it throws correctly instead?
//
//        CardStack<I_CardModel> stack = createCardStack(4);
//        CardStack<I_CardModel> stack2 = createCardStack(6);
//
//        stack2.removeAll(stack);
//
//        System.out.println(stack2.size());
//
//        assertEquals(2,stack2.size());
    }

    @Test
    void popSubset() {

        var stack = createCardStack(4);
        var stack2 = stack.popSubset(2);

        assertEquals(2,stack2.size());
        assertEquals(2,stack.size());


    }

    @Test
    void getCard() {

        BuildStack stack = createCardStack(4);
        I_CardModel cardModel = new Card(E_CardSuit.HEARTS,3,true);
        I_CardModel card = stack.getCard(2);

        assertEquals(cardModel.getRank(),card.getRank());
        assertEquals(cardModel.getSuit(),card.getSuit());
        assertEquals(cardModel.isFacedUp(),card.isFacedUp());

        assertTrue(card.equals(cardModel));

    }

    @Test
    void size() {
        
        BuildStack stack = createCardStack(4);

        int arrSize = stack.size();

        assertEquals(4,arrSize);

        for (int i = 0; i < 5; i++) {
            I_CardModel card = new Card(E_CardSuit.SPADES,i+1,true);
            stack.add(card);
        }

        int arrSize2 = stack.size();

        assertEquals(9,arrSize2);
        
    }

    @Test
    void isEmpty() {

        BuildStack stack = createCardStack(4);

        assertFalse(stack.isEmpty());

        stack.clear();
        System.out.println(stack.size());

        assertTrue(stack.isEmpty());

    }

    @Test
    void contains() {
        BuildStack stk = createCardStack(4);
        BuildStack stk2 = createCardStack(4);

        int i = 0;
        for( I_CardModel e : stk ){
            System.out.println(i++);
            assertTrue(stk2.contains(e));
        }
    }

    @Test
    void containsCard(){

        BuildStack stack = createCardStack(4);

        I_CardModel card = new Card(E_CardSuit.HEARTS,3,true);
        boolean assesment = stack.containsCard(card);

        assertTrue(assesment);

    }

    @Test
    void iterator() {
        BuildStack stk = createCardStack(4);
        Iterator itk = stk.iterator();
        int i = 1;
        while(itk.hasNext())
            assertEquals(i++, ((I_CardModel) itk.next()).getRank() );
    }

    @Test
    void toArray() {
        BuildStack stk = createCardStack(4);
        Object arr[] = stk.toArray();
        int i = 0;
        for(I_CardModel e:stk){
            assertEquals(((I_CardModel) arr[i++]),e);
        }
    }

    @Test
    void add() {
        BuildStack stack = createCardStack(4);
        I_CardModel card = new Card(E_CardSuit.CLUBS,6,true);

        assertEquals(4,stack.size());

        stack.add(card);

        assertEquals(5,stack.size());
    }

    @Test
    void remove() {
        BuildStack stack = createCardStack(4);

        assertEquals(4,stack.size());

        stack.remove(stack.getCard(1));

        assertEquals(3,stack.size());

    }

    @Test
    void containsAll() { //TODO how does this test containsAll without using the method?
        BuildStack stack = createCardStack(4);
        BuildStack stack1 = createCardStack(3);
//        boolean c = true;
//        for(I_CardModel e: stack1){
//            if( !stack.contains(e) ){
//                c = false;
//            }
//        }
//        assertTrue(c);
        assertTrue(stack.containsAll(stack1));

//        stack = createCardStack(4);
//        stack1 = createCardStack(5);
//        c = true;
//        for(I_CardModel e: stack1){
//            if( !stack.contains(e) ){
//                c = false;
//            }
//        }
//        assertFalse(c);
        assertFalse(stack1.containsAll(stack));
    }

    @Test
    void canMoveFrom() {
        BuildStack stack = new BuildStack();
        stack.add(new Card(E_CardSuit.SPADES, 7, true));
        stack.add(new Card(E_CardSuit.HEARTS, 6, true));
        stack.add(new Card(E_CardSuit.SPADES, 5, true));
        stack.add(new Card(E_CardSuit.DIAMONDS, 4, true));
        stack.add(new Card(E_CardSuit.CLUBS, 3, true));
        stack.add(new Card(E_CardSuit.HEARTS, 2, true));
        stack.add(new Card(E_CardSuit.SPADES, 1, true));

        assertTrue(stack.canMoveFrom(7));
        stack.clear();

        stack.add(new Card(E_CardSuit.SPADES, 7, false));
        stack.add(new Card(E_CardSuit.HEARTS, 6, false));
        stack.add(new Card(E_CardSuit.SPADES, 5, false));
        stack.add(new Card(E_CardSuit.DIAMONDS, 4, true));
        stack.add(new Card(E_CardSuit.CLUBS, 3, true));
        stack.add(new Card(E_CardSuit.HEARTS, 2, true));
        stack.add(new Card(E_CardSuit.SPADES, 1, true));

        assertFalse(stack.canMoveFrom(5));

        stack.clear();
        stack.add(new Card(E_CardSuit.SPADES, 7, false));
        assertFalse(stack.canMoveFrom(2));
    }

    @Test
    void canMoveTo() {
        BuildStack stackSpades = new BuildStack();
        stackSpades.add(new Card(E_CardSuit.SPADES, 3, true));

        BuildStack stackClubs = new BuildStack();
        stackClubs.add(new Card(E_CardSuit.CLUBS, 3, true));

        BuildStack stackDiamond = new BuildStack();
        stackDiamond.add(new Card(E_CardSuit.DIAMONDS, 3, true));

        BuildStack stackHeart = new BuildStack();
        stackHeart.add(new Card(E_CardSuit.DIAMONDS, 3, true));


        // red Card Tests
        // Heart
        BuildStack stack2 = new BuildStack();
        stack2.add(new Card(E_CardSuit.HEARTS, 2, true));
        stack2.add(new Card(E_CardSuit.SPADES, 1, true));
        // succes = Color is correct, numbers are correct, and faceupp
        assertTrue(  stack2.canMoveTo(stackSpades) );
        assertTrue(  stack2.canMoveTo(stackClubs) );
        // fail, wrong color
        assertFalse( stack2.canMoveTo(stackDiamond) );
        assertFalse( stack2.canMoveTo(stackHeart) );

        stack2.clear();
        // Diamonds

        stack2.add(new Card(E_CardSuit.DIAMONDS, 2, true));
        stack2.add(new Card(E_CardSuit.CLUBS, 1, true));
        // succes = Color is correct, numbers are correct, and faceupp
        assertTrue(  stack2.canMoveTo(stackSpades) );
        assertTrue(  stack2.canMoveTo(stackClubs) );
        // fail, wrong color
        assertFalse( stack2.canMoveTo(stackDiamond) );
        assertFalse( stack2.canMoveTo(stackHeart) );


        // fail due to Numbers
        BuildStack stack4 = new BuildStack();
        stack4.add(new Card(E_CardSuit.SPADES, 6, true));
        stack4.add(new Card(E_CardSuit.HEARTS, 5, true));
        assertFalse(  stack4.canMoveTo(stackSpades) );

        // fail due to faceUp
        BuildStack stack5 = new BuildStack();
        stack5.add(new Card(E_CardSuit.SPADES, 2, false));
        stack5.add(new Card(E_CardSuit.HEARTS, 1, false));
        assertFalse(  stack5.canMoveTo(stackSpades) );
    }

    private BuildStack createCardStack(int stacksize){
        BuildStack stack = new BuildStack();

        for (int i = 0; i < stacksize; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i + 1,true);
            stack.add(card);
        }

        return stack;
    }
}