package history;

import static model.cabal.E_PileID.*;

import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;


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
    protected AbstractSequentialList<I_GameState> history;
    /**
     * A variable holding an  up to date image of the game state.
     */
    private I_GameState currentState;

    private Logger log ;
    private int numNonDrawEvents;

    public GameHistory() {
        this(Map.of());
    }

    public GameHistory(I_BoardModel board) {
        this(
                Stream.of(E_PileID.values())
                        .filter(pile -> board.getPile(pile) == null)
                        .map(pile -> new SimpleEntry<>(pile, board.getPile(pile)))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
        );
    }

    public GameHistory(Map<E_PileID, List<I_CardModel>> boardAsMap) {
        history = new LinkedList<>();
        log = Logger.getLogger(getClass().getName());
        currentState = new State(boardAsMap);
        numNonDrawEvents = 0;
    }

    //------------------  I_GameHistory Functions  ------------------------------------------

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public boolean isRepeatState(BiPredicate<I_GameState, I_GameState> predicate) {
        return getRepeatStates(predicate).size() > 0;
    }



    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Collection<I_GameState> getRepeatStates(BiPredicate<I_GameState, I_GameState> predicate) {
        // start a stream of the history
        return history.stream()
                .map(e -> (I_GameState) e)
                // remove all elements, not fulfilling the predicate
                .filter(e -> predicate.test(currentState, e))
                // reduce all elements down to one by removing all elements not shared by all collections
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        log.info("Received event :\n\t" + event);

        E_PileID pileID;
        try {
            pileID = getEventSourcePile(event, true);
        } catch (Exception e) {
            log.warning(
                    "Unknown source. \""
                    + event.getPropertyName() +
                    "\" does not correspond to any known source from E_PileID."
            );
            return;
        }

        if ( !isNewValueTypeCorrect(event) ) return;



        if (isNewMove(event)) {
            addGameState();
            numNonDrawEvents = 0;
        }


        try {
            Collection<I_CardModel> newValue = ((Collection<I_CardModel>)event.getNewValue());
            currentState.put(pileID, List.copyOf((Collection<? extends I_CardModel>) newValue));
        } catch (ClassCastException e) {
            log.warning("Failed to change state on event:\n\t" + event);
        }

    }


    //------------------  private helper functions  ------------------------------------------

    /**
     * Makes a copy of the current state of the game and adds it to the history list
     */
    private void addGameState() {
        history.add(0, currentState.clone());
        log.info("State added to history:\n\t" + currentState);
    }

    /**
     * A safe method for extracting the source pile from an event
     * @param event an event to extract the source pile from.
     * @return the pile the propertyChangeEvent reports a change in or null if not recognized
     */
    private E_PileID getEventSourcePile(PropertyChangeEvent event) {
        return getEventSourcePile(event, false);
    }

    /**
     * A safe method for extracting the source pile from an event
     * @param event an event to extract the source pile from.
     * @param throwOnError if false no {@link IllegalArgumentException} will ever be thrown
     *                     even if the source name can't be converted to a pile.
     * @return the pile the propertyChangeEvent reports a change in or null if no match
     * is found and {@code throwOnError = false}.
     */
    private E_PileID getEventSourcePile(PropertyChangeEvent event, boolean throwOnError) {
        try {
            return E_PileID.valueOf(event.getPropertyName());
        } catch (IllegalArgumentException e) {
            if (throwOnError) {
                return null;
            } else
                throw e;
        }
    }


    //------------------  private propertyChange helper functions  ------------------------------------------

    /**
     * Checks if the event that is coming in is the a continuation of previous events or starts a new one
     *
     * @param event the incoming event
     * @return true if the event is part of a new move, false otherwise.
     */
    private boolean isNewMove(PropertyChangeEvent event) {
        E_PileID pileID = getEventSourcePile(event);

        // A draw event is part of a move until two non-draw events have been fired
        if (!pileID.equals(DRAWSTACK)) numNonDrawEvents++;

        // A non-draw event is part of a move if it is the first or second non-draw event since the last state was added
        return numNonDrawEvents > 2;
    }

    /**
     * Check if the new event contained in an event is of a compatible type and log errors.
     * @param event the event which brings a new value
     * @return true if no error is found
     */
    private boolean isNewValueTypeCorrect(PropertyChangeEvent event) {
        if ( !(event.getNewValue() instanceof Collection) ) {
            log.warning(
                    "New value does not conform to constraints. New value is not an instance of Collection."
            );
            return false;
        }

        List<Object>  newValue;
        try {
            newValue = List.copyOf((Collection<Object>) event.getNewValue());
        } catch (ClassCastException e) {
            log.warning("Trying to cast new value in event to Collection<Object> but failed:\n\t" + event);
            return false;
        }

        if (newValue.size() < 1)
            return true;

        if (newValue.stream().anyMatch(e -> !(e instanceof I_CardModel))) {
            log.warning(
                    "New value does not conform to constraints. " +
                    "New value is a Collection not containing I_CardModel."
            );
            return false;
        }

        return true;

    }

}
