package model.cabal.internals;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        }
    }

    @Test
    void canMoveFrom() {
    }

    @Test
    void canMoveTo() {
    }
}