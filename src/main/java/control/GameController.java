package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

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
    Logger log = Logger.getLogger(getClass().getName());
    Scanner scan = new Scanner(System.in).useDelimiter("(\\b|\\B)");

    @Override
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
            log.info("Possible moves: " + moves.size());
            Move move = boardCtrl.pickMove(moves);
            log.info("Chose move: " + move);
            promptPlayer(move);
            if (move != null)
                boardCtrl.makeMove(move);
        } while (moves.size() > 0);
    }


    private void promptPlayer(Move move) {
        System.out.println("Complete the following move, then press any button to continue:\n\t" + move);
        ScanSingleton.getScanner().next();
    }

}