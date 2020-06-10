package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import javax.annotation.Nonnull;
import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CardStack<cardType extends I_CardModel> extends PropertyEditorSupport implements I_SolitaireStacks<cardType> {

    private List<cardType> stack;

    public CardStack(List<cardType> list) {
        this.stack = list;
    }

    public CardStack(){
        stack = new LinkedList<>();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends cardType> c) throws IllegalMoveException {
        return stack.addAll(c);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return stack.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return false; //should not be implemented
    }

    @Override
    public Collection<cardType> popSubset(int range) throws IllegalMoveException {
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
    public cardType getCard(int position) {
        return stack.get(position);
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
    public boolean contains(@Nonnull Object o) {
        return stack.contains(o);
    }


    @Override
    public @Nonnull Iterator<cardType> iterator() {
        return stack.iterator();
    }

    @Override
    public @Nonnull Object[] toArray() {
        return new Object[0];
    }

    @Override
    public @Nonnull <T> T[] toArray(@Nonnull T[] ts) {
        return (T[]) stack.toArray(ts);
    }

    @Override
    public boolean add(cardType o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Collection c) {
        return stack.containsAll(c);
    }


    @Override
    public boolean canMoveFrom(int range) {
        return false;
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<cardType> cards) {
        return false;
    }
}
