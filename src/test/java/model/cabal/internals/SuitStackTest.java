package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class SuitStackTest {

    @Test
    void popSubset() {
        SuitStack suitStack = createSuitStack(4,true);

        //The old suit stack with all the cards in it
        for (I_CardModel card : suitStack){
            System.out.println("The old suit stack: "+card);
        }

        I_CardModel card = new Card(HEARTS,4,true);

        if (suitStack.canMoveFrom(1)){

            System.out.println("passed canMoveFrom test");

            Collection<I_CardModel> newSuitStack = suitStack.popSubset(1);

            assertNotNull(newSuitStack);

            // The new suit stack with the one card in it
            for (I_CardModel card1 : newSuitStack){
                System.out.println("The new suit stack: "+card1);
            }

            System.out.println("Card in that is popped from the drawstack: "+newSuitStack.iterator().next());
            System.out.println("Card to compare with: " + card);

            // The method will throw an exception if you try to pop more than range 1
            assertThrows(IllegalMoveException.class,() -> suitStack.popSubset(2));

            // check if the popped card is the card we want
            assertEquals(card,newSuitStack.iterator().next());

        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {

        SuitStack suitStack = createSuitStack(10,true);
        SuitStack suitStack1 = createSuitStack(4,false);
        SuitStack suitStack2 = new SuitStack();

        //Check that a card can be moved from the top of the stack
        assertTrue(suitStack.canMoveFrom(1));

        // Check that an exception will be thrown if range is greater than 1
        assertFalse(suitStack.canMoveFrom(2));

        // Check if the function returns false if the card to be moved is face down
        assertFalse(suitStack1.canMoveFrom(1));

        if (suitStack2.isEmpty()){
            System.out.println("isEmpty test passed");

            // Check if an exception will be thrown if the suit stack is empty an we want to know if we can move the top card
            assertFalse(suitStack2.canMoveFrom(1));
        }else {
            fail();
        }
    }

    @Test
    void canMoveTo() {

        SuitStack receivingStack = createSuitStack(8,true);
        SuitStack receivingStackEmpty = new SuitStack();

        //The  build stack that should be able to be merged into receivingStack
        BuildStack buildStack = new BuildStack();
        buildStack.add(new Card(HEARTS,9,true));

        //A build stack with an ace
        BuildStack aceStack = new BuildStack();
        aceStack.add(new Card(HEARTS,1));

        //The build stack that is empty and cant be merged
        BuildStack buildStack1 = new BuildStack();

        //The build stack that should not be able to go in the suit stack because of suit
        BuildStack buildStack2 = new BuildStack();
        buildStack2.add(new Card(E_CardSuit.DIAMONDS,9,true));

        //The build stack that is not able to be merged into the suitstack because the size is greater than 1
        BuildStack buildStack3 = new BuildStack();
        buildStack3.add(new Card(HEARTS,9,true));
        buildStack3.add(new Card(HEARTS,10,true));

        //The build stack where the rank is not 1 less
        BuildStack buildStack4 = new BuildStack();
        buildStack4.add(new Card(HEARTS,10,true));

        //The build stack where the card is not face up
        BuildStack buildStack5 = new BuildStack();
        buildStack5.add(new Card(HEARTS,9,false));

        if (buildStack.canMoveFrom(1)) {
            assertTrue(receivingStackEmpty.canMoveTo(aceStack.getSubset(1)));
            assertTrue(receivingStack.canMoveTo(buildStack.getSubset(1)));
        }else {
            fail();
        }
        if (!buildStack1.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(buildStack1));
        }else {
            fail();
        }
        if (buildStack2.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(buildStack2.getSubset(1)));
        }else {
            fail();
        }
        if (buildStack3.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(buildStack3.getSubset(1)));
        }else {
            fail();
        }
        if (buildStack4.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(buildStack4.getSubset(1)));
        }else {
            fail();
        }
        if (!buildStack5.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(buildStack5.getSubset(1)));
        }else {
            fail();
        }

        //the draw stack that should be able to be added to receive stack
        var drawCards = new ArrayList<I_CardModel>();
        drawCards.add(new Card(HEARTS,9,true));
        DrawStack drawStack = new DrawStack(drawCards);
        drawStack.turnCard();

        //the draw stack that is empty
        DrawStack drawStack1 = new DrawStack();

        //the draw stack that is not the same suit
        var drawCards2 = new ArrayList<I_CardModel>();
        drawCards2.add(new Card(E_CardSuit.CLUBS,9,true));
        DrawStack drawStack2 = new DrawStack(drawCards2);
        drawStack2.turnCard();

        // the draw stack where there are to many cards in it
        var drawCards3 = new ArrayList<I_CardModel>();
        drawCards3.add(new Card(HEARTS,9,true));
        drawCards3.add(new Card(HEARTS,9,true));
        drawCards3.add(new Card(HEARTS,10,true));
        DrawStack drawStack3 = new DrawStack(drawCards3);
        drawStack3.turnCard();

        // the draw stack where the rank of the card is not 1 less
        var drawCards4 = new ArrayList<I_CardModel>();
        drawCards4.add(new Card(HEARTS,10,true));
        DrawStack drawStack4 = new DrawStack(drawCards4);
        drawStack4.turnCard();

        // the draw stack where the card is face down
        var drawCards5 = new ArrayList<I_CardModel>();
        drawCards5.add(new Card(HEARTS,9,false));
        DrawStack drawStack5 = new DrawStack(drawCards5);
        drawStack5.turnCard();

        if (drawStack.canMoveFrom(1)) {
            assertTrue(receivingStack.canMoveTo(drawStack.getSubset(1)));
        }else {
            fail();
        }
        if (drawStack2.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(drawStack2.getSubset(1)));
        }else {
            fail();
        }
        if (drawStack4.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(drawStack4.getSubset(1)));
        }else {
            fail();
        }
        if (!drawStack5.canMoveFrom(1)) {
            assertFalse(receivingStack.canMoveTo(drawStack5.getSubset(1)));
        }else {
            fail();
        }
        assertThrows(NoSuchElementException.class, drawStack1::turnCard,
                "The drawstack is empty, so you cant turn a card");
    }

    private SuitStack createSuitStack(int elements, boolean isFaceUp){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(HEARTS, i + 1, isFaceUp);
            suitStack.add(card);
        }

        return suitStack;
    }
}