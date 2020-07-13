import control.I_GameController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameExecutor {
    Supplier<I_GameController> gameSupplier;
    int numGames;

    public GameExecutor(I_GameController game) {
        this(() -> game, 1);
    }

    public GameExecutor(Supplier<I_GameController> gameSupplier, int numGames) {
        this.gameSupplier = gameSupplier;
        this.numGames = numGames;
    }

    public List<I_GameController.GameResult> getResults() {
        final var simulationThreadPool = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);

        final var futureSimulations = IntStream
                .range(0, numGames)
                .mapToObj( num -> CompletableFuture.supplyAsync(() -> gameSupplier.get(), simulationThreadPool))
                .map(future -> future.thenApplyAsync(e -> e.startGame("sim")))
                .peek(future -> future.completeOnTimeout(
                        new I_GameController.GameResult(-1, (new TimeoutException("Game took too long to complete"))),
                        10,
                        TimeUnit.MINUTES))
                .peek(future -> future.exceptionally(throwable -> new I_GameController.GameResult(-1, throwable)))
                .collect(Collectors.toList());

        final var allFutures = CompletableFuture.allOf(futureSimulations.toArray(CompletableFuture[]::new));

        System.out.println("Starting to simulate..");

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
                                    new I_GameController.GameResult(
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
                                .collect(Collectors.toMap(I_GameController.GameResult::getResult, __ -> 1, Integer::sum))
                );
            }
        }

        System.out.println("Simulations finished!");

        // This variable can be used to pull different data out of the simulations
        return futureSimulations.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

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
