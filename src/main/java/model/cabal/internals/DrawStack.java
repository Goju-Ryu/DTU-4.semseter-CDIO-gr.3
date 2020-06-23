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

        int rangeIndex = drawIndex + range ;
        if(!stack.get(rangeIndex % size()).isFacedUp()){
            throw new IllegalMoveException("Card at this range: "+range+" has not been turned yet");
        }

        if (range == 0)
            return List.of();

        var returnable = List.of( stack.get(drawIndex) );  //getSubset(drawIndex)
        stack.remove(drawIndex--); //remove the card and lower index to point to the new card that can be drawn
        return returnable;
    }

    @Override
    public List<I_CardModel> getSubset(int range) {
        List<I_CardModel> returnable;
        var startIndex = Math.max(drawIndex, 0); //if drawIndex is negative set this index to 0 else use drawIndex.

        if (drawIndex + range < stack.size())
            returnable = stack.subList(startIndex, startIndex + range);
        else {
            int rangeIndex = (startIndex + range) % stack.size();
            returnable = stack.subList(startIndex, stack.size());
            returnable.addAll(stack.subList(0, rangeIndex));
        }

        Collections.reverse(returnable);
        return returnable;
    }

    @Override
    public boolean canMoveFrom(int range) {

        if( range > stack.size() ){
            throw new IllegalArgumentException(
                    "Range was larger than the stack size",
                    new IndexOutOfBoundsException("range: " + range + ", but size is only: " + size())
            );
        }

        if (range - 1 < 1)
            return true;

        return getCard(range - 1).isFacedUp();
    }

    @Override
    public boolean add(I_CardModel o) {
        drawIndex = Math.max(drawIndex, 0); //if drawIndex is negative set this index to 0 else use drawIndex.
        stack.add(drawIndex, o);
        return true;
    }

    @NonNullType
    @Override
    public Iterator<I_CardModel> iterator() {
        var startIndex = (drawIndex + 1) % size();
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
        if (size() == 0)
            return stack.get(position);
        return stack.get((drawIndex + 1 + position) % size());
    }

    @Override
    public I_CardModel getTopCard() {
        return drawIndex < 0 ? null : super.getCard(drawIndex);
    }

//-------------------  DrawStack specific methods  ----------------------------------------------------------

    public I_CardModel turnCard() {
        drawIndex++;
        return getCard(0);
    }

}
