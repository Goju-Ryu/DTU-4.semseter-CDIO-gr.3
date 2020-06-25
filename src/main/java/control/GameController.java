package control;

import model.GameCardDeck;
import model.Move;
import model.cabal.RefBoard;
import model.error.UnendingGameException;
import view.I_Tui;
import view.Tui;

import java.io.FileWriter;
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
    StringBuilder builder = null;

    public GameController() {
    }

    public GameController(StringBuilder builder) {
        this.builder = builder;
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
        int calcLimit = 800;
        int counter = 0 ;
        try {
            List<Move> moves;
            do {
                moves = boardCtrl.possibleMoves();
                Move move = boardCtrl.pickMove(moves);

                if (move != null) {
                        boardCtrl.makeMove(move);
                }

                if (boardCtrl.hasWonGame()) {
                    String str = "(Y)GAME WON! congratulaztions me!";
                    if(builder != null)
                        builder.append(str + "\n");
                    tui.promptPlayer(str);

                    return true;
                }
                counter++;
            } while (moves.size() > 0 && counter < calcLimit);
        } catch (UnendingGameException e) {
            String str = "(X)Game has entered an Unending Loop. So the game has ended.";
            if(builder != null)
                builder.append(str + "\n");
            tui.promptPlayer(str);
            return false;
        }
        String str = "(Z)Game have used 800 moves, and hasent ended yet";
        if(builder != null)
            builder.append(str + "\n");
        tui.promptPlayer(str);
        return false;
    }
}