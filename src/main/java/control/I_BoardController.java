package control;

import model.Move;

import java.util.List;

public interface I_BoardController {

    List<Move> possibleMoves();

    Move pickMove(List<Move> moves);

    void makeMove(Move move);
}
