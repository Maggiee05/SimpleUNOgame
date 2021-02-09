package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Cards.*;
import Main.*;

import java.util.Arrays;

class CardTest {
    private Game game;
    private Deck deck;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck(game);
    }

    /**
     * Testing if the cards generated match the card types and colors
     */
    @Test
    void testColorCardMatched() {
        for (int i = 0; i < 108; i++) {
            Card card = deck.getTopCard();
            if (card instanceof WildCard || (card instanceof WildDrawFourCard)) {
                assertEquals(Card.Colors.WILD, card.getColor());
            } else {
                assert(card.getColor() == Card.Colors.RED || card.getColor() == Card.Colors.YELLOW
                        || card.getColor() == Card.Colors.GREEN || card.getColor() == Card.Colors.BLUE);
                if (card instanceof NumberCard) {
                    assert(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).contains(((NumberCard) card).cardNum()));
                }
            }
        }
    }

}