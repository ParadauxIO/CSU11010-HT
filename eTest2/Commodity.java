package exam;

/**
 * Child Class for CSU11010-HT eTest 2021
 * <p>
 * @author Rían Errity
 * @since 23/04/2021 DDMMYYYY
 */
public class Commodity extends MyAssets {

    private int totalShares;

    public Commodity(String symbol, double price) {
        super(symbol, 0, price);
        this.totalShares = 0;
    }

    @Override
    public double marketVal() {
        return totalShares * super.getCurPrice();
    }

    public void purchase(int amount, double price) {
        this.totalShares += amount;
        super.setTotalCost((super.getTotalCost() + price) * amount);
    }

    @Override
    public String toString() {
        return String.format("%s ( %d share%s, $ %.2f total cost )", super.getSymbol(), this.totalShares, this.totalShares == 1 ? "" : "s",
                super.getTotalCost());
    }
}
