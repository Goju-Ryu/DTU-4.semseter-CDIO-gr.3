package control;

import data.I_InputDTO;
import history.GameHistory;
import history.I_GameHistory;
import history.I_GameState;
import history.State;
import model.Move;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
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

                for( int index = boardModel.getPile(from).size()-1 ; index >=0 ; index-- ){ // remember that draw pile can require more turned cards than size - 1

                    // data for easy debugging
                    I_SolitaireStacks s = boardModel.getPiles()[from.ordinal()];
                    I_CardModel c = s.getCard(index);

                    // now we need to check if the move is even possible at this index.
                    if (!boardModel.canMoveFrom(from,index)) {
                        break;
                    }

                    if(!boardModel.canMove(from,index,to)) {
                        continue;
                    }

                    boolean improveCardReveal = false;
                    try {
                        var pile = boardModel.getPile(from);
                        improveCardReveal = !pile.get(index-1).isFacedUp();
                    } catch (Exception ignored) {}


                    Move move = new Move( to,from,index, improveAce, improveCardReveal, "Move Desc");
                    move.setDepth(boardModel.getPile(from).size() - index);
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
        if( possibleMoves().size() < 2 && history.isRepeatState() )
            throw new UnendingGameException("Game state is repeated, and possible moves size = 1");

        // now that the list is sorted. we return the best element, the first one.
        moves.sort(comp);
        return pickMove_repeatCheck( moves , 0);

    }

    @Override
    public void makeMove(final Move move) throws UnendingGameException {

        I_CardModel c = boardModel.getPile(move.moveFromStack()).get(move.moveFromRange());
        if( move.moveFromStack() == DRAWSTACK ){
            drawStackMove(move, boardModel);
        }else {
            boardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
        }

        if (  history.isRepeatState() ){
            Collection<I_GameState> s = history.getRepeatStates();
            throw new UnendingGameException("I have been in this state before...");
        }
    }

    protected void drawStackMove(Move move, I_BoardModel boardModel) {
        if (boardModel != null) {
            if( move.moveFromStack() == DRAWSTACK ){

                boardModel.turnCardsToIndex(move.moveFromRange());
                int index = boardModel.getPile(DRAWSTACK).size() -1;
                boardModel.move(move.moveFromStack(),  index, move.moveToStack(), inputDTO.getUsrInput());
            }else {
                boardModel.move(move.moveFromStack(), move.moveFromRange(), move.moveToStack(), inputDTO.getUsrInput());
            }
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

    private Move pickMove_repeatCheck(List<Move> moves, int i){

        if( (moves.size()-1) >= i) {

            Map<E_PileID, List<I_CardModel>> map = boardModel.makeMoveStateMap(moves.get(i));
            State s = new State(map);

            if (history.isRepeatState(s)) {
                return pickMove_repeatCheck(moves, ++i);
            } else {
                return moves.get(i);
            }
        }else{
            throw new UnendingGameException("Game Entered Unending loop\n every move would lead to a repeated game state");
        }

    }

    @Override
    public boolean hasWonGame(){
        // if GAME has been won
        boolean heartCom = (boardModel.getPile(SUITSTACKHEARTS).size() == 13);
        boolean clubsCom = (boardModel.getPile(SUITSTACKCLUBS).size() == 13);
        boolean diamoCom = (boardModel.getPile(SUITSTACKDIAMONDS).size() == 13);
        boolean spadeCom = (boardModel.getPile(SUITSTACKSPADES).size() == 13);
        return (heartCom && spadeCom && diamoCom && clubsCom);
    }

    @Override
    public I_BoardModel getBoard(){
        return boardModel;
    }


}
