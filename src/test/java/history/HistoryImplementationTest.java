package history;

import history.GameHistory;
import history.I_GameHistory;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.security.cert.CollectionCertStoreParameters;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class HistoryImplementationTest {

    /**
     * Test that a propertyChangeEvent is properly sent to history
     */
    @Test
    void propertyEventCommunication(){
        I_BoardModel board = new Board(getImgData());
        GameHistory hist = new GameHistory();

        board.addPropertyChangeListener(hist);

        assertEquals(0, hist.eventBatch.size());

        board.turnCard(getImgData(board));
        assertEquals(1, hist.eventBatch.size());
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
                            return new SimpleEntry<>(pile.name(), pileList.get(pileList.size() - 1));
                        else
                            return new SimpleEntry<>(pile.name(), (I_CardModel) new Card());
                    }
            )
            .peek(entry -> { // replaces face down cards with a randomly generated card
                        if ( !entry.getValue().isFacedUp() )
                            entry.setValue(getRandCard());
                    }
            )
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)); // converts result to a map
    }

    /**
     * Artificially makes an imageData that contains a random set of face up cards.
     * @return a map representing imgData as in the actual system
     */
    private Map<String, I_CardModel> getImgData() {
        return Stream.of(E_PileID.values())
                .map(pile -> new SimpleEntry<>(pile.name(), (I_CardModel) new Card()))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
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
