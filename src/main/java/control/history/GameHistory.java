package control.history;

import java.beans.PropertyChangeEvent;

public class GameHistory implements I_GameHistory {
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }

    @Override
    public boolean isRepeatState() {
        return false;
    }
}
