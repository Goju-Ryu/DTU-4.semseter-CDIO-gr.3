package control;

import data.I_InputDTO;
import data.InputSimDTO;
import data.MockBoard;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.beans.PropertyChangeListener;
import java.util.*;

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
    protected InputSimDTO inputDTO;

    public BoardControllerSimulated() {
        super();
        inputDTO = new InputSimDTO();
        ArrayList<I_CardModel> drawStack = inputDTO.getDrawstack();
        initTemp("simulation", drawStack);
     }

    @Override
    public void initTemp(String uiChoice, ArrayList<I_CardModel> drawStack) {
        super.initTemp(uiChoice, drawStack);
        inputDTO.giveBoard(getBoardModel());
    }

    @Override
    public Map getCards(String uiChoice) {
        return inputDTO.getCards();
    }

    @Override
    public Move pickMove(List<Move> moves) {
        Move m = super.pickMove(moves);
        inputDTO.move(m);
        return m;
    }


}