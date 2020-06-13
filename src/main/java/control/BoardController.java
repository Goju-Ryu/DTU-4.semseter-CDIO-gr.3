package control;

import data.InputDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
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

        I_BoardModel board = new Board(new Card(), new Card(), new Card(),
                new Card(E_CardSuit.HEARTS,2),new Card(), new Card(), new Card(),
                new Card());
        return board;
    }
    public void possibleMoves(Card[] fromCV){

    };

    public void pickMove(Board[] moves){

    };
}
