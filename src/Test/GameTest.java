package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Cards.*;
import Main.*;

import java.util.ArrayList;

class GameTest {
    private Game game;
    private Deck deck;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player player1, player1_;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck(game);
        player1 = new Player("player1", game);
        players.add(player1);
        game.addPlayer(players);
        player1.assignCards(); //assign cards at the beginning of the game, only once needed
        player1_ = game.getPlayers().get(0);
    }

    /**
     * Test if the deck is shuffled when resetting the game
     */
    @Test
    public void testReset() {
        Deck deck1 = new Deck(game);
        game.reset();
        Deck deck2 = new Deck(game);
        assertEquals(false,deck1 == deck2);
    }

    /**
     * Test if the flag for missing a turn takes effect
     */
    @Test
    public void testMissTurn() {
        int oldSize = player1_.getHand().size();
        game.setCurPlayer(player1_);
        // using skip card
        player1_.setSkipped(true);
        game.missTurn();
        assertEquals(oldSize, player1_.getHand().size());
        // using DrawTwo card
        player1_.setDrawTwo(true);
        game.missTurn();
        assertEquals(oldSize+2, player1_.getHand().size());
        // using WildDrawFour card
        player1_.setDrawTwo(false);
        player1_.setDrawFour(true);
        game.missTurn();
        assertEquals(oldSize+6, player1_.getHand().size());//already drew two before, 6 in total
    }

    /**
     * Test if player correctly draw the number of cards
     */
    @Test
    public void testDrawAction() {
        int oldSize = player1_.getHand().size(); // should be 7
        game.setCurPlayer(player1_);
        game.drawAction(5);
        assertEquals(oldSize+5, player1_.getHand().size());
    }

    @Test
    public void testIsValid() {
        Card c1 = new NumberCard(Card.Colors.RED, 3);
        Card c2 = new SkipCard(Card.Colors.RED);

        Card card1 = new NumberCard(Card.Colors.BLUE, 3);
        Card card2 = new NumberCard(Card.Colors.RED, 9);
        Card card3 = new SkipCard(Card.Colors.BLUE);
        Card card4 = new WildDrawFourCard();

        Player test_pool= new Player("testPool", game);

        Player test_player1= new Player("testP1", game);
        Player test_player2= new Player("testP2", game);
        Player test_player3= new Player("testP3", game);
        Player test_player4= new Player("testP4", game);

        //test_pool has RED3, YELLOW SKIPCARD
        test_pool.getHand().add(c1);
        test_pool.getHand().add(c2);

        test_player1.getHand().add(card1); // test_player1 has BLUE3
        test_player2.getHand().add(card2); // test_player3 has RED9
        test_player3.getHand().add(card3); // test_player4 has BLUE SKIP
        test_player4.getHand().add(card4); // test_player3 has WILDDRAWFOUR

        test_pool.playOneCard(test_pool.getHand().get(0));
        //The discard pile now have RED3 on top
        assertTrue(test_player1.canPlay());
        assertTrue(test_player2.canPlay());
        assertFalse(test_player3.canPlay());
        assertTrue(test_player4.canPlay());

        test_pool.playOneCard(test_pool.getHand().get(0));
        //The discard pile now have RED SKIPCARD on top
        assertFalse(test_player1.canPlay());
        assertTrue(test_player2.canPlay());
        assertTrue(test_player3.canPlay());
        assertTrue(test_player4.canPlay());
    }
}