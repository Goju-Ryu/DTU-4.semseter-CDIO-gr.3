package model.cabal.internals;

import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BuildStack extends StackBase {

    public BuildStack(List<I_CardModel> list) {
        this.stack = list;
    }

    public BuildStack(){
        stack = new ArrayList<>();
    }

    @Override
    public Collection<I_CardModel> popSubset(int range) throws IllegalMoveException { // TODO i'm pretty sure this does the opposite of the expected

        int toIndex = stack.size();
        int frIndex = toIndex - range;

        List<I_CardModel> sublist = stack.subList(frIndex, toIndex);

        this.stack = stack.subList(0,frIndex);
        return new BuildStack(sublist);
    }

    @Override
    public boolean canMoveFrom(int range) {

        int top = stack.size() - 1;
        if ( top - (range - 1) < 0 ){
            return false;
        }
        return stack.get(top - range).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<I_CardModel> cards) {

        //TODO: We need to implement so we can put a suitstack in as the Collection, and it would still return true even if the color matched



        I_CardModel card = null; // Getting the last card "the top card"
        for(I_CardModel element: cards){
            card = element;
        }

        assert (card != null);
        if(!(stack.get(0).isFacedUp() && card.isFacedUp())){
            return false;
        }

        //color matching
        E_CardSuit mySuit = stack.get(0).getSuit();
        E_CardSuit otSuit = card.getSuit();

        // a card cannot move to a card of the same color.
        boolean sameColor = E_CardSuit.isSameColour(mySuit, otSuit);
        if(sameColor){
            return false;
        }

        //number matching
        int myRank = stack.get(0).getRank();
        int otRank = card.getRank();

        // ot rank must be equals to one higher than my rank. otherwise it is illegal.
        if(myRank - otRank != 1)
            return false;

        return true;
    }
}
