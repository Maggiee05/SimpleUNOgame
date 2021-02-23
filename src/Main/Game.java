package Main;

import Cards.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The game class represents the game
 * Implementing the game rules
 */
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
     * Direction, 0 for normal direction (small to big index), 1 for reversed
     */
    private int direction;
    /**
     * Iterator of the player
     */
    private int player_iter;
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
    /**
     * True if the player wants to apply reverse on black rule
     */
    private boolean reverseOnB;
    /**
     * Counter for the number of cards to be drawn
     */
    private int draw_num;

    /**
     * Construct a game with certain attributes initialized
     */
    public Game() {
        deck = new Deck(this);
        discardPile = new ArrayList<Card>();
        players = new ArrayList<Player>();
        curPlayer = null;
        direction = 0; //default normal direction, small to large index
        player_iter = 0;
        prevColor = null;
        gameOver = false;
        winner = null;
        reverseOnB = false;// default reverse on black is mandatory, can be changed
        draw_num = 0;
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
        curPlayer.assignCards(); //only once
        direction = 0;
        draw_num = 0;
        reset();
    }

    /**
     * Adding players to the game
     */
    public void addPlayer(ArrayList<Player> ps) {
        this.players.addAll(ps);
    }

    /**
     * Increase the player iterator
     */
    public void incIter() {
        player_iter = getPlayerIter(1);
    }

    /**
     * The current player plays the card
     * Game state is updated
     */
    public void playCard(Card card) {
        curPlayer.playOneCard(card);
        checkGameOver(curPlayer);
        if (card instanceof DrawTwoCard) {
            draw_num += 2;
            splitDraw();
        } else if (card instanceof WildDrawFourCard) {
            draw_num += 4;
        }
        //stateChange(card); // update the game state
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
        draw_num = 0;
    }

    /**
     * Checking if a card is valid to play, current card is valid to play if:
     *      The current card matches either the color or the number or the symbol of the previous card
     *      Otherwise, Wild cards are always valid to play
     * @return true if the card is valid to play, false otherwise
     */
    public boolean isValid(Card currCard) {
        Card prevCard = getPrevCard();// getting the previous card

        if ((prevCard instanceof WildDrawFourCard) && (!(currCard instanceof WildDrawFourCard)) && (curPlayer.drawFour())) {
            return false;
        }

        if ((prevCard instanceof DrawTwoCard) && (!(currCard instanceof DrawTwoCard)) && (curPlayer.drawTwo())) {
            return false;
        }


        Card.Colors currColor = currCard.getColor();
        boolean sameColor = ((currColor == prevCard.getColor()) || (currColor == prevColor)); // whether the color matches with the previous card

        if (currCard instanceof NumberCard) {
            if (prevCard instanceof NumberCard) { //if both number cards, either same color or same number
                boolean sameNum = (((NumberCard) currCard).cardNum() == ((NumberCard) prevCard).cardNum());
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

        return true;
    }

    /**
     * Changing the state of the player
     */
    public void stateChange(Card card) {
        System.out.println("State changed");
        prevColor = card.getColor();
        if (card instanceof SkipCard) {
            Player nextPlayer = players.get(getPlayerIter(1));
            nextPlayer.setSkipped(true);
            System.out.println(nextPlayer.getName() + " is skipped");
        } else if (card instanceof ReverseCard) {
            reverse();
        } else if (card instanceof DrawTwoCard) {
            Player nextPlayer = players.get(getPlayerIter(1));
            nextPlayer.setDrawTwo(true);
        } else if (card instanceof WildCard) {
            reverseOnBlack();
        } else if (card instanceof WildDrawFourCard) {
            reverseOnBlack();
            Player nextPlayer = players.get(getPlayerIter(1));
            nextPlayer.setDrawFour(true);
        }
    }


    public void setPrevColor(Card.Colors color) {
        prevColor = color;
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
        System.out.println("Direction is changed");
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
    public boolean checkGameOver(Player player) {
        if (player.getHand().size() == 0) {
            winner = player;
            gameOver = true;
        }
        return gameOver;
    }

    /**
     * Get the iterator for player
     * @param turn, 0 for previous, 1 for next player, else for current player
     * @return the iterator of the next player
     */
    public int getPlayerIter(int turn) {
        int iter = player_iter % getPlayerNum();
        //getting previous in normal direction or next in reversed direction
        if ((turn == 0 && direction == 0) || (turn == 1 && direction == 1) ) {
            if (iter - 1 < 0) {
                iter = getPlayerNum() - 1;
            } else {
                iter -= 1;
            }
        //getting next in normal direction or previous in reversed direction
        } else if ((turn == 0 && direction == 1) || (turn == 1 && direction == 0)) {
            if (iter + 1 == getPlayerNum()) {
                iter = 0;
            } else {
                iter += 1;
            }
        }
        return iter;
    }


    /**
     * When a draw 2 card is played,
     * the person immediately following and preceding the player draws 1 card
     */
    public void splitDraw() {
        Player prePlayer = players.get(getPlayerIter(0)); // player preceding
        prePlayer.drawOneCard();
        Player nextPlayer = players.get(getPlayerIter(1)); // player following
        nextPlayer.drawOneCard();
    }

    /**
     * When a black card is played the player of the card can choose to change direction.
     * This also determines who draws if a black draw is played.
     * Variations: Mandatory and optional.
     */
    public void reverseOnBlack() {
        if (reverseOnB) {
            reverse();
        }
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

    public Card.Colors getPrevColor() {
        return prevColor;
    }

    public int getPlayerNum() {
        return this.players.size();
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurPlayer() {
        return curPlayer;
    }
    public int getDirection() {
        return direction;
    }

    public Card getPrevCard() {
        return discardPile.get(discardPile.size() - 1);
    }

    public int getStack() {
        return draw_num;
    }

    /**
     * For testing purpose
     */
    public void setCurPlayer(Player p) {
        this.curPlayer = p;
    }

    public void setReverseOnB(boolean f) {
        reverseOnB = f;
    }



}

