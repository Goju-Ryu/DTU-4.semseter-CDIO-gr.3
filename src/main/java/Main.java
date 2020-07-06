import control.GameController;
import control.I_GameController.GameResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
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


                final var simulationThreadPool = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);

                final var futureSimulations = IntStream
                        .range(0, simNum)
                        .mapToObj( num -> CompletableFuture.supplyAsync(GameController::new, simulationThreadPool))
                        .map(future -> future.thenApplyAsync(e -> e.startGame("sim")))
                        .peek(future -> future.completeOnTimeout(
                                new GameResult(-1, (new TimeoutException("Game took too long to complete"))),
                                10,
                                TimeUnit.MINUTES))
                        .peek(future -> future.exceptionally(throwable -> new GameResult(-1, throwable)))
                        .collect(Collectors.toList());

                final var allFutures = CompletableFuture.allOf(futureSimulations.toArray(CompletableFuture[]::new));

                System.out.println("Starting to simulate.."); //TODO find bug beneath this point

                final var inputStream = System.in;
                final var scan = new Scanner(inputStream);
                var completeNum = 0;

                // wait for all futures to complete
                while ( !allFutures.isDone() ) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (hasUsrInput(inputStream)) {
                        var line = scan.nextLine();

                        if (line.equalsIgnoreCase("quit")) {
                            System.out.println("Interrupting simulations...");

                            futureSimulations.forEach(
                                    future -> future.complete(
                                            new GameResult(
                                                    -1,
                                                    new InterruptedException("quit before this game finished")
                                            )
                                    )
                            );
                            break;

                        }

                        if (line.equalsIgnoreCase("print")) {
                            completeNum--;
                            break;
                        }

                    }

                    final var completeFutures = futureSimulations.stream()
                            .filter(CompletableFuture::isDone)
                            .collect(Collectors.toList());

                    if (completeFutures.size() > completeNum) {
                        completeNum = completeFutures.size();
                        System.out.println(
                                completeFutures.stream()
                                        .map(CompletableFuture::join)
                                        .collect(Collectors.toMap(GameResult::getResult, __ -> 1, Integer::sum))
                        );
                    }
                }

                System.out.println("Simulations finished!");

                // This variable can be used to pull different data out of the simulations
                final var gameResults =
                        futureSimulations.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList());


                // log the finished result
                log.info(
                        "Result: " +
                        gameResults.stream()
                                .collect(Collectors.toMap(GameResult::getResult, __ -> 1, Integer::sum))
                );
                log.info(gameResults.toString());
                System.exit(0);
            }
        }
        gameController.startGame(uiChoice);
    }

    private static boolean hasUsrInput(InputStream in) {
        try {
            return in.available() > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
