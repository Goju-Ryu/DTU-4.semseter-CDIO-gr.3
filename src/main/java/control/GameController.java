package control;

import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;

import java.util.List;

/**
 * This class is for controlling an entire game,
 * trough multiple states of the game
 *
 * the diffrence on this and Board controller, is that this acts in relation
 * to multiple states of the game where as BoardController only does one.
 * However this will use BoardControler when i needs to make a move on the board
 */

public class GameController implements I_GameController{

    I_BoardController boardCtrl;

    public void startGame(String uiChoice){
        if (uiChoice.equalsIgnoreCase("sim"))
            boardCtrl = new BoardControllerSimulated();
        else
            boardCtrl = new BoardController(uiChoice);

        gameLoop();

    }

    private void gameLoop() {
        List<Move> moves;
        do {
            moves = boardCtrl.possibleMoves();
            Move move = boardCtrl.pickMove(moves);
            if (move != null)
                boardCtrl.makeMove(move);
        } while (moves.size() > 0);
    }

}