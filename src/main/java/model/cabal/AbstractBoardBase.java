package model.cabal;

import model.GameCardDeck;
import model.I_Move;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_Drawable;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;

public abstract class AbstractBoardBase implements I_BoardModel {

    protected I_SolitaireStacks[] piles;
    protected GameCardDeck deck;
    protected PropertyChangeSupport change;

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
            case SUITSTACK_HEARTS:
                if(!c.getSuit().equals(HEARTS))
                    return false;
                break;
            case SUITSTACK_DIAMONDS:
                if(!c.getSuit().equals(DIAMONDS))
                    return false;
                break;
            case SUITSTACK_SPADES:
                if(!c.getSuit().equals(SPADES))
                    return false;
                break;
            case SUITSTACK_CLUBS:
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

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return List.copyOf(get(pile));
    }


    @Override
    public boolean isStackComplete(E_PileID pileID) {
        var pile = get(pileID);

        if (pileID.isBuildStack())
            return pile.getCard(pile.size() - 1).getRank() == 1; // true if ace on top

        if (pileID == DRAWSTACK)
            return pile.isEmpty();

        //Now we know only suit stacks are left
        return pile.getCard(pile.size() - 1).getRank() == 13; // true if suitStack has a king on top
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!isValidMove(origin, originPos, destination))
            return false;
        if (!from.canMoveFrom(originPos))
            return false;
        if (!to.canMoveTo(from.getSubset(originPos)))
            return false;

        return true;

    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range) {
        I_SolitaireStacks from = get(origin);
        return from.canMoveFrom(range);
    }

    @Override
    public Map<E_PileID, List<I_CardModel>> makeMoveStateMap(I_Move m) {

        I_SolitaireStacks from = get( m.moveFromStack() );
        I_SolitaireStacks to   = get( m.moveToStack()   );

        Collection<I_CardModel> subSet  = from.getSubset(m.moveFromRange());
        Collection<I_CardModel> fromSet = new ArrayList<>(from);
        fromSet.removeAll(subSet);
        Collection<I_CardModel> toSet = new ArrayList<>(to);
        toSet.addAll(subSet);

        Map<E_PileID, List<I_CardModel>> map = new HashMap<>();
        Collection<I_CardModel> col;

        for (E_PileID e : E_PileID.values()) {

            if (e == m.moveFromStack()) {

                col = fromSet;

            } else if (e == m.moveToStack()) {

                col = toSet;

            } else {

                col = this.getPile(e);
            }
            map.put(e, new ArrayList<>(col) );
        }
        return map;
    }

    @Override
    public void turnCardsToIndex( int index ){
        I_SolitaireStacks drawStack = get( DRAWSTACK );
        int numberOfTurns = drawStack.size() - index;
        for (int i = 1; i < numberOfTurns; i++) {
            ((I_Drawable) drawStack).turnCard();
        }
    }


}
