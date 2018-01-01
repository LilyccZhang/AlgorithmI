import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF m_WQUF;
    private WeightedQuickUnionUF m_WQUFWithoutBottom;
    private boolean[][] m_sites;
    private final int m_order;
    private final int m_topRoot;
    private final int m_bottomRoot;
    private int m_numOfOpenSites;
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        m_order = n;
        m_WQUF = new WeightedQuickUnionUF(n*n+2);
        m_WQUFWithoutBottom = new WeightedQuickUnionUF(n*n+1);
        m_topRoot = n * n;
        m_bottomRoot = n * n + 1;
        m_sites = new boolean[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                m_sites[i][j] = false;
            }
        }
        m_numOfOpenSites = 0;
    }
    public void open(int row, int col)
    {
        if (isOpen(row, col))
            return;
        
        int i = row - 1;
        int j = col - 1;
        
        m_sites[i][j] = true;
        m_numOfOpenSites++;
        int index = i*m_order+j;
        
        if (index >= 0 && index < m_order)
        {
            m_WQUF.union(index, m_topRoot);
            m_WQUFWithoutBottom.union(index, m_topRoot);
        }
        if (index >= m_order*(m_order - 1) && index < m_order*m_order)
        {
            m_WQUF.union(index, m_bottomRoot);
        }
        if (i >= 1 && m_sites[i-1][j])
        {
            m_WQUF.union(index-m_order, index);
            m_WQUFWithoutBottom.union(index-m_order, index);
        }
        if (i < m_order-1 && m_sites[i+1][j])
        {
            m_WQUF.union(index+m_order, index);
            m_WQUFWithoutBottom.union(index+m_order, index);
        }
        if ((j >= 1) && m_sites[i][j-1])
        {
            m_WQUF.union(index-1, index);
            m_WQUFWithoutBottom.union(index-1, index);
        }
        if (j < m_order - 1 && m_sites[i][j+1])
        { 
            m_WQUF.union(index+1, index);
            m_WQUFWithoutBottom.union(index+1, index);
        }
    }
    
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || row > m_order)
        {
            throw new IllegalArgumentException();
        }
        if (col < 1 || col > m_order)
        {
            throw new IllegalArgumentException();
        }
        return m_sites[row - 1][col - 1];
    }
    
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > m_order)
        {
            throw new IllegalArgumentException();
        }
        if (col < 1 || col > m_order)
        {
            throw new IllegalArgumentException();
        }
        return m_WQUFWithoutBottom.connected((row-1)*m_order+col-1, m_topRoot);
    }
    
    public int numberOfOpenSites()
    {
        return m_numOfOpenSites;
    }
    public boolean percolates()
    {
        return m_WQUF.connected(m_topRoot, m_bottomRoot);
    }
}