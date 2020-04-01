package Logik;

public class Card implements I_CardModel {

    private CardSuit suit;
    private CardRank rank;
    private boolean isFacedUp;

    public Card(CardSuit suit, CardRank rank, boolean isFacedUp) {
        this.suit = suit;
        this.rank = rank;
        this.isFacedUp = isFacedUp;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}
