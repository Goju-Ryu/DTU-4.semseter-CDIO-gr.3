package control;

import model.cabal.Board;
import model.cabal.internals.card.Card;

public interface I_GameController {




    //TODO: Correct the return value and parameters, and the methods
    void possibleMoves(Card[] fromCV);

    void pickMove(Board[] moves);
}
