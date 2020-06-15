package control;

import data.InputDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.util.ArrayList;

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
        ArrayList<Card> usrInput = accessInput.getUsrInput(UiChoice);

        I_BoardModel board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));
        return board;
    }
    public void possibleMoves(I_BoardModel boardModel){
        //TODO: atm this only check the top card in each stack.
       I_SolitaireStacks[] piles = boardModel.getPiles();
       for(int i=0; i<piles.length;i++){
           piles.getClass();
       }
    };

    public void pickMove(Board[] moves){

    };
}
