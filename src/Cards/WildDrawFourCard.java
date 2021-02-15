package Cards;

/**
 * WildDrawFourCard has the color WILD
 */
public class WildDrawFourCard extends Card {
    public WildDrawFourCard () {
        super(Colors.WILD);
    }

    public String getVal() {
        return "+4";
    }
}
