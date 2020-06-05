package control;

import model.cabal.Board;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

public class GameController implements I_GameController {
    @Override
    public void possibleMoves(Card[] fromCV) {

    }

    @Override
    public void pickMove(Board[] moves) {

    }

    /**
     * Check if a card can be moved to another card
     * @param card1 The card that is moved
     * @param card2 The card that is NOT moved
     *
     * the rules which a card must follow is always determined by the cards which is moved to
     *
     * @return True or False, depending on if the move is legal or not
     */
    public boolean legalMove(Card card1, Card card2){
        //Move card (not king) from column to column
        if (card1.getRank() == card2.getRank() - 1 && card1.getRank() != 13 &&
                ((( card1.getSuit() == E_CardSuit.DIAMONDS || card1.getSuit() == E_CardSuit.HEARTS ) &&
                ( card2.getSuit() == E_CardSuit.CLUBS || card2.getSuit() == E_CardSuit.SPADES )) ||
                (( card1.getSuit() == E_CardSuit.CLUBS || card1.getSuit() == E_CardSuit.SPADES ) &&
                        ( card2.getSuit() == E_CardSuit.DIAMONDS || card2.getSuit() == E_CardSuit.HEARTS))) &&
                card2.getStacktype().equals("column") ){
            return true;

        //Move king TODO: What if a king should be moved from the acePile? (NOT likely), or to an empty stack
        }else if (card1.getRank() == 13 && (card1.getStacktype().equals("turnedPile") || card1.getStacktype().equals("column")) &&
                (card2.getRank() <= 13 || card2.getRank() >= 1) &&
                (card2.getSuit() == E_CardSuit.CLUBS ||
                        card2.getSuit() == E_CardSuit.DIAMONDS ||
                        card2.getSuit() == E_CardSuit.SPADES ||
                        card2.getSuit() == E_CardSuit.HEARTS) &&
                card2.getStacktype().equals("emptyColumn")){//King to empty stack
            return true;

        //Move card from column to acePile
        }else if (card1.getRank() == card2.getRank() + 1 &&
                card1.getSuit().equals(card2.getSuit()) &&
                card2.getStacktype().equals("acePile") ){//Card to Ace stack
            return true;

        }
        return false;
    }


}
