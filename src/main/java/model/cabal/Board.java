package model.cabal;

import model.GameCardDeck;
import model.Move;
import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeSupport;
import java.util.*;

import static model.cabal.E_PileID.*;


/**
 * This is the model of the entire cabal
 */
public class Board extends AbstractBoardUtility implements I_BoardModel {

    public Board(Map<String, I_CardModel> imgData, GameCardDeck cardDeck) { //TODO board should take imgData to initialize self
        deck = cardDeck;
        change = new PropertyChangeSupport(this);
        piles = new I_SolitaireStacks[E_PileID.values().length];

        piles[DRAWSTACK.ordinal()] = new DrawStack();
        piles[SUITSTACKHEARTS.ordinal()] = new SuitStack();
        piles[SUITSTACKDIAMONDS.ordinal()] = new SuitStack();
        piles[SUITSTACKCLUBS.ordinal()] = new SuitStack();
        piles[SUITSTACKSPADES.ordinal()] = new SuitStack();

        for (int i = 0; i < 24; i++) {
            piles[DRAWSTACK.ordinal()].add(new Card());
        }

        for (int i = 0; i < 7; i++) { // for each build pile
            for (int j = 0; j <= i; j++) {  // how many cards in this pile
                if (piles[BUILDSTACK1.ordinal() + i] == null)
                    piles[BUILDSTACK1.ordinal() + i] = new BuildStack();
                else
                    piles[BUILDSTACK1.ordinal() + i].add(new Card());
            }
        }

        for (E_PileID pileID : E_PileID.values()) {
            try {
                var data = extractImgData(imgData, pileID);

                if (data != null) {

                    // if gets an instance of GameCardDeck, so this removes the card from a collection
                    // of cards we know we haven't seen yet
                    if (deck.remove(data)) {
                        piles[pileID.ordinal()].add(data);
                    } else {

                        //this else is only legit if we have seen a
                        // this specific card more than once.
                        throw new IllegalStateException("Trying to add the same card twice during construction.\ncard: " + data);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("an input was empty");
            }
        }

        //Validate that state is still consistent
        validateState(imgData);

        //Make the constructor alert history of it's initial state
        for (E_PileID pileID : values()) {
            change.firePropertyChange(makePropertyChangeEvent(pileID, List.of()));
        }
    }

    /**
     * Use this to play the instantiate a board with a draw stack
     *
     * @param imgData   the initializing data, often taken from the camera
     * @param drawStack A list of alll cards in the draw stack in the physical board / simulated board.
     */
    public Board(Map<String, I_CardModel> imgData, GameCardDeck cardDeck, List<I_CardModel> drawStack) {
        this(imgData, cardDeck);
        get(DRAWSTACK).clear();
        drawStack.remove(null);

        get(DRAWSTACK).addAll(drawStack);
    }

//---------  Genneral methods  -------------------------------------------------------------------------------------

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        var pile = get(pileID);

        if (pileID.isBuildStack())
            return pile.getCard(pile.size() - 1).getRank() == 1; // true if ace on top

        if (pileID == DRAWSTACK)
            return pile.isEmpty();

        //Now we know only suit stacks are left
        return pile.getCard(pile.size() - 1).getRank() == 13; // true if suitStack has a king on top
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return List.copyOf(get(pile));
    }

    @Override
    public I_SolitaireStacks[] getPiles() {
        return piles;
    }

    /**
     * checks if state is equal to physical board
     *
     * @param imgData state to validate against
     * @throws IllegalStateException if state is out of sync
     */
    private void validateState(Map<String, I_CardModel> imgData) throws IllegalStateException {
        for (E_PileID pileID : E_PileID.values()) {
            var pile = piles[pileID.ordinal()];
            var imgCard = extractImgData(imgData, pileID);


            validatePileState(pileID, pile.getTopCard(), imgCard);
        }
    }

    /**
     * Checks if the state of a card is compatible with the card gotten from the external model.
     * If the card is face down, the method will try to reveal it with the correct values.
     *
     * @param origin    the pile the card is from.
     * @param cardModel the card to validate.
     * @param imgCard   the card being validated against
     */
    private void
    validatePileState(E_PileID origin, I_CardModel cardModel, I_CardModel imgCard) throws IllegalStateException {
        if (cardModel == imgCard)
            return;
        if (imgCard == null)
            return; // If there is no data for a field ignore it TODO is this what we want

        if (cardModel == null)
            throw makeStateException(origin, imgCard, null);

        if (!cardModel.isFacedUp()) {
            if (deck.remove(imgCard)) { //if the card was in deck and now removed
                cardModel.reveal(imgCard.getSuit(), imgCard.getRank());
            } else {
                throw new IllegalStateException("Trying to reveal card but card is already in play.\ncard: " + imgCard);
            }
        } else {
            if (!cardModel.equals(imgCard))
                throw makeStateException(origin, imgCard, cardModel);
        }
    }

    /**
     * Method for generating an IllegalStateException with an appropriate error message
     *
     * @param pos      The pile where state is out of sync
     * @param physCard The value of the physical card
     * @param virtCard The card represented in the virtual representation of the board (this class)
     * @return An exception with a nicely formatted message
     */
    private IllegalStateException
    makeStateException(E_PileID pos, I_CardModel physCard, I_CardModel virtCard) {
        return new IllegalStateException(
                "The virtual board and the physical board is out of sync\n" +
                        "\tPosition:\t" + pos + "\n" +
                        "\tVirtual:\t" + virtCard + "\n" +
                        "\tPhysical:\t" + physCard + "\n" +
                        "\tMethod:\t" + Thread.currentThread().getStackTrace()[2] + "\n"
        );
    }

//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        var turnPile = (DrawStack) get(DRAWSTACK);

        if (turnPile.isEmpty())
            throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var oldVal = List.copyOf(turnPile);
        var returnable = turnPile.turnCard();

        //Validate that state is still consistent with the physical board
        validatePileState(DRAWSTACK, returnable, extractImgData(imgData, DRAWSTACK));

        //notify history
        change.firePropertyChange(makePropertyChangeEvent(DRAWSTACK, oldVal));

        return returnable;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return get(DRAWSTACK).getTopCard();
    }

//----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData)
            throws IllegalMoveException {

        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!isValidMove(origin, originPos, destination))
            throw new IllegalMoveException("Cards cannot be moved between " + origin + " and " + destination);

        if (!from.canMoveFrom(originPos))
            throw new IllegalMoveException(origin + " Cannot move cards at position " + originPos);

        Collection<I_CardModel> moveCards = from.getSubset(originPos);
        if (!to.canMoveTo(moveCards))
            throw new IllegalMoveException(destination + " Cannot receive cards: " + moveCards);

        //save state before operation
        Collection<I_CardModel> oldOrigin = Collections.unmodifiableCollection(get(origin)); //todo try list of
        Collection<I_CardModel> oldDest = Collections.unmodifiableCollection(get(destination));

        //change state
        to.addAll(from.popSubset(originPos));

        //Validate that state is still consistent
        validatePileState(
                origin,
                from.isEmpty() ? null : from.getTopCard(),
                extractImgData(imgData, origin)
        );
        validatePileState(
                destination,
                to.isEmpty() ? null : to.getTopCard(),
                extractImgData(imgData, destination)
        );

        //notify listeners om state before and after state change
        change.firePropertyChange(makePropertyChangeEvent(origin, oldOrigin));
        change.firePropertyChange(makePropertyChangeEvent(destination, oldDest));
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!isValidMove(origin, originPos, destination))
            return false;
        if (!from.canMoveFrom(originPos))
            return false;
        if (!to.canMoveTo(from.getSubset(originPos)))
            return false;

        return true;

    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range) {
        I_SolitaireStacks from = get(origin);
        return from.canMoveFrom(range);
    }

    @Override
    public Map<E_PileID, List<I_CardModel>> makeMoveStateMap(Move m) {

        I_SolitaireStacks from = get( m.moveFromStack() );
        I_SolitaireStacks to   = get( m.moveToStack()   );

        Collection<I_CardModel> subSet  = from.getSubset(m.moveFromRange());
        Collection<I_CardModel> fromSet = new ArrayList<>(from);
        fromSet.removeAll(subSet);
        Collection<I_CardModel> toSet = new ArrayList<>(to);
        toSet.addAll(subSet);

        Map<E_PileID, List<I_CardModel>> map = new HashMap<>();
        Collection<I_CardModel> col;

        for (E_PileID e : E_PileID.values()) {

            if (e == m.moveFromStack()) {

                col = fromSet;

            } else if (e == m.moveToStack()) {

                col = toSet;

            } else {

                col = this.getPile(e);
            }
            map.put(e, new ArrayList<>(col) );
        }
        return map;
    }
}





