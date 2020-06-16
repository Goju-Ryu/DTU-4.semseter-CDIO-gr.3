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

        int toIndex = stack.size();
        int fromIndex = toIndex - range;

        if (range > 1){
            throw new IllegalMoveException("You can only take the top card!");
        }else {
            List<I_CardModel> subList = stack.subList(fromIndex,toIndex);
            this.stack = stack.subList(0,fromIndex);

            return new SuitStack(subList);
        }
    }

    @Override
    public boolean canMoveFrom(int range) {

        if (range > 1) {
            throw new IllegalArgumentException("Range cant be bigger than 0");
        }

        if (!(stack.get(range).isFacedUp())){
            return false;
        }

        if (stack.isEmpty()){
            throw new IllegalStateException("Stack must not be empty");
        }
        return true;
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {

        I_CardModel card = cards.iterator().next();

        System.out.println(card);

        // check size of collection
        if (cards.size() > 1 || cards.size() < 1){
            return false;
        }

        // check if the suit is the same
        if (!(stack.get(0).getSuit() == card.getSuit())){
            return false;
        }

        // check if the card in cards collection is 1 rank higher
        if (!(stack.get(0).getRank() - card.getRank() == 1)){
            return false;
        }
        return true;
    }
}
