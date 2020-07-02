package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;
import model.error.UnendingGameException;
import view.EmptyTui;
import view.I_Tui;
import view.Tui;

import java.util.List;

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
    public GameResult startGame(String uiChoice) {
        if (uiChoice.equalsIgnoreCase("sim")) {
            tui = new EmptyTui();
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

    private GameResult gameLoop() {
        int roundCount = 0;
        try {
            List<Move> moves;
            do {
                roundCount++;
                moves = boardCtrl.possibleMoves();

                if (moves.size() <= 0) {
                    return new GameResult(roundCount, E_Result.NO_MOVES);
                }

//            log.info("Found " + moves.size() + " possible moves: " + moves);
                Move move = boardCtrl.pickMove(moves);

//            log.info("Chose move: " + move);
                tui.promptPlayer(move);

                if (move != null) {
                    try {
                        boardCtrl.makeMove(move);
                    } catch (UnendingGameException e) {
                        tui.promptPlayer("Game has entered an Unending Loop. So the game has ended.");
                        return new GameResult(roundCount, E_Result.ENDLESS, e);
                    }
                } else {
                    return new GameResult(roundCount, new NullPointerException("move was unexpectedly null"));
                }

                if (boardCtrl.hasWonGame()) {
                    tui.promptPlayer("GAME WON! congratulaztions me!");
                    return new GameResult(roundCount, E_Result.WON);
                }

            } while (moves.size() > 0);

            return new GameResult(roundCount, new UnknownError("Exited game loop without resolving the game..."));
        } catch (Throwable e) {
            return new GameResult(roundCount, e);
        }
    }


}