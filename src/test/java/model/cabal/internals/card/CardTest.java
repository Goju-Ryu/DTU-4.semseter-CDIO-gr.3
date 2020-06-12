package model.cabal.internals.card;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    I_CardModel faceUp;
    I_CardModel faceDown;

    @BeforeEach
    void beforeEach(){
        faceUp = new Card(E_CardSuit.DIAMONDS, 5);
        faceDown = new Card();
    }

    //TODO: implement tests and agree on expected behavior
    @Test
    void getSuit() {
    }

    @Test
    void getSuitText() {
    }

    @Test
    void getRank() {
    }

    @Test
    void getRankValue() {
    }

    @Test
    void isFacedUp() {
    }

    @Test
    void setFacedUp() {
    }

    @Test
    void toStringValue() {
    }

    @Test
    void equals() {
        var c1 = new Card(E_CardSuit.HEARTS, 1);
        var c2 = new Card(E_CardSuit.HEARTS, 1);

        assertTrue(c1.equals(c2));
        assertEquals(c1, c2);
    }
}