package control;

import data.InputSimDTO;
import data.MockBoard;
import model.GameCardDeck;
import model.Move;
import model.cabal.*;
import model.cabal.internals.BuildStack;
import model.cabal.internals.DrawStack;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.util.*;
import static model.cabal.E_PileID.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardControllerTest {

    private class testBoardCont extends BoardController{

        protected I_BoardModel refBoardModel;
        public testBoardCont(I_BoardModel refBoard) {
            refBoardModel = refBoard;
            inputDTO = new InputSimDTO(refBoardModel);
            boardModel = refBoard;
        }

    }

    @Test
    void PossibleMoves_Drawstack() {

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.HEARTS     , 9  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.HEARTS     , 7  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.CLUBS      , 9  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.CLUBS      , 7  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.SPADES     , 5  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.DIAMONDS   , 9  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.DIAMONDS   , 7  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( E_CardSuit.SPADES     , 1 ));
        list.add(new Card( E_CardSuit.HEARTS     , 1 ));
        list.add(new Card( E_CardSuit.CLUBS     , 1 ));

        // the Board and Getting Results.
        I_BoardModel board = new testBoard(map,list);
        GameCardDeck a = GameCardDeck.getInstance();
        BoardController boardCnt = new testBoardCont(board);


        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3,result.size());

    }
    /*
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

        map.put(DRAWSTACK.name(),d1);
        map.put(BUILDSTACK1.name(),b1);
        map.put(BUILDSTACK2.name(),b2);
        map.put(BUILDSTACK3.name(),b3);
        map.put(BUILDSTACK4.name(),b4);
        map.put(BUILDSTACK5.name(),b5);
        map.put(BUILDSTACK6.name(),b6);
        map.put(BUILDSTACK7.name(),b7);


        testBoardCont boardCnt = new testBoardCont(board);
        List<Move> result = boardCnt.possibleMoves();

        return result;
    }
    */

    private class testBoard extends AbstractBoardUtility implements I_BoardModel{

        public testBoard(Map<E_PileID, I_CardModel> map , List<I_CardModel> list){
            piles = new I_SolitaireStacks[E_PileID.values().length];

            piles[DRAWSTACK.ordinal()] = new DrawStack(list);
            piles[SUITSTACKHEARTS.ordinal()]  = new SuitStack();
            piles[SUITSTACKDIAMONDS.ordinal()] = new SuitStack();
            piles[SUITSTACKCLUBS.ordinal()]   = new SuitStack();
            piles[SUITSTACKSPADES.ordinal()]  = new SuitStack();

            for (int i = 0; i < 7; i++) { // for each build pile
                for (int j = 0; j <= i; j++) {  // how many cards in this pile
                    if (piles[BUILDSTACK1.ordinal() + i] == null)
                        piles[BUILDSTACK1.ordinal() + i] = new BuildStack();
                    else
                        piles[BUILDSTACK1.ordinal() + i].add(new Card());
                }
            }

            for (E_PileID pileID : E_PileID.values()) {
                I_CardModel data = extractIMGData(map, pileID);
                if(data == null)
                    continue;
                piles[pileID.ordinal()].add(data);
            }
        }
        private I_CardModel extractIMGData(Map<E_PileID, I_CardModel> imgData, E_PileID key) {
            return imgData.getOrDefault(key, null); //This assumes a strict naming scheme and will return null if not found
        }

        //InterFace implementation
        @Override
        public boolean isStackComplete(E_PileID pileID) {
            I_SolitaireStacks pile = get(pileID);

            if (pileID.isBuildStack())
                return pile.getCard(0).getRank() == 1; // true if ace on top

            if (pileID == DRAWSTACK)
                return pile.isEmpty();

            //Now we know only suit stacks are left
            return pile.getCard(0).getRank() == 13; // true if suitStack has a king on top
        }

        @Override
        public List<I_CardModel> getPile(E_PileID pile) {
            I_SolitaireStacks s = get(pile);
            List<I_CardModel> cs = new ArrayList<>(s);
            return cs;
        }

        @Override
        public I_SolitaireStacks[] getPiles() {
            return piles;
        }

        // draw stack specific implementation.
        @Override
        public I_CardModel turnCard(Map<String, I_CardModel> imgData) {
            DrawStack turnPile = (DrawStack) get(DRAWSTACK);

            if (turnPile.isEmpty())
                throw new IndexOutOfBoundsException("There are no cards to turn. All cards have been drawn.");

            var returnable = turnPile.turnCard();

            var imgCard = extractImgData(imgData, DRAWSTACK);
            validateCardState(DRAWSTACK, returnable, imgCard);

            return returnable;
        }

        @Override
        public I_CardModel getTurnedCard() {
            return null;
        }

        @Override
        public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
            I_SolitaireStacks from = get(origin);
            I_SolitaireStacks to = get(destination);

            //change state
            to.addAll(from.popSubset(originPos));

            //check that state is consistent with the physical board
            if (!from.isEmpty( ))
                validateCardState(origin, from.getCard(from.size() - 1), extractImgData(imgData, origin));
            else
                validateCardState(origin, null, extractImgData(imgData, origin));
        }

        @Override
        public boolean canMove(E_PileID origin, int originPos, E_PileID destination) {
            I_SolitaireStacks from = get(origin);
            I_SolitaireStacks to = get(destination);

            boolean a = isValidMove(origin, originPos, destination);
            boolean b = from.canMoveFrom(originPos);
            boolean c = to.canMoveTo(from.getSubset(originPos));

            return a && b && c;
        }

        @Override
        public boolean canMoveFrom(E_PileID origin, int range) {
            I_SolitaireStacks from = get(origin);
            return from.canMoveFrom(range);
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
        }
    }
}
