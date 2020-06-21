package control;

import data.InputSimDTO;
import model.GameCardDeck;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.RefBoard;

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

    protected I_BoardModel refBoardModel;

    public BoardControllerSimulated() {
        this(new RefBoard(), new GameCardDeck());
    }

    public BoardControllerSimulated(I_BoardModel refBoard, GameCardDeck cardDeck) {
        super(new InputSimDTO(refBoard, cardDeck));
        refBoardModel = refBoard;
        boardModel = new Board(inputDTO.getUsrInput(), cardDeck);
        deck = cardDeck;
    }

    @Override
    public void makeMove(Move move) {
        refBoardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        super.makeMove(move);
    }

}