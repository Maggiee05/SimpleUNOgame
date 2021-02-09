package Cards;

/**
 * NumberCard has the assigned color and number
 */
public class NumberCard extends Card{
    int cardNumber;

    public NumberCard (Colors color, int number) {
        super(color);
        this.cardNumber = number;
    }

    public int cardNum() {
        return this.cardNumber;
    }

}
