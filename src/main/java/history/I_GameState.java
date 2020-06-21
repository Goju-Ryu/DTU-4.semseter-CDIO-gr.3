package history;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.List;
import java.util.Map;

public interface I_GameState extends Map<E_PileID, List<I_CardModel>>, Cloneable {
    I_GameState clone();
}
