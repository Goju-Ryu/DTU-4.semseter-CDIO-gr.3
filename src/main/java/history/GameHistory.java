package history;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.function.BiPredicate;

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
    protected AbstractSequentialList<Map<E_PileID, List<I_CardModel>>> history;

    /**
     * A variable holding an  up to date image of the game state.
     */
    protected I_GameState currentState;

    /**
     * An iterator to do the heavy work of implementing the Iterator Interface
     */
    protected Iterator<Map<E_PileID, List<I_CardModel>>> iterator;

    /**
     * A list of {@link PropertyChangeEvent}s waiting to accepted as an operation.
     * When accepted the list is emptied and a new snapshot is added to {@link history} before new events arrive.
     */
    protected List<PropertyChangeEvent> eventBatch;

    public GameHistory() {
        history = new LinkedList<>();
        iterator = history.iterator();
        currentState = new State();
    }


    //------------------  I_GameHistory Functions  ------------------------------------------

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean isRepeatState() {
        return false;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean isRepeatState(List<BiPredicate<I_GameState, I_GameState>> predicates) {
        return false;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Collection<I_GameState> getRepeatStates() {
        return null;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Collection<I_GameState> getRepeatStates(List<BiPredicate<I_GameState, I_GameState>> predicates) {
        return null;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
    }


    //------------------  Iterator Functions  ------------------------------------------

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public I_GameState next() {
        return new State( iterator.next() );
    }


    //------------------  private helper functions  ------------------------------------------

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
     * Makes a copy of the current state of the game and adds it to the history list
     */
    private void addGameState() {
        history.add(0, Map.copyOf(currentState));
    }


}
