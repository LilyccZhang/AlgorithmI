import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints  {
private final int segNum;

private Point[] points;
private ArrayList<LineSegment> lineSegs;
public FastCollinearPoints(Point[] pointsIn)
{
    if (pointsIn == null)
        throw new java.lang.IllegalArgumentException();

    Arrays.sort(pointsIn);
    points = new Point[pointsIn.length];

    if (pointsIn[0] == null)
        throw new java.lang.IllegalArgumentException();
    else
        points[0] = pointsIn[0];

    for (int i = 1; i < pointsIn.length; i++)
    {
        if (pointsIn[i] == null || pointsIn[i - 1] == pointsIn[i])
            throw new java.lang.IllegalArgumentException();
        else
            points[i] = pointsIn[i];
    }

    lineSegs = new ArrayList<LineSegment>();
    for (int j = 0; j < pointsIn.length - 3; j++)
    {
        Arrays.sort(points);
        Arrays.sort(points, pointsIn[j].slopeOrder());
        int cnt;
        Point begin, end, cur;
        double beginSlope;
        for (int l = 1; l < points.length; )
        {
            cnt = 0;
            begin = points[l];
            beginSlope = pointsIn[j].slopeTo(points[l]);
            while (++l < (points.length) && beginSlope == pointsIn[j].slopeTo(points[l]))
            {
                cnt++;
            }
            if (cnt >= 2)
            {
                cur = pointsIn[j];
                if (cur.compareTo(begin) < 0)
                {
                    end = points[l-1];
                    lineSegs.add(new LineSegment(cur, end));
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
         FastCollinearPoints collinear = new FastCollinearPoints(points);
         for (LineSegment segment : collinear.segments()) 
         {
             StdOut.println(segment);
             segment.draw();
         }
         StdDraw.show();
     }
}
