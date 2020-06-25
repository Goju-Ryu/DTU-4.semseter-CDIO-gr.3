package model;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class GameCardDeck extends TreeSet<I_CardModel> {

    private Logger log;

    public GameCardDeck() {
        super(comparator);
        for (E_CardSuit suit : E_CardSuit.values()) {
            for (int i = 0; i < 13; i++) {
                add(new Card(suit, i + 1));
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    private static Comparator<I_CardModel> comparator = (o1, o2) -> {
        int val1 = o1.getRank() + 13*(o1.getSuit().ordinal());
        int val2 = o2.getRank() + 13*(o2.getSuit().ordinal());
        return val1 - val2;
    };
}
