package control;

import data.InputDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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

    public List<Move> possibleMoves(I_BoardModel boardModel){

        LinkedList<Move> moves = new LinkedList<>();
        I_SolitaireStacks[] piles = boardModel.getPiles();

        // go through each card in each pile
        // so i save the current pile as pile
        // and i do a for each card in this pile.

        for(int from = 0; from < piles.length;from++){
            I_SolitaireStacks pile = piles[from];
            for (int depth = 1; depth <= pile.size() ; depth++) {

                // now we need to check if the move is even possible at this index.
                // this is in the case of a type of pile where you cannot move more
                // than one card at a time. like an Ace pile.

                if( !pile.canMoveFrom(depth) ){
                    break;
                }
                Collection<I_CardModel> cards = pile.getSubset(depth);

                    // now before going any Further, we need to know :
                    // would this move reveal a card underneath this card?
                    boolean improveCardReveal = false;
                    try { // we are aware that the pile might not be this big.
                        improveCardReveal = !pile.getCard(depth + 1).isFacedUp();
                    }catch (Exception ignored){}

                // then i go through each top card in each stack,
                // to se if the current card can move there to.

                for (int to = 0; to < piles.length; to++) {
                    if(from == to){
                        // do nothing. shouldent move to and from the same
                    }else if ( piles[to].canMoveTo( cards ) ){

                        // todo better practice solution to this problem than checking the class
                        // before making the move we want to know:
                        // is this an Ace pile, if it is, then it is an improvement of the win condition.
                        boolean improveAce = false;
                        if(piles[to].getClass() == SuitStack.class){
                            improveAce = true;
                        }

                        // from  == what pile we are looking for our card in
                        // depth == the pile we are looking to move to
                        // to    == the depth of our moving range

                        Move move = new Move(to,from,depth,improveAce,improveCardReveal, "Move Desc");
                        moves.add(move);
                    }
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

    public List<Card> getUserInput(String UiChoice){
        return accessInput.getUsrInput(UiChoice);
    }

}
