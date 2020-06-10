package model.cabal;


import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeListener;
import java.util.List;


/**
 * This is the model of the entire cabal
 */
public class Board implements I_BoardModel {

    @Override
    public I_CardModel turnCard() {
        ////fire property change event of the drawStacks
        return null;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }


    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        //fire property change event of the stacks involved
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        return false;
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return null;
    }

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        return false;
    }


//-----------------------------------PropertyEditor methods-------------------------------------------------------------



    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
