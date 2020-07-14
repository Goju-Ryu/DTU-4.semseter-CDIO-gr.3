package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static model.cabal.E_PileID.*;


/**
 * This is the model of the entire cabal
 */
public class Board extends AbstractBoardBase implements I_BoardModel {

    public Board(Map<E_PileID, I_CardModel> imgData, GameCardDeck cardDeck) { //TODO board should take imgData to initialize self
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
                var data = imgData.get(pileID);

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
    public Board(Map<E_PileID, I_CardModel> imgData, GameCardDeck cardDeck, List<I_CardModel> drawStack) {
        this(imgData, cardDeck);
        get(DRAWSTACK).clear();
        var filteredDrawStack = drawStack.stream().filter(Objects::nonNull).collect(Collectors.toList());
        get(DRAWSTACK).addAll(List.copyOf(filteredDrawStack));
        deck.removeAll(filteredDrawStack.stream().filter(I_CardModel::isFacedUp).collect(Collectors.toList()));
    }

//---------  Genneral methods  -------------------------------------------------------------------------------------


    /**
     * checks if state is equal to physical board
     *
     * @param imgData state to validate against
     * @throws IllegalStateException if state is out of sync
     */
    private void validateState(Map<E_PileID, I_CardModel> imgData) throws IllegalStateException {
        for (E_PileID pileID : E_PileID.values()) {
            var pile = piles[pileID.ordinal()];
            var imgCard = imgData.get(pileID);


            validatePileState(pileID, pile.isEmpty() ? null : pile.getTopCard(), imgCard);
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

    //----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(final E_PileID origin, final int originPos, final E_PileID destination, Map<E_PileID, I_CardModel> imgData)
            throws IllegalMoveException {

        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);
        I_CardModel card = from.getCard(originPos);
        /*TODO!!!!!!!!
        boolean a =isValidMove(origin, originPos, destination);
        if (!isValidMove(origin, originPos, destination))
            throw new IllegalMoveException("Cards cannot be moved between " + origin + " with card "+card+" and " + destination);

        if (!from.canMoveFrom(originPos))
            throw new IllegalMoveException(origin + " Cannot move cards at position " + originPos);

        Collection<I_CardModel> moveCards = from.getSubset(originPos);
        if (!to.canMoveTo(moveCards))
            throw new IllegalMoveException(destination + " Cannot receive cards: " + moveCards);
            */
        //save state before operation
        Collection<I_CardModel> oldOrigin = List.copyOf(get(origin));
        Collection<I_CardModel> oldDest = List.copyOf(get(destination));

        //change state
        to.addAll(from.popSubset(originPos));

        //Validate that state is still consistent
        validatePileState(
                origin,
                from.isEmpty() ? null : from.getTopCard(),
                imgData.get(origin)
        );
        validatePileState(
                destination,
                to.isEmpty() ? null : to.getTopCard(),
                imgData.get(destination)
        );

        //notify listeners om state before and after state change
        change.firePropertyChange(makePropertyChangeEvent(origin, oldOrigin));
        change.firePropertyChange(makePropertyChangeEvent(destination, oldDest));
    }

}





