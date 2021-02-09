package Main;

import Cards.*;

import java.util.ArrayList;

public class Player {
    /**
     * Array list holding cards as player's hand
     */
    public ArrayList<Card> hand;
    /**
     * Number of cards in a player's hand
     */
    public int cardsInHand;
    /**
     * The game
     */
    public Game game;

    /**
     * Boolean variables to determine action need
     */
    public boolean skip_;
    public boolean drawTwo_;
    public boolean drawFour_;

    /**
     * Construct a player
     */
    public Player(String name, Game game) {
        hand = new ArrayList<Card>();
        cardsInHand = 0;
        this.game = game;

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
                player.getHand().add(card); // takes one card from top of deck
                player.cardsInHand++;
            }
        }
    }

    /**
     * Draw one Card from the deck
     * @Return the card that was drew
     */
    public Card drawOneCard() {
        //If the draw pile is empty, update the deck
        if (game.getDeck().isEmpty()) {
            game.updateDeck();
        }
        //Draw the top card from the deck
        Card todraw = game.getDeck().getTopCard();
        hand.add(todraw);
        cardsInHand++;
        return todraw;
    }

    /**
     * Play one card from the hand
     */
    public void playOneCard(Card card) {
        hand.remove(card);
        cardsInHand--;
        game.discardPile_add(card);
        game.setPrevColor(card.getColor());
    }

    /**
     * Return true if player has a valid card to play in hand,
     *        false else.
     */
    public boolean canPlay() {
        for (Card cardInHand:hand) {
            if (game.isValid(cardInHand)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find the valid card in hand
     * @Return the card that is valid to play
     */
    public Card findValidCard() {
        Card card = null;
        for (int i = 0; i < cardsInHand; i++) {
            card = hand.get(i);
            if (game.isValid(card)) {
                return card;
            }
        }
        return card;
    }

    public ArrayList<Card> getHand() {
        return hand;
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
