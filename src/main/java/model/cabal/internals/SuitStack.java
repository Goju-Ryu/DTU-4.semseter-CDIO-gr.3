package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SuitStack extends StackBase {

    public SuitStack(List<I_CardModel> list) {
        this.stack = list;
    }
    public SuitStack(){
        stack = new ArrayList<>();
    }


    @Override
    public boolean canMoveFrom(int index) {

        if (stack.isEmpty()) {
            return false;
        }

        if (!(stack.get(index).equals(getTopCard()))){
            return false;
        }

        if (!(stack.get(index).isFacedUp())){
            return false;
        }

        return true;
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        if (cards.size() != 1) return false;

        for (I_CardModel card : cards) {
            if ( !card.isFacedUp() )
                return false;

            if ( this.isEmpty() )
                return card.getRank() == 1;

            if (card.getRank() - getTopCard().getRank() != 1)
                return false;

            if ( !card.getSuit().equals(getTopCard().getSuit()) )
                return false;

        }


        return true;
    }


}
