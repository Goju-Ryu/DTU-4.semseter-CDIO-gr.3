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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected I_InputDTO inputDTO;

     public BoardControllerSimulated() {
         //this(new MockBoard());
     }

    public BoardControllerSimulated(I_BoardModel refBoard) {

    }

    @Override
    public void makeMove(Move move) {
        refBoardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        super.makeMove(move);
    }

    public I_BoardModel MakeNewBoard(String UiChoice,List<I_CardModel> cardsForBoard){

        Map<String, I_CardModel> usrInputMap = new HashMap<>();
        usrInputMap.put(E_PileID.DRAWSTACK.name(), cardsForBoard.get(0));
        usrInputMap.put(E_PileID.SUITSTACKHEARTS.name(), null);
        usrInputMap.put(E_PileID.SUITSTACKDIAMONDS.name(), null);
        usrInputMap.put(E_PileID.SUITSTACKSPADES.name(), null);
        usrInputMap.put(E_PileID.SUITSTACKCLUBS.name(), null);
        usrInputMap.put(E_PileID.BUILDSTACK1.name(), cardsForBoard.get(5));
        usrInputMap.put(E_PileID.BUILDSTACK2.name(), cardsForBoard.get(6));
        usrInputMap.put(E_PileID.BUILDSTACK3.name(), cardsForBoard.get(7));
        usrInputMap.put(E_PileID.BUILDSTACK4.name(), cardsForBoard.get(8));
        usrInputMap.put(E_PileID.BUILDSTACK5.name(), cardsForBoard.get(9));
        usrInputMap.put(E_PileID.BUILDSTACK6.name(), cardsForBoard.get(10));
        usrInputMap.put(E_PileID.BUILDSTACK7.name(), cardsForBoard.get(11));

        return new Board(usrInputMap);
    }

}