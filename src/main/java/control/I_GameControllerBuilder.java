package control;

public interface I_GameControllerBuilder {

    I_GameControllerBuilder setSimulation(boolean b);
    I_GameControllerBuilder setUiChoice(String choice);
    I_GameControllerBuilder setRandomGame(boolean b);

    I_GameController build();
}
