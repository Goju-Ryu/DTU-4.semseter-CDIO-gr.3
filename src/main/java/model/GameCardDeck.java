package model;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class GameCardDeck implements Iterable<I_CardModel> {

    private Logger log;

    LinkedHashMap<String,I_CardModel> listCardsAll;

    public GameCardDeck() {
        listCardsAll = new LinkedHashMap<>(52);
        for (E_CardSuit suit : E_CardSuit.values()) {
            for (int i = 0; i < 13; i++) {

                int key = i + 1;
                listCardsAll.put(suit.name() +"-"+key ,new Card(suit, key));
            }
        }
    }

    public boolean remove(I_CardModel o)  {
        String key = o.getSuit().name() +"-"+o.getRank();
        I_CardModel a = listCardsAll.remove(key);
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


    public Iterator<I_CardModel> iterator() {
        Iterator<I_CardModel> it = new Iterator<I_CardModel>() {

            private int currentIndex = 0;
            List<I_CardModel> list = new ArrayList<>(List.copyOf(listCardsAll.values()));

            @Override
            public boolean hasNext() {
                return currentIndex < list.size() && list.get(currentIndex) != null;
            }

            @Override
            public I_CardModel next() {

                return list.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public int size(){
        return listCardsAll.size();
    }

    public boolean removeAll(Collection<I_CardModel> c) {
        boolean a = false;
        for (I_CardModel card: c ) {
            a = remove(card);
        }
        return a;
    }
}
