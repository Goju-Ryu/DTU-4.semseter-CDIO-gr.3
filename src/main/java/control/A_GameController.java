package control;

import model.Move;
import model.error.UnendingGameException;
import view.I_Tui;

import java.util.List;

public class A_GameController implements I_GameController {
    protected I_BoardController boardCtrl;
    protected I_Tui tui;

    public A_GameController(I_BoardController boardController, I_Tui tui) {
        boardCtrl = boardController;
        this.tui = tui;
    }

    @Override
    public GameResult startGame(String UiChoice) {
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
                try {
                    Move move = boardCtrl.pickMove(moves);

//                  log.info("Chose move: " + move);
                    tui.promptPlayer(move);

                    if (move != null) {

                        boardCtrl.makeMove(move);
                    } else {
                        return new GameResult(roundCount, new NullPointerException("move was unexpectedly null"));
                    }

                    if (boardCtrl.hasWonGame()) {
                        tui.promptPlayer("GAME WON! congratulaztions me!");
                        return new GameResult(roundCount, E_Result.WON);
                    }
                } catch (UnendingGameException e) {
                    tui.promptPlayer("Game has entered an Unending Loop. So the game has ended.");
                    return new GameResult(roundCount, E_Result.ENDLESS, e);
                }

            } while (moves.size() > 0);

            return new GameResult(roundCount, new UnknownError("Exited game loop without resolving the game..."));
        } catch (Throwable e) {
            return new GameResult(roundCount, e);
        }
    }
}
