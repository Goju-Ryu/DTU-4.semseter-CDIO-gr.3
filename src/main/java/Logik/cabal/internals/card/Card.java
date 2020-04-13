package Logik.cabal.internals.card;

import Logik.cabal.internals.card.E_CardRank;
import Logik.cabal.internals.card.E_CardSuit;
import Logik.cabal.internals.card.I_CardModel;

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

    public Card(E_CardSuit suit, E_CardRank rank) {  //TODO: Skal denne ikke være vendt med forsiden opad som standard i stedet for ned ad?
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
        return suit;
    }

    // get the suit members text
    @Override
    public String getSuitText(){
        return suit.getCardSuit();
    }

    // get the rank member
    @Override
    public E_CardRank getRank() {
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
