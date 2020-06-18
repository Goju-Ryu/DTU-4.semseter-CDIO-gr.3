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
        return stack.subList((stack.size()-1) - range,stack.size() - 1);
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
        I_CardModel card2 = null;

        for (I_CardModel element : stack){
            card2 = element;
        }

        System.out.println(card);
        System.out.println(card2);

        // check size of collection
        if (cards.size() != 1){
            return false;
        }

        // check if the suit is the same
        assert card2 != null;
        if (!(card2.getSuit() == card.getSuit())){
            return false;
        }

        // check if the card in cards collection is 1 rank higher
        if (!(card.getRank() - card2.getRank() == 1)){
            return false;
        }

        // check if the card is face up
        if (!cards.iterator().next().isFacedUp()){
            return false;
        }

        if (stack.isEmpty() && card.getRank() == 1){
            return true;
        }
        return true;
    }
}
