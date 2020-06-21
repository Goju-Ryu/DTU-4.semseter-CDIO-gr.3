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
            inputDTO = new InputSimDTO(new TestGameCardDeck(refBoard));
            boardModel = refBoard;
        }

    }
    private class TestGameCardDeck extends GameCardDeck{
        public TestGameCardDeck(I_BoardModel m){
            super();
            for (I_SolitaireStacks s : m.getPiles()) {
                for (I_CardModel c: s) {
                    if(c.isFacedUp())
                        super.remove(c);
                }
            }
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
        BoardController boardCnt = new testBoardCont(board);


        List<Move> result = boardCnt.possibleMoves();
        assertEquals(3,result.size());

    }

    @Test
    void PossibleMoves_Drawstack3() {
        // What is the top cards of the rows, anything undeclared is empty list.

        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.DIAMONDS   , 3  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.DIAMONDS   , 4  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.HEARTS     , 9  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.HEARTS     , 8  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.SPADES     , 3  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.CLUBS      , 12  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.CLUBS      , 11  ));

        // the drawStack.
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( E_CardSuit.DIAMONDS      ,  1 ));
        list.add(new Card( E_CardSuit.HEARTS     , 2 ));
        list.add(new Card( E_CardSuit.SPADES      ,  8 ));
        list.add(new Card( E_CardSuit.SPADES      ,  7 ));
        list.add(new Card( E_CardSuit.CLUBS      ,  3 ));
        list.add(new Card( E_CardSuit.CLUBS      ,  2 ));


        // the Board and Getting Results.
        I_BoardModel board = new testBoard(map,list);
        BoardController boardCnt = new testBoardCont(board);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(7,result.size());

    }

    @Test
    void PossibleMoves_0Moves() {

        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.SPADES   , 2  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.SPADES   , 3  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.SPADES   , 4  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.SPADES   , 5  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.SPADES   , 6  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.SPADES   , 7  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.SPADES   , 8  ));


        I_BoardModel board = new testBoard(map);
        BoardController boardCnt = new testBoardCont(board);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(0, result.size());
    }

    @Test
    void PossibleMoves_1AceMoves() {
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.SPADES   , 1  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.SPADES   , 3  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.SPADES   , 4  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.SPADES   , 5  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.SPADES   , 6  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.SPADES   , 7  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.SPADES   , 8  ));


        I_BoardModel board = new testBoard(map);
        BoardController boardCnt = new testBoardCont(board);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(1, result.size());
    }

    @Test
    void PossibleMoves_6Moves() {
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.SPADES   , 2  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.HEARTS   , 3  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.CLUBS    , 4  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.HEARTS   , 5  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.SPADES   , 6  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.DIAMONDS , 7  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.CLUBS    , 8  ));


        I_BoardModel board = new testBoard(map);
        BoardController boardCnt = new testBoardCont(board);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(6, result.size());

    }

    @Test
    void PossibleMoves_5Moves(){
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( E_CardSuit.DIAMONDS , 4  ));
        map.put(BUILDSTACK3,new Card( E_CardSuit.SPADES   , 5  ));
        map.put(BUILDSTACK5,new Card( E_CardSuit.HEARTS   , 6  ));
        map.put(BUILDSTACK2,new Card( E_CardSuit.SPADES   , 4  ));
        map.put(BUILDSTACK4,new Card( E_CardSuit.HEARTS   , 5  ));
        map.put(BUILDSTACK6,new Card( E_CardSuit.SPADES   , 6  ));
        map.put(BUILDSTACK7,new Card( E_CardSuit.DIAMONDS , 7  ));


        I_BoardModel board = new testBoard(map);
        BoardController boardCnt = new testBoardCont(board);

        List<Move> result = boardCnt.possibleMoves();
        assertEquals(5, result.size());
    }


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

        public testBoard(Map<E_PileID, I_CardModel> map){
            piles = new I_SolitaireStacks[E_PileID.values().length];

            piles[DRAWSTACK.ordinal()] = new DrawStack();
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
