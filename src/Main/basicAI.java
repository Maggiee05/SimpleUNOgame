package Main;

import Cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class basicAI extends Player {

    public basicAI(String name, Game game) {
        super (name, game);
    }

    public Card.Colors pickColorRdm() {
        ArrayList<Card.Colors> colorsList = new ArrayList<>();
        colorsList.add(Card.Colors.BLUE);
        colorsList.add(Card.Colors.RED);
        colorsList.add(Card.Colors.YELLOW);
        colorsList.add(Card.Colors.GREEN);
        colorsList.add(Card.Colors.WILD);
        Card.Colors c = colorsList.get(new Random().nextInt(colorsList.size()));
        return c;
    }

}
