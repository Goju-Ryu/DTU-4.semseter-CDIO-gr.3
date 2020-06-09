package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CardStack<T> extends ArrayList implements I_SolitaireStacks {

    @Override
    public List<T> popSubset(int range) throws IllegalMoveException {

        int top = this.size() - 1;
        int point = top - range;

        List<T> list = this.subList(point,top);

        // remove the sublist from the original CardStack

        Iterator<T> it = this.iterator();
        while(it.hasNext()){
            it.remove();
        }

//        for (int i = 0; i < point; i++) {
//            int a = top - i;
//            this.remove(a);
//        }

        return list;


    }

    @Override
    public I_CardModel getCard(int position) {
        return null;
    }

    @Override
    public boolean contains(I_CardModel card) {
        return false;
    }

    @Override
    public boolean canMoveFrom(int range) {
        return false;
    }

    @Override
    public boolean canMoveTo(Collection cards) {
        return false;
    }
}
