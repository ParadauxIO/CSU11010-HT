/**
 * Represents a Line in 2D space.
 * @author Rían Errity
 * @since 12/03/2021
 * */
public class Line {

    private final Point p1;
    private final Point p2;

    /**
     * Provided two points, create a line between them.
     * */
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Provided two sets of (x, y) values; create points and create a line between them.
     * */
    public Line(int x1, int y1, int x2, int y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /*
    * Dumb Getters
    * */

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    /**
     * Calculates the slope of the two points that represent the line.
     * @throws IllegalStateException when the points do not have a defined slope. (x1 == x2)
     * */
    public double getSlope() {
        if (p1.getX() == p2.getX()) {
            throw new IllegalStateException("These lines have an invalid slope.\nX Values are both equal to: " + p1.getX());
        }

        return (double) (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }
}
