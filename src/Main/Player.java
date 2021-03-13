package Main;

import Cards.*;

import java.util.ArrayList;

/**
 * The player class represents the current player
 */
public class Player {
    /**
     * Array list holding cards as player's hand
     */
    private ArrayList<Card> hand;
    /**
     * The game
     */
    protected Game game;
    /**
     * Player's name
     */
    private String name;
    /**
     * Boolean variables to determine action need
     */
    private boolean skip_;
    private boolean drawTwo_;
    private boolean drawFour_;

    /**
     * Constructor for a player
     * @param name of the player
     * @param game that is ongoing
     */
    public Player(String name, Game game) {
        hand = new ArrayList<Card>();
        this.game = game;
        this.name = name;

        skip_ = false;
        drawTwo_ = false;
        drawFour_ = false;
    }

    /**
     * Assigning 7 cards to each player at the beginning of the game
     */
    public void assignCards() {
        Player player;
        for (int i = 0; i < game.getPlayerNum(); i++) {
            //System.out.println(player);
            for (int j = 0; j < 7; j++) {
                player = game.getPlayers().get(i);
                Card card = game.getDeck().getTopCard();
                player.getHand().add(card);
            }
        }
    }

    /**
     * Draw one Card from the deck
     * @return the card that was drew
     */
    public Card drawOneCard() {
        //If the draw pile is empty, update the deck
        if (game.getDeck().isEmpty()) {
            game.updateDeck();
        }
        //Draw the top card from the deck
        Card todraw = game.getDeck().getTopCard();
        hand.add(todraw);
        return todraw;
    }

    /**
     * Play one card from the hand
     * @param card that the player plays out
     */
    public void playOneCard(Card card) {
        hand.remove(card);
        game.discardPile_add(card);
        game.setPrevColor(card.getColor());
    }

    /**
     * Find the valid card in hand
     * @return the card that is valid to play, null if no valid card
     */
    public Card findValidCard() {
        for (Card cardInHand:hand) {
            if (game.isValid(cardInHand)) {
                return cardInHand;
            }
        }
        return null;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    /*-----------------------------------------------------------------*/
    /* Handler for the flags */
    /*-----------------------------------------------------------------*/

    public boolean skipped() {
        return skip_;
    }

    public boolean drawTwo() {
        return drawTwo_;
    }

    public boolean drawFour() {
        return drawFour_;
    }

    public void setSkipped(boolean flag) {
        skip_ = flag;
    }

    public void setDrawTwo(boolean flag) {
        drawTwo_ = flag;
    }

    public void setDrawFour(boolean flag) {
        drawFour_ = flag;
    }


}
