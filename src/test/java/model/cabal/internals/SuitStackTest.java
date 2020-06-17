package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SuitStackTest {

    @Test
    void popSubset() {
        SuitStack suitStack = createSuitStack(4,E_CardSuit.HEARTS,true);

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

        SuitStack suitStack = createSuitStack(10,E_CardSuit.CLUBS,true);
        SuitStack suitStack1 = createSuitStack(4,E_CardSuit.HEARTS,false);
        SuitStack suitStack2 = new SuitStack();

        //Check that a card can be moved from the top of the stack
        assertTrue(suitStack.canMoveFrom(1));

        // Check that an exception will be thrown if range is greater than 1
        assertThrows(IllegalArgumentException.class, () -> suitStack.canMoveFrom(2));

        // Check if the function returns false if the card to be moved is face down
        assertFalse(suitStack1.canMoveFrom(1));

        if (suitStack2.isEmpty()){
            System.out.println("isEmpty test passed");

            // Check if an exception will be thrown if the suit stack is empty an we want to know if we can move the top card
            assertThrows(IndexOutOfBoundsException.class,() -> suitStack2.canMoveFrom(0));
        }else {
            fail();
        }
    }

    @Test
    void canMoveTo() {

        SuitStack receivingStack = createSuitStack(8,E_CardSuit.SPADES,true);

        //The  build stack that should be able to be merged into receivingStack
        BuildStack incomingBuildStack = new BuildStack();
        incomingBuildStack.add(new Card(E_CardSuit.SPADES,9,true));

        //The build stack that is empty and cant be merged
        BuildStack incomingBuildStack1 = new BuildStack();

        //The build stack that should not be able to go in the suit stack because of suit
        BuildStack incomingBuildStack2 = new BuildStack();
        incomingBuildStack2.add(new Card(E_CardSuit.HEARTS,9,true));

        //The build stack that is not able to be merged into the suitstack because the size is greater than 1
        BuildStack incomingBuildStack3 = new BuildStack();
        incomingBuildStack3.add(new Card(E_CardSuit.SPADES,9,true));
        incomingBuildStack3.add(new Card(E_CardSuit.SPADES,10,true));

        //The build stack where the rank is not 1 less
        BuildStack incomingBuildStack4 = new BuildStack();
        incomingBuildStack4.add(new Card(E_CardSuit.SPADES,10,true));

        //The build stack where the card is not face up
        BuildStack incomingBuildStack5 = new BuildStack();
        incomingBuildStack5.add(new Card(E_CardSuit.SPADES,9,false));

        //the draw stack that should be able to be added to receive stack


        //the draw stack that is empty


        //the draw stack that is not the same suit


        // the draw stack where there are to many cards in it


        //

        if (incomingBuildStack.canMoveFrom(0)){
            System.out.println("1st pass");
            if (!incomingBuildStack1.canMoveFrom(1)){
                System.out.println("2nd pass");
                if (incomingBuildStack2.canMoveFrom(0)){
                    System.out.println("3th pass");
                    if (incomingBuildStack3.canMoveFrom(0)){
                        System.out.println("4th pass");
                        if (incomingBuildStack4.canMoveFrom(0)){
                            System.out.println("5th pass");
                            if (!incomingBuildStack5.canMoveFrom(0)){
                                System.out.println("6th pass");

                                assertTrue(receivingStack.canMoveTo(incomingBuildStack));
                                assertThrows(NoSuchElementException.class,() -> receivingStack.canMoveTo(incomingBuildStack1),
                                        "There are no elements in the stack, so an exception will be thrown");


                                assertFalse(receivingStack.canMoveTo(incomingBuildStack2));
                                assertFalse(receivingStack.canMoveTo(incomingBuildStack3));
                                assertFalse(receivingStack.canMoveTo(incomingBuildStack4));
                                assertFalse(receivingStack.canMoveTo(incomingBuildStack5));

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
    }

    private SuitStack createSuitStack(int elements, E_CardSuit suit, boolean isFaceUp){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1,isFaceUp);
            suitStack.add(card);
        }

        return suitStack;
    }
}