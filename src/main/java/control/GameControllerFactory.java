package control;

import model.GameCardDeck;
import model.cabal.RefBoard;
import view.EmptyTui;
import view.I_Tui;
import view.Tui;

public class GameControllerFactory implements I_GameControllerFactory {

    @Override
    public I_GameController getBoardController(E_GameType type) {
        I_Tui tui;
        switch (type) {
            case cam:
                tui = new Tui(true);
                return new GameController(new BoardController("cam", tui), tui);

            case gui:
                tui = new Tui(false);
                return new GameController(new BoardController("ManGUI", tui), tui); //Todo check if not waiting works

            case std:
                tui = new EmptyTui();
                return new GameController(
                        new BoardControllerSimulated(new RefBoard(RefBoard.stdBoard), new GameCardDeck()),
                        tui);

            case sim:
                tui = new EmptyTui();
                return new GameController(new BoardControllerSimulated(), tui);

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}