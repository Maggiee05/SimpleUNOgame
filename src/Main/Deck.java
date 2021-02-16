package Main;

import Cards.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The deck class represents the draw pile
 * Holding cards for players to draw from
 */
public class Deck {
    /**
     * The deck holding cards
     */
    public ArrayList<Card> deck;
    /**
     * The game
     */
    private Game game;

    /**
     * Construct a deck of cards with 108 cards:
     *      Four WildCard, Four WildDrawFour
     *      For each color in red, yellow, green and blue:
     *          Having one 0 NumberCard, two 1-9 NumberCard, two SkipCard, two ReverseCard, two DrawTwoCard
     */
    public Deck(Game game) {
        this.game = game;
        deck = new ArrayList<>();
        Card.Colors[] values = {Card.Colors.RED, Card.Colors.YELLOW, Card.Colors.GREEN, Card.Colors.BLUE};

        //two 1-9 cards in four colors
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 10; j++) {
                deck.add(new NumberCard(values[i], j));
                deck.add(new NumberCard(values[i], j));
            }
        }

        for (int i = 0; i < 4; i++) {
            //each color has one 0 NumberCard
            deck.add(new NumberCard(values[i], 0));
            //each color has two SkipCards
            deck.add(new SkipCard(values[i]));
            deck.add(new SkipCard(values[i]));
            //each color has two ReverseCards
            deck.add(new ReverseCard(values[i]));
            deck.add(new ReverseCard(values[i]));
            //each color has two DrawTwoCards
            deck.add(new DrawTwoCard(values[i]));
            deck.add(new DrawTwoCard(values[i]));
            //four WildCards in total
            deck.add(new WildCard());
            //four WildDrawFourCards in total
            deck.add(new WildDrawFourCard());
        }

        shuffleDeck();
    }

    /**
     * Shuffle the deck. Needed when start a new game or draw pile is empty
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Get the top card of the draw pile
     * @return the top card from the draw pile
     */
    public Card getTopCard() {
        Card topCard = deck.get(0);
        deck.remove(0);
        return topCard;
    }

    /**
     * Shuffle deck for a number card on top
     * For the purpose of picking number card when update the deck
     */
    public void checkTopNumberCard() {
        Card topCard = deck.get(0);
        while (!(topCard instanceof NumberCard)) {
            shuffleDeck();
            topCard = deck.get(0);
        }
    }

    /**
     * @return true if the draw pile is empty, false otherwise
     */
    public boolean isEmpty() {
        return (deck.size() == 0);
    }

    /**
     * Add card to the deck, called when the draw pile is empty and need to create a new deck
     */
    public void addToDeck(Card card) {
        deck.add(card);
    }

    /**
     * @return the size of the deck
     */
    public int getDeckSize() {
        return deck.size();
    }

}
