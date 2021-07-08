/**
 * Unit Tests for {@link Line} and {@link Point}
 * @author Rían Errity
 * @since 12/03/2021
 * */
public final class LineTest {

    /**
     * Test, without using a testing framework, the validity of the provided Point.java as well as the
     * constructed Line.java from the UML Diagram.
     * @throws TestFailedException When the output of a given test is not as expected.
     * */
    public static void main(String[] args) throws TestFailedException {
        System.out.println("Point and Line Unit Testing.");

        System.out.println("\n\nTesting Point.java\n---------");
        System.out.println("... Testing invalid point");

        // Ensure the provided Point's IllegalArgument works
        try {
            Point point = new Point(-1, 0);

            // Exception expected from above.
            throw new TestFailedException("Expected IllegalArgumentException from Point creation.");
        } catch (IllegalArgumentException expected) {
            System.out.println("..... Negative x in point satisfies requirements ");
        }

        try {
            Point point = new Point(0, -1);

            // Exception expected from above.
            throw new TestFailedException("Expected IllegalArgumentException from Point creation.");
        } catch (IllegalArgumentException expected) {
            System.out.println("..... Negative y in point satisfies requirements");
        }

        // Testing my Line.java's constructors
        System.out.println("\n\nTesting Line.java\n---------");
        System.out.println("... Constructors + Getters");

        System.out.println(".... Testing explicit point constructor: Line(x1, y1, x2, y2)");
        Line line = new Line(1, 2, 3, 4);

        if (!((line.getP1().getX() == 1 && line.getP1().getY() == 2) && (line.getP2().getX() == 3 && line.getP2().getY() == 4))) {
            throw new TestFailedException("Expected points to be equal.");
        }

        Point p1 = new Point(6, 9);
        Point p2 = new Point(3, 5);

        System.out.println(".... Testing provided point Constructor: Line(p1, p2)");
        line = new Line(p1, p2);
        if (!(line.getP1().equals(p1) && line.getP2().equals(p2))) {
            throw new TestFailedException("Expected points to be equal.");
        }

        System.out.println("... Testing Line#getSlope");
        double slope = line.getSlope();

        if (!fuzzyEqual(slope, 1.33d, 0.01d)) {
            System.out.println("Incorrect slope provided.");
        }

        System.out.println(".... Testing invalid slope (x1 == x2)");
        line = new Line(5, 6, 5, 2);

        try {
            line.getSlope();

            // Exception expected from above.
            throw new TestFailedException("IllegalStateException not thrown with invalid slope.");
        } catch (IllegalStateException expected) {
            // Expected, testing to see if this is thrown.
        }

        System.out.println("... Testing Line printing with explicit points: x1 = 5, y1 = 6, x2 = 5, y2 = 2");
        System.out.printf(".... actual: x1 = %d, x2 = %d, x2 = %d, y2 = %d%n", line.getP1().getX(), line.getP1().getY(),
                line.getP2().getX(), line.getP2().getY());

        System.out.println("\n\nTests completed.");
    }

    public static boolean fuzzyEqual(double value1, double value2, double permittedRange){
        return Math.abs(value1-value2) < permittedRange;
    }

    /**
     * To be thrown when a test fails. (Assertion Failed equivalent)
     * */
    private static final class TestFailedException extends RuntimeException {
        public TestFailedException(String message) {
            super(message);
        }
    }
}
