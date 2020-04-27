package model.cabal.internals.card;

/**
 * This is the enumerator for the suit of the card.
 * A card can only be one of these 4 suits, so that is why we created the suit
 * as an enumerator.
 *
 * Each of the enumerator constants will get a String value, where it is written more naturally.
 *
 * Then we need the card suit string to be constant hence the final keyword.
 * We need a Constructor to construct the text. In enums the constructor needs to be private.
 * The constructor give each enum member its value.
 */
public enum E_CardSuit {
    HEARTS("Hearts", true),
    SPADES("Spades", false),
    DIAMONDS("Diamonds", true),
    CLUBS("Clubs", false);

    // The private field representing the String values of the enum members
    private final String cardSuitText;
    private final boolean isRed;
    // Constructor
    // this keyword represent each member of the enum
    private E_CardSuit(String cardSuitText, boolean isRed){
        this.cardSuitText = cardSuitText;
        this.isRed = isRed;
    }

    // Method to print the suit
    // Dont know if you have to add toString()
    public String getCardSuit(){
        return cardSuitText;
    }

    public static boolean isSameColour(E_CardSuit first, E_CardSuit second) {
        if (first == null || second == null)
            return false;
        return first.isRed == second.isRed;
    }
}
