import control.*;
import view.EmptyTui;
import view.I_Tui;
import view.Tui;

import java.io.File;
import java.io.IOException;
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

        I_GameControllerFactory gameFactory = new GameControllerFactory();
        E_GameType type = (args.length > 0 ? E_GameType.valueOf(args[0]) : E_GameType.cam);
        int numGames = (args.length > 1 ? Integer.parseInt(args[1]) : 1);
        I_Tui tui;

        switch (type) {
            case cam:
                tui = new Tui(true);
                break;

            case gui:
                tui = new Tui(false);
                break;

            // These cases both use the same tui
            case std:
            case sim:
                tui = new EmptyTui();
                break;

            default:
                tui = new Tui(true);

        }




        var gameResults = new GameExecutor(() -> gameFactory.getBoardController(type), numGames).getResults();

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
