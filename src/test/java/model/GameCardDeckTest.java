package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCardDeckTest {

    @Test
    void getInstance() {
        var deck = GameCardDeck.getInstance();

        assertNotNull(deck);
        assertEquals(52, deck.size());
        assertSame(deck, GameCardDeck.getInstance());
    }
}