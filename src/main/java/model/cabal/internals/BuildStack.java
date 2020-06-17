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
        if (top - (range - 1) < 0 ){
            return false;
        }
        return stack.get(top - range).isFacedUp();
    }

    @Override
    public boolean canMoveTo(@Nonnull Collection<I_CardModel> cards) {

        //TODO: We need to implement so we can put a suitstack in as the Collection, and it would still return true even if the color matched

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
        System.out.println("my suit: "+mySuit);
        System.out.println("other suit: "+otSuit);

        if((cards.iterator().next().getSuit() == E_CardSuit.HEARTS) && (card.getSuit() == E_CardSuit.HEARTS)){
            System.out.println("Both cards were HEARTS");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.HEARTS) && (card.getSuit() == E_CardSuit.DIAMONDS)){
            System.out.println("Calling card were DIAMOND, and other card were HEART");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.DIAMONDS) && (card.getSuit() == E_CardSuit.HEARTS)){
            System.out.println("Calling card were HEART, and other card were DIAMOND");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.DIAMONDS) && (card.getSuit() == E_CardSuit.DIAMONDS)){
            System.out.println("Both cards were DIAMOND");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.CLUBS) && (card.getSuit() == E_CardSuit.CLUBS)){
            System.out.println("Both cards were CLUBS");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.CLUBS) && (card.getSuit() == E_CardSuit.SPADES)){
            System.out.println("Calling card were SPADES, and other card were CLUBS");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.SPADES) && (card.getSuit() == E_CardSuit.CLUBS)){
            System.out.println("Calling card were CLUBS, and other card were SPADES");
            return false;
        }else if ((cards.iterator().next().getSuit() == E_CardSuit.SPADES) && (card.getSuit() == E_CardSuit.SPADES)){
            System.out.println("Both cards were SPADES");
            return false;
        }else {
            //number matching
            int myRank = card.getRank();
            int otRank = cards.iterator().next().getRank();

            System.out.println("my rank: "+myRank);
            System.out.println("other rank: "+otRank);

            // ot rank must be equals to one lower than my rank. otherwise it is illegal.
            return myRank - otRank == 1;
        }
    }
}
