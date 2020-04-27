package model.cabal;

import model.cabal.internals.card.I_CardModel;

public interface I_BoardModel {

    I_CardModel turnCard();
    I_CardModel getTurnedCard();
    void initialize();
}
