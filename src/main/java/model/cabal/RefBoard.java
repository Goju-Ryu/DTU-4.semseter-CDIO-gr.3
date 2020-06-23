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
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleEntry;
import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;

public class RefBoard extends AbstractBoardUtility implements I_BoardModel {



    public RefBoard() {
        this(stdBoard);
    }

    public RefBoard(I_BoardModel boardModel) {
        this(Arrays.stream(E_PileID.values())
                .map(pileID -> new SimpleEntry<>(pileID.name(), boardModel.getPile(pileID)))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
        );
    }

    //Constructors
    public RefBoard(Map<String, List<I_CardModel>> boardData) { //TODO board should take imgData to initialize self
        piles = new I_SolitaireStacks[E_PileID.values().length];

        piles[DRAWSTACK.ordinal()] = new DrawStack();
        piles[SUITSTACKHEARTS.ordinal()]  = new SuitStack();
        piles[SUITSTACKDIAMONDS.ordinal()] = new SuitStack();
        piles[SUITSTACKCLUBS.ordinal()]   = new SuitStack();
        piles[SUITSTACKSPADES.ordinal()]  = new SuitStack();

        for (E_PileID pileID : E_PileID.values()) {
            if (pileID.isBuildStack())
                piles[pileID.ordinal()] = new BuildStack();
            piles[pileID.ordinal()].addAll(boardData.get(pileID.name()));
        }
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

        return turnPile.turnCard();
    }

    @Override
    public I_CardModel getTurnedCard() {
        return piles[DRAWSTACK.ordinal()].getTopCard();
    }


    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        //change state
        to.addAll(from.popSubset(originPos));

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

    private static Map<String, List<I_CardModel>> stdBoard = Map.of(
            DRAWSTACK.name(), List.of( //24 cards
                    new Card(HEARTS,2 ),
                    new Card(SPADES, 1),
                    new Card(CLUBS, 6),
                    new Card(HEARTS,5 ),
                    new Card(DIAMONDS, 4),
                    new Card(HEARTS, 11),
                    new Card(CLUBS, 7),
                    new Card(CLUBS, 1),
                    new Card(HEARTS, 12),
                    new Card(DIAMONDS, 10),
                    new Card(HEARTS, 3),
                    new Card(SPADES,4 ),
                    new Card(SPADES, 7),
                    new Card(CLUBS, 2),
                    new Card(HEARTS, 4),
                    new Card(CLUBS, 9),
                    new Card(DIAMONDS, 11),
                    new Card(HEARTS, 13),
                    new Card(DIAMONDS, 5),
                    new Card(HEARTS,10 ),
                    new Card(SPADES, 6),
                    new Card(CLUBS, 8),
                    new Card(HEARTS,9 ),
                    new Card(SPADES, 5)

            ),
            BUILDSTACK1.name(), List.of( // 1 card
                    new Card(DIAMONDS,6)
            ),
            BUILDSTACK2.name(), List.of( // 2 cards
                    new Card(CLUBS,3),
                    new Card(SPADES,8)
            ),
            BUILDSTACK3.name(), List.of( // 3 cards
                    new Card(DIAMONDS,13),
                    new Card(DIAMONDS,7),
                    new Card(SPADES,9)
            ),
            BUILDSTACK4.name(), List.of( // 4 cards
                    new Card(SPADES,13),
                    new Card(DIAMONDS,12),
                    new Card(DIAMONDS,9),
                    new Card(CLUBS,10)
            ),
            BUILDSTACK5.name(), List.of( // 5 cards
                    new Card(SPADES,11),
                    new Card(CLUBS,13),
                    new Card(SPADES,3),
                    new Card(CLUBS,12),
                    new Card(HEARTS,6)
            ),
            BUILDSTACK6.name(), List.of( // 6 cards
                    new Card(SPADES,12),
                    new Card(HEARTS,8),
                    new Card(SPADES,2),
                    new Card(CLUBS,4),
                    new Card(DIAMONDS,8),
                    new Card(HEARTS,7)
            ),
            BUILDSTACK7.name(), List.of( // 7 cards
                    new Card(DIAMONDS,2),
                    new Card(SPADES,10),
                    new Card(CLUBS,5),
                    new Card(DIAMONDS,3),
                    new Card(DIAMONDS,1),
                    new Card(HEARTS,1),
                    new Card(CLUBS,11)
            )
    );
}
