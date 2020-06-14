package history;

import javafx.util.Pair;
import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * This interface promises to keeps track of changes to the board during the game.
 * It facilitates checking if a specific states have been encountered before as well
 * as giving a way to represent all actions taken in a game
 */
public interface I_GameHistory extends PropertyChangeListener, Iterator<I_GameState> {

    /**
     * Checks if the current state has been encountered before
     * @return true if the current game state is equal to an earlier encountered state
     */
    boolean isRepeatState();

    /**
     * Checks if the current state has been encountered before using a list of predicates to compare states with
     * @param predicates a list of predicates testing some relation between two states.
     * @return true if the current game state is equal to an earlier encountered state
     */
    boolean isRepeatState(final List<BiPredicate<I_GameState, I_GameState>> predicates);

    /**
     * Checks if the current state has been encountered before.
     * All states that returns true given the predicate being aplied to it as well as the current state are
     * being returned as a collection.
     * @return true if the current game state is equal to an earlier encountered state
     */
    Collection<I_GameState> getRepeatStates();

    /**
     * Checks if the current state has been encountered before using a list of predicates to compare states with.
     * All states that returns true given the predicate being applied to it as well as the current state are
     * being returned as a collection.
     * @param predicates a list of predicates testing some relation between two states.
     * @return true if the current game state is equal to an earlier encountered state
     */
    Collection<I_GameState> getRepeatStates(final List<BiPredicate<I_GameState, I_GameState>> predicates);



    //todo might be a good idea to implement a method that checks for the moves that has been tried when in a given state
}
