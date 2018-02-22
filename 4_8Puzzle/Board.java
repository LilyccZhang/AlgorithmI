import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board{
    private final int _dim;
    private final int[] _blocks;
    private int _hamming;
    private int _manhattan;
    private ArrayList<Board> _neighbors;
    private int _blankXIndex;//X coordinate of blank block
    private int _blankYIndex;//Y coordinate of blank block
    private boolean _isGoal;
    public Board(int[][] blocks)
    {
        _dim = blocks.length;
        _blocks =  new int[_dim*_dim];
        for (int i = 0; i < _dim; i++)
        {
            for (int j = 0; j < _dim; j++)
            {
                _blocks[i*_dim + j] = blocks[i][j];
            }
        }
        _hamming = 0;
        _manhattan = 0;
        for (int i = 0; i < _dim; i++)
        {
            for (int j = 0; j < _dim; j++)
            {
                int value = _blocks[i*_dim + j];
                if (value == 0)
                {
                    _blankXIndex = i;
                    _blankYIndex = j;
                }

                if ((value != 0) && value != (i*_dim + j  + 1))
                {
                    _hamming++;
                    int xIndex = (value - 1)/_dim;
                    int yIndex = (value - 1)%_dim;
                    _manhattan += Math.abs(xIndex - i);
                    _manhattan += Math.abs(yIndex - j);
                }
            }
        }
        _isGoal =  true;
        if (_hamming != 0)
            _isGoal = false;
    }

    private Board(int[] blocks)
    {
        _dim = (int)Math.sqrt( blocks.length);
        _blocks =  new int[blocks.length];
        System.arraycopy(blocks, 0, _blocks, 0, blocks.length);

        _hamming = 0;
        _manhattan = 0;
        for (int i = 0; i < _dim; i++)
        {
            for (int j = 0; j < _dim; j++)
            {
                int value = _blocks[i*_dim + j];
                if (value == 0)
                {
                    _blankXIndex = i;
                    _blankYIndex = j;
                }

                if ((value != 0) && value != (i*_dim + j  + 1))
                {
                    _hamming++;
                    int xIndex = (value - 1)/_dim;
                    int yIndex = (value - 1)%_dim;
                    _manhattan += Math.abs(xIndex - i);
                    _manhattan += Math.abs(yIndex - j);
                }
            }
        }
        _isGoal =  true;
        if (_hamming != 0)
            _isGoal = false;
    }
    public int dimension()
    {
        return _dim;
    }
    public int hamming()
    {
        return _hamming;
    }
    public int manhattan()
    {
        return _manhattan;
    }
    public boolean isGoal()
    {
        return _isGoal;
    }
    public Board twin()
    {
        int[] twinBlocks = new int[_dim*_dim];
        System.arraycopy(_blocks, 0, twinBlocks, 0, _blocks.length);
         
        if (twinBlocks[0] != 0 && twinBlocks[1] != 0)
        {
            swap(twinBlocks, 0, 1);
        }
        else
        {
            swap(twinBlocks, _dim*_dim - 2, _dim*_dim - 1);
        }
        return new Board(twinBlocks);
    }
    private void swap(int[] a,  int i, int j)
    {
        int tmp  = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public boolean equals(Object y)
    {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        
        Board that = (Board)y;
        if (this.dimension() != that.dimension())
            return false;
        if (!Arrays.equals(this._blocks, that._blocks))
            return false;
        return true;
    }
    public Iterable<Board> neighbors()
    {
        _neighbors = new ArrayList<Board>();
        int[] neighborBlocks = new int[_dim*_dim];
        System.arraycopy(_blocks, 0, neighborBlocks, 0, _blocks.length);

        if (_blankXIndex > 0)
        {
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, (_blankXIndex - 1)*_dim + _blankYIndex);
            _neighbors.add(new Board(neighborBlocks));
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, (_blankXIndex - 1)*_dim + _blankYIndex);
        }
        if (_blankXIndex < _dim - 1)
        {
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, (_blankXIndex + 1)*_dim + _blankYIndex);
            _neighbors.add(new Board(neighborBlocks));
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, (_blankXIndex + 1)*_dim + _blankYIndex);
        }
        if (_blankYIndex > 0)
        {
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, _blankXIndex*_dim + _blankYIndex - 1);
            _neighbors.add(new Board(neighborBlocks));
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, _blankXIndex*_dim + _blankYIndex - 1);
        }

        if (_blankYIndex < _dim - 1)
        {
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, _blankXIndex*_dim + _blankYIndex + 1);
            _neighbors.add(new Board(neighborBlocks));
            swap(neighborBlocks, _blankXIndex*_dim + _blankYIndex, _blankXIndex*_dim + _blankYIndex + 1);
        }
        return _neighbors;
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append(_dim + "\n");
        for (int i = 0; i < _dim; i++)
        {
            for (int j = 0; j < _dim; j++)
            {
                string.append(String.format("%2d  ", _blocks[i*_dim+j]));
            }
            string.append('\n');
        }
        return string.toString();
    }

    public static void main(String[] args)
    {
        In in = new In("8Puzze.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        StdOut.println(initial.dimension());
        StdOut.println(initial.toString());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.twin().toString());
        for (Board board : initial.neighbors())
            StdOut.println(board.toString());
    }
}
