import control.GameController;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

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


                int simNum = Integer.parseInt(args[1]);
                List<CompletableFuture<Boolean>> futures = new ArrayList<>(simNum);

                for (int i = 0; i < simNum; i++) {
                    futures.add(
                            CompletableFuture.supplyAsync(() -> (new GameController()).startGame("sim"))
                    );
                }


                System.out.println(futures.parallelStream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));


                return;
            }
        }
        gameController.startGame(uiChoice);
    }
}
