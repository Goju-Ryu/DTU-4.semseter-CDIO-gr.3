package control;

import data.I_InputDTO;
import data.InputDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static model.cabal.E_PileID.*;

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

    protected I_BoardModel boardModel;
    protected I_InputDTO inputDTO;
    protected String uiChoice;

    public BoardController() {

    }

    public BoardController(String uiChoice) {
        this.uiChoice = uiChoice;
        inputDTO = new InputDTO(uiChoice);
        this.boardModel = new Board(inputDTO.getUsrInput());
    }


    @Override
    public List<Move> possibleMoves(){

        LinkedList<Move> moves = new LinkedList<>();

        // go through each card in each pile
        // so i save the current pile as pile
        // and i do a for each card in this pile.

        for(E_PileID from: values()){
            for (int depth = 1; depth <= boardModel.getPile(from).size() ; depth++) {
                for (E_PileID to: values()) {
                    if(from == to)
                        continue;

                    //Check if move is legal
                    if(!boardModel.canMove(from,depth,to)){
                        continue;
                    }

                    boolean improveCardReveal = false;
                    try {
                        var fromPile = boardModel.getPile(from);
                        improveCardReveal = !fromPile.get(fromPile.size() - depth).isFacedUp();
                    } catch (Exception ignored) {}

                    boolean improveAce = false;
                    if (to == SUITSTACKSPADES ||to == SUITSTACKCLUBS || to == SUITSTACKDIAMONDS ||to == SUITSTACKHEARTS ){
                        improveAce = true;
                    }

                    Move move = new Move(to, from, depth, improveAce, improveCardReveal, "Move Desc");
                    moves.add(move);

                }
            }
        }
        return moves;
    }

    public Move pickMove(List<Move> moves) {

        if(moves.size() == 0){
            return null;
        }

        // now that the list is sorted. we return the best element, the first one.
        moves.sort(comp);
        return moves.get(0);

    }

    @Override
    public void makeMove(Move move) {
        boardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
    }


    // so we want to sort the moves by two values
    // 1st priority : does it move something into the ace pile
    // 2nd priority : does it reveal an unknown card
    // then the list would have the elements that does both first,
    // then the element that does 1st priority
    // then the elements that does the 2nd priority
    // then then the elements that does none of these things.
    private Comparator<Move> comp = (o1,  o2) -> {
        // we set up a scenario on checking the first Priority condition,
        // this means we se if the ace condition is improved.
        if( o1.improvesAceCondition() == o2.improvesAceCondition()) {
            // better being revealing an unknown card.
            if (o1.improvesByTurningCard() == o2.improvesByTurningCard()){
                return 0;
            }else{
                // o1 and o2 values are oposate each other, so we can check one of them then asume the reversed
                if(o1.improvesByTurningCard())
                    return -1;
                return 1;
            }
        }else{
            // o1 and o2 values are oposate each other, so we can check one of them then asume the reversed
            if(o1.improvesAceCondition())
                return -1;
            return 1;
        }
    };
}
