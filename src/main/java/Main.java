import control.GameController;

public class Main {
    public static void main(String[] args) {
        var gameController = new GameController();
        String uiChoice = "";

        if (args.length > 0)
            uiChoice = args[0];
        if (uiChoice.equalsIgnoreCase("gui"))
            uiChoice = "ManGUI";

        gameController.startGame(uiChoice);
    }
}
