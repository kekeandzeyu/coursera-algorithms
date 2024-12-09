import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Heuristic goalstate;

    private static class Heuristic implements Comparable<Heuristic> {
        private final Board board;
        private final int moves;
        private final Heuristic previous;
        private final int manhattan;

        public Heuristic(Board board, int moves, Heuristic previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

        public int priority() {
            return moves + manhattan;
        }

        @Override
        public int compareTo(Heuristic other) {
            return Integer.compare(this.priority(), other.priority());
        }
    }

    /**
     * Find a solution to the initial board using the A* algorithm.
     *
     * @param initial The initial board, throw {@code IllegalArgumentException} if {Acode null}
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null.");
        }

        MinPQ<Heuristic> pq = new MinPQ<>();
        MinPQ<Heuristic> twinpq = new MinPQ<>();

        pq.insert(new Heuristic(initial, 0, null));
        twinpq.insert(new Heuristic(initial.twin(), 0, null));

        while (true) {
            goalstate = solveStep(pq);
            if (goalstate != null) break;

            Heuristic twinGoal = solveStep(twinpq);
            if (twinGoal != null) break; // Twin reached goal, original is unsolvable
        }
    }

    /**
     * Solve a single step of the A* algorithm.
     *
     * @param pq The priority queue to solve
     * @return The goal state if found, {@code null} otherwise
     */
    private Heuristic solveStep(MinPQ<Heuristic> pq) {
        if (pq.isEmpty()) return null;

        Heuristic current = pq.delMin();

        if (current.board.isGoal()) {
            return current;
        }

        for (Board neighbor : current.board.neighbors()) {
            if (current.previous == null || !neighbor.equals(current.previous.board)) {
                pq.insert(new Heuristic(neighbor, current.moves + 1, current));
            }
        }
        return null;
    }

    /**
     * is the initial board solvable?
     *
     * @return {@code true} if the initial board is solvable,
     *         {@code false} otherwise
     */
    public boolean isSolvable() {
        return goalstate != null;
    }

    /**
     * Minimum number of moves to solve the initial board.
     *
     * @return Minimum number of moves to solve the initial board,
     *         -1 if unsolvable
     */
    public int moves() {
        return isSolvable() ? goalstate.moves : -1;
    }

    /**
     * Give out the sequence of boards in a shortest solution.
     *
     * @return Sequence of boards, {@code null} if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> solutionStack = new Stack<>();
        Heuristic current = goalstate;
        while (current != null) {
            solutionStack.push(current.board);
            current = current.previous;
        }

        return solutionStack;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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

}