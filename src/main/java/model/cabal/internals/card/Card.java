package model.cabal.internals.card;

import java.util.Collection;

/**
 * This is a model of a playing card.
 */
public class Card implements I_CardModel {

    Collection c;

    private E_CardSuit suit;
    private Integer rank;
    private boolean isFacedUp;

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

    @Override
    public boolean equals(I_CardModel card) {
        if (this.rank == card.getRank() && this.suit.equals(card.getSuit()) && this.isFacedUp() == card.isFacedUp()){
            return true;
        }
        return false;
    }

    // If the card is face up it will return the cards rank and suit
    // otherwise it will return an empty string
    @Deprecated
    public String toStringValue() {
        String string = "";
        if (isFacedUp){
            string += rankToString() + " of " + suit.toString();
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

    @Override
    public int compareTo(I_CardModel o) {
        int b = this.rank.compareTo(o.getRank());

        System.out.println(b);
        if (this.suit.equals(o.getSuit()) && b == 0 && this.isFacedUp == o.isFacedUp()){

            return 0;
        }
        return b;
    }
}
