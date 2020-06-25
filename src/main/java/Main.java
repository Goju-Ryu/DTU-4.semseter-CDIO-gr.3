import control.GameController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Logger log = Logger.getLogger("Main");
        try {
            final String programDir = System.getProperty("user.dir");
            var logFile = new File(programDir + "/log/" + log.getName() + ".xml");
            logFile.delete();
            logFile.getParentFile().mkdirs();
            log.addHandler(new FileHandler(logFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }



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


                var results = futures.parallelStream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());
                log.log(Level.ALL, "Results: \n" + results);

                return;
            }
        }
        gameController.startGame(uiChoice);
    }
}
