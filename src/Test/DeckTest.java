package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Cards.*;
import Main.*;

import java.util.Arrays;

class DeckTest {
    private Game game;
    private Deck deck;
    private Card temp_card;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck(game);
    }

    /**
     * Testing if the deck generated 108 cards at the beginning
     */
    @Test
    void testDeckConstructor() {
        assertEquals(deck.getDeckSize(), 108);
    }

    /**
     * Testing if the deck size decreases when top card is drew
     */
    @Test
    void testGetTopCard() {
        for (int i = 0; i < 10; i++) {
            deck.getTopCard();
        }
        assertEquals(98, deck.getDeckSize());
    }

    /**
     * Testing if adding cards to deck increases the deck size
     */
    @Test
    void testAddToDeck() {
        for (int i = 0; i < 10; i++) {
            deck.addToDeck(temp_card);
        }
        assertEquals(118, deck.getDeckSize());
    }
}