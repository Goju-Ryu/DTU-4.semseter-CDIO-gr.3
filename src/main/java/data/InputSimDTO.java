package data;

import model.GameCardDeck;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputSimDTO implements I_InputDTO {

    private I_BoardModel boardModel;

    /**
     * A constructor taking no arguments. This will make the class return random unused card in each position.
     */
    public InputSimDTO() {
        this(null);
    }

    /**
     * This constructor takes an implementation of the {@link I_BoardModel} interface that it will keep itself
     * consistent with.
     * Example uses are:
     *  - Having a board model used to by the game as internal model, with some face up and some face down cards.
     *  For face down cards a new random unused card is returned, but for the know values, that value is returned.
     *  - Having a board model with all cards known to act as the physical board. this class will always return the top
     *  card of each pile of that board. It does require an outside class to make the "physical" and virtual boards
     *  stay in sync. If one forgets that, the state will quickly be inconsistent. to do so, remember to use the
     *  move method with the same parameters in both boards.
     * @param simulatedBoard The board used as reference for which cards should be returned.
     */
    public InputSimDTO(I_BoardModel simulatedBoard){
        boardModel = simulatedBoard;
    }

    public void setSimulatedBoard(I_BoardModel simulatedBoard) {
        boardModel = simulatedBoard;
    }


    @Override
    public Map<String, I_CardModel> getUsrInput(){
        if (boardModel != null)
            return getImgData(boardModel);

        return getImgData();
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
                .peek(entry -> { if (entry.getValue() == null) entry.setValue(new Card());} )
                .peek(entry -> { // replaces face down cards with a randomly generated card
                            if (!entry.getValue().isFacedUp())
                                entry.setValue(getRandCard());
                        }
                )
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                )); // converts result to a map
    }

    /**
     * Artificially makes an imageData that contains a random set of face up cards.
     * @return a map representing imgData as in the actual system
     */
    private Map<String, I_CardModel> getImgData() {
        return Stream.of(E_PileID.values())
                .map(pile -> new AbstractMap.SimpleEntry<>(pile.name(), getRandCard()))
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

        if (card == null)
            throw new NoSuchElementException();

        return card;
    }

}
