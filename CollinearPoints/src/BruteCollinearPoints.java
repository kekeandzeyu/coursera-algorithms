import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments; // Array of line segments (immutable)

    /**
     * Finds all line segments containing 4 points (brute force).
     *
     * @param points array of points
     * @throws IllegalArgumentException if <tt>points</tt> is <tt>null</tt>,
     *         if any point in <tt>points</tt> is <tt>null</tt>, or if
     *         <tt>points</tt>contains a repeated point
     */
    public BruteCollinearPoints(Point[] points) {
        /* points and point in points cannot be null */
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point cannot be null");
            }
        }

        /* Check for duplicate points */
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }

        /* Find line segments (use ArrayList to dynamically grow) */
        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        int n = sortedPoints.length;

        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                for (int r = q + 1; r < n - 1; r++) {
                    for (int s = r + 1; s < n; s++) {
                        double slopePQ = sortedPoints[p].slopeTo(sortedPoints[q]);
                        double slopePR = sortedPoints[p].slopeTo(sortedPoints[r]);
                        double slopePS = sortedPoints[p].slopeTo(sortedPoints[s]);

                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            foundSegments.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                    }
                }
            }
        }

        // Convert ArrayList to array, use toArray() method to allocate the right size
        segments = foundSegments.toArray(new LineSegment[0]);
    }

    /**
     * Get the number of line segments
     *
     * @return The number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Get the line segments
     *
     * @return An array of line segments
     */
    public LineSegment[] segments() {
        return segments.clone();
    }
}
