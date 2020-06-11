package model.cabal.internals;

import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import javax.annotation.Nonnull;
import java.util.*;

public class CardStack implements I_SolitaireStacks {

    private List<I_CardModel> stack;

    public CardStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public CardStack(){
        stack = new ArrayList<>();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends I_CardModel> c) throws IllegalMoveException {
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
    public boolean retainAll(@Nonnull Collection c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<I_CardModel> it = this.iterator();
        while (it.hasNext()) {

            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(@Nonnull Collection c) {
        throw new IllegalCallerException("This method should not be called"); //should not be implemented
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException { // TODO i'm pretty sure this does the opposite of the expected

        int toIndex = stack.size() - 1;
        int frIndex = toIndex - range;

        List<I_CardModel> sublist = stack.subList(frIndex, toIndex);

        this.stack = stack.subList(frIndex, toIndex);

        return new CardStack(sublist);
    }

    @Override
    public I_CardModel getCard(int position) {
        return stack.get(position);
    }

    @Override
    public Collection<I_CardModel> getSubset(int range) {
        int toIndex = stack.size() - 1;
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
//        if(o instanceof model.cabal.internals.card.I_CardModel) {
//            I_CardModel c = (I_CardModel) o;
//            for (I_CardModel card : this.stack) {
//                if (c.equals(card)){
//                    return true;
//                }
//            }
//        }else {
//            try {
//                throw new ExecutionControl.NotImplementedException("object is not of correct type");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return false;
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
    public boolean canMoveFrom(int range) {
        int top = stack.size() -1;
        if( top - (range-1) <0 ){
            return false;
        }

        return  stack.get(top - (range-1)).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<I_CardModel> cards) {
        // TODO shouldn't this method check some of these for all cards it gets?

        I_CardModel card = null; // Getting the last card "the top card"
        for(I_CardModel element: cards){
            card = element;
        }

        assert (card != null);
        if(!(stack.get(0).isFacedUp() && card.isFacedUp())){
            return false;
        }
        //color matching
        E_CardSuit mySuit = stack.get(0).getSuit();
        E_CardSuit otSuit = card.getSuit();

        // a card cannot move to a card of the same color.
        boolean sameColor = E_CardSuit.isSameColour(mySuit, otSuit);
        if(sameColor){
            return false;
        }

        //number matching
        int myRank = stack.get(0).getRank();
        int otRank = card.getRank();

        // ot rank must be equals to one higher than my rank. otherwise it is illigal.
        if(otRank - myRank != 1)
            return false;

        return true;
    }

    @Override
    public boolean containsCard(I_CardModel card) {
        return stack.contains(card);
//        for (I_CardModel i_cardModel : stack) {
//            if (i_cardModel.equals(card)) {
//                return true;
//            }
//        }
//        return false;
    }
}
