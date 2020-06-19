package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClubsStack extends SuitStack {

    private final E_PileID pileID = E_PileID.CLUBSACEPILE;
    private final E_CardSuit stackSuit = E_CardSuit.CLUBS;

    public ClubsStack(List<I_CardModel> list) {
        super(list);
    }

    public ClubsStack() {
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

            return new ClubsStack(subList) {
            };
        }
    }

}
