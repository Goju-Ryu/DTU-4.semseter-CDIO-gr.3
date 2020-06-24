package control;

import data.I_InputDTO;
import data.InputSimDTO;
import history.GameHistory;
import model.GameCardDeck;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.RefBoard;
import model.cabal.internals.card.I_CardModel;

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

    protected I_BoardModel refBoardModel;

    public BoardControllerSimulated(boolean testBoolean){
        super(testBoolean);
    }


    /**
     * Creates a standard simulation with a random game layout.
     * Use this if you don't care about the layout of the board
     * but just want to simulate a game with no guaranties.
     */
    public BoardControllerSimulated() {
        this(new RefBoard(), new GameCardDeck());
    }

    /**
     * This takes a board and an inputDTO. The board is the virtual board.
     * The inputDTO is the source of data when moves are made. It should
     * almost always be an {@link InputSimDTO}, unexpected results may
     * occur otherwise.
     *
     * This constructor can be used to simulate a predetermined game or a random one
     * depending on the relation between the board and inputDTO.
     *
     * @param boardModel The board being controlled by this class
     * @param inputDTO An InputDTO to provide data to board
     */
    public BoardControllerSimulated(I_BoardModel boardModel, I_InputDTO inputDTO) {
        super(true);
        this.boardModel = boardModel;
        this.inputDTO = inputDTO;
        history = new GameHistory(boardModel);
    }


    /**
     * Make a new test game with full control over it's starting conditions.
     *
     * @param refBoard The board simulating the physical board. this is the judge of what the board should look like.
     * @param cardDeck The deck that keeps track of which cards are known by the simulated board.
     */
    public BoardControllerSimulated(I_BoardModel refBoard, GameCardDeck cardDeck) {
        super(true);
        inputDTO = new InputSimDTO(refBoard, cardDeck);
        refBoardModel = refBoard;

        ArrayList<I_CardModel> drawCards = new ArrayList<>();
        int drawStackCardCount = 24;
        for(int i = 0; i < drawStackCardCount; i++) {
            I_CardModel drawCard = inputDTO.getUsrInput().get("DRAWSTACK");
            drawCards.add(drawCard);

            System.out.println("currDrawCard: " + drawCard);
            System.out.println("Turning the next card in the draw stack...");
        }

        boardModel = new Board(inputDTO.getUsrInput(), cardDeck, drawCards);
        history = new GameHistory();
        boardModel.addPropertyChangeListener(history);
    }

    @Override
    public void makeMove(Move move) {
        if (refBoardModel != null)
            refBoardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        super.makeMove(move);
    }

}