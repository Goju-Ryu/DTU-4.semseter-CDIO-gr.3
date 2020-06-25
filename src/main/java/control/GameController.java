package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;
import model.error.UnendingGameException;
import view.I_Tui;
import view.Tui;

import java.util.List;
import java.util.logging.Logger;

/**
 * This class is for controlling an entire game,
 * through multiple states of the game the difference on this and Board controller,
 * is that this acts in relation to multiple states of the game where as BoardController
 * only does one. However this will use BoardController when i needs to make a move on the board
 */

public class GameController implements I_GameController {

    I_BoardController boardCtrl;
    I_Tui tui;

    public GameController() {
    }

    @Override
    public boolean startGame(String uiChoice) {
        if (uiChoice.equalsIgnoreCase("sim")) {
            tui = new Tui(false);
            boardCtrl = new BoardControllerSimulated();
        } else if (uiChoice.equalsIgnoreCase("std")) {
            tui = new Tui(true);
            boardCtrl = new BoardControllerSimulated(new RefBoard(RefBoard.stdBoard), new GameCardDeck());
        } else {
            tui = new Tui(true);
            boardCtrl = new BoardController(uiChoice, tui);
        }

        return gameLoop();
    }

    private boolean gameLoop() {
        try {
            List<Move> moves;
            do {
                moves = boardCtrl.possibleMoves();

    //            log.info("Found " + moves.size() + " possible moves: " + moves);
                Move move = boardCtrl.pickMove(moves);

    //            log.info("Chose move: " + move);
                //tui.promptPlayer(move);

                if (move != null) {
                        boardCtrl.makeMove(move);
                }

                if (boardCtrl.hasWonGame()) {
                    tui.promptPlayer("(Y)GAME WON! congratulaztions me!");
                    return true;
                }

            } while (moves.size() > 0);
        } catch (UnendingGameException e) {
            tui.promptPlayer("(X)Game has entered an Unending Loop. So the game has ended.");
            return false;
        }

        return false;
    }


}