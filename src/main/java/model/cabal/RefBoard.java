package model.cabal;

import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeListener;
import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.E_PileID.DRAWSTACK;

public class RefBoard extends AbstractBoardUtility implements I_BoardModel {

    //Constructors
    public RefBoard(Map<String, I_CardModel> imgData) { //TODO board should take imgData to initialize self
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
            I_CardModel data = extractImgData(imgData, pileID);
            piles[pileID.ordinal()].add(data);
        }
    }

    /**
     * Use this to play the instantiate a board with a draw stack
     *
     * @param imgData
     * @param drawStack
     */
    public RefBoard(Map<String, I_CardModel> imgData, ArrayList<I_CardModel> drawStack) {
        this(imgData);
        get(DRAWSTACK).clear();
        drawStack.add(extractImgData(imgData,DRAWSTACK));
        get(DRAWSTACK).addAll(drawStack);
    }


    //InterFace implementation
    @Override
    public boolean isStackComplete(E_PileID pileID) {
        I_SolitaireStacks pile = get(pileID);

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
    public I_SolitaireStacks[] getPiles() {
        return piles;
    }

    // draw stack specific implementation.
    @Override
    public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
        DrawStack turnPile = (DrawStack) get(DRAWSTACK);

        if (turnPile.isEmpty())
            throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var returnable = turnPile.turnCard();

        var imgCard = extractImgData(imgData, DRAWSTACK);
        validateCardState(DRAWSTACK, returnable, imgCard);

        return returnable;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }


    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        //change state
        to.addAll(from.popSubset(originPos));

        //check that state is consistent with the physical board
        if (!from.isEmpty( ))
            validateCardState(origin, from.getCard(from.size() - 1), extractImgData(imgData, origin));
        else
            validateCardState(origin, null, extractImgData(imgData, origin));
    }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        boolean a = isValidMove(origin, originPos, destination);
        boolean b = from.canMoveFrom(originPos);
        boolean c = to.canMoveTo(from.getSubset(originPos));

        return a && b && c;
    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range) {
        I_SolitaireStacks from = get(origin);
        return from.canMoveFrom(range);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }
}
