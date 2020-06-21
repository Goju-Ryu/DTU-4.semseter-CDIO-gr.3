package data;

import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.StackBase;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeListener;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
/**
 * A mock Board is much like a normal board,
 * instead of staring at the starting state it just starts at a given state,
 * this means that you can initiate a board with multiple cards face op in one of the buildstacks
 * or maybe even have an empty buildstack.
 */
public class MockBoard implements I_BoardModel {
    I_BoardModel boardImplementation;


    public MockBoard() {
        boardImplementation = new Board(Map.of());
        EnumMap<E_PileID, List<I_CardModel>> map = new EnumMap<>(E_PileID.class);
        //TODO Make standard layout of MockBoard

        for (E_PileID pileID : E_PileID.values()) {
            boardImplementation.getPile(pileID).clear();
            boardImplementation.getPile(pileID).addAll(map.get(pileID));
        }

    }

    public MockBoard(Map<E_PileID, List<I_CardModel>> boardMap) {

        boardImplementation = new Board(Map.of());
        I_SolitaireStacks[] solitaireStacks = boardImplementation.getPiles();

        for (E_PileID pileID : E_PileID.values()) {

            solitaireStacks[pileID.ordinal()].clear();
            solitaireStacks[pileID.ordinal()].addAll(boardMap.get(pileID));

        }
        System.out.println("hej");
    }

    public MockBoard(I_BoardModel boardModel) {
        boardImplementation = boardModel;
    }

    /**
     * Returns a certain pile
     *
     * @param pile The pile id
     * @return a list of the cards in the given pile
     */
    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return boardImplementation.getPile(pile);
    }

    /**
     * Check if you can add more cards to the pile.
     * <p>
     * -  If a build stack has an ace on the top then you cant push anything to it
     * -  If a suitPile has a King on top then it is complete
     * -  If the draw stack is empty
     *
     * @param pileID the pile that is to be checked
     */
    @Override
    public boolean isStackComplete(E_PileID pileID) {
        return false;
    }

    /**
     * @param imgData
     * @return Turn a new card from the draWStack and return it.
     */
    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        return boardImplementation.turnCard(imgData);
    }

    /**
     * @return Get the top card in the pile of turned cards.
     */
    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }

    @Override
    public I_SolitaireStacks[] getPiles() {
        return boardImplementation.getPiles();
    }

    /**
     * This function will move a stack of cards from one destination to another
     *
     * @param origin      Where the pile is moved from
     * @param originPos   The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @param imgData
     * @throws IllegalMoveException If one of the piles cannot do the operation due to rules constraints.
     */
    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        boardImplementation.move(origin, originPos, destination, imgData);
    }


    /**
     * This function will determine if the move is legal when you look at the rules
     *
     * @param origin      Where the pile is moved from
     * @param originPos   The index of how high in the pile we move from. the top position is 0.
     * @param destination Where the pile is moved to
     * @return true if legal or false if illegal
     */
    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) {
        return false;
    }

    /**
     * This function will do the same as the one above, but you dont have to define the position where you
     * would split the pile.
     * <p>
     * This will be useful when moving from turnPile or suitPiles because you can only take the top card in the pile.
     * <p>
     * We dont have to implement this.
     *
     * @param origin      Where the pile is moved from
     * @param destination Where the pile is moved to
     * @return true if legal or false if illegal
     */
    @Override
    public boolean canMove(E_PileID origin, E_PileID destination) {
        return false;
    }


    /**
     * Add a listener to the board. This subscribes it to all piles on the board.
     *
     * @param listener the listener to be attached.
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    /**
     * Remove a listener from the board. This detaches it from all piles on the board.
     *
     * @param listener the listener to be detached.
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range){
        // todo implement this
        return false;
    }

}