package history;

import javafx.util.Pair;
import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleImmutableEntry;

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
    default boolean isRepeatState() {
        return isRepeatState(List.of(IDENTITY_EQUAL, PILE_SIZE_EQUAL, PILE_CONTENT_EQUAL));
    }

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
    default Collection<I_GameState> getRepeatStates() {
        return getRepeatStates(List.of(IDENTITY_EQUAL, PILE_SIZE_EQUAL, PILE_CONTENT_EQUAL));
    }

    /**
     * Checks if the current state has been encountered before using a list of predicates to compare states with.
     * All states that returns true given the predicate being applied to it as well as the current state are
     * being returned as a collection.
     * @param predicates a list of predicates testing some relation between two states.
     * @return true if the current game state is equal to an earlier encountered state
     */
    Collection<I_GameState> getRepeatStates(final List<BiPredicate<I_GameState, I_GameState>> predicates);


    BiPredicate<I_GameState, I_GameState> IDENTITY_EQUAL = I_GameHistory::identityEqual;
    BiPredicate<I_GameState, I_GameState> PILE_SIZE_EQUAL = I_GameHistory::sizeEqual;
    BiPredicate<I_GameState, I_GameState> PILE_CONTENT_EQUAL = I_GameHistory::contentEqual;


    private static boolean identityEqual(final I_GameState state1, final I_GameState state2) {
        return state1 == state2;
    }

    private static boolean sizeEqual(final I_GameState state1, final I_GameState state2) {

        Stream.of(new SimpleImmutableEntry<I_GameState, I_GameState>(state1, state2))
                .filter(pair -> (pair.getKey() != null && pair.getValue() != null))
                .map(pair ->
                        new SimpleImmutableEntry<>(
                                pair.getKey().values(),
                                pair.getValue().values()
                        ))
                .filter(pair -> pair.getKey().size() == pair.getValue().size())
                // TODO implement the remaining conditions
                .toArray();  // this is to avoid errors while working

        return false;

    }

    private static boolean contentEqual(final I_GameState state1, final I_GameState state2) {
        return false;
    }

    default <t extends I_GameState> Predicate<t> partiallyApplyPredicate(BiPredicate<t, t> biPred, t arg) {
        return test -> biPred.test(arg, test);
    }
    //todo might be a good idea to implement a method that checks for the moves that has been tried when in a given state
}
