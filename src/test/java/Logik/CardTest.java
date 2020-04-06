package Logik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    I_CardModel faceUp;
    I_CardModel faceDown;

    @BeforeEach
    void beforeEach(){
        faceUp = new Card(E_CardSuit.DIAMONDS, E_CardRank.FIVE);
        faceDown = new Card();
    }

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