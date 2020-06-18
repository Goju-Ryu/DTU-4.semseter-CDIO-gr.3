import control.GameController;
import control.GameControllerSimulated;
import control.I_GameController;
import model.cabal.Board;
import model.cabal.I_BoardModel;

public class main {
    public static void main(String[] args) {
        I_GameController Klondike = new GameControllerSimulated();
        //I_GameController Klondike = new GameController();
        Klondike.startGame("ManGUI");
//        Klondike.startGame("sdsldfndslfnds");

        System.out.println("Hello Main World!");
        //change
    }
}
