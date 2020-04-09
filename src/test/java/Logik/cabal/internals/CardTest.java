package Logik.cabal.internals;

import Logik.cabal.internals.card.Card;
import Logik.cabal.internals.card.E_CardRank;
import Logik.cabal.internals.card.E_CardSuit;
import Logik.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {
    I_CardModel faceUp;
    I_CardModel faceDown;

    @BeforeEach
    void beforeEach(){
        faceUp = new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE);
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
}