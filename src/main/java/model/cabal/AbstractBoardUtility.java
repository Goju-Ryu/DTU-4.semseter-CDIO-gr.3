package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.I_CardModel;

import java.util.Map;
import java.util.Objects;

import static model.cabal.E_PileID.DRAWSTACK;
import static model.cabal.internals.card.E_CardSuit.*;
import static model.cabal.internals.card.E_CardSuit.CLUBS;

public abstract class AbstractBoardUtility {

    protected I_SolitaireStacks[] piles;
    protected GameCardDeck deck;

    /**
     * Query the map for the relevant data and return it.
     * @param imgData The map of data to check extract the data from
     * @param key The pile to which the data should correspond
     * @return The data the map contains about the given pile
     */
    protected I_CardModel extractImgData(Map<String, I_CardModel> imgData, E_PileID key) {
        return imgData.getOrDefault(key.name(), null); //This assumes a strict naming scheme and will return null if not found
    }

    protected I_SolitaireStacks get(E_PileID pile){
        return piles[pile.ordinal()];
    }


    /**
     * checks if state is equal to physical board
     * @param imgData state to validate against
     * @throws IllegalStateException if state is out of sync
     */
    protected void validateState(Map<String, I_CardModel> imgData) throws IllegalStateException {
        for (E_PileID pileID : E_PileID.values()) {
            var pile = piles[pileID.ordinal()];
            validateCardState(pileID, pile.getCard(pile.size() - 1), extractImgData(imgData, pileID));
        }
    }

    /**
     * Method for generating an IllegalStateException with an appropriate error message
     * @param pos The pile where state is out of sync
     * @param physCard The value of the physical card
     * @param virtCard The card represented in the virtual representation of the board (this class)
     * @param info Extra information that might be helpful
     * @return An exception with a nicely formatted message
     */
    protected IllegalStateException
    makeStateException(E_PileID pos, I_CardModel physCard, I_CardModel virtCard, String info) {
        return new IllegalStateException(
                "The virtual board and the physical board is out of sync\n" +
                        "\tPosition:\t" + pos + "\n" +
                        "\tVirtual:\t" + virtCard + "\n" +
                        "\tPhysical:\t" + physCard + "\n" +
                        "\tMethod:\t" +  Thread.currentThread().getStackTrace()[2] + "\n" +
                        "\tInfo:\t" + info
        );
    }

    /**
     * Checks if the state of a card is compatible with the card gotten from the external model.
     * If the card is face down, the method will try to reveal it with the correct values.
     * @param origin the pile the card is from.
     * @param cardModel the card to validate.
     * @param imgCard the card being validated against
     */
    protected void
    validateCardState(E_PileID origin, I_CardModel cardModel, I_CardModel imgCard) throws IllegalStateException {
        if ( cardModel != null && !cardModel.isFacedUp()) {
            if (deck.remove(imgCard)) { //if the card was in deck and now removed
                cardModel.reveal(imgCard.getSuit(), imgCard.getRank());
            } else {
                throw new IllegalStateException("Trying to reveal card but card is already in play.\ncard: " + imgCard);
            }
        } else {
            if (!Objects.equals(cardModel, imgCard))
                throw makeStateException(origin, imgCard, cardModel, "no info");
        }
    }

    protected boolean isValidMove(E_PileID from, int originPos, E_PileID to) {

        // if you try to move to the same stack
        if(from == to)
            return false;

        // If you try to move to the turn pile
        if (to.equals(DRAWSTACK))
            return false;

        var fromPile = get(from);

        I_CardModel c = fromPile.getCard(fromPile.size() - originPos);
        if ( !c.isFacedUp() )
            return false;

        //var cardSuits = fromPile.getSubset(originPos).stream().map(I_CardModel::getSuit);

        switch (to) {
            case SUITSTACKHEARTS:
                if(c.getSuit() != HEARTS)
                    return false;
                break;
            case SUITSTACKDIAMONDS:
                if(c.getSuit() != DIAMONDS)
                    return false;
                break;
            case SUITSTACKSPADES:
                if(c.getSuit() != SPADES)
                    return false;
                break;
            case SUITSTACKCLUBS:
                if(c.getSuit() != CLUBS)
                    return false;
                break;
        }


        return true;
    }


}
