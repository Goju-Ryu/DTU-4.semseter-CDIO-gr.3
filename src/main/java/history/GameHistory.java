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
        iterator = history.iterator();
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
    public boolean isRepeatState(List<BiPredicate<I_GameState, I_GameState>> predicates) {
        return getRepeatStates(predicates).size() > 0; //TODO use the allMatch method instead
    }



    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Collection<I_GameState> getRepeatStates(List<BiPredicate<I_GameState, I_GameState>> predicates) {
        var higherOrderPredicate = predicates.stream().reduce(BiPredicate::and);

        return history.stream()
                .map(State::new)
                .filter( (I_GameState state) -> higherOrderPredicate.orElseThrow().test(currentState, state))
                .map(state -> (I_GameState) state)
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


        if (!matchesPreviousEvents(event)) {
            addGameState();
        }


        currentState.put(pileID, (((List<I_CardModel>) event.getNewValue())));
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
     * Makes a copy of the current state of the game and adds it to the history list
     */
    private void addGameState() {
        history.add(0, Map.copyOf(currentState));
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
     * Checks if the event that is coming in is the a continuation of previous events
     *
     * @param event the incoming event
     * @return true if the two events are part of the same move operation, false otherwise.
     */
    private boolean matchesPreviousEvents(PropertyChangeEvent event) {
        E_PileID pileID = getEventSourcePile(event);
        if (pileID.equals(TURNPILE)) return true;

        numNonDrawEvents++;
        return numNonDrawEvents < 2;
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
        List<?>  newValue = List.of(event.getNewValue());

        if ( newValue.size() > 0 && !(newValue.get(0) instanceof I_CardModel) ) {
            log.warning(
                    "New value does not conform to constraints. " +
                    "New value is a Collection not containing I_CardModel."
            );
            return false;
        }

        return true;

    }

}
