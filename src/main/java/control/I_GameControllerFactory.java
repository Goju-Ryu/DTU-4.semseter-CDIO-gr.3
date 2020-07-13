package control;

public interface I_GameControllerFactory {
    I_GameController getBoardController(E_GameType gameType);
}
