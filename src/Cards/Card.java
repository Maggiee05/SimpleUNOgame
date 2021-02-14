package Cards;

public abstract class Card {
    public enum Colors {
        RED, YELLOW, GREEN, BLUE, WILD;
    }

    Colors color;

    /**
     * Construct card with certain color
     */
    public Card(Colors color) {
        this.color = color;
    }


    public Colors getColor() {
        return this.color;
    }

}
