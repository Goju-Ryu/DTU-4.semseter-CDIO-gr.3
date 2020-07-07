package control;

public class GameControllerBuilder implements I_GameControllerBuilder {
    boolean isSimulation = false;
    String uiChoice = "";
    boolean isRandom = false;

    @Override
    public I_GameControllerBuilder setSimulation(boolean b) {
        isSimulation = b;
        return this;
    }

    @Override
    public I_GameControllerBuilder setUiChoice(String choice) {
        uiChoice = choice;
        return this;
    }

    @Override
    public I_GameControllerBuilder setRandomGame(boolean b) {
        isRandom = b;
        return this;
    }

    @Override
    public I_GameController build() {
        if (isSimulation) {
            return new GameControllerSimulation(isRandom);
        } else {
            return new GameController(uiChoice);
        }
    }
}
