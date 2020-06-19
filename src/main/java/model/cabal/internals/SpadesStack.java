package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.List;

public class SpadesStack extends SuitStack{

    private final E_PileID pileID = E_PileID.SPADESACEPILE;

    public SpadesStack(List<I_CardModel> list) {
        super(list);
    }

    public SpadesStack() {
    }

    public E_PileID getPileID() {
        return pileID;
    }
}
