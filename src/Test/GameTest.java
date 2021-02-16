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
    private Player player1, player2;
    private Player player3, player4;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck(game);
        player1 = new Player("player1", game);
        player2 = new Player("player2", game);
        player3 = new Player("player3", game);
        player4 = new Player("player4", game);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        game.newGame(players);
    }

    /**
     * Test if the deck is shuffled when resetting the game
     */
    @Test
    public void testReset() {
        Deck deck1 = new Deck(game);
        game.reset();
        Deck deck2 = new Deck(game);
        assertNotEquals(deck1, deck2);
    }

    /**
     * Test if certain new players are added to the game
     */
    @Test
    public void testAddPlayer() {
        Player p1 = new Player("p1", game);
        Player p2 = new Player("p2", game);
        Player p3 = new Player("p3", game);
        ArrayList<Player> ps = new ArrayList<Player>() {
            {add(p1);
            add(p2);
            add(p3);}};
        game.addPlayer(ps);
        //System.out.println(game.getPlayers());
        assertEquals(7, game.getPlayers().size());
    }


    /**
     * Test if the flag for missing a turn takes effect
     */
    @Test
    public void testMissTurn() {
        int oldSize = player1.getHand().size();
        game.setCurPlayer(player1);
        // using skip card
        player1.setSkipped(true);
        game.missTurn();
        assertEquals(oldSize, player1.getHand().size());
        // using DrawTwo card
        game.setCurPlayer(player1);
        Card c1 = new NumberCard(Card.Colors.RED, 4);
        Card c2 = new DrawTwoCard(Card.Colors.RED);
        player1.getHand().add(c1);
        player2.getHand().add(c2);
        player1.playOneCard(c1);
        game.playCard();
    }

    /**
     * Test if player correctly draw the number of cards
     */
    @Test
    public void testDrawAction() {
        int oldSize = player1.getHand().size(); // should be 7
        game.setCurPlayer(player1);
        game.drawAction(5);
        assertEquals(oldSize+5, player1.getHand().size());
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
        assertNotEquals(null, test_player1.findValidCard());
        assertNotEquals(null, test_player2.findValidCard());
        assertEquals(null, test_player3.findValidCard());
        assertNotEquals(null, test_player4.findValidCard());

        test_pool.playOneCard(test_pool.getHand().get(0));
        //The discard pile now have RED SKIPCARD on top
        assertEquals(null, test_player1.findValidCard());
        assertNotEquals(null, test_player2.findValidCard());
        assertNotEquals(null, test_player3.findValidCard());
        assertNotEquals(null, test_player4.findValidCard());
    }

    /**
     * Test if the iterator of player is working
     * Should be 0 ~ #total players
     */
    @Test
    public void testGetPlayerIter() {
        //4 players in total, iter 0~3
        //current iterator is 0
        assertEquals(3, game.getPlayerIter(0)); //previous=3
        assertEquals(1, game.getPlayerIter(1)); //next=1
    }

    @Test
    public void testStateChange() {
        //test reverse
        System.out.println(players);
        int old_iter = game.getPlayerIter(1);
        game.stateChange(new ReverseCard(Card.Colors.BLUE));
        int new_iter = game.getPlayerIter(1);
        assertNotEquals(old_iter, new_iter);
        assertEquals(players.size(), old_iter+new_iter);
        //test wild
        //is possible to be the same if the player declared the same color
        System.out.println(game.getPrevColor());
        game.stateChange(new WildCard());
        System.out.println(game.getPrevColor());
        //test draw two
        game.stateChange(new DrawTwoCard(Card.Colors.BLUE));
        int next = game.getPlayerIter(1);
        assertTrue(players.get(next).drawTwo());
    }


    @Test
    public void testUpdateDeck() {
        int old_size = game.getDeck().getDeckSize();
        for (int i = 0; i < old_size; i++) {
            Card c = player1.drawOneCard();
            player1.playOneCard(c);
        }
        int empty_size = game.getDeck().getDeckSize();
        game.updateDeck();
        int updated_size = game.getDeck().getDeckSize();

        assertEquals(0,empty_size);
        assertEquals(old_size, updated_size);
    }


    /**
     * Test if split draw works. The previous and next player draws one card
     */
    @Test
    public void testSplitDraw() {
        game.splitDraw();
        int prev = game.getPlayerIter(0);
        int next = game.getPlayerIter(1);
        int curr = game.getPlayerIter(2);
        assertEquals(players.get(curr).getHand().size()+1, players.get(prev).getHand().size());
        assertEquals(players.get(curr).getHand().size()+1, players.get(next).getHand().size());
    }
}