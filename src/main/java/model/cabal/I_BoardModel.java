package model.cabal;

import model.cabal.internals.card.I_CardModel;

public interface I_BoardModel {

    void turnCard();
    I_CardModel getTurnedCard();
    void initialize();

}
