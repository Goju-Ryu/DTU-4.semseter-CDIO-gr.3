package control;

import data.I_InputDTO;
import model.Move;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import model.error.UnendingGameException;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardControllerTest {

  
    public static class testBoardController extends AbstractBoardController {
        public testBoardController(AbstractMap.SimpleImmutableEntry<I_BoardModel, I_InputDTO> util) {
            super(util.getKey(), util.getValue());
        }
    }

    @Test
    void PossibleMoves_Drawstack() {

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( HEARTS     , 9  ));
        map.put( BUILDSTACK2.name(),new Card( HEARTS     , 7  ));
        map.put( BUILDSTACK3.name(),new Card( CLUBS      , 9  ));
        map.put( BUILDSTACK4.name(),new Card( CLUBS      , 7  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES     , 5  ));
        map.put( BUILDSTACK6.name(),new Card( DIAMONDS   , 9  ));
        map.put( BUILDSTACK7.name(),new Card( DIAMONDS   , 7  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( SPADES     , 1 ));
        list.add(new Card( HEARTS     , 1 ));
        list.add(new Card( CLUBS     , 1 ));

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3, result.size());
    }

    @Test
    void PossibleMoves_Drawstack3() {

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( DIAMONDS   , 3  ));
        map.put( BUILDSTACK2.name(),new Card( DIAMONDS   , 4  ));
        map.put( BUILDSTACK3.name(),new Card( HEARTS     , 9  ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS     , 8  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES     , 3  ));
        map.put( BUILDSTACK6.name(),new Card( CLUBS      , 12  ));
        map.put( BUILDSTACK7.name(),new Card( CLUBS      , 11  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( DIAMONDS      ,  1 ));
        list.add(new Card( HEARTS     , 2 ));
        list.add(new Card( SPADES      ,  8 ));
        list.add(new Card( SPADES      ,  7 ));
        list.add(new Card( CLUBS      ,  3 ));
        list.add(new Card( CLUBS      ,  2 ));

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(7,result.size());

    }

    @Test
    void PossibleMoves_Drawstack4() {

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1.name(),new Card( DIAMONDS    , 3  ));
        map.put(BUILDSTACK2.name(),new Card( DIAMONDS    , 4  ));
        map.put(BUILDSTACK3.name(),new Card( HEARTS      , 9  ));
        map.put(BUILDSTACK4.name(),new Card( HEARTS      , 8  ));
        map.put(BUILDSTACK5.name(),new Card( SPADES      , 3  ));
        map.put(BUILDSTACK6.name(),new Card( CLUBS       , 12  ));
        map.put(BUILDSTACK7.name(),new Card( CLUBS       , 11  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( DIAMONDS  ,7 ));
        list.add(new Card( CLUBS     ,1 ));
        list.add(new Card( CLUBS     ,8 ));
        list.add(new Card( CLUBS     ,10));
        
        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3, result.size());

    }
    
    @Test
    void PossibleMoves_0Moves() {

        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES   , 2  ));
        map.put( BUILDSTACK2.name(),new Card( SPADES   , 3  ));
        map.put( BUILDSTACK3.name(),new Card( SPADES   , 4  ));
        map.put( BUILDSTACK4.name(),new Card( SPADES   , 5  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES   , 6  ));
        map.put( BUILDSTACK6.name(),new Card( SPADES   , 7  ));
        map.put( BUILDSTACK7.name(),new Card( SPADES   , 8  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(0, result.size());

    }

    @Test
    void PossibleMoves_1AceMoves() {
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES   , 1  ));
        map.put( BUILDSTACK2.name(),new Card( SPADES   , 3  ));
        map.put( BUILDSTACK3.name(),new Card( SPADES   , 4  ));
        map.put( BUILDSTACK4.name(),new Card( SPADES   , 5  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES   , 6  ));
        map.put( BUILDSTACK6.name(),new Card( SPADES   , 7  ));
        map.put( BUILDSTACK7.name(),new Card( SPADES   , 8  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(1, result.size());
    }

    @Test
    void PossibleMoves_6Moves() {
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES   , 2  ));
        map.put( BUILDSTACK2.name(),new Card( HEARTS   , 3  ));
        map.put( BUILDSTACK3.name(),new Card( CLUBS    , 4  ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS   , 5  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES   , 6  ));
        map.put( BUILDSTACK6.name(),new Card( DIAMONDS , 7  ));
        map.put( BUILDSTACK7.name(),new Card( CLUBS    , 8  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(6, result.size());

    }

    @Test
    void PossibleMoves_5Moves(){
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( DIAMONDS , 4  ));
        map.put( BUILDSTACK3.name(),new Card( SPADES   , 5  ));
        map.put( BUILDSTACK5.name(),new Card( HEARTS   , 6  ));
        map.put( BUILDSTACK2.name(),new Card( SPADES   , 4  ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS   , 5  ));
        map.put( BUILDSTACK6.name(),new Card( SPADES   , 6  ));
        map.put( BUILDSTACK7.name(),new Card( DIAMONDS , 7  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(5, result.size());
    }

    @Test
    void PossibleMoves_testFromError(){

        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES    , 9   ));
        map.put( BUILDSTACK3.name(),new Card( SPADES    , 8   ));
        map.put( BUILDSTACK5.name(),new Card( DIAMONDS  , 13  ));
        map.put( BUILDSTACK2.name(),new Card( DIAMONDS  , 4   ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS    , 13  ));
        map.put( BUILDSTACK6.name(),new Card( SPADES    , 11  ));
        map.put( BUILDSTACK7.name(),new Card( DIAMONDS  , 6   ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( DIAMONDS  ,  7 ));
        list.add(new Card( CLUBS     ,  8 ));
        list.add(new Card( CLUBS     ,  10));
        list.add(new Card( HEARTS    ,  1 ));
        list.add(new Card( CLUBS     ,  1 ));
        list.add(new Card( DIAMONDS  ,  6 ));

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3, result.size());
    }

    @Test
    void PossibleMoves_testFromError_2(){

        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES    , 6   ));
        map.put( BUILDSTACK3.name(),new Card( DIAMONDS  , 12   ));
        map.put( BUILDSTACK5.name(),new Card( HEARTS    , 13  ));
        map.put( BUILDSTACK2.name(),new Card( DIAMONDS  , 4   ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS    , 12  ));
        map.put( BUILDSTACK6.name(),new Card( DIAMONDS  , 2  ));
        map.put( BUILDSTACK7.name(),new Card( SPADES    , 8   ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( CLUBS    ,  1 ));
        list.add(new Card( CLUBS    ,  2 ));
        list.add(new Card( CLUBS    ,  3 ));
        list.add(new Card( CLUBS    ,  8 ));
        list.add(new Card( HEARTS   ,  2 ));

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        testBoardController boardCnt = new testBoardController(util);

        List<Move> result = boardCnt.possibleMoves();
        Move m = boardCnt.pickMove(result);

        assertEquals(SUITSTACKCLUBS, m.moveToStack());
        assertEquals(DRAWSTACK, m.moveFromStack());
        assertEquals(1,m.moveFromRange());
    }

    @Test
    void make_a_move(){

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( SPADES    , 3  ));
        map.put( BUILDSTACK2.name(),new Card( HEARTS    , 3  ));
        map.put( BUILDSTACK3.name(),new Card( DIAMONDS     , 10 ));
        map.put( BUILDSTACK4.name(),new Card( HEARTS    , 10 ));
        map.put( BUILDSTACK5.name(),new Card( SPADES    , 10 ));
        map.put( BUILDSTACK6.name(),new Card( CLUBS     , 10 ));
        map.put( BUILDSTACK7.name(),new Card( HEARTS    , 8  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( SPADES     , 8 ));
        list.add(new Card( DIAMONDS   , 8 ));
        list.add(new Card( CLUBS      , 8 ));
        list.add(new Card( SPADES     , 9 ));
        list.add(new Card( DIAMONDS   , 9 ));
        list.add(new Card( CLUBS      , 9 ));
        list.add(new Card( HEARTS     , 9 ));

        //Move Queue
        Queue<Move> moves = new LinkedList<>();
        moves.add( new Move(  BUILDSTACK5,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move(  BUILDSTACK4,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move(  BUILDSTACK5,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move(  BUILDSTACK3,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move(  BUILDSTACK4,DRAWSTACK, 2, false , false,"") );

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);
        for (Move m: moves) {
            boardCnt.makeMove(m);
        }
        System.out.println("asd");
    }

    @Test
    void PickAMove_NoCircle_ifOtherChoice() {

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(), new Card(SPADES, 1));
        map.put( BUILDSTACK2.name(), new Card(HEARTS, 2));
        map.put( BUILDSTACK3.name(), new Card(CLUBS, 3));
        map.put( BUILDSTACK4.name(), new Card(HEARTS, 4));
        map.put( BUILDSTACK5.name(), new Card(SPADES, 5));
        map.put( BUILDSTACK6.name(), new Card(CLUBS, 6));
        map.put( BUILDSTACK7.name(), new Card(HEARTS, 7));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card(HEARTS, 9));
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());
        list.add(new Card());

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        List<Move> moves = boardCnt.possibleMoves();
        Move m = boardCnt.pickMove(moves);
        System.out.println(m);

    }

    @Test
    void RepeatState_impl_test(){

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, I_CardModel> map = new HashMap<>();
        map.put( BUILDSTACK1.name(),new Card( HEARTS     , 4  ));
        map.put( BUILDSTACK2.name(),new Card( SPADES     , 11 ));
        map.put( BUILDSTACK3.name(),new Card( SPADES     , 9  ));
        map.put( BUILDSTACK4.name(),new Card( SPADES     , 4  ));
        map.put( BUILDSTACK5.name(),new Card( SPADES     , 6  ));
        map.put( BUILDSTACK6.name(),new Card( SPADES     , 8  ));
        map.put( BUILDSTACK7.name(),new Card( DIAMONDS   , 4  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( CLUBS     , 3 ));

        // the Board and Getting Results.
        var util = TestUtil.getTestReadyBoard(map,list);
        AbstractBoardController boardCnt = new testBoardController(util);

        assertDoesNotThrow(() -> {
            List<Move> moves = boardCnt.possibleMoves();
            Move move = boardCnt.pickMove(moves);
            boardCnt.makeMove(move);
        });

        assertDoesNotThrow(() -> {
            List<Move> moves = boardCnt.possibleMoves();
            Move move = boardCnt.pickMove(moves);
            boardCnt.makeMove(move);
        });

        assertThrows(UnendingGameException.class, () -> {
            List<Move> moves = boardCnt.possibleMoves();
            Move move = boardCnt.pickMove(moves);
            boardCnt.makeMove(move);
        });
    }


}
