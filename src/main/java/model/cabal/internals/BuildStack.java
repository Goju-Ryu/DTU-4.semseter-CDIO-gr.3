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
        if(range == 0){
            return false;
        }

        if (stack.isEmpty()) return false;
        int top = stack.size();
        if (top - range < 0 ){
            return false;
        }

        return stack.get(top - range).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<I_CardModel> cards) {

        //TODO: We need to implement so we can put a suitstack in as the Collection, and it would still return true even if the color matched

        if (stack.isEmpty()){
            if (cards.iterator().next().getRank() == 13){
                return true;
            }else {
                return false;
            }
        }

        I_CardModel card = null; // Getting the last card "the top card"

        for (I_CardModel element : stack){
            card = element;
        }

        assert (card != null);
        if (!(cards.iterator().next().isFacedUp() && card.isFacedUp())){
            return false;
        }

        //color matching
        E_CardSuit mySuit = card.getSuit();
        E_CardSuit otSuit = cards.iterator().next().getSuit();


        if((cards.iterator().next().getSuit() == E_CardSuit.HEARTS) && (card.getSuit() == E_CardSuit.HEARTS)){

            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.HEARTS) && (card.getSuit() == E_CardSuit.DIAMONDS)){
             return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.DIAMONDS) && (card.getSuit() == E_CardSuit.HEARTS)){
             return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.DIAMONDS) && (card.getSuit() == E_CardSuit.DIAMONDS)){
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.CLUBS) && (card.getSuit() == E_CardSuit.CLUBS)){
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.CLUBS) && (card.getSuit() == E_CardSuit.SPADES)){
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.SPADES) && (card.getSuit() == E_CardSuit.CLUBS)){
             return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.SPADES) && (card.getSuit() == E_CardSuit.SPADES)){
             return false;
        }else {
            //number matching
            int myRank = card.getRank();
            int otRank = cards.iterator().next().getRank();

            // ot rank must be equals to one lower than my rank. otherwise it is illegal.
            return myRank - otRank == 1;
        }
    }
}
