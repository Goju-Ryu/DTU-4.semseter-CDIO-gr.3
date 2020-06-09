package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CardStack implements I_SolitaireStacks {

    private List<I_CardModel> stack;

    public CardStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public CardStack(){
        stack = new LinkedList<>();
    }

    @Override
    public boolean addAll(Collection c) throws IllegalMoveException {
        return stack.addAll(c);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
        return stack.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return false; //should not be implemented
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {



        return null;



//        int top = this.size() - 1;
//        int point = top - range;
//
//        List<T> list = new CardStack<T>();
//        list = this.subList(point,top);
//
//        // remove the sublist from the original CardStack
//
//        for (int i = 0; i < point; i++) {
//            int a = top - i;
//            this.remove(a);
//        }
//
//        return list;
    }

    @Override
    public I_CardModel getCard(int position) {

        I_CardModel card = stack.get(position);

        return card;
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return stack.contains(o);
    }


    @Override
    public Iterator iterator() {
        return stack.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return stack.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
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
