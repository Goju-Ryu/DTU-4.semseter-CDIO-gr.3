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

public class GameController extends A_GameController implements I_GameController {
    public GameController(String uiChoice) {
        this(uiChoice, new Tui(true));
    }

    private GameController(String uiChoice, I_Tui tui) {
        this(tui, new BoardController(uiChoice, tui));
    }

    <boardController extends BoardController>
    GameController(I_Tui tui, boardController boardController) {
        this.tui = tui;
        boardCtrl = boardController;
    }
}