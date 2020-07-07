import control.GameControllerBuilder;
import control.GameExecutor;
import control.I_GameController;
import control.I_GameControllerBuilder;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Main {
    public static void main(final String[] args) {
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

        I_GameControllerBuilder gameBuilder = new GameControllerBuilder();
        int numGames = 1;

        if (args.length == 0) {
            gameBuilder.setSimulation(false);
            gameBuilder.setUiChoice("cam");
        } else {
            for (String arg : args) {
                switch (arg) {
                    case "sim":
                        gameBuilder.setSimulation(true);
                        gameBuilder.setRandomGame(true);
                        break;
                    case "std":
                        gameBuilder.setSimulation(true);
                        gameBuilder.setRandomGame(false);
                        break;
                    case "cam":
                        gameBuilder.setSimulation(false);
                        gameBuilder.setUiChoice(arg);
                        break;
                    case "gui":
                        gameBuilder.setSimulation(false);
                        gameBuilder.setUiChoice("ManGUI");
                        break;
                    default:
                        try {
                            numGames = Integer.parseInt(arg);
                        } catch (Exception e) {
                            new RuntimeException("Couldn't recognize Argument...", e).printStackTrace();
                        }
                }
            }
        }

        var gameResults = new GameExecutor(gameBuilder::build, numGames).getResults();

        // log the finished result
        log.info(
                "Result: " +
                gameResults.stream()
                        .collect(Collectors.toMap(I_GameController.GameResult::getResult, __ -> 1, Integer::sum))
        );

        log.info(gameResults.toString());
        System.exit(0);
    }

}
