import control.GameController;

public class main {
    public static void main(String[] args) {
        var gameController = new GameController();
        String uiChoice;

        if (args.length == 0) {
            gameController.startGame("cam");
        } else if (args[0].equalsIgnoreCase("sim")) {
            gameController.startGame("sim");
        } else if (args[0].equalsIgnoreCase("cam")){
            gameController.startGame("cam");
        } else if (args[0].equalsIgnoreCase("gui")) {
            gameController.startGame("ManGUI");
        } else throw new IllegalArgumentException("Option: \"" + args[0] + "\" is not a valid option.");

    }
}
