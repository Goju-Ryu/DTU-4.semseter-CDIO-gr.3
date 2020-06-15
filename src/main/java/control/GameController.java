package control;

import data.InputDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.util.ArrayList;
/**
 * This class is for controlling an entire game,
 * trough multiple states of the game
 *
 * the diffrence on this and Board controller, is that this acts in relation
 * to multiple states of the game where as BoardController only does one.
 * However this will use BoardControler when i needs to make a move on the board
 */

public class GameController implements I_GameController{

    //Todo implement this so that it returns I_BoardModel here
    public void startGame(String UiChoice){
        I_BoardController boardCtrl = new BoardController();
        I_BoardModel boardMod = null;
        if(UiChoice =="simulation"){
            //TODO: implement that it can iniate a simulated board here
        }else{
            boardMod = boardCtrl.MakeNewBoard(UiChoice);
        }
        boardCtrl.possibleMoves(boardMod);

    }

}