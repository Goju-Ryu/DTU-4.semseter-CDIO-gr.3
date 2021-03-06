package control;

import model.Move;
import model.cabal.I_BoardModel;

import java.util.List;

public interface I_BoardController {

    List<Move> possibleMoves();

    Move pickMove(List<Move> moves);

    void makeMove(Move move);

    boolean hasWonGame();

    I_BoardModel getBoard();
}
