package control;

import data.InputDTO;
import model.I_Move;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class is for the individual controlls of each Board,
 * so all the things you can do to an individual Board.
 *
 * the diffrence on this and Game controller, is that this only acts in relation
 * to one given state of the game where as GameController can play trough an entire game.
 * This controller is one Game controller uses everytime it needs to make
 * a move
 */
public class BoardController implements I_BoardController {
    private InputDTO accessInput = new InputDTO();

    public I_BoardModel MakeNewBoard(String UiChoice){
        ArrayList<Card> usrInput = getUserInput(UiChoice);

        I_BoardModel board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));
        return board;
    }
    public void possibleMoves(I_BoardModel boardModel){
        LinkedList<I_Move> moves = new LinkedList<>();
        I_SolitaireStacks[] piles = boardModel.getPiles();

        // go through each card in each pile
        // so i save the current pile as pile
        // and i do a for each card in this pile.

        for(int from =0; from<piles.length;from++){
            I_SolitaireStacks pile = piles[from];
            for (int depth = 0; depth < pile.size() ; depth++) {

                // now we need to check if the move is even possible at this index.
                // this is in the case of a type of pile where you cannot move more
                // than one card at a time. like an Ace pile.

                if( !pile.canMoveFrom(depth) ){
                    break;
                }
                Collection<I_CardModel> cards = pile.getSubset(depth);

                    // now before going any Further, we need to know :
                    // would this move reveal a card underneath this card?
                    boolean improveCardReveal = false;
                    try { // we are aware that the pile might not be this big.
                        improveCardReveal = !pile.getCard(depth + 1).isFacedUp();
                    }catch (Exception ignored){}

                // then i go through each top card in each stack,
                // to se if the current card can move there to.

                for (int to = 0; to < piles.length; to++) {
                    if (piles[to].canMoveTo( cards )){

                        // todo better practice solution to this problem than checking the class
                        // before making the move we want to know:
                        // is this an Ace pile, if it is, then it is an improvement of the win condition.
                        boolean improveAce = false;
                        if(piles[to].getClass() == SuitStack.class){
                            improveAce = true;
                        }

                        // from  == what pile we are looking for our card in
                        // depth == the pile we are looking to move to
                        // to    == the depth of our moving range

                        I_Move move = new Move(to,from,depth,improveAce,improveCardReveal);
                        moves.add(move);
                    }
                }
            }
        }
    };

    public void pickMove(Board[] moves){

    };

    public ArrayList<Card> getUserInput(String UiChoice){
        return accessInput.getUsrInput(UiChoice);
    }

}
