package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;
import model.cabal.internals.card.I_CardModel;
import model.error.UnendingGameException;
import view.I_Tui;
import view.Tui;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This class is for controlling an entire game,
 * trough multiple states of the game
 * <p>
 * the diffrence on this and Board controller, is that this acts in relation
 * to multiple states of the game where as BoardController only does one.
 * However this will use BoardControler when i needs to make a move on the board
 */

public class GameController implements I_GameController {

    I_BoardController boardCtrl;
    Logger log;
    //Scanner scan = new Scanner(System.in).useDelimiter("(\\b|\\B)");
    I_Tui tui;

    public GameController() {
//        log = Logger.getLogger(getClass().getName());
    }

    @Override
    public boolean startGame(String uiChoice) {
        if (uiChoice.equalsIgnoreCase("sim")) {
            boardCtrl = new BoardControllerSimulated();
            tui = new Tui(false);
        } else if (uiChoice.equalsIgnoreCase("std")) {
            boardCtrl = new BoardControllerSimulated(new RefBoard(RefBoard.stdBoard), new GameCardDeck());
            tui = new Tui(false);
        } else {
            boardCtrl = new BoardController(uiChoice);
            tui = new Tui(true);
        }

        return gameLoop();
    }

    private boolean gameLoop() {

        List<Move> moves;
        do {
            moves = boardCtrl.possibleMoves();

//            log.info("Found " + moves.size() + " possible moves: " + moves);
            Move move = boardCtrl.pickMove(moves);

//            log.info("Chose move: " + move);
            tui.promptPlayer(move);

            if (move != null) {
                try {
                    boardCtrl.makeMove(move);
                } catch (UnendingGameException e) {
                    tui.promptPlayer("Game has entered an Unending Loop. So the game has ended.");
                    return false;
                }
            }

            if (boardCtrl.hasWonGame()) {
                tui.promptPlayer("GAME WON! congratulaztions me!");
                return true;
            }
        } while (moves.size() > 0);

        return false;
    }


}