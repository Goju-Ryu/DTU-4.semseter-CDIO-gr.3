package data;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.util.Map;

public interface I_InputDTO {
    Map<E_PileID, I_CardModel> getUsrInput();
}
