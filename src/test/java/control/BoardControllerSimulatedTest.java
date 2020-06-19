package control;

import data.InputSimDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BoardControllerSimulatedTest {

//    private class testBoardCont extends BoardControllerSimulated{
//
//        Map<String, I_CardModel> cards = new HashMap<>();
//        public testBoardCont( Card ncards[] ){
//            int q = 0;
//            for (int i = 1; i <=( 1 + 4 + 7 ) ; i++) {
//                if((i > 1 && i < 6 )){
//                    cards.add(new Card());
//                }else{
//                    cards.add(ncards[q++]);
//                }
//            }
//        }
//
//        @Override
//        public List<Move> possibleMoves(I_BoardModel boardModel){
//
//            LinkedList<Move> moves = new LinkedList<>();
//            I_SolitaireStacks[] piles = boardModel.getPiles();
//
//            // go through each card in each pile
//            // so i save the current pile as pile
//            // and i do a for each card in this pile.
//
//            for(int from = 0; from < piles.length;from++){
//                I_SolitaireStacks pile = piles[from];
//                for (int depth = 1; depth < pile.size() ; depth++) {
//
//                    // now we need to check if the move is even possible at this index.
//                    // this is in the case of a type of pile where you cannot move more
//                    // than one card at a time. like an Ace pile.
//
//                    if( !pile.canMoveFrom(depth) ){
//                        break;
//                    }
//                    Collection<I_CardModel> cards = pile.getSubset(depth);
//
//                    // now before going any Further, we need to know :
//                    // would this move reveal a card underneath this card?
//                    boolean improveCardReveal = false;
//                    try { // we are aware that the pile might not be this big.
//                        improveCardReveal = !pile.getCard(depth + 1).isFacedUp();
//                    }catch (Exception ignored){}
//
//                    // then i go through each top card in each stack,
//                    // to se if the current card can move there to.
//
//                    for (int to = 0; to < piles.length; to++) {
//
//                        if(from == to){
//                            // do nothing. shouldent move to and from the same
//                        }else if ( piles[to].canMoveTo( cards ) ){
//
//                            // todo better practice solution to this problem than checking the class
//                            // before making the move we want to know:
//                            // is this an Ace pile, if it is, then it is an improvement of the win condition.
//                            boolean improveAce = false;
//                            if(piles[to].getClass() == SuitStack.class){
//                                improveAce = true;
//                            }
//
//                            // from  == what pile we are looking for our card in
//                            // depth == the pile we are looking to move to
//                            // to    == the depth of our moving range
//
//                            Move move = new Move(to,from,depth,improveAce,improveCardReveal, "Move Desc");
//                            moves.add(move);
//                        }
//
//                    }
//                }
//            }
//            return moves;
//        }
//
//        @Override
//        public Map<String, I_CardModel> getUserInput() {
//            return cards;
//        }
//
//    }
//
//    @Test
//    void PossibleMoves_testsAboutPriorityOfMoves() {
//
//        // first Test. is for an ImpossibleGame. this game has no moves.
//        List<Move> res = getPosMoves(
//                // the Cards Ordering are fixed  in the contstructor of the testBoard
//                new Card( E_CardSuit.SPADES , 2 ),     // the drawStack
//                new Card( E_CardSuit.SPADES , 3  ),     // buildStack 1
//                new Card( E_CardSuit.SPADES , 4  ),     // buildStack 2
//                new Card( E_CardSuit.SPADES , 5  ),     // buildStack 3
//                new Card( E_CardSuit.SPADES , 6  ),     // buildStack 4
//                new Card( E_CardSuit.SPADES , 7  ),   // buildStack 5
//                new Card( E_CardSuit.SPADES , 8  ),      // buildStack 6
//                new Card( E_CardSuit.SPADES , 9  )      // buildStack 7
//        );
//        assertEquals(0,res.size());
//
//        // Assert that we can move 1 onto Ace pile from a drawstack
//        res = getPosMoves(
//                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
//                new Card( E_CardSuit.SPADES , 3  ),     // 5
//                new Card( E_CardSuit.SPADES , 4  ),     // 6
//                new Card( E_CardSuit.SPADES , 5  ),     // 7
//                new Card( E_CardSuit.SPADES , 6  ),     // 8
//                new Card( E_CardSuit.SPADES , 7  ),     // 9
//                new Card( E_CardSuit.SPADES , 8  ),     // 10
//                new Card( E_CardSuit.SPADES , 9  )      // 11
//        );
//        assertEquals(1,res.size());
//
//
//        res = getPosMoves(
//                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
//                new Card( E_CardSuit.HEARTS , 2  ),     // 5
//                new Card( E_CardSuit.CLUBS , 3  ),     // 6
//                new Card( E_CardSuit.HEARTS , 4  ),     // 7
//                new Card( E_CardSuit.SPADES , 5  ),     // 8
//                new Card( E_CardSuit.DIAMONDS , 6  ),     // 9
//                new Card( E_CardSuit.CLUBS , 7  ),     // 10
//                new Card( E_CardSuit.DIAMONDS , 8  )      // 11
//        );
//        assertEquals(7,res.size());
//    }
//    private List<Move> getPosMoves(Card c1, Card c2,Card c3, Card c4,Card c5, Card c6,Card c7, Card c8){
//        Card cards[] = {c1,c2,c3,c4,c5,c6,c7,c8};
//        String str ="";
//
//        for (Card c: cards ) {
//            str += c.getSuit() + "," + c.getRank() + ".";
//        }
//
//        testBoardCont boardCnt = new testBoardCont(cards);
//        I_BoardModel board = boardCnt.MakeNewBoard("hej");
//        List<Move> result = boardCnt.possibleMoves(board);
//        System.out.println(str);
//
//        for (Move m: result) {
//            System.out.println("f"+(m.moveFromStack_index()-5) + ". t"+(m.moveToStack_index()-5) + ". r" +m.moveFromRange());
//        }
//
//        System.out.println(result);
//        return result;
//    }
}