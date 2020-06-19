package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.List;

public class DiamondsStack extends SuitStack {

    private final E_PileID pileID = E_PileID.DIAMONDACEPILE;

    public DiamondsStack(List<I_CardModel> list) {
        super(list);
    }

    public DiamondsStack() {
    }

    public E_PileID getPileID() {
        return pileID;
    }
}
