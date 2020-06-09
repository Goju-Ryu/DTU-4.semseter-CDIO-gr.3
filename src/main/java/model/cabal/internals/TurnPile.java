package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Collection;
import java.util.Stack;

public class TurnPile extends Stack implements I_SolitaireStacks {
    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        return null;
    }

    @Override
    public I_CardModel getCard(int position) {
        return null;
    }

    @Override
    public boolean contains(I_CardModel card) {
        return false;
    }

    @Override
    public boolean canMoveFrom(int range) {
        return false;
    }

    @Override
    public boolean canMoveTo(Collection cards) {
        return false;
    }
}
