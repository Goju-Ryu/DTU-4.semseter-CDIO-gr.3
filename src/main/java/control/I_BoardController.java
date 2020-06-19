package control;

import model.Move;
import model.cabal.I_BoardModel;

import java.util.LinkedList;
import java.util.List;

public interface I_BoardController {
    I_BoardModel MakeNewBoard(String UiChoice);

    //TODO: Correct the return value and parameters, and the methods
    List<Move> possibleMoves(I_BoardModel boardModel);

    Move pickMove(List<Move> moves);
}
