package control;

import data.InputSimDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BoardControllerSimulatedTest {

    private class testBoardCont extends BoardControllerSimulated{

        List<Card> cards = new ArrayList<>(8);
        public testBoardCont( Card ncards[] ){
            int q = 0;
            for (int i = 1; i <=( 1 + 4 + 7 ) ; i++) {
                if((i > 1 && i < 6 )){
                    cards.add( null );
                }else{
                    cards.add(ncards[q++]);
                }
            }
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

                    for (int depth = 1; depth <= boardModel.getPile(from).size(); depth++) {

                        // now we need to check if the move is even possible at this index.
                        if (!boardModel.canMove(from, depth, to)) {
                            break;
                        }

                        boolean improveCardReveal = false;
                        try {
                            improveCardReveal = !boardModel.getPile(from).get(depth).isFacedUp();
                        } catch (Exception ignored) {}

                        boolean improveAce = false;
                        if (to == E_PileID.SPADESACEPILE ||to == E_PileID.CLUBSACEPILE || to == E_PileID.DIAMONDACEPILE ||to == E_PileID.HEARTSACEPILE ){
                            improveAce = true;
                        }

                        Move move = new Move(to, from, depth, improveAce, improveCardReveal, "Move Desc");
                        moves.add(move);

                    }
                }
            }
            return moves;
        }

        @Override
        public List<Card> getUserInput(String UiChoice) {
            return cards;
        }

    }


    @Test
    void PossibleMoves_0Moves() {

        // first Test. is for an ImpossibleGame. this game has no moves.
        List<Move> res = getPosMoves(
                // the Cards Ordering are fixed  in the contstructor of the testBoard
                new Card(E_CardSuit.SPADES, 2),     // the drawStack
                new Card(E_CardSuit.SPADES, 3),     // buildStack 1
                new Card(E_CardSuit.SPADES, 4),     // buildStack 2
                new Card(E_CardSuit.SPADES, 5),     // buildStack 3
                new Card(E_CardSuit.SPADES, 6),     // buildStack 4
                new Card(E_CardSuit.SPADES, 7),   // buildStack 5
                new Card(E_CardSuit.SPADES, 8),      // buildStack 6
                new Card(E_CardSuit.SPADES, 9)      // buildStack 7
        );
        assertEquals(0, res.size());
    }
    @Test
    void PossibleMoves_1AceMoves() {
        // Assert that we can move 1 onto Ace pile from a drawstack
        List<Move>res = getPosMoves(
                new Card(E_CardSuit.SPADES, 1),      // 0  // aces = 1 , 2, 3, 4
                new Card(E_CardSuit.SPADES, 3),     // 5
                new Card(E_CardSuit.SPADES, 4),     // 6
                new Card(E_CardSuit.SPADES, 5),     // 7
                new Card(E_CardSuit.SPADES, 6),     // 8
                new Card(E_CardSuit.SPADES, 7),     // 9
                new Card(E_CardSuit.SPADES, 8),     // 10
                new Card(E_CardSuit.SPADES, 9)      // 11
        );
        //assertEquals(1,res.size());
    }
    @Test
    void PossibleMoves_7BuildMoves() {

        List<Move>res = getPosMoves(
                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
                new Card( E_CardSuit.HEARTS , 2  ),     // 5
                new Card( E_CardSuit.CLUBS , 3  ),     // 6
                new Card( E_CardSuit.HEARTS , 4  ),     // 7
                new Card( E_CardSuit.SPADES , 5  ),     // 8
                new Card( E_CardSuit.DIAMONDS , 6  ),     // 9
                new Card( E_CardSuit.CLUBS , 7  ),     // 10
                new Card( E_CardSuit.DIAMONDS , 8  )      // 11
        );
        assertEquals(8,res.size());

    }



    private List<Move> getPosMoves(Card d1, Card b1,Card b2, Card b3,Card b4, Card b5,Card b6, Card c8){
        Card cards[] = {d1,b1,b2,b3,b4,b5,b6,c8};

        testBoardCont boardCnt = new testBoardCont(cards);
        I_BoardModel board = boardCnt.MakeNewBoard("hej");
        List<Move> result = boardCnt.possibleMoves(board);

        for (Move m: result) {
            List<I_CardModel> stack = board.getPile(m.moveFromStack());
            int i = stack.size() - m.moveFromRange();
            I_CardModel c =  stack.get(i) ;
            System.out.println("\n currentCard = " +c.getSuit() + ", " + c.getRank() );
            System.out.println(m);
        }

        return result;
    }
}