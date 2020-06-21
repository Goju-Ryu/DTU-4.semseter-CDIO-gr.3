package history;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeListener;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
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


    /**
     * This predicate returns true only if the two states are actually both referencing the same object.
     */
    BiPredicate<I_GameState, I_GameState> IDENTITY_EQUAL = I_GameHistory::identityEqual;

    /**
     * This predicate returns true if the sizes of the lists in the first state are all
     * equal to the length of the corresponding list in the second state.
     */
    BiPredicate<I_GameState, I_GameState> PILE_SIZE_EQUAL = I_GameHistory::sizeEqual;

    /**
     * This predicate returns true only if the content of each list in one state matches exactly the content
     * of the corresponding lists in the other state. Objects.equals() is used to check equality of the contents.
     */
    BiPredicate<I_GameState, I_GameState> PILE_CONTENT_EQUAL = I_GameHistory::contentEqual;


    /**
     * Returns true only if the two states are actually both referencing the same object.
     *
     * @param state1 a state to compare
     * @param state2 the state to compare it to
     * @return true if the contents of one state all equals the contents of the other state
     */
    private static boolean identityEqual(final I_GameState state1, final I_GameState state2) {
        return state1 == state2;
    }

    /**
     * Returns true if the sizes of the lists in the first state are all
     * equal to the length of the corresponding list in the second state.
     *
     * @param state1 a state to compare
     * @param state2 the state to compare it to
     * @return true if the contents of one state all equals the contents of the other state
     */
    private static boolean sizeEqual(final I_GameState state1, final I_GameState state2) {
        //if a state is null there surely must be a mistake somewhere
        if (state1 == null || state2 == null) throw new NullPointerException("A state cannot be null when comparing");

        // make a stream with a data structure containing both states
        return Stream.of(new SimpleImmutableEntry<>(state1, state2))
                .flatMap( // transform the elements by making a new stream with the transformation to be used
                        pair -> Arrays.stream(E_PileID.values()) // stream the pileIDs
                                // make new pair of the elements of that pile from each state
                                .map(pileID -> new SimpleImmutableEntry<>(
                                                pair.getKey().get(pileID),
                                                pair.getValue().get(pileID)
                                        )
                                )
                )
                .allMatch(pair -> pair.getKey().size() == pair.getValue().size()); // check they have equal sizes
    }

    /**
     * Returns true only if the content of each list in one state matches exactly the content
     * of the corresponding lists in the other state. Objects.equals() is used to check equality of the contents.
     *
     * @param state1 a state to compare
     * @param state2 the state to compare it to
     * @return true if the contents of one state all equals the contents of the other state
     */
    private static boolean contentEqual(final I_GameState state1, final I_GameState state2) {
        //if a state is null there surely must be a mistake somewhere
        if (state1 == null || state2 == null) throw new NullPointerException("A state cannot be null when comparing");
        //if ( !sizeEqual(state1, state2) ) return false;

        return Stream.of(new SimpleImmutableEntry<>(state1, state2))
                .flatMap( //map the pair of states to many pairs of piles
                        statePair -> Arrays.stream(E_PileID.values())
                                .map(pileID -> new SimpleImmutableEntry<>(
                                                statePair.getKey().get(pileID),
                                                statePair.getValue().get(pileID)
                                        )
                                )
                )
                .flatMap(  // map the pairs of piles to many pairs of Optional<I_card>s.
                        listPair -> IntStream //make sure to always traverse the longer list
                                .range(0, Math.max(listPair.getKey().size(), listPair.getValue().size()))
                                .mapToObj(i -> new SimpleImmutableEntry<>(
                                        getIfExists(listPair.getKey(), i),
                                        getIfExists(listPair.getValue(), i))
                                )
                        )
                // map pairs to booleans by checking that the element in a pair are equal
                .map(cardPair -> Objects.equals(cardPair.getKey(), cardPair.getValue()))
                // reduce the many values to one bool by saying all must be true or the statement is false
                .reduce(true, (e1, e2) -> e1 && e2);
    }

    default <t extends I_GameState> Predicate<t> partiallyApplyPredicate(BiPredicate<t, t> biPred, t arg) {
        return test -> biPred.test(arg, test);
    }


    /**
     * A convenient to get an optional card from a list that might throw index out of bounds
     * @param list the list to take an element from
     * @param index the index of the element
     * @return empty if element is null or dosen't exist, otherwise the element at index wrapped in {@link Optional}
     */
    private static Optional<I_CardModel> getIfExists(List<I_CardModel> list, int index) {
        try {
            return Optional.ofNullable(list.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    //todo might be a good idea to implement a method that checks for the moves that has been tried when in a given state
}