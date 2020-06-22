package model.cabal;

import model.GameCardDeck;
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
import java.util.*;

import static model.cabal.E_PileID.*;


/**
 * This is the model of the entire cabal
 */
public class Board extends AbstractBoardUtility implements I_BoardModel {

    private PropertyChangeSupport change;


    public Board(Map<String, I_CardModel> imgData, GameCardDeck cardDeck) { //TODO board should take imgData to initialize self
        deck = cardDeck;
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
            }catch (Exception e){
                System.out.println("an input was empty");
            }
        }
    }

    /**
     * Use this to play the instantiate a board with a draw stack
     *
     * @param imgData
     * @param drawStack
     */
    public Board(Map<String, I_CardModel> imgData, GameCardDeck cardDeck, ArrayList<I_CardModel> drawStack) {
        this(imgData, cardDeck);
        get(DRAWSTACK).clear();
        drawStack.add(extractImgData(imgData,DRAWSTACK));
        get(DRAWSTACK).addAll(drawStack);
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

    @Override
    public I_SolitaireStacks[] getPiles(){
        return piles;
    };

//---------  Methods for the cardPile and the turnPile  --------------------------------------------------------

    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        var turnPile = (DrawStack) get(DRAWSTACK);

        if (turnPile.isEmpty())
                throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var returnable = turnPile.turnCard();

        var imgCard = extractImgData(imgData, DRAWSTACK);

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


        //notify listeners om state before and after state change
        change.firePropertyChange( makePropertyChangeEvent(origin, oldOrigin) );
        change.firePropertyChange( makePropertyChangeEvent(destination, oldDest) );
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        boolean a = isValidMove(origin, originPos, destination);
        if(!a)
            return false;
        boolean b = from.canMoveFrom(originPos);
        if(!b)
            return false;
        boolean c = to.canMoveTo(from.getSubset(originPos));

        return a && b && c;

    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range){
        I_SolitaireStacks from = get(origin);
        return from.canMoveFrom(range);
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
                pile.name(),
                oldVal,
                Collections.unmodifiableCollection(piles[pileIndex])
        );

    }




}
