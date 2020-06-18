package control;

import data.InputDTO;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.cabal.E_PileID.*;
import static model.cabal.E_PileID.BUILDSTACK6;
import static model.cabal.E_PileID.BUILDSTACK7;

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

        Map<String, I_CardModel> usrInputMap = new HashMap<>();
        usrInputMap.put(TURNPILE.name(), usrInputMap.get(0));
        usrInputMap.put(HEARTSACEPILE.name(), usrInputMap.get(1));
        usrInputMap.put(DIAMONDACEPILE.name(), usrInputMap.get(2));
        usrInputMap.put(SPADESACEPILE.name(), usrInputMap.get(3));
        usrInputMap.put(CLUBSACEPILE.name(), usrInputMap.get(4));
        usrInputMap.put(BUILDSTACK1.name(), usrInputMap.get(5));
        usrInputMap.put(BUILDSTACK2.name(), usrInputMap.get(6));
        usrInputMap.put(BUILDSTACK3.name(), usrInputMap.get(7));
        usrInputMap.put(BUILDSTACK4.name(), usrInputMap.get(8));
        usrInputMap.put(BUILDSTACK5.name(), usrInputMap.get(9));
        usrInputMap.put(BUILDSTACK6.name(), usrInputMap.get(10));
        usrInputMap.put(BUILDSTACK7.name(), usrInputMap.get(11));

        return new Board(usrInputMap);
    }
    public void possibleMoves(I_BoardModel boardModel){
        //TODO: atm this only check the top card in each stack.
       I_SolitaireStacks[] piles = boardModel.getPiles();
       for(int i=0; i<piles.length;i++){
           piles.getClass();
       }
    };

    public void pickMove(Board[] moves){

    };
}
