package model.cabal;

import static model.cabal.E_PileID.*;

import data.InputAccesPoint;
import data.InputDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.UnexpectedException;
import java.util.*;


/**
 * This is the model of the entire cabal
 */
public final class Board implements I_BoardModel {

    private PropertyChangeSupport change;

    private I_SolitaireStacks[] piles;

    private InputDTO accessInput = new InputDTO();

    public Board() { //TODO board should take imgData to initialize self
        change = new PropertyChangeSupport(this);
        piles = new I_SolitaireStacks[values().length];

        String usrInput = accessInput.getUsrInput();
        System.out.println(usrInput);
        for (E_PileID id : E_PileID.values()) {
            if (id == TURNPILE) { //initializes with 24 cards face down
                var stack = new DrawStack();
                for (int j = 0; j < 24; j++) {
                    stack.add(new Card());
                }
                piles[id.ordinal()] = stack;
            }
            else if (id.isBuildStack()) {
                var stack = new BuildStack();

                switch (id){ // notice no break. a card enters and ads 1 card for every case beneath it
                    case BUILDSTACK7: stack.add(new Card());
                    case BUILDSTACK6: stack.add(new Card());
                    case BUILDSTACK5: stack.add(new Card());
                    case BUILDSTACK4: stack.add(new Card());
                    case BUILDSTACK3: stack.add(new Card());
                    case BUILDSTACK2: stack.add(new Card());
                    case BUILDSTACK1: stack.add(new Card()); break;
                    default:
                        throw new RuntimeException("An unknown E_Pile_ID with isBuildStack=true was encountered: " + id);
                }

                piles[id.ordinal()] = stack;
            }
            else if (id == HEARTSACEPILE || id == DIAMONDACEPILE || id == CLUBSACEPILE || id == SPADESACEPILE) {
                piles[id.ordinal()] = new SuitStack();
            }
            else {
                throw new RuntimeException("An unknown E_Pile_ID was encountered: " + id);
            }
        }
    }



//---------  Genneral methods  -------------------------------------------------------------------------------------

    @Override
    public boolean isStackComplete(E_PileID pileID) {
        var pile = get(pileID);

        if (pileID.isBuildStack())
            return pile.getCard(0).getRank() == 1; // true if ace on top

        if (pileID == TURNPILE)
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


    /**
     * checks if state is equal to physical board
     * @param imgData state to validate against
     * @throws IllegalStateException if state is out of sync
     */
    private void validateState(Map<String, I_CardModel> imgData) {

    }

    /**
     * Query the map for the relevant data and return it.
     * @param imgData The map of data to check extract the data from
     * @param key The pile to which the data should correspond
     * @return The data the map contains about the given pile
     */
    private I_CardModel extractImgData(Map<String, I_CardModel> imgData, E_PileID key) {
        if(imgData.containsKey(key.name()))
            return imgData.get(key.name()); //This assumes a strict naming scheme and will return null if not found
        else throw new NoSuchElementException("key \"" + key.name() + "\" not found in map: " + imgData);
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


//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        var turnPile = (DrawStack) get(TURNPILE);

        if (turnPile.isEmpty())
                throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var returnable = turnPile.turnCard();

        var imgCard = extractImgData(imgData, TURNPILE);
        if ( !returnable.isFacedUp() ) {
            returnable.reveal(imgCard.getSuit(), imgCard.getRank());
        } else {
            if ( !returnable.equals(imgCard) )
                throw makeStateException(TURNPILE, imgCard, returnable, "no info");
        }

        return returnable;
    }

    @Override
    public I_CardModel getTurnedCard() {
        var turnPile = (DrawStack) get(TURNPILE);
        return turnPile.getTopCard();
    }


//----------  Move card methods  -----------------------------------------------------------------------------

    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData)
            throws IllegalMoveException {

        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        if (!isValidMove(from, to))
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
        var exposedCard = from.getCard(from.size() - 1);
        var imgCard = extractImgData(imgData, origin);
        if ( !exposedCard.isFacedUp() ) {
            exposedCard.reveal(imgCard.getSuit(), imgCard.getRank());
        } else {
            if ( !exposedCard.equals(imgCard) )
                throw makeStateException(origin, imgCard, exposedCard, "no info");
        }

        //notify listeners om state before and after state change
        change.firePropertyChange( makePropertyChangeEvent(origin, oldOrigin) );
        change.firePropertyChange( makePropertyChangeEvent(destination, oldDest) );
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        return isValidMove(from, to)
               && from.canMoveFrom(originPos)
               && to.canMoveTo(from.getSubset(originPos));
    }

    private boolean isValidMove(I_SolitaireStacks from, I_SolitaireStacks to) {
        if(from == to)
            return false;

        var turnPile = get(TURNPILE);

        if (to.equals(turnPile))
            return false;

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
