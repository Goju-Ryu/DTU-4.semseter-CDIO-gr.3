package control;

import data.InputSimDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;

import java.util.LinkedList;
import java.util.List;

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
    public Board MakeNewBoard(String UiChoice) {
        List<Card> usrInput = getUserInput(UiChoice);
        Board board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));
        return board;
    }

    @Override
    public List<Move> possibleMoves(I_BoardModel boardModel) {
        return super.possibleMoves(boardModel);
    }

    @Override
    public Move pickMove(List<Move> moves) {
        return super.pickMove(moves);
    }

    @Override
    public List<Card> getUserInput(String UiChoice) {
        return accessInput.getUsrInput(UiChoice);
    }
}