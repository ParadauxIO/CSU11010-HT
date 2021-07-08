public class Swap {

    private int numberOne;
    private int numberTwo;

    public Swap(int numberOne, int numberTwo) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
    }

    public void swap() {
        // Method 1
        numberOne += numberTwo;
        numberTwo = numberOne - numberTwo;
        numberOne -= numberTwo;
    }

    public int getNumberOne() {
        return numberOne;
    }

    public int getNumberTwo() {
        return numberTwo;
    }

    public void setNumberOne(int numberOne) {
        this.numberOne = numberOne;
    }

    public void setNumberTwo(int numberTwo) {
        this.numberTwo = numberTwo;
    }
}
