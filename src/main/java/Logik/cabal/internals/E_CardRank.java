package Logik.cabal.internals;

/**
 * This enumerator is the rank of the card. card can have ranks from 1 - 10 and the Jack,
 * Queen and King.
 * We give them values from 1 - 13, to make it easier to see whether a card can be
 * placed on top of another card.
 *
 *
 */
public enum E_CardRank {
    TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    TEN(10), JACK(11), QUEEN(12), KING(13),
    ACE(1);

    // The private field representing the int values of the enum members
    private final int cardRankValue;

    // Constructor
    // this keyword represent each member of the enum
    private E_CardRank(int cardRankValue) {
        this.cardRankValue = cardRankValue;
    }

    // This method is used to get the rank
    // we can use it to compare a rank of a card to another card
    public int getCardRank(){
        return cardRankValue;
    }

}
