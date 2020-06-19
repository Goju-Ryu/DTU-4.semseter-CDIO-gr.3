package control;

import data.I_InputDTO;
import data.InputDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

    protected I_InputDTO accessInput;
    protected String uiChoice;

    public BoardController(String uiChoice) {
        this.uiChoice = uiChoice;
        accessInput = new InputDTO(uiChoice);
    }

    public I_BoardModel MakeNewBoard(String uiChoice){
        return new Board(getUserInput());
    }

    @Override
    public List<Move> possibleMoves(I_BoardModel boardModel){

        LinkedList<Move> moves = new LinkedList<>();

        // go through each card in each pile
        // so i save the current pile as pile
        // and i do a for each card in this pile.

        for(E_PileID from: E_PileID.values()){
            for (E_PileID to: E_PileID.values()) {
                if(from == to)
                    continue;

                for (int depth = 1; depth < boardModel.getPile(from).size(); depth++) {

                    // now we need to check if the move is even possible at this index.
                    if (!boardModel.canMove(from, depth, to)) {
                        break;
                    }

                    boolean improveCardReveal = false;
                    try {
                        improveCardReveal = !boardModel.getPile(from).get(depth).isFacedUp();
                    } catch (Exception ignored) {}

                    boolean improveAce = false;
                    if (to == HEARTSACEPILE ||to == CLUBSACEPILE || to == E_PileID.DIAMONDACEPILE ||to == SPADESACEPILE ){
                        improveAce = true;
                    }

                    Move move = new Move(to, from, depth, improveAce, improveCardReveal, "Move Desc");
                    moves.add(move);

                }
            }
        }
        return moves;
    }

    public Move pickMove(List<Move> moves){

        if( moves.size() == 0){
            return null;
        }

        // so we want to sort the moves by two values
        // 1st priority : does it move something into the ace pile
        // 2nd priority : does it reveal an unknown card
        // then the list would have the elements that does both first,
        // then the element that does 1st priority
        // then the elements that does the 2nd priority
        // then then the elements that does none of these things.

        Comparator<Move> comp = new Comparator<>() {

            // i am making a custom comparator to compare elements of the list via this priority
            // the assignment here is to return 1, if move 1 improves ace, and move 2 does not
            // return 0, if they both improve , or are neutral in this regard.
            // return -1 if move 1 does not improve the ace pile, and move 2 does.

            @Override
            public int compare(Move o1, Move o2) {

                // we set up a scenario on checking the first Priority condition,
                // this means we se if the ace condition is improved.
                if( o1.improvesAceCondition() == o2.improvesAceCondition()) {
                    return secondPrio(o1,o2);
                }else{
                    // o1 and o2 values are oposate each other, so we can check one of them then asume the reversed
                    if(o1.improvesAceCondition())
                        return 1;
                    return -1;
                }
            }

            // this methods job is to return 1 if move o1 is better than o2
            // better being revealing an unknown card.
            private int secondPrio(Move o1, Move o2){
                if (o1.improvesByTurningCard() == o2.improvesByTurningCard()){
                    return 0;
                }else{
                    // o1 and o2 values are oposate each other, so we can check one of them then asume the reversed
                    if(o1.improvesByTurningCard())
                        return 1;
                    return -1;
                }
            }

        };

        // now that the list is sorted. we return the best element, the first one.
        moves.sort(comp);
        return moves.get(0);

    };

}
