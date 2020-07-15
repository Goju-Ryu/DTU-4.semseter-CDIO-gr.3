package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.*;

public class DrawStack extends StackBase implements I_SolitaireStacks, I_Drawable  {

    public DrawStack() {
        this(new LinkedList<>());
    }

    public DrawStack(List<I_CardModel> list) {
        if (list instanceof LinkedList)
            stack = list;
        else
            stack = new LinkedList<>(list);
    }

    //-----------  Implementation ----------------------------------------------------------------
    @Override
    public Collection<I_CardModel> popSubset(final int index) throws IllegalMoveException {
        if (!canMoveFrom()) {
            throw new IllegalMoveException("Can't move cards out of drawStack");
        }

        var returnCards = getSubset(index);

        if (returnCards.size() > 1)
            throw new IllegalMoveException("A drawstack can only pop one card. You tried to pop from "+index);

        if ( !returnCards.isEmpty() && !returnCards.get(0).isFacedUp() )
            throw new IllegalMoveException("Card at this index: "+index+" has not been turned yet");

        stack.removeAll( returnCards ); //remove the card
        return returnCards;
    }

    @Override
    public List<I_CardModel> getSubset(final int index) {
        var r = List.of(getCard(index));
        return r;
    }

    @Override
    public boolean add(I_CardModel o) {
        ((LinkedList<I_CardModel>) stack).addLast(o);
        return true;
    }


    // Board Game specifics
    @Override
    public boolean canMoveFrom(final int range) {
        if( range > stack.size() ){
            throw new IllegalArgumentException(
                    "Range was larger than the stack size",
                    new IndexOutOfBoundsException("range: " + range + ", but size is only: " + size())
            );
        }
        return getSubset(range).get(0).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }

    @Override
    public I_CardModel getCard(final int position) {
        var r = stack.get( position % size());
        return r;
    }

    @Override
    public I_CardModel getTopCard() {
        return stack.get(this.size() - 1 );
    }

    @Override
    public void turnCard() {
        int top = size() -1;
        I_CardModel card = stack.get(top);
        stack.remove(top);
        ((LinkedList<I_CardModel>) stack).addFirst(card);

        getCard(0);
    }



    // --- // --- // --- // --- // --- // --- // ---  DrawStack specific methods  ----------------------------------------------------------
    @NonNullType
    @Override
    public Iterator<I_CardModel> iterator() {
        return stack.iterator();
    }

    @Override
    public int size() {
        return stack.size();
    }
}
