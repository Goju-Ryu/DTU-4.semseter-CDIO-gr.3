package model.cabal;

import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public interface I_BoardModel {

//---------  Genneral methods  -------------------------------------------------------------------------------------

    /**
     * Returns a certain pile
     *
     * @param pile The pile id
     * @return a list of the cards in the given pile
     */
    List<I_CardModel> getPile(E_PileID pile);

    /**
     * Check if you can add more cards to the pile.
     *
     *   -  If a build stack has an ace on the top then you cant push anything to it
     *   -  If a suitPile has a King on top then it is complete
     *   -  If the draw stack is empty
     *
     * @param pileID the pile that is to be checked
     */
    boolean isStackComplete(E_PileID pileID);


//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    /**
     *
     * @return Turn a new card from the draWStack and return it.
     */
    I_CardModel turnCard(Map<String, I_CardModel> imgData);

    /**
     *
     * @return Get the top card in the pile of turned cards.
     */
    I_CardModel getTurnedCard();

    I_SolitaireStacks[] getPiles();

//----------  Move card methods  -----------------------------------------------------------------------------

    /**
     * This function will move a stack of cards from one destination to another
     *
     * @param origin Where the pile is moved from
     * @param originPos The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException If one of the piles cannot do the operation due to rules constraints.
     */
    void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException;

    /**
     * This function will move a stack of cards from one destination to another.
     * It will be used for the turned pile or the ace piles because you can only move the top cards from those piles
     *
     * We dont have to implement this
     *
     * @param origin Where the pile is moved from
     * @param destination Where the pile is moved to
     * @throws IllegalMoveException If one of the piles cannot do the operation due to rules constraints.
     */
    default void move(E_PileID origin, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        move(origin, 1, destination, imgData);
    }

    /**
     * This function will determine if the move is legal when you look at the rules
     *
     * @param origin Where the pile is moved from
     * @param originPos The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @return true if legal or false if illegal
     */
    boolean canMove(E_PileID origin, int originPos, E_PileID destination);

    /**
     * This function will do the same as the one above, but you dont have to define the position where you
     * would split the pile.
     *
     * This will be useful when moving from turnPile or suitPiles because you can only take the top card in the pile.
     *
     * We dont have to implement this.
     *
     * @param origin Where the pile is moved from
     * @param destination Where the pile is moved to
     * @return true if legal or false if illegal
     */
    default boolean canMove(E_PileID origin, E_PileID destination) {
        return canMove(origin,1, destination);
    }


//-----------  PropertyListener Support   ----------------------------------------

    /**ner to the board. This subscribes it to all piles on the board.
     * @pa
     *      * Add a listeram listener the listener to be attached.
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a listener from the board. This detaches it from all piles on the board.
     * @param listener the listener to be detached.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}
