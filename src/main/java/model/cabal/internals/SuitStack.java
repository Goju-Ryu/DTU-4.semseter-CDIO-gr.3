package model.cabal.internals;

import model.cabal.E_PileID;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SuitStack extends StackBase {

    public SuitStack(List<I_CardModel> list) {
        this.stack = list;
    }
    public SuitStack(){
        stack = new ArrayList<>();
    }

    /**
     * this method must only be used from one of the extending classes that overides this method
     * */
    @Override
    @NonNullType
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException {
        return null;
    }

    @Override
    public boolean canMoveFrom(int range) {

        if (range != 1) {
            return false;
        }

        if (stack.isEmpty()){
            return false;
        }

        if (!(stack.get(stack.size() - range).isFacedUp())){
            return false;
        }

        return true;
    }

    @Override
    public boolean canMoveTo(@NonNullType Collection<I_CardModel> cards) {

        if (cards.isEmpty()) return false;

        I_CardModel card = null;
        for (I_CardModel inCard : cards) {
            card = inCard;
        }

        if (stack.isEmpty()){
            assert card != null;
            if (card.getRank() == 1 && card.getSuit() == this.getStackSuit() ){
                return true;
            }else {
                return false;
            }
        }

        I_CardModel card2 = null;

        for (I_CardModel element : stack){
            card2 = element;
        }

        System.out.println("incomming card: "+card);
        System.out.println("suit stack card: "+card2);

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

    @NonNullType
    public abstract E_PileID getPileID();

    @NonNullType
    public abstract E_CardSuit getStackSuit();
}
