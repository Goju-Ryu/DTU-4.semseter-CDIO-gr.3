package model.cabal;

import static model.cabal.E_PileID.*;
import model.cabal.internals.CardStack;
import model.cabal.internals.I_SolitaireStacks;
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
    private int drawPosition;

    public Board() {
        change = new PropertyChangeSupport(this);
        drawPosition = 0;
        piles = new I_SolitaireStacks[12];

        for (I_SolitaireStacks pile : piles) {
            pile = new CardStack();
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
        var turnPile = get(TURNPILE);
        return turnPile.getCard(drawPosition++ % turnPile.size() );
    }

    @Override
    public I_CardModel getTurnedCard() {
        return get(TURNPILE).getCard(drawPosition);
    }


//----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException { //TODO make this handle unknown cards
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!from.canMoveFrom(originPos))
            throw new IllegalMoveException(origin.getPileIDText() + " Cannot move cards at position " + originPos);

        Collection<I_CardModel> moveCards = from.getSubset(originPos);
        if (!to.canMoveTo(moveCards))
            throw new IllegalMoveException(destination.getPileIDText() + " Cannot receive cards: " + moveCards);

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

        return from.canMoveFrom(originPos) && to.canMoveTo(from.getSubset(originPos));
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
