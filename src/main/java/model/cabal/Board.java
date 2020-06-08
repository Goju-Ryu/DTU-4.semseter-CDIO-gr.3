package model.cabal;


import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;


/**
 * This is the model of the entire cabal
 */
public class Board implements I_BoardModel {


    @Override
    public I_CardModel turnCard() {
        return null;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }

    @Override
    public boolean isStackComplete() {
        return false;
    }

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {

    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        return false;
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return null;
    }


//-----------------------------------PropertyEditor methods-------------------------------------------------------------

    @Override
    public void setValue(Object value) {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean isPaintable() {
        return false;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {

    }

    @Override
    public String getJavaInitializationString() {
        return null;
    }

    @Override
    public String getAsText() {
        return null;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

    @Override
    public String[] getTags() {
        return new String[0];
    }

    @Override
    public Component getCustomEditor() {
        return null;
    }

    @Override
    public boolean supportsCustomEditor() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
