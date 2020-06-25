import control.GameController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        var gameController = new GameController();
        String uiChoice = "";

        if (args.length > 0)
            uiChoice = args[0];
        if (uiChoice.equalsIgnoreCase("gui"))
            uiChoice = "ManGUI";

        if (uiChoice.equalsIgnoreCase("sim")) {
            if (args.length > 1) {
                try {
                    int simNum = Integer.parseInt(args[1]);
                    CompletableFuture<Boolean>[] futures = new CompletableFuture[simNum];
                    for (int i = 0; i < futures.length; i++) {
                        futures[i] = CompletableFuture.completedFuture((new GameController()).startGame("sim"));
                    }
                        CompletableFuture.allOf(futures).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        gameController.startGame(uiChoice);
    }
}
