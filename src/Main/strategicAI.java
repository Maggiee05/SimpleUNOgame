package Main;

import Cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class strategicAI extends Player{
    public strategicAI(String name, Game game) {
        super (name, game);
    }

    public Card.Colors pickColorSt() {
        Card.Colors[] values = {Card.Colors.BLUE, Card.Colors.GREEN, Card.Colors.RED, Card.Colors.YELLOW};
        ArrayList<Card.Colors> colorinHand = new ArrayList<>();
        for (Card c : game.getCurPlayer().getHand()) {
            if (c.getColor() != Card.Colors.WILD) {
                colorinHand.add(c.getColor());
            }
        }

        ArrayList<Integer> counts = new ArrayList<>();
        Set<Card.Colors> st = new HashSet<Card.Colors>(colorinHand);
        for (Card.Colors val:values)
            counts.add(Collections.frequency(colorinHand, val));
//        System.out.println(counts);
        int idx = counts.indexOf(Collections.max(counts));

        return values[idx];
    }

}
