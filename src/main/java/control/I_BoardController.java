package control;

import model.I_Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;

import java.util.ArrayList;
import java.util.LinkedList;

public interface I_BoardController {
    I_BoardModel MakeNewBoard(String UiChoice);

    //TODO: Correct the return value and parameters, and the methods
    LinkedList<I_Move> possibleMoves(I_BoardModel boardModel);

    void pickMove(Board[] moves);
}
