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
import java.util.*;
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleEntry;
import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;

public class RefBoard extends AbstractBoardBase implements I_BoardModel {


    /**
     * A constructor making a randomly setup RefBoard
     */
    public RefBoard() {
        this(randBoard());
    }

    /**
     * A constructor making a new RefBoard with the layout of the provided board
     * @param boardModel The layout to copy
     */
    public RefBoard(I_BoardModel boardModel) {
        this(Arrays.stream(E_PileID.values())
                .map(pileID -> new SimpleEntry<>(pileID.name(), boardModel.getPile(pileID)))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
        );
    }

    /**
     * A constructor making a new RefBoard with the provided layout
     * @param boardData The layout to copy
     */
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


    @Override
    public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
        I_SolitaireStacks from = get(origin);
        I_SolitaireStacks to = get(destination);

        var oldFrom = List.copyOf(from);
        var oldTo = List.copyOf(to);

        //change state
        to.addAll(from.popSubset( originPos));

        change.firePropertyChange(makePropertyChangeEvent(origin, oldFrom));
        change.firePropertyChange(makePropertyChangeEvent(destination, oldTo));
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

    public static final Map<String, List<I_CardModel>> stdBoard = Map.of(
            DRAWSTACK.name(), List.of( //24 cards
                    new Card(CLUBS,5),
                    new Card(SPADES, 3),
                    new Card(SPADES, 9),
                    new Card(CLUBS,8),
                    new Card(CLUBS, 7),
                    new Card(DIAMONDS, 12),
                    new Card(HEARTS, 11),
                    new Card(HEARTS, 2),
                    new Card(CLUBS, 10),
                    new Card(CLUBS, 13),
                    new Card(SPADES, 7),
                    new Card(SPADES,11),
                    new Card(HEARTS, 5),
                    new Card(CLUBS, 2),
                    new Card(HEARTS, 4),
                    new Card(DIAMONDS, 10),
                    new Card(DIAMONDS, 7),
                    new Card(CLUBS, 9),
                    new Card(HEARTS, 10),
                    new Card(DIAMONDS,11),
                    new Card(HEARTS, 3),
                    new Card(SPADES, 6),
                    new Card(SPADES,5),
                    new Card(DIAMONDS, 1)

            ),
            BUILDSTACK1.name(), List.of( // 1 card
                    new Card(CLUBS,11)
            ),
            BUILDSTACK2.name(), List.of( // 2 cards
                    new Card(SPADES,8),
                    new Card(SPADES,1)
            ),
            BUILDSTACK3.name(), List.of( // 3 cards
                    new Card(SPADES,13),
                    new Card(DIAMONDS,13),
                    new Card(SPADES,4)
            ),
            BUILDSTACK4.name(), List.of( // 4 cards
                    new Card(CLUBS,3),
                    new Card(DIAMONDS,4),
                    new Card(SPADES,10),
                    new Card(HEARTS,8)
            ),
            BUILDSTACK5.name(), List.of( // 5 cards
                    new Card(DIAMONDS,8),
                    new Card(CLUBS,4),
                    new Card(DIAMONDS,3),
                    new Card(HEARTS,7),
                    new Card(DIAMONDS,2)
            ),
            BUILDSTACK6.name(), List.of( // 6 cards
                    new Card(CLUBS,1),
                    new Card(HEARTS,13),
                    new Card(DIAMONDS,6),
                    new Card(SPADES,12),
                    new Card(HEARTS,6),
                    new Card(CLUBS,6)
            ),
            BUILDSTACK7.name(), List.of( // 7 cards
                    new Card(HEARTS,9),
                    new Card(CLUBS,12),
                    new Card(SPADES,2),
                    new Card(DIAMONDS,5),
                    new Card(HEARTS,1),
                    new Card(HEARTS,12),
                    new Card(DIAMONDS,9)
            )
    );





}
