package model.cabal;

import model.cabal.internals.*;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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

        Board cabal = new Board();
        cabal.initialize();

        assertEquals(cabal.getSuitPile().length, acePileNum);
        assertEquals(cabal.getBuildStacks().length, columnNum);

        for (int i = 0; i < acePileNum; i++) {
            assertNotNull(cabal.getSuitPile()[i]);
        }

        for (int i = 0; i < columnNum; i++) {
            assertEquals(cabal.getBuildStacks()[i].size(), 7 - i);
            for (int j = 0; j < cabal.getBuildStacks()[i].size(); j++) {
                assertNotNull(cabal.getBuildStacks()[i].getAt(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }

    @Test
    void initializeAfterAssigns() {
        final int acePileNum = 4;
        final int columnNum = 7;

        Board cabal = new Board();
        cabal.initialize();
        cabal.turnCard();
        cabal.turnCard();

        CardStack stack1 = new CardStack(new Card(E_CardSuit.CLUBS, 5));
        CardStack stack2 = new CardStack(new Card(E_CardSuit.HEARTS, 4));
        cabal.getBuildStacks()[1].add(stack1);
        cabal.getBuildStacks()[4].add(stack2);

        List<I_CardModel> cards = new ArrayList<>();
        new CardStack( new Card(E_CardSuit.CLUBS, 1) )
                .moveTo(
                        cabal.getSuitPile()[E_CardSuit.CLUBS.ordinal()]
                )
        ;

        cabal.initialize();

        assertEquals(cabal.getSuitPile().length, acePileNum);
        assertEquals(cabal.getBuildStacks().length, columnNum);

        for (int i = 0; i < acePileNum; i++) {
            assertNotNull(cabal.getSuitPile()[i]);
        }

        for (int i = 0; i < columnNum; i++) {
            assertEquals(cabal.getBuildStacks()[i].size(), 7 - i);
            for (int j = 0; j < cabal.getBuildStacks()[i].size(); j++) {
                assertNotNull(cabal.getBuildStacks()[i].getAt(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }

}