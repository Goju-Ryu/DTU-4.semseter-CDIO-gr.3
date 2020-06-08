package model.cabal.internals.card;

import java.security.InvalidParameterException;

/**
 * We have an enumerator with each card symbol, and we have getters for the enumerator
 * card value.
 * We only have getters so that we cant change a card once it have been created.
 */
public interface I_CardModel {

    /**
     * A method to assign values to suit and rank when a card is first revealed
     *
     * @param suit the suit to be assigned
     * @param rank the rank to be assigned
     */
    void reveal(E_CardSuit suit, int rank) throws InvalidParameterException;

    /**
     * Get the suit of the card
     */
    E_CardSuit getSuit();

    /**
     * Get the rank of the Card
     * @return
     */
    Integer getRank();

    /**
     *
     */
    boolean isFacedUp();

    /**
     * A normal toString method that returns the
     */
    String toString();
}
