package control;

import data.InputSimDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        assertEquals(0,res.size());

        // Assert that we can move 1 onto Ace pile from a drawstack
        res = getPosMoves(
                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
                new Card( E_CardSuit.SPADES , 3  ),     // 5
                new Card( E_CardSuit.SPADES , 4  ),     // 6
                new Card( E_CardSuit.SPADES , 5  ),     // 7
                new Card( E_CardSuit.SPADES , 6  ),     // 8
                new Card( E_CardSuit.SPADES , 7  ),     // 9
                new Card( E_CardSuit.SPADES , 8  ),     // 10
                new Card( E_CardSuit.SPADES , 9  )      // 11
        );
        assertEquals(1,res.size());


        res = getPosMoves(
                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
                new Card( E_CardSuit.HEARTS , 2  ),     // 5
                new Card( E_CardSuit.CLUBS , 3  ),     // 6
                new Card( E_CardSuit.HEARTS , 4  ),     // 7
                new Card( E_CardSuit.SPADES , 5  ),     // 8
                new Card( E_CardSuit.DIAMONDS , 6  ),     // 9
                new Card( E_CardSuit.CLUBS , 7  ),     // 10
                new Card( E_CardSuit.DIAMONDS , 8  )      // 11
        );
        assertEquals(7,res.size());

        res = getPosMoves(
                new Card( E_CardSuit.SPADES , 1 ),      // 0  // aces = 1 , 2, 3, 4
                new Card( E_CardSuit.HEARTS , 2  ),     // 5
                new Card( E_CardSuit.CLUBS , 3  ),     // 6
                new Card( E_CardSuit.HEARTS , 4  ),     // 7
                new Card( E_CardSuit.SPADES , 5  ),     // 8
                new Card( E_CardSuit.DIAMONDS , 6  ),     // 9
                new Card( E_CardSuit.CLUBS , 7  ),     // 10
                new Card( E_CardSuit.DIAMONDS , 8  )      // 11
        );
        assertEquals(7,res.size());

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


        System.out.println(result);
        return result;
    }
}