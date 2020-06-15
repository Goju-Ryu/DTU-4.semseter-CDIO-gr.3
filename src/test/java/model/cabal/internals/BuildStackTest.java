package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildStackTest {

    @Test
    void popSubset() {

        BuildStack buildStack = new BuildStack();
        BuildStack subset = null;

        for (int i = 0; i < 10; i++) {
            I_CardModel card = new Card(E_CardSuit.CLUBS,i+1,true);
            buildStack.add(card);
        }

        assertEquals(10,buildStack.size());

        assertThrows(IndexOutOfBoundsException.class,
                () -> buildStack.popSubset(12),
                "Buildstack is only 10 in size");

        if (buildStack.canMoveFrom(5)){
            subset = (BuildStack) buildStack.popSubset(5);

            assertEquals(5,buildStack.size());
            assertEquals(5,subset.size());

            BuildStack checkStack = new BuildStack();
            for (int i = 0; i < 5 ; i++) {
                I_CardModel card = new Card(E_CardSuit.CLUBS,i+1,true);
                checkStack.add(card);
            }

            for (I_CardModel card : subset){
                System.out.println("Subset list: "+card);
            }
            for (I_CardModel card : buildStack){
                System.out.println("Buildstack list: "+card);
            }

            BuildStack checkStack1 = new BuildStack();
            for (int i = 5; i < 10; i++) {
                I_CardModel card = new Card(E_CardSuit.CLUBS,i+1,true);
                checkStack1.add(card);
            }

            for (I_CardModel card : checkStack1){
                System.out.println("checkstat: "+card);
            }

            for (int i = 0; i < 5; i++) {
                assertEquals(buildStack.getCard(i), checkStack.getCard(i));
            }

            for (int i = 0; i < 5; i++) {
                assertEquals(subset.getCard(i), checkStack1.getCard(i));
            }
        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {
        BuildStack buildStack = new BuildStack();

        // Emulate a Build stack  with som face up and face down cards
        I_CardModel card0 = new Card(E_CardSuit.CLUBS,3,false);
        I_CardModel card1 = new Card(E_CardSuit.DIAMONDS,3,false);
        I_CardModel card2 = new Card(E_CardSuit.HEARTS,3,false);
        I_CardModel card3 = new Card(E_CardSuit.HEARTS,12,true);
        I_CardModel card4 = new Card(E_CardSuit.CLUBS,11,true);
        I_CardModel card5 = new Card(E_CardSuit.DIAMONDS,10,true);

        buildStack.add(card0);
        buildStack.add(card1);
        buildStack.add(card2);
        buildStack.add(card3);
        buildStack.add(card4);
        buildStack.add(card5);

        for (I_CardModel card : buildStack){
            System.out.println("Buildstack: " +card);
        }

        // Check if the cards that is face up can be moved
        for (int i = 1; i < 4; i++) {
            assertTrue(buildStack.canMoveFrom(i));
        }

        //Check if the cards that is face down can be moved
        for (int i = 4; i < 7; i++) {
            assertFalse(buildStack.canMoveFrom(i));
        }
    }

    @Test
    void canMoveTo() {

        // The build stack that will receive a stack
        BuildStack receivingStack = new BuildStack();
        receivingStack.add(new Card(E_CardSuit.HEARTS,6));
        receivingStack.add(new Card(E_CardSuit.HEARTS,9));

        // receiveStack should be able receive this stack according to the rules
        BuildStack incomingStack = new BuildStack();
        incomingStack.add(new Card(E_CardSuit.CLUBS,8));
        incomingStack.add(new Card(E_CardSuit.HEARTS,7));
        incomingStack.add(new Card(E_CardSuit.SPADES,6));
        incomingStack.add(new Card(E_CardSuit.DIAMONDS,5));

        //receiveStack should not be able to receive this because the suits where the stacks meet will be the same color
        BuildStack incomingStack1 = new BuildStack();
        incomingStack1.add(new Card(E_CardSuit.DIAMONDS,8));
        incomingStack1.add(new Card(E_CardSuit.SPADES,7));
        incomingStack1.add(new Card(E_CardSuit.HEARTS,6));
        incomingStack1.add(new Card(E_CardSuit.CLUBS,5));

        //receiveStack should not be able to receive this because the ranks of the cards are the same
        BuildStack incomingStack2 = new BuildStack();
        incomingStack2.add(new Card(E_CardSuit.CLUBS,9));
        incomingStack2.add(new Card(E_CardSuit.HEARTS,8));
        incomingStack2.add(new Card(E_CardSuit.SPADES,7));
        incomingStack2.add(new Card(E_CardSuit.DIAMONDS,6));

        //receiveStack should not be able to receive this because the rank of the incomming stacks bottom card
        //are more than 1 less in rank
        BuildStack incomingStack3 = new BuildStack();
        incomingStack3.add(new Card(E_CardSuit.CLUBS,7));
        incomingStack3.add(new Card(E_CardSuit.HEARTS,6));
        incomingStack3.add(new Card(E_CardSuit.SPADES,5));
        incomingStack3.add(new Card(E_CardSuit.DIAMONDS,4));

        //receiveStack should not be able to receive this because the rank of the incomming stacks bottom card
        //are higher in rank
        BuildStack incomingStack4 = new BuildStack();
        incomingStack4.add(new Card(E_CardSuit.CLUBS,10));
        incomingStack4.add(new Card(E_CardSuit.HEARTS,9));
        incomingStack4.add(new Card(E_CardSuit.SPADES,8));
        incomingStack4.add(new Card(E_CardSuit.DIAMONDS,7));

        //The incoming Stack must not contain a face down card, because you can only move face up cards
        BuildStack incomingStack5 = new BuildStack();
        incomingStack5.add(new Card(E_CardSuit.SPADES,8,false));

        // incoming build stack tests
        assertTrue(incomingStack.canMoveTo(receivingStack));
        assertFalse(incomingStack1.canMoveTo(receivingStack));
        assertFalse(incomingStack2.canMoveTo(receivingStack));
        assertFalse(incomingStack3.canMoveTo(receivingStack));
        assertFalse(incomingStack4.canMoveTo(receivingStack));
        assertFalse(incomingStack5.canMoveTo(receivingStack));

    }
}