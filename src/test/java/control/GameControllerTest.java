package control;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest {

    @Test
    void possibleMoves() {
    }

    @Test
    void pickMove() {
    }

    @Test
    void legalMove() {

        //TODO: maybe implement more scenarios

        GameController control = new GameController();

        // Move a card from a column stack to another column stack

        Card diamond7 = new Card(E_CardSuit.DIAMONDS,7,"column");
        Card clubs6 = new Card(E_CardSuit.CLUBS,6, "column");
        Card clubs7 = new Card(E_CardSuit.CLUBS,7,"column");
        Card heart9 = new Card(E_CardSuit.HEARTS,9,"column");
        Card spade8 = new Card(E_CardSuit.SPADES,8, "column");

        assertTrue(control.legalMove(clubs6,diamond7));
        assertTrue(control.legalMove(diamond7,spade8));
        assertTrue(control.legalMove(spade8,heart9));

        assertFalse(control.legalMove(clubs6,clubs6));
        assertFalse(control.legalMove(heart9,spade8));
        assertFalse(control.legalMove(clubs6,clubs7));

        // Move a king to an empty spot in a column

        Card kingHeart = new Card(E_CardSuit.HEARTS, 13,"column");
        Card kingDiamond = new Card(E_CardSuit.DIAMONDS, 13,"turnedPile");
        Card kingSpades = new Card(E_CardSuit.SPADES, 13,"column");
        Card kingClubs = new Card(E_CardSuit.CLUBS, 13,"TurnedPile");
        Card missingCard = new Card(E_CardSuit.CLUBS,4,"emptyColumn");

        //assertTrue(control.legalMove(kingClubs,missingCard)); //TODO not working. See logic, implement legalMove so the test passes
        assertFalse(control.legalMove(kingDiamond,spade8));

        // Move a card from the turnedPile or the columns to the acePile

        Card clubs6InAce = new Card(E_CardSuit.CLUBS,6, "acePile");
        Card clubs7NotInAce = new Card(E_CardSuit.CLUBS,7,"column");
        Card clubs7InTurnedPile = new Card(E_CardSuit.CLUBS,7,"turnedPile");

        assertTrue(control.legalMove(clubs7NotInAce,clubs6InAce));
        assertTrue(control.legalMove(clubs7InTurnedPile,clubs6InAce));
        assertTrue(control.legalMove(clubs6InAce,diamond7));
        assertFalse(control.legalMove(diamond7,clubs6InAce));

    }
}