import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver{
    private boolean _isSolvable;
    private SearchNode _lastNode;
    
    public Solver(Board initial)
    {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial));
        minPQTwin.insert(new SearchNode(initial.twin()));
        
        SearchNode node, nodeTwin;
        while (true)
        {
            node = minPQ.delMin();
            nodeTwin = minPQTwin.delMin();
            if  (node._board.isGoal())
            {
                _lastNode = node;
                _isSolvable = true;
                break;
            }
            if (nodeTwin._board.isGoal())
            {
                _lastNode = nodeTwin;
                _isSolvable = false;
                break;
            }
            for (Board board : node._board.neighbors())
            {
                if ((node._preNode == null) || !board.equals(node._preNode._board))
                    minPQ.insert(new SearchNode(board, node));
            }
            for (Board board : nodeTwin._board.neighbors())
            {
                if ((nodeTwin._preNode == null) || !board.equals(nodeTwin._preNode._board))
                    minPQTwin.insert(new SearchNode(board, nodeTwin));
            }
        }
    }
    public boolean isSolvable()
    {
        return _isSolvable;
    }

    public int moves()
    {
        if (!isSolvable())
            return -1;
        return _lastNode._moves;
    }

    public Iterable<Board> solution()
    {
        if (isSolvable() == false)
            return null;
        
        Stack<Board> boards = new Stack<Board>();
        SearchNode node = _lastNode;
        while (node != null)
        {
            boards.push(node._board);
            node = node._preNode;
        }
        return boards;
    }
    public static void main(String[] args)
    {
        
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class SearchNode implements Comparable<SearchNode>{
        private final int _moves;
        private final int _priority;
        private final SearchNode _preNode;
        private final Board _board;

        public SearchNode(Board board)
        {
            if (board == null)
                throw new java.lang.IllegalArgumentException();
            _moves = 0;
            _preNode = null;
            _board = board;
            _priority =  _board.manhattan();
        }
        public SearchNode(Board board, SearchNode preSearchNode)
        {
            if (board == null || preSearchNode == null)
                throw new java.lang.IllegalArgumentException();
            _moves = preSearchNode._moves + 1;
            _board = board;
            _preNode = preSearchNode;
            _priority = _board.manhattan()+ _moves;
        }
        public int compareTo(SearchNode that)
        {
            if (this._priority == that._priority)
                return this._board.hamming() - that._board.hamming();
            return this._priority - that._priority;
        }
    }
}



