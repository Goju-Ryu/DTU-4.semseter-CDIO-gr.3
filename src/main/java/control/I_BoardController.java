package control;

import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;

public interface I_BoardController {
    I_BoardModel MakeNewBoard(String UiChoice);

    //TODO: Correct the return value and parameters, and the methods
    void possibleMoves(Card[] fromCV);

    void pickMove(Board[] moves);
}
