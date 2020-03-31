package Logik;

/**
 * We have an enumerator with each card symbol, and we have getters for the enumerator
 * card value.
 * We only have getters so that we cant change a card once it have been created.
 */
public interface I_CardModel {

    enum cardType{
        hearts,
        spades,
        diamonds,
        clubs
    }

    enum getCardType{}
    int getCardValue();
    String toString();

}
