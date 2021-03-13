package Test;

import Cards.Card;
import Main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AITest {
    private Game game;
    private ArrayList<Player> players = new ArrayList<Player>();
    private strategicAI testP;

    @BeforeEach
    public void setUp() {
        game = new Game();
        testP = new strategicAI("testP", game);
        ArrayList<Player> players = new ArrayList<>();
        players.add(testP);
        game.addPlayer(players);
        game.setCurPlayer(testP);
        testP.assignCards();
    }

    /**
     * Test if the color picked by basic AI is a valid color
     */
    @Test
    public void testBasicAI() {
        basicAI testP = new basicAI("p1", game);
        for (int i = 0; i < 10; i++) {
            assertNotNull(testP.pickColorRdm());
        }
    }

    /**
     * Test if the color picked by strategic player is the most frequent color
     * in the AI's hand. (WILD color are not valid)
     */
    @Test
    public void testStrategicAI() {
        Integer[] ct = {0,0,0,0,0};
        Card.Colors color = testP.pickColorSt();
        for (Card card:testP.getHand()) {
            if (card.getColor() == color) {
                ct[0]++;
            }
            if (card.getColor() == Card.Colors.BLUE) {
                ct[1]++;
            }
            if (card.getColor() == Card.Colors.GREEN) {
                ct[2]++;
            }
            if (card.getColor() == Card.Colors.YELLOW) {
                ct[3]++;
            }
            if (card.getColor() == Card.Colors.RED) {
                ct[4]++;
            }
        }

        assertTrue(ct[0] >= ct[1]);
        assertTrue(ct[0] >= ct[2]);
        assertTrue(ct[0] >= ct[3]);
        assertTrue(ct[0] >= ct[4]);
    }

}
