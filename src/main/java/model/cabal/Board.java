package model.cabal;

import static model.cabal.E_PileID.*;
import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * This is the model of the entire cabal
 */
public final class Board implements I_BoardModel {

    private PropertyChangeSupport change;

    private I_SolitaireStacks[] piles;

    public Board() {
        change = new PropertyChangeSupport(this);
        piles = new I_SolitaireStacks[values().length];

        for (E_PileID id : E_PileID.values()) {
            if (id == TURNPILE)
                piles[id.ordinal()] = new DrawStack();
            else if (id.isBuildStack())
                piles[id.ordinal()] = new BuildStack();
            else if (id == HEARTSACEPILE || id == DIAMONDACEPILE || id == CLUBSACEPILE || id == SPADESACEPILE)
                piles[id.ordinal()] = new SuitStack();
            else {
                throw new RuntimeException("An unknown E_Pile_ID was encountered: " + id);
            }
        }
    }



//---------  Genneral methods  -------------------------------------------------------------------------------------

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        var pile = get(pileID);

        if (pileID.isBuildStack())
            return pile.getCard(0).getRank() == 1; // true if ace on top

        if (pileID == TURNPILE)
            return pile.isEmpty();

        //Now we know only suit stacks are left
        return pile.getCard(0).getRank() == 13; // true if suitStack has a king on top
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return List.copyOf(get(pile));
    }

    private I_SolitaireStacks get(E_PileID pile){
        return piles[pile.ordinal()];
    }


//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard() { //TODO make this handle unknown cards
        var turnPile = (DrawStack) get(TURNPILE);

        if (turnPile.isEmpty())
                throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        //TODO validate that this acts like drawing cards physically would
        return turnPile.turnCard();
    }

    @Override
    public I_CardModel getTurnedCard() {
        var turnPile = (DrawStack) get(TURNPILE);
        return turnPile.getTopCard();
    }


//----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException { //TODO make this handle unknown cards
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!isValidMove(from, to))
            throw new IllegalMoveException("Cards cannot be moved between " + origin + " and " + destination);

        if (!from.canMoveFrom(originPos))
            throw new IllegalMoveException(origin + " Cannot move cards at position " + originPos);

        Collection<I_CardModel> moveCards = from.getSubset(originPos);
        if (!to.canMoveTo(moveCards))
            throw new IllegalMoveException(destination + " Cannot receive cards: " + moveCards);

        //save state before operation
        Collection<I_CardModel> oldOrigin = Collections.unmodifiableCollection(get(origin));
        Collection<I_CardModel> oldDest = Collections.unmodifiableCollection(get(destination));

        //change state
        to.addAll(from.popSubset(originPos));

        //notify listeners om state before and after state change
        change.firePropertyChange( makePropertyChangeEvent(origin, oldOrigin) );
        change.firePropertyChange( makePropertyChangeEvent(destination, oldDest) );
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        return isValidMove(from, to)
               && from.canMoveFrom(originPos)
               && to.canMoveTo(from.getSubset(originPos));
    }

    private boolean isValidMove(I_SolitaireStacks from, I_SolitaireStacks to) {
        if(from == to)
            return false;

        var turnPile = get(TURNPILE);

        if (to.equals(turnPile))
            return false;

        return true;
    }


//-----------------------------------PropertyEditor methods-------------------------------------------------------------



    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        change.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        change.removePropertyChangeListener(listener);
    }

    private PropertyChangeEvent makePropertyChangeEvent(E_PileID pile, Collection<I_CardModel> oldVal) {
        int pileIndex = pile.ordinal();
        return new PropertyChangeEvent(
                piles[pileIndex],
                pile.getPileIDText(),
                oldVal,
                Collections.unmodifiableCollection(piles[pileIndex])
        );

    }
}
