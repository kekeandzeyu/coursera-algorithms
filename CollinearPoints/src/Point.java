import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.y == that.y) {
            if (this.x == that.x) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return +0.0;
            }
        }
        else {
            if (this.x == that.x) {
                return Double.POSITIVE_INFINITY;
            } else {
                return (double) (that.y - this.y) / (that.x - this.x);
            }
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* Either y0 < y1 or if y0 = y1 and x0 < x1 */
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        /* x0 = x1 and y0 = y1 */
        else if ((this.y == that.y) && (this.x == that.x)) {
            return 0;
        }
        else {
            return 1;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            return Double.compare(slope1, slope2);
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        p1.draw();

        Point p2 = new Point(2, 3);
        p2.draw();
        p1.drawTo(p2);
        System.out.println("Slope (p1-p2): " + p1.slopeTo(p2));

        if (p1.compareTo(p2) < 0) {
            System.out.println("p1 < p2");
        }
        else if (p1.compareTo(p2) == 0) {
            System.out.println("p1 = p2");
        }
        else {
            System.out.println("p1 > p2");
        }

        Point p3 = new Point(3, 3);
        p3.draw();
        p1.drawTo(p3);
        System.out.println("Slope (p1-p3): " + p1.slopeTo(p3));

        Comparator<Point> comparator = p1.slopeOrder();
        int comparisonResult = comparator.compare(p2, p3);
        if (comparisonResult < 0) {
            System.out.println("Slope p1->p2 < Slope p1->p3");
        } else if (comparisonResult == 0) {
            System.out.println("Slope p1->p2 = Slope p1->p3");
        } else {
            System.out.println("Slope p1->p2 > Slope p1->p3");
        }
    }
}