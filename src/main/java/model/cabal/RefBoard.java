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
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleEntry;
import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;

public class RefBoard extends AbstractBoardUtility implements I_BoardModel {


    public RefBoard() {
        this(randBoard());
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
        change = new PropertyChangeSupport(this);

        piles[DRAWSTACK.ordinal()] = new DrawStack();
        piles[SUITSTACKHEARTS.ordinal()]  = new SuitStack();
        piles[SUITSTACKDIAMONDS.ordinal()] = new SuitStack();
        piles[SUITSTACKCLUBS.ordinal()]   = new SuitStack();
        piles[SUITSTACKSPADES.ordinal()]  = new SuitStack();

        for (E_PileID pileID : E_PileID.values()) {
            if (pileID.isBuildStack())
                piles[pileID.ordinal()] = new BuildStack();
            piles[pileID.ordinal()].addAll(boardData.getOrDefault(pileID.name(), List.of()));
        }

        //Make the constructor alert history of it's initial state
        for (E_PileID pileID : E_PileID.values()) {
            change.firePropertyChange( makePropertyChangeEvent(pileID, List.of()) );
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
        var oldVal = List.copyOf(get(DRAWSTACK));

        if (turnPile.isEmpty())
            throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

        var turnCard = turnPile.turnCard();

        change.firePropertyChange(makePropertyChangeEvent(DRAWSTACK, oldVal));

        return turnCard;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return piles[DRAWSTACK.ordinal()].getTopCard();
    }


    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        var oldFrom = List.copyOf(from);
        var oldTo = List.copyOf(to);

        //change state
        to.addAll(from.popSubset(originPos));

        change.firePropertyChange(makePropertyChangeEvent(origin, oldFrom));
        change.firePropertyChange(makePropertyChangeEvent(destination, oldTo));
      }

    @Override
    public boolean canMove(E_PileID origin, int originPos, E_PileID destination) {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        Collection<I_CardModel> d = from.getSubset(originPos);
        boolean a = isValidMove(origin, originPos, destination);
        boolean b = from.canMoveFrom(originPos);
        boolean c = to.canMoveTo(d);

        return a && b && c;
    }

    @Override
    public boolean canMoveFrom(E_PileID origin, int range) {
        I_SolitaireStacks from = get(origin);
        return from.canMoveFrom(range);
    }

    private static Map<String, List<I_CardModel>> randBoard() {
        var map = new HashMap<String, List<I_CardModel>>();
        var deck = new GameCardDeck();

        var drawStack = new ArrayList<I_CardModel>();
        for (int i = 0; i < 24; i++) {
            drawStack.add(getRandCard(deck));
        }
        map.put(DRAWSTACK.name(), drawStack);

        for (int i = 1; i <= 7; i++) {
            var buildStack = new ArrayList<I_CardModel>();
            for (int j = 0; j < i; j++) {
                buildStack.add(getRandCard(deck));
            }
            map.put(BUILDSTACK1.name().replace("1", Integer.toString(i)), buildStack);
        }


        return map;
    }

    /**
     * @return a random face up card within the confines of legal suit and rank values.
     * The same value is never drawn twice.
     */
    private static I_CardModel getRandCard(GameCardDeck deck) {
        if (deck.size() == 0)
            throw new NoSuchElementException();

        var deckIterator = deck.iterator();
        var rand = new Random();
        var randIndex = rand.nextInt(deck.size());

        I_CardModel card = deckIterator.next(); //Todo be wary of this, might be an error but i'm not sure
        for (int i = 0; i < randIndex; i++) {
            card = deckIterator.next();
        }

        if (card == null)
            throw new NoSuchElementException();

        deck.remove(card);

        return card;
    }

    public static Map<String, List<I_CardModel>> stdBoard = Map.of(
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

    @Override
    public Map<E_PileID, List<I_CardModel>> makeMoveStateMap(Move m) {

        I_SolitaireStacks from = get(m.moveFromStack());
        I_SolitaireStacks to = get(m.moveToStack());

        //change state
        to.addAll(from.popSubset(m.moveFromRange()));

        Map<E_PileID, List<I_CardModel>> map = new HashMap<>();
        Collection<I_CardModel> col;
        for (E_PileID e : E_PileID.values()) {

            if (e == m.moveFromStack()) {

                col = from;

            } else if (e == m.moveToStack()) {

                col = to;

            } else {

                col = this.getPile(e);
            }
            map.put(e, new ArrayList<>(col) );
        }
        return map;
    }
}
