package model;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class GameCardDeck {

    private Logger log;

    Map<String,I_CardModel> listCardsAll;

    public GameCardDeck() {
        listCardsAll = new HashMap<>(52);
        for (E_CardSuit suit : E_CardSuit.values()) {
            for (int i = 0; i < 13; i++) {
                listCardsAll.put(suit.name() +"-"+i + 1 ,new Card(suit, i + 1));
            }
        }

    }


    public boolean remove(I_CardModel o) {
        I_CardModel a = listCardsAll.remove(o.getSuit().name() +"-"+o.getRank());
        if(a == null)
            return false;

        return a.getRank().equals(o.getRank()) && a.getSuit().ordinal() == o.getSuit().ordinal();
    }

    private static Comparator<I_CardModel> comparator = (o1, o2) -> {
        /*int temp = o1.getSuit().ordinal();
        int temp2 = o2.getSuit().ordinal();
        int a = o1.getSuit().ordinal();
        int b = o2.getSuit().ordinal();
        int c = 13*a;
        int d = 13*b;*/
        int val1 = o1.getRank() + 13*(o1.getSuit().ordinal());
        int val2 = o2.getRank() + 13*(o2.getSuit().ordinal());
        return val1 - val2;
    };
}
