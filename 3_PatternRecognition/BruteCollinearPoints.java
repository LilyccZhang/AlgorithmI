import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegs;
    private final int segNum;

    public BruteCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new java.lang.IllegalArgumentException();

        Arrays.sort(points);
        if (points[0] == null)
            throw new java.lang.IllegalArgumentException();

        for (int i = 1; i < points.length; i++)
        {
            if (points[i] == null || points[i - 1] == points[i])
                throw new java.lang.IllegalArgumentException();
        }

          lineSegs = new ArrayList<LineSegment>();
        int num = points.length;
        double slope;
        for (int i = 0; i < num - 3; i++)
        {
            for (int j = i + 1; j < num; j++)
            {
                for (int k = j + 1; k < num; k++)
                {
                    slope = points[i].slopeTo(points[j]);
                    if (slope == points[i].slopeTo(points[k]))
                    {
                        for (int l = k + 1; l < num; l++)
                        {
                            if (slope == points[i].slopeTo(points[l]))
                            {
                                lineSegs.add(new LineSegment(points[i], points[l]));
                            }
                        }
                    }
                }
            }
        }
        segNum = lineSegs.size();
    }

     public int numberOfSegments()
     {
         return segNum;
    }

     public LineSegment[] segments()
    {
        LineSegment[] segms = new LineSegment[segNum];
        int i = 0;
        for (LineSegment seg : lineSegs)
            segms[i++] = seg;
        return segms;
    }

    public static void main(String[] args) 
    {
        // read the n points from a file
        In in = new In("input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) 
        {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) 
        {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
