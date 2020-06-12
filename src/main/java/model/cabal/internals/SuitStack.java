package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.Collection;

public class SuitStack extends StackBase { //TODO implement this class
    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        return null;
    }

    @Override
    public boolean canMoveFrom(int range) {
        return false;
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }
}
