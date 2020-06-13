package control.history;

import model.cabal.internals.card.I_CardModel;
import org.w3c.dom.events.Event;

import java.beans.PropertyChangeEvent;
import java.util.AbstractSequentialList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  TODO: Please don't touch this class if you aren't Mads/GojuRyu       //
//  lot's of plans are being prepared for with the methods that are      //
//  empty. Any implementations that get even minor details wrong might   //
//  be useless for their intended purpose. If you want to help with this //
//  class please ask for a method to implement and you will get a very   //
//  specific description of it's purpose and requirements.               //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

/**
 *{@inheritDoc}
 */
public class GameHistory implements I_GameHistory {

    /**
     * A list that represents the history of the game. The sequential list has one element per operation.
     * each element is an array containing the piles as they were in an array of lists.
     *
     * The list must have a concept of sequence and random access is not important
     * The lists in the sequential list must be copy on write to ensure no changes to the past ever occurs.
     */
    AbstractSequentialList<CopyOnWriteArrayList<I_CardModel>[]> history;

    public GameHistory() {
        history = new LinkedList<>();
    }


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


    /**
     * Checks if the event that is coming in is the a continuation of previous events
     *
     * @param event the incoming event
     * @return true if the two events are part of the same move operation, false otherwise.
     */
    private boolean matchesPreviousEvents(PropertyChangeEvent event) {
        return false;//TODO Implement
    }

    /**
     * Takes a list of matching events and adds them to the history as one new state
     * This is an operation that adds one new element to the history but doesnt modify it in any other way.
     */
    private void addGameState(List<PropertyChangeEvent> events) {

    }
}
