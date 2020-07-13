package util;

import data.I_InputDTO;
import data.InputSimDTO;
import model.GameCardDeck;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.RefBoard;
import model.cabal.internals.card.I_CardModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TestUtil {
    public static AbstractMap.SimpleImmutableEntry<I_BoardModel, I_InputDTO> getTestReadyBoard(Map<String, I_CardModel> imgData) {
        var deck = new GameCardDeck();
        I_BoardModel boardModel = new BoardEventListener(imgData, deck);
        I_BoardModel ref = new RefBoard(boardModel);
        I_InputDTO input = new InputSimDTO(ref, deck);
        return new AbstractMap.SimpleImmutableEntry<>(ref, input);
    }

    public static AbstractMap.SimpleImmutableEntry<I_BoardModel, I_InputDTO>
    getTestReadyBoard(Map<String, I_CardModel> imgData, List<I_CardModel> drawStack) {
        var deck = new GameCardDeck();
        I_BoardModel boardModel = new BoardEventListener(imgData, deck, drawStack);
        I_BoardModel ref = new RefBoard(boardModel);
        I_InputDTO input = new InputSimDTO(ref, deck);
        return new AbstractMap.SimpleImmutableEntry<>(ref, input);
    }
}

class BoardEventListener extends Board implements PropertyChangeListener {
    BoardEventListener(Map<String, I_CardModel> imgData, GameCardDeck gameDeck) {
        super(imgData, gameDeck);
    }

    BoardEventListener(Map<String, I_CardModel> imgData, GameCardDeck gameDeck, List<I_CardModel> drawStack) {
        super(imgData, gameDeck, drawStack);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        E_PileID pileID = E_PileID.valueOf(event.getPropertyName());

        Collection<I_CardModel> oldValue = ((Collection<I_CardModel>)event.getOldValue());
        Collection<I_CardModel> newValue = ((Collection<I_CardModel>)event.getNewValue());
        if (oldValue.size() > newValue.size()) {
            get(pileID).retainAll(newValue);
        } else {
            newValue.removeAll(oldValue);
            get(pileID).addAll(newValue);
        }
    }
}