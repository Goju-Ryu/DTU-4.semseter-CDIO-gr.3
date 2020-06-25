package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static model.cabal.E_PileID.DRAWSTACK;
import static model.cabal.internals.card.E_CardSuit.*;
import static model.cabal.internals.card.E_CardSuit.CLUBS;

public abstract class AbstractBoardUtility  {

    protected I_SolitaireStacks[] piles;
    protected GameCardDeck deck;
    protected PropertyChangeSupport change;

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

    protected boolean isValidMove(E_PileID from, int originPos, E_PileID to) {

        // if you try to move to the same stack
        if(from == to)
            return false;

        // If you try to move to the turn pile
        if (to.equals(DRAWSTACK))
            return false;

        I_SolitaireStacks fromPile = get(from);
        I_CardModel c = fromPile.getCard(originPos);
        if (!c.isFacedUp())
            return false;

        switch (to) {
            case SUITSTACKHEARTS:
                if(!c.getSuit().equals(HEARTS))
                    return false;
                break;
            case SUITSTACKDIAMONDS:
                if(!c.getSuit().equals(DIAMONDS))
                    return false;
                break;
            case SUITSTACKSPADES:
                if(!c.getSuit().equals(SPADES))
                    return false;
                break;
            case SUITSTACKCLUBS:
                if(!c.getSuit().equals(CLUBS))
                    return false;
                break;
        }


        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        change.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        change.removePropertyChangeListener(listener);
    }

    protected PropertyChangeEvent makePropertyChangeEvent(E_PileID pile, Collection<I_CardModel> oldVal) {
        int pileIndex = pile.ordinal();
        return new PropertyChangeEvent(
                piles[pileIndex],
                pile.name(),
                oldVal,
                List.copyOf(piles[pileIndex])
        );

    }


}
