package model.cabal;

import control.BoardController;
import control.BoardControllerSimulated;
import data.I_InputDTO;
import data.InputSimDTO;
import model.GameCardDeck;
import model.Move;
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
import java.lang.reflect.Field;
import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void constructor() {
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map, new GameCardDeck());

        assertEquals(7, board.getPile(E_PileID.BUILDSTACK7).size());
        assertEquals(1, board.getPile(E_PileID.BUILDSTACK1).size());
        assertEquals(24, board.getPile(E_PileID.DRAWSTACK).size());
        assertEquals(0, board.getPile(E_PileID.SUITSTACKHEARTS).size());
    }

    @Test
    void isStackComplete() { //TODO Actually test the method it says it does

        Map<String, I_CardModel> map = new HashMap<>();

        int i = 1;
        for (E_PileID e: E_PileID.values()) {

            if(i > 1 && i < 6) {
                i++;
                continue;
            }

            map.put( e.toString() , new Card(E_CardSuit.SPADES, i++) );
        }
        var deck = new GameCardDeck();
        I_BoardModel board = new Board(map, deck);

        for (E_PileID e: E_PileID.values()) {
            List<I_CardModel> stack = board.getPile(e);
            int size = stack.size();

            // specificly for the ace piles
            if (size == 0){
                boolean acePile = false;
                if(e == E_PileID.SUITSTACKCLUBS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKDIAMONDS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKHEARTS)
                    acePile = true;
                if(e == E_PileID.SUITSTACKSPADES)
                    acePile = true;

                assertTrue(acePile);
                continue;
            }

            I_CardModel c = board.getPile(e).get(size-1);
            assertEquals(map.get(e.toString()).getRank(),c.getRank());
            String mapsSuit = map.get(e.toString()).getSuit().toString();
            boolean SuitMatches = mapsSuit.equals( c.getSuit().toString() );
            assertTrue(SuitMatches);
        }

        for (E_PileID e: E_PileID.values()) {
            switch(e){
                case DRAWSTACK:
                    assertEquals(25,board.getPile(e).size());
                    break;
                case SUITSTACKCLUBS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKDIAMONDS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKHEARTS:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case SUITSTACKSPADES:
                    assertEquals(0,board.getPile(e).size());
                    break;
                case BUILDSTACK1:
                    assertEquals(1,board.getPile(e).size());
                    break;
                case BUILDSTACK2:
                    assertEquals(2,board.getPile(e).size());
                    break;
                case BUILDSTACK3:
                    assertEquals(3,board.getPile(e).size());
                    break;
                case BUILDSTACK4:
                    assertEquals(4,board.getPile(e).size());
                    break;
                case BUILDSTACK5:
                    assertEquals(5,board.getPile(e).size());
                    break;
                case BUILDSTACK6:
                    assertEquals(6,board.getPile(e).size());
                    break;
                case BUILDSTACK7:
                    assertEquals(7,board.getPile(e).size());
            }
        }


        System.out.println("hej");
    }

    @Test
    void make_a_move(){

        // What is the top cards of the rows, anything undeclared is empty list.
        Map<E_PileID, I_CardModel> map = new HashMap<>();
        map.put(BUILDSTACK1,new Card( SPADES    , 3  ));
        map.put(BUILDSTACK2,new Card( HEARTS    , 3  ));
        map.put(BUILDSTACK3,new Card( CLUBS     , 10 ));
        map.put(BUILDSTACK4,new Card( HEARTS    , 10 ));
        map.put(BUILDSTACK5,new Card( SPADES    , 10 ));
        map.put(BUILDSTACK6,new Card( CLUBS     , 10 ));
        map.put(BUILDSTACK7,new Card( HEARTS    , 8  ));

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
        moves.add( new Move( BUILDSTACK5,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move( BUILDSTACK4,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move( BUILDSTACK5,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move( BUILDSTACK3,DRAWSTACK, 1, false , false,"") );
        moves.add( new Move( BUILDSTACK4,DRAWSTACK, 2, false , false,"") );


        GameCardDeck deck = new GameCardDeck();
        I_BoardModel board = new testBoard(map,list,deck);

        testBoardController boardController = new testBoardController(board, deck);
        for (Move m: moves) {
            boardController.makeMove(m);
        }
        System.out.println("asd");
    }

    // todo note that this only tests heartstack, this should be exended to test all implementations of suitstack, even if they are identical
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
    private class testBoard extends AbstractBoardUtility implements I_BoardModel{

        public testBoard(Map<E_PileID, I_CardModel> map , List<I_CardModel> list,GameCardDeck cardDeck){
            deck = cardDeck;
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
            //removeDataFromDeck(list);
            deck.removeAll(list);
            deck.removeAll(map.values());

        }

        public testBoard(Map<E_PileID, I_CardModel> map,GameCardDeck cardDeck){
            deck = cardDeck;
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
            deck.removeAll(map.values());
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
            to.addAll( from.popSubset(originPos) );
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
    private class testBoardController extends BoardControllerSimulated {

        protected I_BoardModel refBoardModel;
        public testBoardController(I_BoardModel refBoard, GameCardDeck deck) {
            super(true);
            refBoardModel = refBoard;
            inputDTO = new InputSimDTO(refBoard,deck);
            boardModel = refBoard;
        }

    }
    private class TestGameCardDeck extends GameCardDeck{
        public TestGameCardDeck(Map<E_PileID, I_CardModel> m){
            super();
            for (E_PileID e : E_PileID.values() ) {
                I_CardModel c = m.get(e);
                if(c!=null)
                    if(c.isFacedUp())
                        super.remove(c);
            }
        }
    }
}