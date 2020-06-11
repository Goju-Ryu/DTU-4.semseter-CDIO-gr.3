package model.cabal.internals;

import jdk.jshell.spi.ExecutionControl;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyEditorSupport;
import java.util.*;

public class CardStack<cardType extends I_CardModel> extends PropertyEditorSupport implements I_SolitaireStacks<cardType> {

    private List<cardType> stack;

    public CardStack(List<cardType> list) {
        this.stack = list;
    }

    public CardStack(){
        stack = new ArrayList<>();
    }

    @Override
    public boolean addAll(Collection<? extends cardType> c) throws IllegalMoveException {
        for (cardType element: c) {
            stack.add(element);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < stack.size() ; i++) {
         stack.remove(i);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<cardType> it = this.iterator();
        while (it.hasNext()) {

            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll( Collection<?> c) {
        try {
            throw new ExecutionControl.NotImplementedException("Not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return false; //should not be implemented
    }

    @Override
    public CardStack<cardType> popSubset(int range) throws IllegalMoveException {

            int toIndex = stack.size() - 1;
            int frIndex = toIndex - range;

            List<cardType> sublist = stack.subList(frIndex, toIndex);
            List<cardType> newStack = stack.subList(frIndex, toIndex);

            this.stack = newStack;

            CardStack<cardType> cardStack = new CardStack<>(sublist);

            return cardStack;
    }

    @Override
    public cardType getCard(int position) {
        return stack.get(position);
    }

    @Override
    public Collection<cardType> getSubset(int range) {
        int toIndex = stack.size() - 1;
        int frIndex = toIndex - range;
        List<cardType> sublist = stack.subList(frIndex, toIndex);
        CardStack<cardType> cardStack = new CardStack<>(sublist);
        return cardStack;
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

        if(o instanceof I_CardModel) {
            cardType c = (cardType) o;
            for (cardType card : this.stack) {
                if (c.equals(card)){
                    return true;
                }
            }
        }else {
            try {
                throw new ExecutionControl.NotImplementedException("object is not of correct type");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public Iterator<cardType> iterator() {
        return stack.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public  <T> T[] toArray( T[] ts) {
        return (T[]) stack.toArray(ts);
    }

    @Override
    public boolean add(cardType o) {
        stack.add(o);
        return true;
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
    public boolean containsAll(Collection c) {
        return stack.containsAll(c);
    }


    @Override
    public boolean canMoveFrom(int range) {
        int top = stack.size() -1;
        return  stack.get(top - range).isFacedUp();
    }

    @Override
    public boolean canMoveTo(Collection<cardType> cards) {
        int top = stack.size() -1;

        cardType card = null;
        for(cardType element: cards){
            card = element;
        }

        E_CardSuit mySuit = stack.get(top).getSuit();
        E_CardSuit otSuit = card.getSuit();

        E_CardSuit.isSameColour(mySuit, otSuit);

        return E_CardSuit.isSameColour(mySuit, otSuit);
    }

    @Override
    public boolean containsCard(I_CardModel card) {

        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).equals(card)){
                return true;
            }
        }
        return false;
    }
}
