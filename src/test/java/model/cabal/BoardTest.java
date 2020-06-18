package model.cabal;

import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BoardTest {

    @Test
    void isStackComplete() {

        Card card1 = new Card(E_CardSuit.SPADES,2);
        Card card2 = new Card(E_CardSuit.SPADES,1);
        Card card3 = new Card(E_CardSuit.SPADES,3);
        Card card4 = new Card(E_CardSuit.SPADES,4);
        Card card5 = new Card(E_CardSuit.SPADES,5);
        Card card6 = new Card(E_CardSuit.SPADES,6);
        Card card7 = new Card(E_CardSuit.SPADES,7);
        Card card8 = new Card(E_CardSuit.SPADES,8);

        var imgData = Map.of(E_PileID.TURNPILE.name(), (I_CardModel) new Card(E_CardSuit.SPADES, 1));



        I_BoardModel board = new Board(card1,card2,card3,card4,card5,card6,card7,card8);
        //I_BoardModel boardModel = new Board()

        board.turnCard(imgData);


        if (board.canMove(E_PileID.BUILDSTACK1,0,E_PileID.SPADESACEPILE)) {
            System.out.println("The move is legal");
        }
        board.move(E_PileID.BUILDSTACK1,E_PileID.SPADESACEPILE, getImgData(board));






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

    private SuitStack createSuitStack(int elements, E_CardSuit suit){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1);
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
                .map( // transforms elements of the stream to mapEntries
                        pile -> {
                            var pileList = board.getPile(pile);
                            if ( !pileList.isEmpty())
                                return new AbstractMap.SimpleEntry<>(pile.name(), pileList.get(pileList.size() - 1));
                            else
                                return new AbstractMap.SimpleEntry<>(pile.name(), (I_CardModel) new Card());
                        }
                )
                .peek(entry -> { // replaces face down cards with a randomly generated card
                            if ( !entry.getValue().isFacedUp() )
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
        var rand = new Random();
        var randSuit = rand.nextInt(E_CardSuit.values().length);
        var randRank = rand.nextInt(13);

        return new Card(E_CardSuit.values()[randSuit], randRank);
    }
}