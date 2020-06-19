package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class StackBase implements I_SolitaireStacks {

    protected List<I_CardModel> stack;

    public StackBase(List<I_CardModel> list) {
        this.stack = list;
    }

    public StackBase(){
         this(new ArrayList<>());
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends I_CardModel> c) throws IllegalMoveException {
        try {
            return stack.addAll(c);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public boolean retainAll(@Nonnull Collection c) {
        return stack.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection c) {
        return stack.removeAll(c);
    }

    @Override
    public I_CardModel getCard(int position) {
        return stack.get(position);
    }

    @Override
    public Collection<I_CardModel> getSubset(int range) {
        int toIndex = stack.size();
        int frIndex = toIndex - range;
        return stack.subList(frIndex, toIndex);
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
    public @NonNullType Iterator<I_CardModel> iterator() {
        return stack.iterator();
    }

    @Override
    @NonNullType
    @Nonnull
    public Object[] toArray() {
        return stack.toArray();
    }

    @Override
    @NonNullType
    @Nonnull
    public <T> T[] toArray(@Nonnull T[] ts) {
        return (T[]) stack.toArray(ts);
    }

    @Override
    public boolean add(I_CardModel o) {
        return stack.add(o);
    }

    /**
    * This overridden version of remove will remove a card from the top of the stack of cards
    *
    * @param o The object in the list that is to be removed.
    * @return true when the object is removed, and false if otherwise
    */
    @Override
    public boolean remove(Object o) {
        return stack.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection c) {
        return stack.containsAll(c);
    }

    @Override
    public boolean containsCard(I_CardModel card) {
        return stack.contains(card);
    }
}
