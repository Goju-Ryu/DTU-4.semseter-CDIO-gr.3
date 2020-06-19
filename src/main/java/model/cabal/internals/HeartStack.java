package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeartStack extends SuitStack {

    private final E_PileID pileID = E_PileID.HEARTSACEPILE;
    private final E_CardSuit stackSuit = E_CardSuit.HEARTS;

    public HeartStack(List<I_CardModel> list) {
        super(list);
    }

    public HeartStack() {
        stack = new ArrayList<>();
    }

    @Override
    @NonNullType
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        int toIndex = stack.size();
        int fromIndex = toIndex - range;

        if (range > 1){
            throw new IllegalMoveException("You can only take the top card!");
        }else {
            List<I_CardModel> subList = stack.subList(fromIndex,toIndex);
            this.stack = stack.subList(0,fromIndex);

            return new HeartStack(subList) {
            };
        }
    }

    @Override
    public E_CardSuit getStackSuit() {
        return stackSuit;
    }

    @Override
    public E_PileID getPileID() {
        return pileID;
    }
}
