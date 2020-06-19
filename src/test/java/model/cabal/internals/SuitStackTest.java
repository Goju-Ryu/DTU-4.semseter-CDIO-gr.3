package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SuitStackTest {

    @Test
    void popSubset() {
        SuitStack suitStack = createSuitStack(4,true);

        //The old suit stack with all the cards in it
        for (I_CardModel card : suitStack){
            System.out.println("The old suit stack: "+card);
        }

        I_CardModel card = new Card(E_CardSuit.HEARTS,4,true);

        if (suitStack.canMoveFrom(1)){
            System.out.println("passed canMoveFrom test");

            Collection<I_CardModel> suitStack1 = suitStack.popSubset(1);

            // The new suit stack with the one card in it
            for (I_CardModel card1 : suitStack){
                System.out.println("The new suit stack: "+card1);
            }

            System.out.println("Card in that is popped from the drawstack: "+suitStack1.iterator().next());
            System.out.println("Card to compare with: "+card);

            // The method will throw an exception if you try to pop more than range 1
            assertThrows(IllegalMoveException.class,() -> suitStack.popSubset(2));

            // check if the popped card is the card we want
            assertEquals(card,suitStack1.iterator().next());

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
        buildStack.add(new Card(E_CardSuit.HEARTS,9,true));

        //A build stack with an ace
        BuildStack aceStack = new BuildStack();
        aceStack.add(new Card(E_CardSuit.HEARTS,1));

        //The build stack that is empty and cant be merged
        BuildStack buildStack1 = new BuildStack();

        //The build stack that should not be able to go in the suit stack because of suit
        BuildStack buildStack2 = new BuildStack();
        buildStack2.add(new Card(E_CardSuit.DIAMONDS,9,true));

        //The build stack that is not able to be merged into the suitstack because the size is greater than 1
        BuildStack buildStack3 = new BuildStack();
        buildStack3.add(new Card(E_CardSuit.HEARTS,9,true));
        buildStack3.add(new Card(E_CardSuit.HEARTS,10,true));

        //The build stack where the rank is not 1 less
        BuildStack buildStack4 = new BuildStack();
        buildStack4.add(new Card(E_CardSuit.HEARTS,10,true));

        //The build stack where the card is not face up
        BuildStack buildStack5 = new BuildStack();
        buildStack5.add(new Card(E_CardSuit.HEARTS,9,false));

        if (buildStack.canMoveFrom(1)){
            System.out.println("1st pass");
            if (!buildStack1.canMoveFrom(2)){
                System.out.println("2nd pass");
                if (buildStack2.canMoveFrom(1)){
                    System.out.println("3th pass");
                    if (buildStack3.canMoveFrom(1)){
                        System.out.println("4th pass");
                        if (buildStack4.canMoveFrom(1)){
                            System.out.println("5th pass");
                            if (!buildStack5.canMoveFrom(1)){
                                System.out.println("6th pass");

                                assertTrue(receivingStackEmpty.canMoveTo(aceStack));
                                assertTrue(receivingStack.canMoveTo(buildStack));
                                assertFalse(receivingStack.canMoveTo(buildStack2));
                                assertFalse(receivingStack.canMoveTo(buildStack3));
                                assertFalse(receivingStack.canMoveTo(buildStack4));
                                assertFalse(receivingStack.canMoveTo(buildStack5));

                            }
                        }else {
                            fail();
                        }
                    }else {
                        fail();
                    }
                }else {
                    fail();
                }
            }else {
                fail();
            }
        }else {
            fail();
        }

        //the draw stack that should be able to be added to receive stack
        DrawStack drawStack = new DrawStack();
        drawStack.add(new Card(E_CardSuit.HEARTS,9,true));
        drawStack.turnCard();

        //the draw stack that is empty
        DrawStack drawStack1 = new DrawStack();

        //the draw stack that is not the same suit
        DrawStack drawStack2 = new DrawStack();
        drawStack2.add(new Card(E_CardSuit.CLUBS,9,true));
        drawStack2.turnCard();

        // the draw stack where there are to many cards in it
        DrawStack drawStack3 = new DrawStack();
        drawStack3.add(new Card(E_CardSuit.HEARTS,9,true));
        drawStack3.add(new Card(E_CardSuit.HEARTS,10,true));
        drawStack3.turnCard();

        // the draw stack where the rank of the card is not 1 less
        DrawStack drawStack4 = new DrawStack();
        drawStack4.add(new Card(E_CardSuit.HEARTS,10,true));
        drawStack4.turnCard();

        // the draw stack where the card is face down
        DrawStack drawStack5 = new DrawStack();
        drawStack5.add(new Card(E_CardSuit.HEARTS,9,false));
        drawStack5.turnCard();

        if (drawStack.canMoveFrom(1)){
            if (drawStack2.canMoveFrom(1)){
                if (drawStack3.canMoveFrom(1)){
                    if (drawStack4.canMoveFrom(1)){
                        if (drawStack5.canMoveFrom(1)){
                            System.out.println("all canMoveFrom tests passed");

                            assertTrue(receivingStack.canMoveTo(drawStack));
                            assertThrows(IndexOutOfBoundsException.class, drawStack1::turnCard,
                                    "The drawstack is empty, so you cant turn a card");
                            assertFalse(receivingStack.canMoveTo(drawStack2));
                            assertFalse(receivingStack.canMoveTo(drawStack3));
                            assertFalse(receivingStack.canMoveTo(drawStack4));
                            assertFalse(receivingStack.canMoveTo(drawStack5));

                        }
                    }
                }
            }
        } else {
            fail();
        }
    }

    private SuitStack createSuitStack(int elements, boolean isFaceUp){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i+1,isFaceUp);
            suitStack.add(card);
        }

        return suitStack;
    }
}