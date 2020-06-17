package control;

import data.InputDTO;
import model.I_Move;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
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
        for(int i=0; i<piles.length;i++){
            I_SolitaireStacks pile = piles[i];
            //E_PileID fromPileID = pile.
            for (int j = 0; j < pile.size() ; j++) {

                // now we need to check if the move is even possible at this index.
                // this is in the case of a type of pile where you cannot move more
                // than one card at a time. like an Ace pile.
                if( !pile.canMoveFrom(j) ){
                    break;
                }

                Collection<I_CardModel> cards = pile.getSubset(j);
                // then i go through each top card in each stack,
                // to se if the current card can move there to.
                for (int k = 0; k < piles.length; k++) {
                    I_CardModel topCard = piles[k].getCard(0);
                    if (piles[k].canMoveTo( cards )){

                        // i == what pile we are looking for our card in
                        // k == the pile we are looking to move to
                        // j == the depth of our moving range
                        I_Move move = new Move(i, k, j){
                            @Override
                            public Board move(Board board) {
                                return null;
                            }
                        };
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
