package control;

import data.I_InputDTO;
import history.GameHistory;
import history.I_GameHistory;
import history.I_GameState;
import model.Move;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.I_CardModel;
import model.error.UnendingGameException;

import java.util.*;

import static model.cabal.E_PileID.*;

public abstract class AbstractBoardController implements I_BoardController {

    protected I_BoardModel boardModel;
    protected I_InputDTO inputDTO;
    protected I_GameHistory history;

    public AbstractBoardController(){

    }

    public AbstractBoardController(I_BoardModel boardModel, I_InputDTO inputDTO) {
        this.boardModel = boardModel;
        this.inputDTO = inputDTO;
        this.history = new GameHistory(boardModel);
    }

    @Override
    public List<Move> possibleMoves(){

        LinkedList<Move> moves = new LinkedList<>();

        // go through each card in each pile
        // so i save the current pile as pile
        // and i do a for each card in this pile.

        for(E_PileID from: E_PileID.values()){
            for (E_PileID to: E_PileID.values()) {

                if(from == to)
                    continue;

                boolean improveAce = false;
                if (to == SUITSTACKSPADES ||to == SUITSTACKCLUBS || to == SUITSTACKDIAMONDS ||to == SUITSTACKHEARTS ){
                    improveAce = true;
                }

                for (int depth = 1; depth <= boardModel.getPile(from).size() ; depth++) {

                    // data for easy debugging
                    int b =  boardModel.getPile(from).size();
                    int a = b - depth;
                    List<I_CardModel> s = boardModel.getPile(from);
                    I_CardModel c = s.get(a);

                    // now we need to check if the move is even possible at this index.
                    if (!boardModel.canMoveFrom(from, depth)) {
                        break;
                    }

                    if(!boardModel.canMove(from,depth,to)) {
                        continue;
                    }

                    boolean improveCardReveal = false;
                    try {
                        var pile = boardModel.getPile(from);
                        improveCardReveal = !pile.get(depth).isFacedUp();
                    } catch (Exception ignored) {}


                    Move move = new Move( to,from, depth, improveAce, improveCardReveal, "Move Desc");
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    @Override
    public Move pickMove(List<Move> moves) throws UnendingGameException {

        if(moves.size() == 0){
            return null;
        }

        // before sorting we want to know if we have been in this same state before.
        if( history.isRepeatState())
            throw new UnendingGameException("Game state is repeated, and possible moves size = 1");

        // now that the list is sorted. we return the best element, the first one.
        moves.sort(comp);
        return moves.get(0);

    }

    @Override
    public void makeMove(Move move) throws UnendingGameException {
        //todo: make it so that inputDTO promts for ui every time
        if( move.moveFromStack() == DRAWSTACK ){
            // draw stack has a unique rule set, that makes.
            for (int i = 0; i < move.moveFromRange()-1 ; i++) {
                boardModel.turnCard(Map.of()); // Empty map because we want it to ignore inputs in these iterations
            }
            // now when it has turned all the necessary cards in the drawstack we give it an input.
            boardModel.turnCard(inputDTO.getUsrInput());
            boardModel.move(move.moveFromStack(),  move.moveToStack(), inputDTO.getUsrInput());
        }else {
            boardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        }

        if (  history.isRepeatState() ){
            Collection<I_GameState> s = history.getRepeatStates();
            throw new UnendingGameException("I have been in this state before...");
        }
    }

    // so we want to sort the moves by two values
    // 1st priority : does it move something into the ace pile
    // 2nd priority : does it reveal an unknown card
    // then the list would have the elements that does both first,
    // then the element that does 1st priority
    // then the elements that does the 2nd priority
    // then then the elements that does none of these things.
    protected Comparator<Move> comp = (o1, o2) -> {
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
