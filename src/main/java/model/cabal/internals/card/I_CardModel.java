package model.cabal.internals.card;

/**
 * We have an enumerator with each card symbol, and we have getters for the enumerator
 * card value.
 * We only have getters so that we cant change a card once it have been created.
 */
public interface I_CardModel {

    /**
     * @return return
     */
    E_CardSuit getSuit();
    String getSuitText();
    Integer getRank();
    boolean isFacedUp();
    void setFacedUp(boolean facedUp);
    String toString();
    String toStringValue();
}
