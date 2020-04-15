package model.cabal.internals.card;

/**
 * This is a model of a playing card.
 */
public class Card implements I_CardModel {

    private E_CardSuit suit;
    private E_CardRank rank;
    private boolean isFacedUp;


    public Card() {
        isFacedUp = false;
    }

    public Card(E_CardSuit suit, E_CardRank rank) {
        this.suit = suit;
        this.rank = rank;
        isFacedUp = true;
    }

    public Card(E_CardSuit suit, E_CardRank rank, boolean isFacedUp) {
        this.suit = suit;
        this.rank = rank;
        this.isFacedUp = isFacedUp;
    }

    //TODO: use optional for suit and rank getters

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
    public E_CardRank getRank() {
        if (rank == null) throw new NullPointerException("Rank hasn't been set yet");
        return rank;
    }

    // get the rank member value
    @Override
    public int getRankValue(){
        return rank.getCardRank();
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
            string += rank.getCardRank() + " of " + suit.getCardSuit();
        }else {
            string = "Card is faced down";
        }
        return string;
    }


}
