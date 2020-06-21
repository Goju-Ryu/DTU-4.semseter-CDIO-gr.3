package model.cabal;

import control.BoardController;
import data.InputDTO;
import data.InputSimDTO;
import data.MockBoard;
import model.GameCardDeck;
import model.Move;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @AfterEach
    void closeDeck(){
        GameCardDeck.getInstance().close();
    }

    @Test
    void constructor() {
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 2; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);

        assertEquals(7, board.getPile(E_PileID.BUILDSTACK7).size());
        assertEquals(1, board.getPile(E_PileID.BUILDSTACK1).size());
        assertEquals(24, board.getPile(E_PileID.DRAWSTACK).size());
        assertEquals(0, board.getPile(E_PileID.SUITSTACKHEARTS).size());
    }

    @Test
    void isStackComplete2() { //TODO Actually test the method it says it does

        Map<String, I_CardModel> map = new HashMap<>();

        int i = 1;
        for (E_PileID e: E_PileID.values()) {

            if(i > 1 && i < 6) {
                i++;
                continue;
            }

            map.put( e.toString() , new Card(E_CardSuit.SPADES, i++) );
        }

        I_BoardModel board = new Board(map);

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
    void isStackComplete() { //TODO Actually test the method it says it does
        Map<String, I_CardModel> map = new HashMap<>();
        for (int i = 0; i <= 7; i++) {
            map.put("BUILDSTACK" + i, new Card(E_CardSuit.SPADES, i+1));
        }
        map.put("BUILDSTACK1", new Card(E_CardSuit.SPADES, 1));

        I_BoardModel board = new Board(map);
        InputDTO inputSim = new InputDTO("hej");
        //Todo: the error is probably here, try and figure out why it does not print
        System.out.println("sflvknsflvk"+map);
        board.turnCard(inputSim.getUsrInput());

        if (board.canMove(E_PileID.BUILDSTACK1, E_PileID.SUITSTACKSPADES)) {
            System.out.println("The move is legal");
        }
        var testData = inputSim.getUsrInput();
        board.move(E_PileID.BUILDSTACK1, E_PileID.SUITSTACKSPADES, testData);

        System.out.println(board.getPile(E_PileID.BUILDSTACK1));
    }

    @Test
    void getPile() {
    }

    @Test
    void getPiles() {
    }

    @Test
    void turnCard() {
    }

    @Test
    void getTurnedCard() {
    }

    @Test
    void move() {
    }

    @Test
    void canMove() {

        I_CardModel buildStacks[] = {
                new Card(SPADES, 3),
                new Card(HEARTS, 3),
                new Card(CLUBS, 10),
                new Card(HEARTS, 10),
                new Card(SPADES, 10),
                new Card(CLUBS, 10),
                new Card(HEARTS, 8)
        };
        I_CardModel aceStacks[] = {
                null,
                null,
                null,
                null
        };
        I_CardModel drawStack[] = {
                new Card(HEARTS, 1),
                new Card(SPADES, 1),
                new Card(CLUBS, 1),
                new Card(DIAMONDS, 1),
                new Card(HEARTS, 2),
                new Card(CLUBS,2)
        };

        I_BoardModel board = createBoard( drawStack, aceStacks, buildStacks );
        InputSimDTO in = new InputSimDTO(board);
        System.out.println("from DRAWSTACK " + drawStack[drawStack.length-1] + " to BUILDSTACK " + buildStacks[1]);

        Move m = new Move(DRAWSTACK, BUILDSTACK2, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

        m = new Move(DRAWSTACK, BUILDSTACK1, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

        m = new Move(DRAWSTACK, SUITSTACKDIAMONDS, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

        m = new Move(DRAWSTACK, SUITSTACKCLUBS, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

        m = new Move(DRAWSTACK, SUITSTACKSPADES, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

        m = new Move(DRAWSTACK, SUITSTACKHEARTS, 1, false , false,"");
        board.move(m.moveFromStack(),m.moveToStack(),in.getUsrInput());

    }

    @Test
    void addPropertyChangeListener() {
    }

    @Test
    void removePropertyChangeListener() {
    }

    // todo note that this only tests heartstack, this should be exended to test all implementations of suitstack, even if they are identical
    private SuitStack createSuitStack(int elements){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(HEARTS,i+1);
            suitStack.add(card);
        }

        return suitStack;
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
    private I_BoardModel createBoard(I_CardModel[] drawstack, I_CardModel[] aceStack, I_CardModel[] buildstacks ){

        List<List<I_CardModel>> listAceStacks = new ArrayList<>();
        int i = 0;
        for (E_PileID e: E_PileID.values()) {
            if(e == BUILDSTACK1 || e == BUILDSTACK2 ||e == BUILDSTACK3 ||e == BUILDSTACK4 ||e == BUILDSTACK5 ||e == BUILDSTACK6 || e== BUILDSTACK7) {
                continue;
            }
            if(e== DRAWSTACK)
                continue;

            ArrayList<I_CardModel> newArr = new ArrayList<>();
            I_CardModel c = aceStack[i++];
            if( c != null)
                newArr.add(aceStack[i++]);

            listAceStacks.add(newArr);
        }

        List<List<I_CardModel>> listBuildstacks = new ArrayList<>();
        i = 0;
        for(E_PileID e: E_PileID.values()){
            if(!(e == BUILDSTACK1 || e == BUILDSTACK2 ||e == BUILDSTACK3 ||e == BUILDSTACK4 ||e == BUILDSTACK5 ||e == BUILDSTACK6 || e== BUILDSTACK7))
                continue;


            ArrayList<I_CardModel> newArr = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                newArr.add(new Card());
            }
            newArr.add(buildstacks[i++]);
            listBuildstacks.add(newArr);
        }

        Map<E_PileID, List<I_CardModel>> map =  new HashMap<>();
        map.put(DRAWSTACK, new ArrayList<>(Arrays.asList(drawstack)));

        // in tests created with this method, the Order of card suits here are
        // important for the test to succed
        map.put(SUITSTACKHEARTS     , listAceStacks.get(0));
        map.put(SUITSTACKDIAMONDS   , listAceStacks.get(1));
        map.put(SUITSTACKCLUBS      , listAceStacks.get(2));
        map.put(SUITSTACKSPADES     , listAceStacks.get(3));

        map.put(BUILDSTACK1  , listBuildstacks.get(0));
        map.put(BUILDSTACK2  , listBuildstacks.get(1));
        map.put(BUILDSTACK3  , listBuildstacks.get(2));
        map.put(BUILDSTACK4  , listBuildstacks.get(3));
        map.put(BUILDSTACK5  , listBuildstacks.get(4));
        map.put(BUILDSTACK6  , listBuildstacks.get(5));
        map.put(BUILDSTACK7  , listBuildstacks.get(6));
        return new MockBoard(map);
    }
}