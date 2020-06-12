package control;

import control.history.GameHistory;
import control.history.I_GameHistory;
import data.InputAccesPoint;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.Board;
import model.cabal.I_BoardModel;

public class GameController implements I_GameController{

    private I_BoardModel boardModel;
    private I_GameHistory history;
    private InputAccesPoint connection; //may have to be InputDTO??

    public GameController() {


        connection = new InputAccesPoint(
                ManagedChannelBuilder
                        .forTarget("localhost:50051")
                        .usePlaintext()
                        .build()
        );

        history = new GameHistory();

        boardModel = new Board();


    }

    @Override
    public void start() {
        gameLoop();
    }

    private void gameLoop() {

    }
}
