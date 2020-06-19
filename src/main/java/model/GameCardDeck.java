package model;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.util.*;
import java.util.logging.Logger;

public class GameCardDeck extends TreeSet<I_CardModel> {

    private static GameCardDeck instance;
    private Logger log;

    private GameCardDeck() {
        super(comparator);
        for (E_CardSuit suit : E_CardSuit.values()) {
            for (int i = 0; i < 13; i++) {
                add(new Card(suit, i + 1));
            }
        }
        log = Logger.getLogger(getClass().getName());
        log.info("Created deck of cards: " + Arrays.toString(toArray()));
    }

    public static GameCardDeck getInstance() {
        if (instance == null)
            instance = new GameCardDeck();

        return instance;
    }


    @Override
    public boolean remove(Object o) {
        log.info("cardDeck.Remove: " + o);
        log.info("is contained: " + contains(o));
        boolean b = super.remove(o);
        return b;
    }

    private static Comparator<I_CardModel> comparator = (o1, o2) -> {
        var val1 = o1.getRank() * 13^o1.getSuit().ordinal();
        var val2 = o2.getRank() * 13^o2.getSuit().ordinal();
        return val1 - val2;
    };

}
