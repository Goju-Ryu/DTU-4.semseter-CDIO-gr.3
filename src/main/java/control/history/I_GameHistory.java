package control.history;

import java.beans.PropertyChangeListener;

public interface I_GameHistory extends PropertyChangeListener {

    /**
     * Checks if the current state has been encountered before
     * @return true if the current game state is equal to an earlier encountered state
     */
    boolean isRepeatState();

    //todo might be a good idea to implement a method that checks for the moves that has been tried when in a given state

}
