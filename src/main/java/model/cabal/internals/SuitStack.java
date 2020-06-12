package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuitStack extends StackBase { //TODO implement this class


    public SuitStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public SuitStack(){
        stack = new ArrayList<>();
    }

    public Collection<I_CardModel> popSubset() throws IllegalMoveException {
        return popSubset(stack.size() - 2);
    }


    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {

        List<I_CardModel> subList = stack.subList(range,stack.size() - 1);

        return (SuitStack) subList;

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
