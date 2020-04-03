package Logik;

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
            assertEquals(cabal.getAcesPile()[i].size(), 0);
        }

        for (int i = 0; i < columnNum; i++) {
            assertEquals(cabal.getColumns()[i].size(), 7 - i);
        }

        assertEquals(cabal.getCardPile().size(), 24); //24 cards left in a deck when cabal is prepared
        assertEquals(cabal.getTurnedPile().size(), 0);
    }
}