import control.GameController;
import control.GameControllerSimulated;
import control.I_GameController;

public class main {
    public static void main(String[] args) {
        I_GameController gameController;
        String uiChoice;

        if (args.length == 0) {
            gameController = new GameController();
            gameController.startGame("default");
        } else if (args[0].equalsIgnoreCase("sim")) {
            gameController = new GameControllerSimulated();
            gameController.startGame("sim");

        } else if (args[0].equalsIgnoreCase("cam")){
            gameController = new GameController();
            gameController.startGame("default");

        } else if (args[0].equalsIgnoreCase("gui")) {
            gameController = new GameController();
            gameController.startGame("ManGUI");
        } else throw new IllegalArgumentException("Option: \"" + args[0] + "\" is not a valid option.");

    }
}
