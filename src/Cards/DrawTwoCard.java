package Cards;

/**
 * DrawTwoCard has the assigned color
 */
public class DrawTwoCard extends Card{
    public DrawTwoCard(Colors color) {
        super(color);
    }

    public String getVal() {
        return "+2";
    }
}
