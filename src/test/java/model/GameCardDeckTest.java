package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCardDeckTest {
//TODO make more tests
    @Test
    void getInstance() {
        var deck = new GameCardDeck();

        assertNotNull(deck);
        assertEquals(52, deck.size());
    }

    @Test
    void test() {
        List.of().subList(0, 0);
    }
}