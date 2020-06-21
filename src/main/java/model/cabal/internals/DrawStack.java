package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawStack extends StackBase implements I_SolitaireStacks {

    /**
     * This variable describes which cards have been drawn and which haven't
     * every index higher than this value has not yet ben drawn and the lower ones has.
     * This means that the index always points at the card that is able to be drawn out on the board.
     */
    protected int drawIndex;


    public DrawStack() {
        this(new ArrayList<>());
    }

    public DrawStack(List<I_CardModel> list) {
        super(list);
        drawIndex = -1;
    }

//-----------  Implementation ----------------------------------------------------------------

    public Collection<I_CardModel> popSubset() throws IllegalMoveException {
        return popSubset(0);
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        var errors = canMoveFromMsg(range);
        if (!errors.isEmpty())
            throw new IllegalMoveException(errors);
        if (range != 0){
            throw new IllegalMoveException(errors);
        }

        var returnable = List.of( stack.get(drawIndex) );
        stack.remove(drawIndex--); //remove the card and lower index to point to the new card that can be drawn

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

        int reversedRange = stack.size() - ( range );
        boolean val = stack.get(reversedRange).isFacedUp();
        return val;
    }

    private String canMoveFromMsg(int range){
        StringBuilder builder = new StringBuilder();
        int caseNum = 0;
        if (range > 1) {
            builder.append("Range cannot be greater than 0.");
            caseNum++;
        }
        if (drawIndex < 0) {
            if (caseNum++ > 0)
                builder.append("\n");
            builder.append("Index too low, no cards have been drawn yet.");
        }
        if (drawIndex > size()) {
            throw new IllegalStateException(
                    "Lost Track of Where in the DrawStack The next card should be turned from...",
                    new IndexOutOfBoundsException("DrawStack index: " + drawIndex + ", but size is only: " + size())
            );
        }
        return builder.toString();
    }


    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        return false;
    }

//-------------------  DrawStack specific methods  ----------------------------------------------------------

    public I_CardModel turnCard() {
        return getCard(++drawIndex);
    }

    public I_CardModel getTopCard() {
        return super.getCard(drawIndex);
    }

}
