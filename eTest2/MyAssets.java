package exam;

/**
 * Child Class for CSU11010-HT eTest 2021
 * <p>
 * @author Rían Errity
 * @since 23/04/2021 DDMMYYYY
 */
public abstract class MyAssets implements Portfolio {

    private final String symbol;
    private double totalCost;
    private double curPrice;

    protected MyAssets(String symbol, double totalCost, double curPrice) {
        this.symbol = symbol;
        this.totalCost = totalCost;
        this.curPrice = curPrice;
    }

    @Override
    public double profit() {
        return marketVal() - totalCost;
    }

    public void setCurrentPrice(double curPrice) {
        this.curPrice = curPrice;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurPrice() {
        return curPrice;
    }
}
