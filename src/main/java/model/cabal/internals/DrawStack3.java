/*
package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawStack3 extends StackBase implements I_SolitaireStacks {

    int drawIndex;
    public DrawStack() {
        this(new ArrayList<>());
    }

    public DrawStack(List<I_CardModel> list) {
        super(list);
        drawIndex = size()-1;
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

        if(!stack.get(currentTopConvertRange(range)).isFacedUp()){
            throw new IllegalMoveException("Card at this range: "+range+" has not been turned yet");
        }

        if (range == 0)
            return List.of();

        var returnable = List.of( stack.get(currentTopConvertRange(range)) );
        stack.remove(currentTopConvertRange(range)); //remove the card and lower index to point to the new card that can be drawn
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

        return stack.get(currentTopConvertRange(range)).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }

//-------------------  DrawStack specific methods  ----------------------------------------------------------

    private int currentTopConvertRange(int range){


        int f = drawIndex - ( range  );
        if(drawIndex < 0)
            drawIndex += (size() );
        return drawIndex;
    }

    public I_CardModel turnCard() {
        currentTopConvertRange(1);
        return getCard(drawIndex);
    }

    public I_CardModel getTopCard() {
        return super.getCard(drawIndex);
    }

}
*/