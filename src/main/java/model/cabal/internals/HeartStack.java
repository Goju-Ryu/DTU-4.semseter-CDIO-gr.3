package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.List;

public class HeartStack extends SuitStack {

    private final E_PileID pileID = E_PileID.HEARTSACEPILE;

    public HeartStack(List<I_CardModel> list) {
        super(list);
    }

    public HeartStack() {
    }

    public E_PileID getPileID() {
        return pileID;
    }
}
