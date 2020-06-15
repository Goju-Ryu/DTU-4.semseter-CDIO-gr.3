package control;

import data.InputDTO;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.DrawStack;
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
public class BoardController implements I_BoardController {
    private InputDTO accessInput = new InputDTO();

    public I_BoardModel MakeNewBoard(String UiChoice){
        ArrayList<Card> usrInput = accessInput.getUsrInput(UiChoice);

        I_BoardModel board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));
        return board;
    }
    public ArrayList<String> possibleMoves(I_BoardModel boardModel){

        ArrayList<String> moves = new ArrayList<>();

        DrawStack drawStack = (DrawStack) boardModel.getPile(E_PileID.TURNPILE);

        //TODO: atm this only check the top card in each stack.
       I_SolitaireStacks[] piles = boardModel.getPiles();
       for(int i=0; i<piles.length;i++){
           piles.getClass();
       }
       return moves;
    }

    public void pickMove(Board[] moves){

    };
}
