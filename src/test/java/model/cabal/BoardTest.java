package model.cabal;

import model.GameCardDeck;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    @Test
    void constructor() {
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);

        assertEquals(7, board.getPile(E_PileID.BUILDSTACK7).size());
        assertEquals(1, board.getPile(E_PileID.BUILDSTACK1).size());
        assertEquals(24, board.getPile(E_PileID.TURNPILE).size());
        assertEquals(0, board.getPile(E_PileID.HEARTSACEPILE).size());
    }

    @Test
    void isStackComplete() {
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);
        //I_BoardModel boardModel = new Board()

        board.turnCard(getImgData(board));


        if (board.canMove(E_PileID.BUILDSTACK1, E_PileID.SPADESACEPILE)) {
            System.out.println("The move is legal");
        }

        var testData = getImgData(board);
        board.move(E_PileID.BUILDSTACK1, E_PileID.SPADESACEPILE, testData);






        System.out.println(board.getPile(E_PileID.BUILDSTACK1));




    }

    @Test
    void getPile() {
    }

    @Test
    void getPiles() {
    }

    @Test
    void turnCard() {
    }

    @Test
    void getTurnedCard() {
    }

    @Test
    void move() {
    }

    @Test
    void canMove() {

    }

    @Test
    void addPropertyChangeListener() {
    }

    @Test
    void removePropertyChangeListener() {
    }

    // todo note that this only tests heartstack, this should be exended to test all implementations of suitstack, even if they are identical
    private SuitStack createSuitStack(int elements){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(E_CardSuit.HEARTS,i+1);
            suitStack.add(card);
        }

        return suitStack;
    }

    /**
     * Artificially makes an imageData that agrees with board in all instances except for when a card is face down.
     * When a face down card is encountered it is replaced by a random face up card.
     * @param board The board to which the data should conform;
     * @return a map representing imgData as in the actual system
     */
    private Map<String, I_CardModel> getImgData(I_BoardModel board) {
        return Stream.of(E_PileID.values())
                .filter(pile -> board.getPile(pile).size() > 1)
                .map( // transforms elements of the stream to mapEntries
                        pile -> {
                            var pileList = board.getPile(pile);
                            return new AbstractMap.SimpleEntry<>(pile.name(), pileList.get(pileList.size() - 1));
                        }
                )
                .peek(entry -> { // replaces face down cards with a randomly generated card
                            if ( entry.getValue() != null && !entry.getValue().isFacedUp() )
                                entry.setValue(getRandCard());
                        }
                )
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)); // converts result to a map
    }

    /**
     * Artificially makes an imageData that contains a random set of face up cards.
     * @return a map representing imgData as in the actual system
     */
    private Map<String, I_CardModel> getImgData() {
        return Stream.of(E_PileID.values())
                .map(pile -> new AbstractMap.SimpleEntry<>(pile.name(), (I_CardModel) new Card()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    /**
     * @return a random face up card within the confines of legal suit and rank values
     */
    private I_CardModel getRandCard() {
        var deck = GameCardDeck.getInstance();
        var deckIterator = deck.iterator();
        var rand = new Random();
        var randIndex = rand.nextInt(deck.size());
        I_CardModel card = deckIterator.next();
        for (int i = 0; i < randIndex; i++) {
            card = deckIterator.next();
        }

        return card;
    }
}