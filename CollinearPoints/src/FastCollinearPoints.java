import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    /**
     * Finds all line segments containing 4 or more points (based on sorting).
     *
     * @param points array of points
     * @throws IllegalArgumentException if <tt>points</tt> is <tt>null</tt>,
     *                                  if any point in <tt>points</tt> is <tt>null</tt>, or if
     *                                  <tt>points</tt>contains a repeated point
     */
    public FastCollinearPoints(Point[] points) {
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

        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        int n = sortedPoints.length;

        for (int p = 0; p < n; p++) {
            /* Set point p as origin */
            Point origin = sortedPoints[p];
            Point[] otherPoints = new Point[n - 1];
            int index = 0;
            for (int i = 0; i < n; i++) {
                if (i != p) {
                    otherPoints[index++] = sortedPoints[i];
                }
            }

            Arrays.sort(otherPoints, origin.slopeOrder());

            int count = 1;
            /* Iterate through other points */
            for (int q = 1; q < n; q++) {
                if (q < otherPoints.length && origin.slopeTo(otherPoints[q - 1]) == origin.slopeTo(otherPoints[q])) {
                    count++;
                } else {
                    // Ensure that we have more than 4 points in a segment, and that the origin point is the smallest
                    // By doing this, we avoid ineligible and duplicate segments
                    if (count >= 3 && origin.compareTo(otherPoints[q - count]) < 0) {
                        foundSegments.add(new LineSegment(origin, otherPoints[q - 1]));
                    }
                    count = 1; // Reset count for next potential segment
                }
            }

            /* Remember to check for the last case! */
            if (count >= 3 && origin.compareTo(otherPoints[otherPoints.length - count]) < 0) {
                foundSegments.add(new LineSegment(origin, otherPoints[otherPoints.length - 1]));
            }
        }

        segments = foundSegments.toArray(new LineSegment[0]);
    }

    /**
     * Get the number of line segments.
     *
     * @return The number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Get the line segments.
     *
     * @return An array of line segments
     */
    public LineSegment[] segments() {
        return segments.clone();
    }
}
