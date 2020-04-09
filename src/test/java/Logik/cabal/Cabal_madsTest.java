package Logik.cabal;

import Logik.cabal.Cabal_mads;
import Logik.cabal.internals.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Cabal_madsTest {

    @Test
    void turnCard() {
    }

    @Test
    void getTurnedCard() {
    }

    @Test
    void initialize() {
        final int acePileNum = 4;
        final int columnNum = 7;

        Cabal_mads cabal = new Cabal_mads();
        cabal.initialize();

        assertEquals(cabal.getAcesPile().length, acePileNum);
        assertEquals(cabal.getColumns().length, columnNum);

        for (int i = 0; i < acePileNum; i++) {
            assertNotNull(cabal.getAcesPile()[i]);
        }

        for (int i = 0; i < columnNum; i++) {
            assertEquals(cabal.getColumns()[i].size(), 7 - i);
            for (int j = 0; j < cabal.getColumns()[i].size(); j++) {
                assertNotNull(cabal.getColumns()[i].getAt(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }

    @Test
    void initializeAfterAssigns() {
        final int acePileNum = 4;
        final int columnNum = 7;

        Cabal_mads cabal = new Cabal_mads();
        cabal.initialize();
        cabal.turnCard();
        cabal.turnCard();

        CardStack stack1 = new CardStack();
        CardStack stack2 = new CardStack();
        stack1.addToStack(new Card(E_CardSuit.CLUBS, E_CardRank.FIVE, true));
        stack2.addToStack(new Card(E_CardSuit.HEARTS, E_CardRank.FOUR, true));
        cabal.getColumns()[1].add(stack1);
        cabal.getColumns()[4].add(stack2);

        List<I_CardModel> cards = new ArrayList<>();
        cards.add(new Card(E_CardSuit.CLUBS, E_CardRank.ACE));
        cabal.getAcesPile()[E_CardSuit.CLUBS.ordinal()].addToStack(cards);

        cabal.initialize();

        assertEquals(cabal.getAcesPile().length, acePileNum);
        assertEquals(cabal.getColumns().length, columnNum);

        for (int i = 0; i < acePileNum; i++) {
            assertNotNull(cabal.getAcesPile()[i]);
        }

        for (int i = 0; i < columnNum; i++) {
            assertEquals(cabal.getColumns()[i].size(), 7 - i);
            for (int j = 0; j < cabal.getColumns()[i].size(); j++) {
                assertNotNull(cabal.getColumns()[i].getAt(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }
}