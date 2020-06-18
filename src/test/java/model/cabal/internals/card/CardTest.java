package model.cabal.internals.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void reveal() {
        I_CardModel cardModel = new Card();
        cardModel.reveal(E_CardSuit.CLUBS,2);
        I_CardModel cardModel2 = new  Card(E_CardSuit.CLUBS,2,true);
        assertEquals(cardModel2, cardModel);
        assertThrows(IllegalStateException.class,()-> cardModel2.reveal(E_CardSuit.HEARTS, 1));
    }

    @Test
    void getSuit() {
        I_CardModel cardModel = new Card(E_CardSuit.HEARTS, 1,true);
        assertEquals(E_CardSuit.HEARTS, cardModel.getSuit());
    }

    @Test
    void getRank() {
        I_CardModel cardModel = new Card(E_CardSuit.HEARTS, 1, true);
        assertEquals(1, cardModel.getRank());
        }


    @Test
    void isFacedUp() {
        I_CardModel cardModel = new Card(E_CardSuit.HEARTS, 1, true);
        assertTrue(cardModel.isFacedUp());
    }

    @Test
    void setFacedUp() {
        I_CardModel cardModel = new Card();
        ((Card) cardModel).setFacedUp(true);
        assertTrue(cardModel.isFacedUp());
    }

    @Test
    void equals() {
        I_CardModel cardModel = new Card(E_CardSuit.DIAMONDS, 2, true);
        I_CardModel cardModel2 = new Card(E_CardSuit.DIAMONDS, 2, true);
        assertTrue(((Card) cardModel).equals(cardModel2));
    }

    @Test
    void toStringValue() {
        I_CardModel cardModel = new Card(E_CardSuit.DIAMONDS,1, true);
        assertEquals("Ace of Diamonds", ((Card) cardModel).toStringValue());

    }

    @Test
    void rankToString() {
        I_CardModel cardModel = new Card(E_CardSuit.HEARTS, 1, true);
        assertEquals("Ace", ((Card) cardModel).rankToString());
    }
}