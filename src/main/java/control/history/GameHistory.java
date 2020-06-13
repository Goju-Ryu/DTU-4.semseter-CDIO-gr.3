package control.history;

import java.beans.PropertyChangeEvent;

public class GameHistory implements I_GameHistory {


    /**
     * {@inheritDoc}
     * @param propertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRepeatState() {
        return false;
    }
}
