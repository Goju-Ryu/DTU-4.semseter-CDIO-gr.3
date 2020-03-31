package Logik;

/**
 *
 */
public enum CardSuit {
    HEARTS("Hearts"),
    SPADES("Spades"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs");

    private final String cardSuitText;

    // Constructor
    private CardSuit(String cardSuitText){
        this.cardSuitText = cardSuitText;
    }

    // Dont know if you have to add toString()
    public String printCardSuit(){
        return cardSuitText.toString();
    }
}
