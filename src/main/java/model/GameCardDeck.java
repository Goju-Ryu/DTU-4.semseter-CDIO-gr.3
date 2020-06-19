package model;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameCardDeck extends HashSet<I_CardModel> {

    private static GameCardDeck instance;

    private GameCardDeck() {
        for (E_CardSuit suit : E_CardSuit.values()) {
            for (int i = 0; i < 13; i++) {
                add(new Card(suit, i + 1));
            }
        }
    }

    public static GameCardDeck getInstance() {
        if (instance == null)
            instance = new GameCardDeck();

        return instance;
    }


}
