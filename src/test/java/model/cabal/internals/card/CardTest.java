package model.cabal.internals.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


//JUnit test for Card class methods
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


    /**
     * demonstrates that hashing of cards works and shows some example hashes
     */
    @Test
    void getHash() {
        I_CardModel cardEmpty1 = new Card();
        I_CardModel cardEmpty2 = new Card();
        I_CardModel card1FaceDown = new Card(E_CardSuit.HEARTS, 1, false);
        I_CardModel card1FaceUp = new Card(E_CardSuit.HEARTS, 1, true);
        I_CardModel card2FaceDown = new Card(E_CardSuit.SPADES, 5, false);
        I_CardModel card2FaceUp = new Card(E_CardSuit.SPADES, 5, true);

        System.out.println("Empty1: \t\t" + cardEmpty1.hashCode());
        System.out.println("Empty2: \t\t" + cardEmpty2.hashCode());
        System.out.println("1 Face down: \t" + card1FaceDown.hashCode());
        System.out.println("1 Face up: \t\t" + card1FaceUp.hashCode());
        System.out.println("2 Face down: \t" + card2FaceDown.hashCode());
        System.out.println("2 Face up: \t\t" + card2FaceUp.hashCode());

        assertTrue(card1FaceDown.hashCode() > 0);
    }
}