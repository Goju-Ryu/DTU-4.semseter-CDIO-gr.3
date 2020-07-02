import control.GameController;
import model.error.UnendingGameException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        var gameController = new GameController();
        Logger log = Logger.getLogger("Main");
        try {
            final String programDir = System.getProperty("user.dir");
            var logFile = new File(programDir + "/log/" + log.getName() + ".log");
            logFile.delete();
            logFile.getParentFile().mkdirs();
            var handler = new FileHandler(logFile.getAbsolutePath());
            log.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String uiChoice = "";

        if (args.length > 0)
            uiChoice = args[0];
        if (uiChoice.equalsIgnoreCase("gui"))
            uiChoice = "ManGUI";

        if (uiChoice.equalsIgnoreCase("sim")) {
            if (args.length > 1) {
                int simNum = Integer.parseInt(args[1]);


                var ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

                Map<E_Result, Integer> sim = IntStream
                        .range(0, simNum)
                        .mapToObj( num -> CompletableFuture.supplyAsync(GameController::new, ex))
                        .map(future -> future.thenApply(e -> e.startGame("sim")))
                        .map(future ->
                            future.thenApply(result -> result ? E_Result.SUCCESS : E_Result.FAILURE)
                                    .exceptionally(e ->
                                    {
                                        if (e.getCause() instanceof UnendingGameException) {
                                            log.info("Fail reason: " + e.getCause().toString());
                                            return E_Result.FAILURE;
                                        } else {
                                            log.warning("Error: " + e.toString());
                                            return E_Result.EXCEPTION;
                                        }
                                    })
                        )
                        .map(future -> { //TODO collect here instead and wait for results in a loop that checks if user cancels to collect the results that are available at the time instead
                            try {
                                return future.get(); //TODO use join() to avoid the explicit casting of exceptions and use exceptionally instead
                            } catch (InterruptedException | ExecutionException e) {
                                log.warning(e.toString());
                                return E_Result.EXCEPTION;
                            }
                        })
                        .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum));

                System.out.println(sim);
                log.info("Result: " + sim);

                System.exit(0);
            }
        }
        gameController.startGame(uiChoice);
    }


    enum E_Result { //TODO make this a member of a class returning the result of a game from the gameController
        SUCCESS,
        FAILURE,
        EXCEPTION // Add timed out as special case of result?
    }
}
