package control;

import com.google.gson.JsonObject;
import data.InputDTO;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import static model.cabal.E_PileID.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardControllerTest {

    private class testBoardCont extends BoardController{

        Map map = null;
        public testBoardCont( Map map ){
            this.map = map;
            init("testing");
        }

        public testBoardCont(Card cards[]){
            Map<String, I_CardModel> map = new HashMap<>();
            for (E_PileID e: E_PileID.values()) {
                switch (e){
                    case DRAWSTACK:
                        map.put(e.toString(),cards[0]);
                        break;
                    case BUILDSTACK1:
                        map.put(e.toString(),cards[1]);
                        break;
                    case BUILDSTACK2:
                        map.put(e.toString(),cards[2]);
                        break;
                    case BUILDSTACK3:
                        map.put(e.toString(),cards[3]);
                        break;
                    case BUILDSTACK4:
                        map.put(e.toString(),cards[4]);
                        break;
                    case BUILDSTACK5:
                        map.put(e.toString(),cards[5]);
                        break;
                    case BUILDSTACK6:
                        map.put(e.toString(),cards[6]);
                        break;
                    case BUILDSTACK7:
                        map.put(e.toString(),cards[7]);
                }
            }
            this.map = map;
            init("testing");
        }

        @Override
        public Map getCards(String uiChoice){
            return map;
        }

    }

    @Test
    void PossibleMoves_Drawstack() {

        Card drStack[] = {
                new Card( E_CardSuit.SPADES     , 1 ),
                new Card( E_CardSuit.HEARTS     , 1  ),
                new Card( E_CardSuit.CLUBS      ,  1 )};

        I_SolitaireStacks drawStack = (I_SolitaireStacks) new DrawStack(Arrays.asList(drStack));

        Card cards[] = {
                new Card( E_CardSuit.SPADES     , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.SPADES     , 9  ),
                new Card( E_CardSuit.DIAMONDS   , 9  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.DIAMONDS   , 9  ) };

        BoardController boardCnt = new testBoardCont(cards);
        boardCnt = changeDrawStack(boardCnt,drawStack);
        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3,result.size());

    }
    @Test
    void PossibleMoves_Drawstack2() {

        Card drStack[] = {
                new Card( E_CardSuit.SPADES     , 1 ),
                new Card( E_CardSuit.HEARTS     , 1  ),
                new Card( E_CardSuit.CLUBS      ,  1 ),
                new Card( E_CardSuit.CLUBS      ,  2 ),
                new Card( E_CardSuit.DIAMONDS      ,  2 ),
                new Card( E_CardSuit.SPADES      ,  2 ),
        };

        I_SolitaireStacks drawStack = (I_SolitaireStacks) new DrawStack(Arrays.asList(drStack));

        Card cards[] = {
                new Card( E_CardSuit.SPADES     , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.SPADES     , 9  ),
                new Card( E_CardSuit.DIAMONDS   , 9  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.DIAMONDS   , 9  ) };

        BoardController boardCnt = new testBoardCont(cards);
        boardCnt = changeDrawStack(boardCnt,drawStack);
        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3,result.size());

    }
    @Test
    void PossibleMoves_Drawstack3() {

        Card drStack[] = {
                new Card( E_CardSuit.SPADES     , 12 ),
                new Card( E_CardSuit.HEARTS     , 11  ),
                new Card( E_CardSuit.CLUBS      ,  1 ),
                new Card( E_CardSuit.CLUBS      ,  3 ),
                new Card( E_CardSuit.DIAMONDS      ,  6 ),
                new Card( E_CardSuit.SPADES      ,  7 ),
                new Card( E_CardSuit.CLUBS      ,  5 ),
                new Card( E_CardSuit.DIAMONDS      ,  7 ),
                new Card( E_CardSuit.SPADES      ,  3 )
        };

        I_SolitaireStacks drawStack = (I_SolitaireStacks) new DrawStack(Arrays.asList(drStack));

        Card cards[] = {
                new Card( E_CardSuit.SPADES     , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.HEARTS     , 9  ),
                new Card( E_CardSuit.SPADES     , 4  ),
                new Card( E_CardSuit.DIAMONDS   , 8  ),
                new Card( E_CardSuit.CLUBS      , 9  ),
                new Card( E_CardSuit.DIAMONDS   , 9  ) };

        BoardController boardCnt = new testBoardCont(cards);
        boardCnt = changeDrawStack(boardCnt,drawStack);
        List<Move> result = boardCnt.possibleMoves();
        assertEquals(4,result.size());

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
        assertEquals(1,res.size());
    }
    @Test
    void PossibleMoves_8Moves() {

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
    @Test
    void PossibleMoves_12Moves(){
        List<Move>res = getPosMoves(
                new Card( E_CardSuit.CLUBS , 4 ),
                new Card( E_CardSuit.SPADES , 4  ),
                new Card( E_CardSuit.DIAMONDS , 5  ),
                new Card( E_CardSuit.HEARTS , 5  ),
                new Card( E_CardSuit.CLUBS , 6  ),
                new Card( E_CardSuit.SPADES , 6  ),
                new Card( E_CardSuit.HEARTS , 7  ),
                new Card( E_CardSuit.DIAMONDS , 7  )
        );
        assertEquals(12,res.size());
    }

    @Test
    void PossibleMoves_5Moves(){
    }
    @Test
    void PossibleMoves_4Moves(){

    }
    @Test
    void PossibleMoves_3Moves(){

    }
    @Test
    void PossibleMoves_4_3_2AceMoves(){
        List<Move> res = getPosMoves(
                new Card( E_CardSuit.CLUBS , 1 ),
                new Card( E_CardSuit.DIAMONDS , 4  ),
                new Card( E_CardSuit.DIAMONDS , 1  ),
                new Card( E_CardSuit.HEARTS , 5  ),
                new Card( E_CardSuit.DIAMONDS , 6  ),
                new Card( E_CardSuit.SPADES , 1  ),
                new Card( E_CardSuit.HEARTS , 1  ),
                new Card( E_CardSuit.DIAMONDS , 7  )
        );
        assertEquals(4,res.size());

        List<Move> res1 = getPosMoves(
                new Card( E_CardSuit.CLUBS , 3 ),
                new Card( E_CardSuit.SPADES , 10  ),
                new Card( E_CardSuit.DIAMONDS , 5  ),
                new Card( E_CardSuit.HEARTS , 5  ),
                new Card( E_CardSuit.CLUBS , 1  ),
                new Card( E_CardSuit.SPADES , 1  ),
                new Card( E_CardSuit.HEARTS , 1),
                new Card( E_CardSuit.DIAMONDS , 12  )
        );
        assertEquals(3,res1.size());

        List<Move> res2 = getPosMoves(
                new Card( E_CardSuit.CLUBS , 4 ),
                new Card( E_CardSuit.SPADES , 4  ),
                new Card( E_CardSuit.DIAMONDS , 6  ),
                new Card( E_CardSuit.HEARTS , 1  ),
                new Card( E_CardSuit.CLUBS , 8  ),
                new Card( E_CardSuit.SPADES , 10  ),
                new Card( E_CardSuit.SPADES , 1),
                new Card( E_CardSuit.DIAMONDS , 12  )
        );
        assertEquals(2,res2.size());

    }

    private BoardController changeDrawStack(BoardController controller, I_SolitaireStacks drawStack){
        Class<?> secretClass = controller.getClass();
        I_BoardModel board = null;


        while(!secretClass.getName().equals(BoardController.class.getName())) {
            secretClass = secretClass.getSuperclass();
        }

        // firstly find the board.
        Field fields[] = secretClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if(field.getName().equals("boardModel")){
                    board = (I_BoardModel) field.get(controller);
                    I_BoardModel b = step2ChangeDrawStack(board, drawStack);
                    field.set( controller ,b );
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return controller;
    }
    private I_BoardModel step2ChangeDrawStack(I_BoardModel board, I_SolitaireStacks drawStack ){
        Class<?> secretClass = board.getClass();
        Field fields[] = secretClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if(field.getName().equals("piles")){
                    I_SolitaireStacks[] piles = (I_SolitaireStacks[]) field.get(board);
                    piles[DRAWSTACK.ordinal()] = drawStack;
                    field.set(board,piles);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return board;
    }
    private List<Move> getPosMoves(Card d1, Card b1,Card b2, Card b3,Card b4, Card b5,Card b6, Card b7){

        Map<String, I_CardModel> map = new HashMap<>();
        for (E_PileID e: E_PileID.values()) {
            switch (e){
                case DRAWSTACK:
                    map.put(e.toString(),d1);
                    break;
                case BUILDSTACK1:
                    map.put(e.toString(),b1);
                    break;
                case BUILDSTACK2:
                    map.put(e.toString(),b2);
                    break;
                case BUILDSTACK3:
                    map.put(e.toString(),b3);
                    break;
                case BUILDSTACK4:
                    map.put(e.toString(),b4);
                    break;
                case BUILDSTACK5:
                    map.put(e.toString(),b5);
                    break;
                case BUILDSTACK6:
                    map.put(e.toString(),b6);
                    break;
                case BUILDSTACK7:
                    map.put(e.toString(),b7);
            }
        }

        testBoardCont boardCnt = new testBoardCont(map);
        List<Move> result = boardCnt.possibleMoves();


        for (Move m: result) {
            List<I_CardModel> stack = boardCnt.getBoardModel().getPile(m.moveFromStack());
            int i = stack.size() - m.moveFromRange();
            I_CardModel c =  stack.get(i) ;
            System.out.println("\n currentCard = " +c.getSuit() + ", " + c.getRank() );
            System.out.println(m);
        }

        return result;
    }

}
