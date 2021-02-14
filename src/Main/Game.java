package Main;

import Cards.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    /**
     * The deck where cards are generated
     */
    private Deck deck;
    /**
     * The discard pile where cards played are collected
     */
    private ArrayList<Card> discardPile;
    /**
     * All players that are playing the game
     */
    private ArrayList<Player> players;
    /**
     * Current player
     */
    private Player curPlayer;
    /**
     * Previous player
     */
    private Player prevPlayer;
    /**
     * Direction, 0 for normal direction (small to big index), 1 for reversed
     */
    private int direction;
    /**
     * Iterator of the player
     */
    private int player_iter;
    /**
     * The color declared by player when playing WILD CARDS
     */
    private Card.Colors colorDeclared;
    /**
     * The color declared by previous player when playing Wild and WildDrawFour Card
     */
    private Card.Colors prevColor;
    /**
     *  Boolean variable indicating the end of the game, true if game over
     */
    private boolean gameOver;
    /**
     * The winner of the game
     */
    private Player winner;

    private boolean reverseOnB;

    /**
     * Construct a game with certain attributes initialized
     */
    public Game() {
        deck = new Deck(this);
        discardPile = new ArrayList<Card>();
        players = new ArrayList<Player>();
        curPlayer = null;
        prevPlayer = null;
        direction = 0;
        player_iter = 0;
        colorDeclared = null;
        prevColor = null;
        gameOver = false;
        winner = null;
        reverseOnB = false;
    }

    /**
     * Resetting the game, draw a card from the deck and place it in the discard pile
     * This card should be a number card
     */
    public void reset() {
        deck.checkTopNumberCard();
        Card topCard = deck.getTopCard();
        discardPile_add(topCard);
        prevColor = topCard.getColor();
    }

    /**
     * Starting a new game with certain players
     */
    public void newGame(ArrayList<Player> players) {
        this.players = players;
        //the first in the players array list is the current player
        curPlayer = players.get(0);
        player_iter = 0;
        curPlayer.assignCards();
        reset();
    }

    /**
     * Adding players to the game
     */
    public void addPlayer(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            this.players.add(players.get(i));
        }
    }

    /**
     * Options for player to perform:
     * If the player misses a turn, apply the special action
     * If the current player has valid card, play it
     * If the current player has no valid car, draw a card from the deck,
     *      if the card drew is valid, play it
     *      if not, continue to the next player
     */
    public void perform() {
        player_iter++; // iterator turns to the next player
        if (curPlayer.skipped() || curPlayer.drawTwo() || curPlayer.drawFour()) {
            missTurn();
        } else if (curPlayer.canPlay()) { // has a valid card
            playCard();
        } else  { // has no valid card, draw a card
            Card card = curPlayer.drawOneCard();
            if (isValid(card)) {
                playCard(); // the card drew is valid, play it
            } else {
                return; // the card drew is not valid, continue
            }
        }
        return;
    }

    /**
     * The current player plays the valid card
     * And the game state need to be updated
     */
    public void playCard() {
        Card card = curPlayer.findValidCard();
        curPlayer.playOneCard(card);
        if (card instanceof DrawTwoCard) {
            splitDraw();
        }
        stateChange(card); // update the game state
    }

    /**
     * The current player misses a turn
     * Being used skipCard, WildCard or WildDrawFourCard by previous player
     */
    public void missTurn() {
        if (curPlayer.skipped()) {
            resetFlag();
        }
        if (curPlayer.drawTwo()) {
            drawAction(2);

        }
        if (curPlayer.drawFour()) {
            drawAction(4);
        }
    }

    public void resetFlag() {
        curPlayer.setSkipped(false);
        curPlayer.setDrawTwo(false);
        curPlayer.setDrawFour(false);
    }

    /**
     * Drawing certain number of cards as declared by previous player
     */
    public void drawAction(int num) {
        for (int i = 0; i < num; i++) {
            curPlayer.drawOneCard();
        }
        resetFlag();
    }

    /**
     * Checking if a card is valid to play, current card is valid to play if:
     *      The current card matches either the color or the number or the symbol of the previous card
     *      Otherwise, Wild cards are always valid to play
     * @return true if the card is valid to play, false otherwise
     */
    public boolean isValid(Card currCard) {
        Card prevCard = discardPile.get(discardPile.size() - 1); // getting the previous card

        Card.Colors currColor = currCard.getColor();
        boolean sameColor = (currColor == prevCard.getColor()); // whether the color matches with the precious card

        if (currCard instanceof NumberCard) {
            if (prevCard instanceof NumberCard) { //if both number cards, either same color or same number
                boolean sameNum = ((((NumberCard) currCard).cardNum() == ((NumberCard) prevCard).cardNum()));
                return (sameColor || sameNum);
            } else { //if not, same color
                return sameColor;
            }

        //Action cards, valid if same color or same symbol
        } else if (currCard instanceof SkipCard) {
            return (sameColor || (prevCard instanceof SkipCard));
        } else if (currCard instanceof ReverseCard) {
            return (sameColor || (prevCard instanceof ReverseCard));
        } else if (currCard instanceof DrawTwoCard) {
            return (sameColor || (prevCard instanceof DrawTwoCard));
        }

        return true; //wild cards always true
    }

    /**
     * Changing the state of the player
     * @TODO: declaring color from the player
     */
    public void stateChange(Card card) {
        int iter = getPlayerIter();
        Player nextPlayer = players.get(iter);
        player_iter++;
        if (card instanceof SkipCard) {
            nextPlayer.setSkipped(true);
        } else if (card instanceof ReverseCard) {
            reverse();
        } else if (card instanceof DrawTwoCard) {
            nextPlayer.setDrawTwo(true);
        } else if (card instanceof WildCard) {
            prevColor = declareColor();
            if (reverseOnB) {
                reverse();
                player_iter -= 2;
            }
        } else if (card instanceof WildDrawFourCard) {
            prevColor = declareColor();
            if (reverseOnB) {
                reverse();
                player_iter -= 2;
                nextPlayer = players.get(iter-2);
            }
            nextPlayer.setDrawFour(true);
        }
    }

    /**
     * Declaring the color by the player
     * Needed when playing WildCard or WildDrawFourCard
     * @return the color declared by the player
     */
    public Card.Colors declareColor() {
        return colorDeclared;
    }


    public void setPrevColor(Card.Colors color) {
        prevColor = color;
    }

    public void setPrevPlayer(Player p) {
        prevPlayer = p;
    }

    /**
     * Adding a card to the discard pile when a player plays the card
     */
    public void discardPile_add(Card card) {
        discardPile.add(card);
    }

    /**
     * Reverse the order of the players' turn
     */
    public void reverse() {
        // Reverse the players Arraylist so that the next player become the previous, vice versa
        if (direction == 0) {
            direction = 1;
        } else {
            direction = 0;
        }
    }

    /**
     * Update the deck when the draw pile is empty
     * The top card of the discard pile is saved
     * And the rest of the discard pile are used to create a new deck and shuffled
     */
    public void updateDeck() {
        if (deck.isEmpty()) {
            int size = discardPile.size();
            Card topSaveCard = discardPile.get(size - 1);
            for (int i = 0; i < size - 1; i++) {
                Card rest = discardPile.get(i);
                deck.addToDeck(rest);
            }
            discardPile.clear();
            discardPile.add(topSaveCard);
            deck.shuffleDeck();
        }
    }

    /**
     * Game is over when a player has no cards in his hand
     * The player is the winner, game over
     */
    public void gameOver() {
        for (Player player:players) {
            if (player.getHand().size() == 0) {
                winner = player;
                gameOver = true;
                return;
            }
        }
    }

    /**
     * Get the iterator for player
     * @return the iterator of the next player
     */
    public int getPlayerIter() {
        int iter = player_iter + 1;
        if (direction == 0) { // normal, small to big index
            if (iter == getPlayerNum()) {
                iter = 0;
            }
        } else { // reversed
            iter = player_iter - 1;
            if (iter < 0) {
                iter = getPlayerNum() - 1;
            }
        }
        return iter;
    }

    /**
     * When a draw 2 card is played,
     * the person immediately following and preceding the player draws 1 card
     */
    public void splitDraw() {
        prevPlayer.drawOneCard(); // the person preceding the player draws one card
        int iter = getPlayerIter();
        Player nextPlayer = players.get(iter); // the person following the player draws one card
        nextPlayer.drawOneCard();
    }


    /*-----------------------------------------------------------------*/
    /* Getters */
    /*-----------------------------------------------------------------*/
    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getPlayerNum() {
        return this.players.size();
    }

    public Player getWinner() {
        return winner;
    }

    /**
     * For testing purpose
     */
    public void setCurPlayer(Player p) {
        this.curPlayer = p;
    }

}

