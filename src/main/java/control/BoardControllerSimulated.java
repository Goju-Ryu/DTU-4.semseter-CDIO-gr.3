package control;

import data.InputSimDTO;
import data.MockBoard;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;

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

    private I_BoardModel refBoardModel;

    public BoardControllerSimulated() {
        this(new MockBoard());
    }

    public BoardControllerSimulated(I_BoardModel refBoard) {
        refBoardModel = refBoard;
        inputDTO = new InputSimDTO(refBoardModel);
        boardModel = new Board(inputDTO.getUsrInput());
    }

    @Override
    public void makeMove(Move move) {
        refBoardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        super.makeMove(move);
    }

}