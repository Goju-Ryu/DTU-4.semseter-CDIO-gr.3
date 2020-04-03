package Logik;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Cabal_madsTest {

    @org.junit.jupiter.api.Test
    void turnCard() {
    }

    @org.junit.jupiter.api.Test
    void getTurnedCard() {
    }

    @org.junit.jupiter.api.Test
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
                assertNotNull(cabal.getColumns()[i].get(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }

    @org.junit.jupiter.api.Test
    void initializeAfterAssigns() {
        final int acePileNum = 4;
        final int columnNum = 7;

        Cabal_mads cabal = new Cabal_mads();
        cabal.initialize();

        cabal.turnCard();
        cabal.turnCard();
        cabal.getColumns()[1]
                .get(0)
                .moveTo(cabal
                        .getColumns()[4]
                        .get(0));

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
                assertNotNull(cabal.getColumns()[i].get(j));
            }
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }
}