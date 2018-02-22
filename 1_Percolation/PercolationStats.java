import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] m_percolatiosProbility;
    private final double m_mean;
    private final double m_stddev;
    private final double m_confidenceLo;
    private final double m_confidenceHi;
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (trials <= 0)
        {
            throw new IllegalArgumentException();
        }
        m_percolatiosProbility = new double[trials];
        for (int i = 0; i < trials; i++)
        {
            Percolation T = new Percolation(n);
            int p, q;
            while(!T.percolates())
            {
                p = StdRandom.uniform(n) + 1;
                q = StdRandom.uniform(n) + 1;
                T.open(p, q);
            }
            m_percolatiosProbility[i] = T.numberOfOpenSites()*1.0/(n*n);
        }
        m_mean = StdStats.mean(m_percolatiosProbility);
        m_stddev = StdStats.stddev(m_percolatiosProbility);
        m_confidenceLo = m_mean - CONFIDENCE_95*m_stddev/Math.sqrt(trials);
        m_confidenceHi = m_mean + CONFIDENCE_95*m_stddev/Math.sqrt(trials);
    }
   public double mean()                          // sample mean of percolation threshold
   {
       return m_mean;
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return m_stddev;
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return m_confidenceLo;
   }
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return m_confidenceHi;
   }

   public static void main(String[] args)        // test client (described below)
   {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        System.out.print("mean:\t\t\t\t\t");  
        System.out.print("= "+stats.mean()+"\n");  
        System.out.print("stddev:\t\t\t\t\t");  
        System.out.print("= "+stats.stddev()+"\n");  
        System.out.print("95% confidence interval\t\t\t");  
        System.out.print("= "+stats.confidenceLo()+",");  
        System.out.print(stats.confidenceHi()+"\n");  
   }
}