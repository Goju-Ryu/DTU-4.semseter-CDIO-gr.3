package model.cabal.internals.card;

/**
 * This is a model of a playing card.
 */
public class Card implements I_CardModel {

    private E_CardSuit suit;
    private Integer rank;
    private boolean isFacedUp;
    private String stacktype = null;


    public Card() {
        isFacedUp = false;
    }

    public Card(E_CardSuit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        isFacedUp = true;
    }

    public Card(E_CardSuit suit, int rank, boolean isFacedUp) {
        this.suit = suit;
        this.rank = rank;
        this.isFacedUp = isFacedUp;
    }

    public Card(E_CardSuit suit, int rank, String stackType) {
        this.suit = suit;
        this.rank = rank;
        this.stacktype = stackType;
    }

    @Override
    public void reveal(E_CardSuit suit, int rank) {
        if (this.suit != null || this.rank != null) {
            throw new IllegalStateException("Card can only be assign suit and rank once");
        }
        this.rank = rank;
        this.suit = suit;
        isFacedUp = true;
    }

    // Get the suit member
    @Override
    public E_CardSuit getSuit() {
        if (suit == null) throw new NullPointerException("Suit hasn't been set yet");
        return suit;
    }

    // get the suit members text
    @Override
    public String getSuitText(){
        if (suit == null) throw new NullPointerException("Suit hasn't been set yet");
        return suit.getCardSuit();
    }

    // get the rank member
    @Override
    public Integer getRank() {
        if (rank == null) throw new NullPointerException("Rank hasn't been set yet");
        if (rank > 13 || rank < 1){
            throw new IllegalArgumentException("Rank has an invalid value");
        }else {
            return rank;
        }
    }

    // is the card face up or face down
    @Override
    public boolean isFacedUp() {
        return isFacedUp;
    }

    public void setFacedUp(boolean facedUp) {
        isFacedUp = facedUp;
    }

    @Override
    public String toString() {
        String string = "";
        if (isFacedUp){
            return "Card{" +
                    "suit=" + suit +
                    ", rank=" + rank +
                    ", isFacedUp=" + isFacedUp +
                    '}';
        }else
            string = "Card is faced down.";
            return string;
    }

    // If the card is face up it will return the cards rank and suit
    // otherwise it will return an empty string
    @Override
    public String toStringValue() {
        String string = "";
        if (isFacedUp){
            string += rankToString() + " of " + suit.getCardSuit();
        }else {
            string = "Card is faced down";
        }
        return string;
    }


    public String rankToString() {
        switch (rank) {
            case 1:
                return "Ace";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return rank.toString();
        }
    }


    public String getStacktype() {
        return stacktype;
    }
}
