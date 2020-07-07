package control;

import model.GameCardDeck;
import model.cabal.RefBoard;
import view.EmptyTui;
import view.Tui;

public class GameControllerSimulation extends A_GameController implements I_GameController {

    GameControllerSimulation(boolean makeRandomGame) {
        if (makeRandomGame) {
            tui = new EmptyTui();
            boardCtrl = new BoardControllerSimulated();
        } else {
            tui = new Tui(true);
            boardCtrl = new BoardControllerSimulated(new RefBoard(RefBoard.stdBoard), new GameCardDeck());
        }
    }
}
