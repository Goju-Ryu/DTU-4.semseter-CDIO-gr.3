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


    /**
     * Creates a standard simulation with a set game layout.
     * Use this if you don't care about changing the layout of the board
     * but just want to simulate a pre determined game.
     */
    public BoardControllerSimulated() {
        this(new RefBoard(), new GameCardDeck());
    }

    public BoardControllerSimulated(GameCardDeck cardDeck) {
        super(new InputSimDTO(cardDeck));
    }


    /**
     * Make a new test game with full control over it's starting conditions.
     *
     * @param refBoard The board simulating the physical board. this is the judge of what the board should look like.
     * @param cardDeck The deck that keeps track of which cards are known by the simulated board.
     */
    public BoardControllerSimulated(I_BoardModel refBoard, GameCardDeck cardDeck) {
        super(new InputSimDTO(refBoard, cardDeck));
        refBoardModel = refBoard;
        boardModel = new Board(inputDTO.getUsrInput(), cardDeck);
        deck = cardDeck;
    }

    @Override
    public void makeMove(Move move) {
        if (refBoardModel != null)
            refBoardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        super.makeMove(move);
    }

}