package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Cards.*;
import Main.*;

import java.util.ArrayList;

class PlayerTest {
    private Game game;
    private Deck deck;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player player1, player2;
    private Player player1_, player2_;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = game.getDeck();
        //System.out.println(deck.getDeckSize());
        player1 = new Player("player1", game);
        player2 = new Player("player2", game);
        players.add(player1);
        players.add(player2);
        //System.out.println(players.size());
        game.addPlayer(players);
        player1.assignCards(); //assign cards at the beginning of the game, only once needed
        player1_ = game.getPlayers().get(0);
        player2_ = game.getPlayers().get(1);

    }

    /**
     * Test if each player is assigned 7 cards at the beginning
     */
    @Test
    public void testAssignCards() {
        assertEquals(7, player1_.getHand().size());
        assertEquals(7, player2_.getHand().size());
    }

    /**
     * Test if DrawOneCard remove the cards from the deck as rules
     * Test if PlayOneCard remove the cards from the player's hand as rules
     */
    @Test
    public void testOneCard() {
        //testing DrawOneCard
        for (int i = 0; i < 10; i++) {
            player1_.drawOneCard();
            player2_.drawOneCard();
        }
        //Remove the assigned 7 cards for each player, and the 10 cards drew above for each
        int curDeckSize = 108 - 7*2 - 10*2;
        assertEquals(curDeckSize, game.getDeck().getDeckSize());
        //Each player should have 17 cards in hand, 7 assigned, 10 drew above
        assertEquals(17, player1_.getHand().size());
        assertEquals(17, player2_.getHand().size());


        //testing PlayOneCard
        for (int i = 0; i < 5; i++) {
            player1_.playOneCard(player1_.getHand().get(0));
            player2_.playOneCard(player2_.getHand().get(0));
        }
        //Each player plays 5 cards
        assertEquals(12, player1_.getHand().size());
        assertEquals(12, player2_.getHand().size());
    }

    /**
     * Test if checking valid follows the rules
     */
    @Test
    public void testCanPlay() {
        Card card1 = new NumberCard(Card.Colors.BLUE, 2);
        Card card2 = new NumberCard(Card.Colors.YELLOW, 4);
        Card card3 = new WildCard();
        Card card4 = new NumberCard(Card.Colors.RED, 2);
        Card card5 = new ReverseCard(Card.Colors.BLUE);

        Player test_player1= new Player("testP1", game);
        Player test_player2= new Player("testP2", game);
        Player test_player3= new Player("testP3", game);
        Player test_player4= new Player("testP4", game);

        //test_player1 has BlUE2, YELLOW4
        test_player1.getHand().add(card1);
        test_player1.getHand().add(card2);
        //test_player2 has WILDCARD
        test_player2.getHand().add(card3);
        //test_player3 has RED2
        test_player3.getHand().add(card4);
        //test_player4 has BLUE REVERSE
        test_player4.getHand().add(card5);

        test_player1.playOneCard(test_player1.getHand().get(0)); // test_player1 now has YELLOW4
        //The discard pile should have BLUE2 on top
        assertEquals(null, test_player1.findValidCard());
        assertNotEquals(null, test_player2.findValidCard());
        assertNotEquals(null, test_player3.findValidCard());
        assertNotEquals(null, test_player4.findValidCard());
    }
}