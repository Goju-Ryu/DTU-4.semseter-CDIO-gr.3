package Logik;

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
    HEARTS("Hearts"),
    SPADES("Spades"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs");

    // The private field representing the String values of the enum members
    private final String cardSuitText;

    // Constructor
    // this keyword represent each member of the enum
    private E_CardSuit(String cardSuitText){
        this.cardSuitText = cardSuitText;
    }

    // Method to print the suit
    // Dont know if you have to add toString()
    public String getCardSuit(){
        return cardSuitText;
    }
}
