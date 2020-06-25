package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;
import model.error.UnendingGameException;

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
        if (uiChoice.equalsIgnoreCase("sim")) {
            boardCtrl = new BoardControllerSimulated();
            testGameLoop();
        } else if (uiChoice.equalsIgnoreCase("std")) {
            boardCtrl = new BoardControllerSimulated(new RefBoard(RefBoard.stdBoard), new GameCardDeck());
            testGameLoop();
        } else {
            boardCtrl = new BoardController(uiChoice);
            gameLoop();
        }

    }

    private void gameLoop() {

        try {
            List<Move> moves;
            do {
                moves = boardCtrl.possibleMoves();
                log.info("Found " + moves.size() + " possible moves: " + moves);
                Move move = boardCtrl.pickMove(moves);
                log.info("Chose move: " + move);
                promptPlayer(move);
                if (move != null)
                    boardCtrl.makeMove(move);
            } while (moves.size() > 0);

        }catch (UnendingGameException e){
            System.out.println("Game has entered an Unending Loop. So the game has ended.");
        }

    }

    private void testGameLoop() {
        List<Move> moves;
        do {
            moves = boardCtrl.possibleMoves();
            log.info("Found "+ moves.size() + " possible moves: " + moves);
            Move move = boardCtrl.pickMove(moves);
            log.info("Chose move: " + move);
            if (move != null)
                boardCtrl.makeMove(move);
        } while (moves.size() > 0);
    }


    private void promptPlayer(Move move) {
        System.out.println("Complete the following move, then press any button to continue:\n\t" + move);
        ScanSingleton.getScanner().next();
    }

}