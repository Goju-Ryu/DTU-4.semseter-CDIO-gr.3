package model.cabal.internals;

import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuitStack extends StackBase {


    public SuitStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public SuitStack(){
        stack = new ArrayList<>();
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        if (range > 1){
            throw new IllegalMoveException("You can only take the top card!");
        }
        return stack.subList((stack.size()-1) - range,stack.size() - 1);
    }

    @Override
    public boolean canMoveFrom(int range) {

        // If the DrawStack is not empty and the range is 1, then we can move a card from the drawstack.
        if (!this.stack.isEmpty() && range == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {
        if (cards.size() == 1){ // check size of collection
            I_CardModel card = cards.iterator().next();
            if (this.stack.get(0).getSuit().equals(card.getSuit())){ // check if the suit is the same
                return this.stack.get(0).getRank() + 1 == card.getRank(); // check if the cards collection is 1 rank higher
            }
            return false;
        }
        return false;
    }
}
