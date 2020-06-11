package model.cabal;

import com.google.common.collect.Lists;
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
public class Board implements I_BoardModel {

    PropertyChangeSupport change;

    I_SolitaireStacks[] piles;

    public Board() {
        change = new PropertyChangeSupport(this);
        piles = new I_SolitaireStacks[12];

        for (I_SolitaireStacks pile : piles) {
            pile = new CardStack();
        }
    }



//---------  Genneral methods  -------------------------------------------------------------------------------------

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        return false;
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return null;
    }


//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard() {
        ////fire property change event of the drawStacks
        return null;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }


//----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        Collection<I_CardModel> oldOrigin = Collections.unmodifiableCollection(piles[origin.ordinal()]);
        Collection<I_CardModel> oldDest = Collections.unmodifiableCollection(piles[origin.ordinal()]);

        //TODO move logic

        change.firePropertyChange( makePropertyChangeEvent(origin, oldOrigin) );
        change.firePropertyChange( makePropertyChangeEvent(destination, oldDest) );
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        return false;
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
