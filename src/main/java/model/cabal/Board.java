package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.*;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;


/**
 * This is the model of the entire cabal
 */
public class Board implements I_BoardModel {

    private PropertyChangeSupport change;

    private I_SolitaireStacks[] piles;

    //can start here
    public Board(Map<String, I_CardModel> imgData) { //TODO board should take imgData to initialize self
        change = new PropertyChangeSupport(this);
        piles = new I_SolitaireStacks[E_PileID.values().length];

        piles[DRAWSTACK.ordinal()] = new DrawStack();
        piles[SUITSTACKHEARTS.ordinal()]  = new SuitStack();
        piles[SUITSTACKDIAMONDS.ordinal()] = new SuitStack();
        piles[SUITSTACKCLUBS.ordinal()]   = new SuitStack();
        piles[SUITSTACKSPADES.ordinal()]  = new SuitStack();

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

        for (E_PileID e: E_PileID.values()) {

            I_CardModel c = imgData.get(e.toString());
            if(c == null)
                continue;
            piles[e.ordinal()].add(c);
        }

        /*for (E_PileID pileID : E_PileID.values()) {
            var data = extractImgData(imgData, pileID);

            if (data != null) {
                if (GameCardDeck.getInstance().remove(data)) { //if the card was in deck and now removed
                    piles[pileID.ordinal()].add(data);
                } else {
                    throw new IllegalStateException("Trying to add the same card twice during construction.\ncard: " + data);
                }
            }

        }*/
    }

//---------  Genneral methods  -------------------------------------------------------------------------------------

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        var pile = get(pileID);

        if (pileID.isBuildStack())
            return pile.getCard(0).getRank() == 1; // true if ace on top

        if (pileID == DRAWSTACK)
            return pile.isEmpty();

        //Now we know only suit stacks are left
        return pile.getCard(0).getRank() == 13; // true if suitStack has a king on top
    }

    @Override
    public List<I_CardModel> getPile(E_PileID pile) {
        return List.copyOf(get(pile));
    }

    private I_SolitaireStacks get(E_PileID pile){
        return piles[pile.ordinal()];
    }

    public I_SolitaireStacks[] getPiles(){
        return piles;
    };

    /**
     * checks if state is equal to physical board
     * @param imgData state to validate against
     * @throws IllegalStateException if state is out of sync
     */
    private void validateState(Map<String, I_CardModel> imgData) throws IllegalStateException {
        for (E_PileID pileID : E_PileID.values()) {
            var pile = piles[pileID.ordinal()];
            validateCardState(pileID, pile.getCard(pile.size() - 1), extractImgData(imgData, pileID));
        }
    }

    /**
     * Query the map for the relevant data and return it.
     * @param imgData The map of data to check extract the data from
     * @param key The pile to which the data should correspond
     * @return The data the map contains about the given pile
     */
    private I_CardModel extractImgData(Map<String, I_CardModel> imgData, E_PileID key) {
        return imgData.getOrDefault(key.name(), null); //This assumes a strict naming scheme and will return null if not found
    }

    /**
     * Method for generating an IllegalStateException with an appropriate error message
     * @param pos The pile where state is out of sync
     * @param physCard The value of the physical card
     * @param virtCard The card represented in the virtual representation of the board (this class)
     * @param info Extra information that might be helpful
     * @return An exception with a nicely formatted message
     */
    private IllegalStateException
    makeStateException(E_PileID pos, I_CardModel physCard, I_CardModel virtCard, String info) {
        return new IllegalStateException(
                "The virtual board and the physical board is out of sync\n" +
                "\tPosition:\t" + pos + "\n" +
                "\tVirtual:\t" + virtCard + "\n" +
                "\tPhysical:\t" + physCard + "\n" +
                "\tMethod:\t" +  Thread.currentThread().getStackTrace()[2] + "\n" +
                "\tInfo:\t" + info
        );
    }

    /**
     * Checks if the state of a card is compatible with the card gotten from the external model.
     * If the card is face down, the method will try to reveal it with the correct values.
     * @param origin the pile the card is from.
     * @param cardModel the card to validate.
     * @param imgCard the card being validated against
     */
    private void
    validateCardState(E_PileID origin, I_CardModel cardModel, I_CardModel imgCard) throws IllegalStateException {
        if ( cardModel != null && !cardModel.isFacedUp()) {
            if (GameCardDeck.getInstance().remove(imgCard)) { //if the card was in deck and now removed
                cardModel.reveal(imgCard.getSuit(), imgCard.getRank());
            } else {
                throw new IllegalStateException("Trying to reveal card but card is already in play.\ncard: " + imgCard);
            }
        } else {
            if (!Objects.equals(cardModel, imgCard))
                throw makeStateException(origin, imgCard, cardModel, "no info");
        }
    }

//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        var turnPile = (DrawStack) get(DRAWSTACK);

        if (turnPile.isEmpty())
                throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var returnable = turnPile.turnCard();

        var imgCard = extractImgData(imgData, DRAWSTACK);
        validateCardState(DRAWSTACK, returnable, imgCard);

        return returnable;
    }

    @Override
    public I_CardModel getTurnedCard() {
        var turnPile = (DrawStack) get(DRAWSTACK);
        return turnPile.getTopCard();
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
        Collection<I_CardModel> oldOrigin = Collections.unmodifiableCollection(get(origin));
        Collection<I_CardModel> oldDest = Collections.unmodifiableCollection(get(destination));

        //change state
        to.addAll(from.popSubset(originPos));

        //check that state is consistent with the physical board
        if (!from.isEmpty( ))
            validateCardState(origin, from.getCard(from.size() - 1), extractImgData(imgData, origin));
        else
            validateCardState(origin, null, extractImgData(imgData, origin));

        //notify listeners om state before and after state change
        change.firePropertyChange( makePropertyChangeEvent(origin, oldOrigin) );
        change.firePropertyChange( makePropertyChangeEvent(destination, oldDest) );
    }


    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        return isValidMove(origin, originPos, destination)
                && from.canMoveFrom(originPos)
                && to.canMoveTo(from.getSubset(originPos));
    }



    private boolean isValidMove(E_PileID from, int originPos, E_PileID to) {

        // if you try to move to the same stack
        if(from == to)
            return false;

        // If you try to move to the turn pile
        if (to.equals(DRAWSTACK))
            return false;

        var fromPile = get(from);

        if ( !fromPile.getCard(fromPile.size() - originPos).isFacedUp() )
            return false;

        var cardSuits = fromPile.getSubset(originPos).stream()
                .map(I_CardModel::getSuit);
        switch (to) {
            case SUITSTACKHEARTS:
                if (cardSuits.anyMatch( suit -> !suit.equals(HEARTS)))
                    return false;
                break;
            case SUITSTACKDIAMONDS:
                if (cardSuits.anyMatch( suit -> !suit.equals(DIAMONDS)))
                    return false;
                break;
            case SUITSTACKSPADES:
                if (cardSuits.anyMatch( suit -> !suit.equals(SPADES)))
                    return false;
                break;
            case SUITSTACKCLUBS:
                if (cardSuits.anyMatch( suit -> !suit.equals(CLUBS)))
                    return false;
                break;
        }

        return true;
    }
//-----------------------------------PropertyEditor methods-------------------------------------------------------------

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        change.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        change.removePropertyChangeListener(listener);
    }

    private PropertyChangeEvent makePropertyChangeEvent(E_PileID pile, Collection<I_CardModel> oldVal) {
        int pileIndex = pile.ordinal();
        return new PropertyChangeEvent(
                piles[pileIndex],
                pile.getDescription(),
                oldVal,
                Collections.unmodifiableCollection(piles[pileIndex])
        );

    }
}
