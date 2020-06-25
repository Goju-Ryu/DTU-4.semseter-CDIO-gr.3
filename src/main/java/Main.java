import control.GameController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        GameController gameController= new GameController();

        Logger log = Logger.getLogger("Main");
        try {
            final String programDir = System.getProperty("user.dir");
            var logFile = new File(programDir + "/log/" + log.getName() + ".xml");
            logFile.delete();
            logFile.getParentFile().mkdirs();
            var handler = new FileHandler(logFile.getAbsolutePath());
//            handler./;
            log.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String uiChoice = "";

        if (args.length > 0)
            uiChoice = args[0];
        if (uiChoice.equalsIgnoreCase("gui"))
            uiChoice = "ManGUI";

        StringBuilder builder = new StringBuilder();
        if (uiChoice.equalsIgnoreCase("sim")) {
            if (args.length > 1) {
                int simNum = Integer.parseInt(args[1]);
                CompletableFuture<Boolean>[] futures = new CompletableFuture[simNum];

                for (int i = 0; i < simNum; i++) {
                    System.out.println("sim nr :" + i);
                    futures[i] = CompletableFuture
                            .supplyAsync(() -> (new GameController(builder)).startGame("sim"));
                }

                try {
                    var res = CompletableFuture.allOf(futures).get(15, TimeUnit.MINUTES);
                    log.info("res: " + res);
                } catch (InterruptedException | TimeoutException | ExecutionException e) {
                    e.printStackTrace();
                }

                // writing output to file
                try {
                    writeOutputFile(builder.toString(),"SimulationResults.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

        }
        gameController.startGame(uiChoice);
    }

    private static void writeOutputFile(String output, String filename) throws IOException{
        FileWriter fw=new FileWriter(filename);
        for (int i = 0; i < output.length(); i++)
            fw.write(output.charAt(i));
        fw.close();
    }
}
