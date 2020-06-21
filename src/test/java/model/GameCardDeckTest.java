package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCardDeckTest {

    @Test
    void getInstance() {
        var deck = new GameCardDeck();

        assertNotNull(deck);
        assertEquals(52, deck.size());
    }
}