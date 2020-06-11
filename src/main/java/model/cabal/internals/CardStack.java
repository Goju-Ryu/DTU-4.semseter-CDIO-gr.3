package model.cabal.internals;

import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class CardStack implements I_SolitaireStacks {

    private List<I_CardModel> stack;

    public CardStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public CardStack(){
        stack = new ArrayList<>();
    }

    @Override
    public boolean addAll(@Nonnull @NonNullType Collection c) throws IllegalMoveException {
        return stack.addAll(c);
//        for (I_CardModel element: c) {
//            stack.add(element);
//        }
//        return true;
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
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        int toIndex = stack.size() - 1;
        int frIndex = toIndex - range;

        List<I_CardModel> sublist = stack.subList(frIndex, toIndex);
        List<I_CardModel> newStack = stack.subList(frIndex, toIndex);

        this.stack = newStack;
        return sublist;
    }

    @Override
    public I_CardModel getCard(int position) {
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
    public @Nonnull Iterator<I_CardModel> iterator() {
        return stack.iterator();
    }

    @Override
    public @Nonnull Object[] toArray() {
        return new Object[0];
    }

    @Override
    public @Nonnull <T> T[] toArray(@Nonnull T[] ts) {
        return (T[]) stack.toArray(ts); //this cast makes some warnings disappear for some reason
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
    public boolean canMoveFrom(int range) {
        int top = stack.size() -1;
        return  stack.get(top - range).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<I_CardModel> cards) {
        int top = stack.size() -1;

        I_CardModel card = null;
        for(I_CardModel element: cards){
            card = element;
        }

        E_CardSuit mySuit = stack.get(top).getSuit();
        E_CardSuit otSuit = card.getSuit();

        E_CardSuit.isSameColour(mySuit, otSuit);

        return E_CardSuit.isSameColour(mySuit, otSuit);
    }
}
