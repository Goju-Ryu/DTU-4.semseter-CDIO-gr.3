package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DrawStackTest {

    @Test
    void popSubset() {
        DrawStack drawStack = createDrawstack(4,E_CardSuit.CLUBS);
        I_CardModel card = new Card(E_CardSuit.CLUBS,1,true);
        I_CardModel card2 = null;

        // You need to turn a Card before you can pop it from the pile
        drawStack.turnCard();

        if (drawStack.canMoveFrom(1)){
            System.out.println("passed");
            Collection<I_CardModel> drawStack1 = drawStack.popSubset(drawStack.size() - 1);


            card2 = drawStack1.iterator().next();

            System.out.println("Card in that is popped from the drawstack: "+card2);
            System.out.println("Card to compare with: "+card);

            assertEquals(card,card2);

            // If you try to pop more than 1 element from the draw stack, an IllegalMoveException will be thrown
            assertThrows(IllegalMoveException.class,
                    ()-> drawStack.popSubset(2),
                    "You cannot pop more than 1 card at a time, so range should be 0");
        }else {
            fail();
        }
    }

    @Test
    void canMoveFrom() {

        //create the Draw stack and turn a card to make sure that you have something to pop
        DrawStack drawStack = createDrawstack(10, E_CardSuit.HEARTS);
        System.out.println(drawStack.size());

        //Check if a card can be removed from the top of the draw stack
        assertTrue(drawStack.canMoveFrom());

        //check if all the other cards can be moved
        for (int i = 2; i < 10; i++) {
            assertTrue(drawStack.canMoveFrom(i));
        }

        drawStack.add(new Card());
        assertTrue(drawStack.canMoveFrom(1));
    }

    @Test
    void canMoveTo() {

        DrawStack drawStack = createDrawstack(4,E_CardSuit.DIAMONDS);

        BuildStack buildStack = new BuildStack();
        buildStack.add(new Card(E_CardSuit.HEARTS,5));

        SuitStack suitStack = new SuitStack();
        suitStack.add(new Card(E_CardSuit.DIAMONDS,8));

        // you cannot move a card from anywhere to the draw stack
        if (buildStack.canMoveFrom()){
            System.out.println("In the first if statement");
            if (suitStack.canMoveFrom()){
                System.out.println("In the if statements before Assertions");

                //Neither build stacks nor suit stacks should be able to move to draw stack
                assertFalse(drawStack.canMoveTo(buildStack));
                assertFalse(drawStack.canMoveTo(suitStack));
            }
        }else {
            fail();
        }
    }

    @Test
    void turnCard() {

        //Testing turnCard() and getTopCard()

        DrawStack drawStack = createDrawstack(10,E_CardSuit.DIAMONDS);
        I_CardModel card = new Card(E_CardSuit.DIAMONDS,1);

        // When no card have been turned an Index out of bounds Exception will be thrown.
        assertNull(drawStack.getTopCard(), "No card have been turned yet");

        drawStack.turnCard();

        // No you can safely get the top card
        I_CardModel card1 = drawStack.getTopCard();

        System.out.println(card);
        System.out.println(card1);

        assertEquals(card,card1);

    }

    @Test
    void getCard() {
        List<I_CardModel> list = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            list.add(new Card(E_CardSuit.HEARTS, i+1));
        }

        DrawStack stack = new DrawStack(list);

        var stackIterator = stack.iterator();
        for (int i = 0; i < 5; i++) {
            assertEquals(i+1, stackIterator.next().getRank());
        }

        assertEquals(1, stack.getCard(0).getRank());
        assertEquals(3, stack.getCard(2).getRank());

        for (int i = 0; i < 3; i++) {
            stack.turnCard(); //DrawIndex is moved from -1 to 2
        }

        // -1  0  1  2  3  4
        //  /  1  2  3  4  5
        assertEquals(1, stack.getCard(0).getRank());
        assertEquals(5, stack.getCard(stack.size()-1).getRank());

        stackIterator = stack.iterator();
        for (int i = 0; i < 5; i++) {
            assertEquals(stack.getCard(i), stackIterator.next());
        }
    }

    private DrawStack createDrawstack(int elements, E_CardSuit suit) {
        List<I_CardModel> cards = new LinkedList<>();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i + 1,true);
            cards.add(card);
        }

        return new DrawStack(cards);
    }
}