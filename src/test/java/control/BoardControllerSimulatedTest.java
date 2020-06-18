package control;

import data.InputSimDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class BoardControllerSimulatedTest {

    private class testBoardCont extends BoardControllerSimulated{

        List<Card> cards = new ArrayList<>(8);
        public testBoardCont( Card ncards[] ){
            int q = 0;
            for (int i = 1; i <=( 1 + 4 + 7 ) ; i++) {
                if((i > 1 && i < 6 )){
                    cards.add(new Card());
                }else{
                    cards.add(ncards[q++]);
                }
            }
        }

        @Override
        public List<Card> getUserInput(String UiChoice) {
            return cards;
        }

    }

    @Test
    void PossibleMoves_testsAboutPriorityOfMoves() {

        // first Test. is for an ImpossibleGame. this game has no moves.
        List<Move> res = getPosMoves(
                // the Cards Ordering are fixed  in the contstructor of the testBoard
                new Card( E_CardSuit.SPADES , 2 ),     // the drawStack
                new Card( E_CardSuit.SPADES , 3  ),     // buildStack 1
                new Card( E_CardSuit.SPADES , 4  ),     // buildStack 2
                new Card( E_CardSuit.SPADES , 5  ),     // buildStack 3
                new Card( E_CardSuit.SPADES , 6  ),     // buildStack 4
                new Card( E_CardSuit.SPADES , 7  ),   // buildStack 5
                new Card( E_CardSuit.SPADES , 8  ),      // buildStack 6
                new Card( E_CardSuit.SPADES , 9  )      // buildStack 7
        );

        res = getPosMoves(
                // the Cards Ordering are fixed  in the contstructor of the testBoard
                new Card( E_CardSuit.HEARTS , 9 ),    // the drawStack
                new Card( E_CardSuit.SPADES , 8  ),   // buildStack 1
                new Card( E_CardSuit.HEARTS , 7  ),   // buildStack 2
                new Card( E_CardSuit.SPADES , 6  ),   // buildStack 3
                new Card( E_CardSuit.HEARTS , 5  ),   // buildStack 4
                new Card( E_CardSuit.SPADES , 4  ),   // buildStack 5
                new Card( E_CardSuit.HEARTS , 3  ),   // buildStack 6
                new Card( E_CardSuit.SPADES , 2  )    // buildStack 7
        );
    }
    private List<Move> getPosMoves(Card c1, Card c2,Card c3, Card c4,Card c5, Card c6,Card c7, Card c8){
        Card cards[] = {c1,c2,c3,c4,c5,c6,c7,c8};
        String str ="";

        for (Card c: cards ) {
            str += c.getSuit() + "," + c.getRank() + ".";
        }

        testBoardCont boardCnt = new testBoardCont(cards);
        I_BoardModel board = boardCnt.MakeNewBoard("hej");
        List<Move> result = boardCnt.possibleMoves(board);
        System.out.println(str);

        for (Move m: result) {
            System.out.println("f"+(m.moveFromStack_index()-5) + ". t"+(m.moveToStack_index()-5) + ". r" +m.moveFromRange());
        }

        System.out.println(result);
        return result;
    }
}