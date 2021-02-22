package Cards;

/**
 * SkipCard has the assigned color
 */
public class SkipCard extends Card{
    public SkipCard(Colors color) {
        super(color);
    }

    public String getVal() {
        return "Skip";
    }
}
