package control;

import data.InputDTO;
import data.InputSimDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;

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
public class BoardControllerSimulated extends BoardController {

    private InputSimDTO accessInput = new InputSimDTO();

    @Override
    public I_BoardModel MakeNewBoard(String UiChoice) {
        ArrayList<Card> usrInput = getUserInput(UiChoice);

        I_BoardModel board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));
        return board;
    }

    @Override
    public void possibleMoves(I_BoardModel boardModel) {
        super.possibleMoves(boardModel);
    }

    @Override
    public void pickMove(Board[] moves) {
        super.pickMove(moves);

    }

    @Override
    public ArrayList<Card> getUserInput(String UiChoice) {
        return accessInput.getUsrInput(UiChoice);
    }
}