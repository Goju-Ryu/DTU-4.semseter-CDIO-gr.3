package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.*;
import java.util.stream.Collectors;

public class DrawStack extends StackBase implements I_SolitaireStacks {

    /**
     * This variable describes which cards have been drawn and which haven't
     * every index higher than this value has not yet ben drawn and the lower ones has.
     * This means that the index always points at the card that is able to be drawn out on the board.
     */
    protected int drawIndex;

    public DrawStack() {
        this(new LinkedList<>());
    }

    public DrawStack(List<I_CardModel> list) {
        super();

        if (list instanceof LinkedList)
            stack = list;
        else
            stack = new LinkedList<>(list);

        drawIndex = -1;
    }

//-----------  Implementation ----------------------------------------------------------------

    public Collection<I_CardModel> popSubset() throws IllegalMoveException {
        return popSubset(1);
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        if (!canMoveFrom()) {
            throw new IllegalMoveException();//todo msg
        }

        if (range > 1)
            throw new IllegalMoveException("drawstack can only move one card at a time.");

//        int rangeIndex = drawIndex + range ;
//        if(!stack.get(rangeIndex % size()).isFacedUp()){
//            throw new IllegalMoveException("Card at this range: "+range+" has not been turned yet");
//        }

        if (range == 0)
            return List.of();

        var index = getSafeDrawIndex();
        var returnCard = getSubset(index + 1).get(0);

        if ( !returnCard.isFacedUp() )
            throw new IllegalMoveException("Card at this range: "+range+" has not been turned yet");

        var returnable = List.of( returnCard );
        stack.remove( returnCard ); //remove the card
        drawIndex = index - 1; //lower index to point to the new card that can be drawn
        return returnable;
    }

    @Override
    public List<I_CardModel> getSubset(int range) {
        //TODO This implementation is fundamentally incorrect it should be fixed later.
        // it is however not a priority, as it does make the rest of the program work, though
        // it is pretty hacky, it would take too long to fix.
        int b = (size()) - (range);
        int index = (getSafeDrawIndex() + b) % size();
        return List.of(stack.get(index));

    }

    @Override
    public boolean canMoveFrom(int range) {

        if( range > stack.size() ){
            throw new IllegalArgumentException(
                    "Range was larger than the stack size",
                    new IndexOutOfBoundsException("range: " + range + ", but size is only: " + size())
            );
        }

        if (range < 1)
            return true;

        return getCard(range).isFacedUp();
    }

    @Override
    public boolean add(I_CardModel o) {
        stack.add(getSafeDrawIndex(), o);
        return true;
    }

    @NonNullType
    @Override
    public Iterator<I_CardModel> iterator() {
        var startIndex = getSafeDrawIndex();
        var returnable = stack.subList(startIndex, stack.size());
        returnable.addAll(stack.subList(0, startIndex));
        return returnable.iterator();
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }

    @Override
    public I_CardModel getCard(int position) {
        return getSubset(position).get(0);
    }

    @Override
    public I_CardModel getTopCard() {
        return getCard(getSafeDrawIndex());
    }

//-------------------  DrawStack specific methods  ----------------------------------------------------------

    public I_CardModel turnCard() {
        if (isEmpty())
            throw new NoSuchElementException("Can't turn a card in an empty stack");
        drawIndex = (drawIndex + 1) % size();
        return getCard(0);
    }
    
    private int getSafeDrawIndex() {
        return Math.max(0, drawIndex);
    }

}
