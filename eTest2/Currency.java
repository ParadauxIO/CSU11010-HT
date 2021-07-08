package exam;

/**
 * Child Class for CSU11010-HT eTest 2021
 * <p>
 * @author Rían Errity
 * @since 23/04/2021 DDMMYYYY
 */
public class Currency implements Portfolio {

    private final double amount;

    public Currency(double amount) {
        this.amount = amount;
    }

    @Override
    public double marketVal() {
        return amount;
    }

    /**
     * @implNote No exchange rate is specified, so there will never be a profit or loss situation for currency.
     * */
    @Override
    public double profit() {
        return 0d;
    }

    @Override
    public String toString() {
        return String.format("Cash ( $ %.2f )", marketVal());
    }
}
