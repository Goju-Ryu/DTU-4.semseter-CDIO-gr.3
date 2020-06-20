package control;

import model.Move;
import model.cabal.I_BoardModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface I_BoardController {

    List<Move> possibleMoves();

    Move pickMove(List<Move> moves);

    void makeMove(Move move);

    void init(String uiChoice);

    Map getCards(String uiChoice);
}
