package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.List;

public class ClubsStack extends SuitStack {

    private final E_PileID pileID = E_PileID.CLUBSACEPILE;

    public ClubsStack(List<I_CardModel> list) {
        super(list);
    }

    public ClubsStack() {
    }

    public E_PileID getPileID() {
        return pileID;
    }
}
