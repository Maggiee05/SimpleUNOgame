package Cards;

/**
 * ReverseCard has the assigned color
 */
public class ReverseCard extends Card{
    public ReverseCard(Colors color) {
        super(color);
    }

    public String getVal() {
        return "Reverse";
    }
}
