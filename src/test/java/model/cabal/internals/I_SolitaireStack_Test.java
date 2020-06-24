package model.cabal.internals;

import control.AbstractBoardController;
import data.I_InputDTO;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.RefBoard;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.List;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.*;

public class I_SolitaireStack_Test {
    public static class testBoardController extends AbstractBoardController {
        public testBoardController(AbstractMap.SimpleImmutableEntry<I_BoardModel, I_InputDTO> util) {
            super(util.getKey(), util.getValue());
        }
    }

    private E_CardSuit rotSuit(int i ){
        for (E_CardSuit e: E_CardSuit.values()) {
            if(e.ordinal() == i)
                return e;
        }
        return null;
    }
    private  Map<String, List<I_CardModel>> createMap(){
        // What is the top cards of the rows, anything undeclared is empty list.
        Map<String, List<I_CardModel>> map = new HashMap<>();
        ArrayList<I_CardModel> list = new ArrayList<>();
        list.add(new Card( HEARTS     , 1 ));
        list.add(new Card( HEARTS     , 2 ));
        list.add(new Card( HEARTS     , 3 ));
        map.put(SUITSTACKHEARTS.name(), list);

        list = new ArrayList<>();
        list.add(new Card( SPADES     , 1 ));
        list.add(new Card( SPADES     , 2 ));
        list.add(new Card( SPADES     , 3 ));
        map.put(SUITSTACKSPADES.name(), list);

        list = new ArrayList<>();
        list.add(new Card( CLUBS     , 1 ));
        list.add(new Card( CLUBS     , 2 ));
        list.add(new Card( CLUBS     , 3 ));
        map.put(SUITSTACKCLUBS.name(), list);

        list = new ArrayList<>();
        list.add(new Card( DIAMONDS     , 1 ));
        list.add(new Card( DIAMONDS     , 2 ));
        list.add(new Card( DIAMONDS     , 3 ));
        map.put(SUITSTACKDIAMONDS.name(), list);

        list = new ArrayList<>();
        list.add(new Card( DIAMONDS     , 4 ));
        list.add(new Card( DIAMONDS     , 5 ));
        list.add(new Card( DIAMONDS     , 6 ));
        map.put(BUILDSTACK1.name(), list);

        list = new ArrayList<>();
        list.add(new Card( HEARTS     , 4 ));
        list.add(new Card( HEARTS     , 5 ));
        list.add(new Card( HEARTS     , 6 ));
        map.put(BUILDSTACK2.name(), list);

        list = new ArrayList<>();
        list.add(new Card( SPADES     , 4 ));
        list.add(new Card( SPADES     , 5 ));
        list.add(new Card( SPADES     , 6 ));
        map.put(BUILDSTACK3.name(), list);

        list = new ArrayList<>();
        list.add(new Card( CLUBS     , 4 ));
        list.add(new Card( CLUBS     , 5 ));
        list.add(new Card( CLUBS     , 6 ));
        map.put(BUILDSTACK4.name(), list);

        list = new ArrayList<>();
        list.add(new Card( DIAMONDS     , 7 ));
        list.add(new Card( DIAMONDS     , 8 ));
        list.add(new Card( DIAMONDS     , 9 ));
        map.put(BUILDSTACK5.name(), list);

        list = new ArrayList<>();
        list.add(new Card( HEARTS     , 7 ));
        list.add(new Card( HEARTS     , 8 ));
        list.add(new Card( HEARTS     , 9 ));
        map.put(BUILDSTACK6.name(), list);

        list = new ArrayList<>();
        list.add(new Card( SPADES     , 7 ));
        list.add(new Card( SPADES     , 8 ));
        list.add(new Card( SPADES     , 9 ));
        map.put(BUILDSTACK7.name(), list);

        list = new ArrayList<>();
        list.add(new Card( SPADES     , 13 ));
        list.add(new Card( CLUBS      , 13 ));
        list.add(new Card( HEARTS     , 13 ));
        map.put(DRAWSTACK.name(), list);
        return map;
    }
    @Test
    void testAllStacksTopCards(){

        Map<String, List<I_CardModel>> map = createMap();
        I_BoardModel board = new RefBoard( map);

        for (E_PileID e: E_PileID.values()) {
            I_SolitaireStacks[] s = board.getPiles();
            I_SolitaireStacks s2 = s[e.ordinal()];
            I_CardModel thisTopCard = s2.getTopCard();
            I_CardModel card;
            switch (e){
                case DRAWSTACK:
                    card = new Card( HEARTS,13 );
                    assertNull(thisTopCard);
                    ((DrawStack)s2).turnCard();
                    assertEquals(card, s2.getTopCard());
                    break;
                case SUITSTACKHEARTS:
                    card = new Card( HEARTS,3 );
                    assertEquals(card, thisTopCard); break;
                case SUITSTACKCLUBS:
                     card = new Card( CLUBS,3 );
                    assertEquals(card, thisTopCard); break;
                case SUITSTACKSPADES:
                     card = new Card( SPADES,3 );
                    assertEquals(card, thisTopCard); break;
                case SUITSTACKDIAMONDS:
                     card = new Card( DIAMONDS,3 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK1:
                     card = new Card( DIAMONDS     , 6 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK2:
                     card = new Card( HEARTS     , 6 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK3:
                     card =new Card( SPADES     , 6 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK4:
                     card = new Card( CLUBS     , 6 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK5:
                     card = new Card( DIAMONDS     , 9 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK6:
                     card = new Card( HEARTS     , 9 );
                    assertEquals(card, thisTopCard); break;
                case BUILDSTACK7:
                     card = new Card( SPADES     , 9 );
                    assertEquals(card, thisTopCard); break;
            }
        }




    }

    @Test
    void testAllStacks_getCard(){

        Map<String, List<I_CardModel>> map = createMap();
        I_BoardModel board = new RefBoard( map);

        for (E_PileID e: E_PileID.values()) {
            I_SolitaireStacks[] s = board.getPiles();
            I_SolitaireStacks s2 = s[e.ordinal()];
            I_CardModel thisCard = s2.getCard( s2.size() - 1 );
            I_CardModel card;
            switch (e){
                case DRAWSTACK:
                    card = new Card( HEARTS,13 );
                    ((DrawStack)s2).turnCard();
                    assertEquals(card, s2.getTopCard());
                    break;
                case SUITSTACKHEARTS:
                    card = new Card( HEARTS,3 );
                    assertEquals(card, thisCard);
                    break;
                case SUITSTACKCLUBS:
                    card = new Card( CLUBS,3 );
                    assertEquals(card, thisCard);
                    break;
                case SUITSTACKSPADES:
                    card = new Card( SPADES,3 );
                    assertEquals(card, thisCard);
                    break;
                case SUITSTACKDIAMONDS:
                    card = new Card( DIAMONDS,3 );
                    assertEquals(card, thisCard);
                    break;
                case BUILDSTACK1:
                    card = new Card( DIAMONDS     , 6 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK2:
                    card = new Card( HEARTS     , 6 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK3:
                    card =new Card( SPADES     , 6 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK4:
                    card = new Card( CLUBS     , 6 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK5:
                    card = new Card( DIAMONDS     , 9 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK6:
                    card = new Card( HEARTS     , 9 );
                    assertEquals(card, thisCard); break;
                case BUILDSTACK7:
                    card = new Card( SPADES     , 9 );
                    assertEquals(card, thisCard); break;
            }
        }
    }


    @Test
    void getCardMatchTopCard() {
        Map<String, List<I_CardModel>> map = createMap();
        I_BoardModel board = new RefBoard( map);

        for (E_PileID e: E_PileID.values()) {
            I_SolitaireStacks[] s = board.getPiles();
            I_SolitaireStacks s2 = s[e.ordinal()];
            if (e.equals(DRAWSTACK))
                ((DrawStack)s2).turnCard();
            I_CardModel getCard = s2.getCard( s2.size() - 1 );
            I_CardModel topCard = s2.getTopCard();
            assertEquals(getCard, topCard);
        }
    }
}





