import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int n;
    private int blankRow, blankCol;

    /**
     * Create a board from an n-by-n array of tiles.
     *
     * @param tiles n-by-n array of tiles
     */
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    /**
     * String representation of the board.
     *
     * @return String representation of the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Board dimension n.
     *
     * @return size n
     */
    public int dimension() {
        return n;
    }

    /**
     * The <em>Hamming distance</em> betweeen a board and the goal board is the
     * number of tiles in the wrong position.
     *
     * @return Number of tiles in the wrong position
     */
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * The <em>Manhattan distance</em> between a board and the goal board is
     * the sum of the Manhattan distances (sum of the vertical and horizontal
     * distance) from the tiles to their goal positions.
     *
     * @return Sum of the Manhattan distances
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int goalRow = (tiles[i][j] - 1) / n;
                    int goalCol = (tiles[i][j] - 1) % n;
                    manhattan += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return manhattan;
    }

    /**
     * Is this board the goal board?
     *
     * @return {@code true} if this board is the goal board,
     * {@code false} otherwise
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Does this board equal y?
     *
     * @param y the other board
     * @return {@code true} if this board equals {@code y}
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board other = (Board) y;
        return n == other.n && Arrays.deepEquals(tiles, other.tiles);
    }

    /**
     * Iterable of all neighboring board positions. Here, a neighbor board is a
     * board that can be obtained by exchanging 0 and one of its neighboring
     * tiles.
     *
     * @return Iterable of all neighboring board positions
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : moves) {
            int newRow = blankRow + move[0];
            int newCol = blankCol + move[1];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                int[][] newTiles = copyTiles();
                swap(newTiles, blankRow, blankCol, newRow, newCol);
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    /**
     * Copy the tiles of the board.
     *
     * @return Copy of the tiles of the board
     */
    private int[][] copyTiles() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    /**
     * Swap tiles at (row1, col1) and (row2, col2).
     */
    private void swap(int[][] arr, int row1, int col1, int row2, int col2) {
        int temp = arr[row1][col1];
        arr[row1][col1] = arr[row2][col2];
        arr[row2][col2] = temp;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles.
     *
     * @return Board
     */
    public Board twin() {
        int[][] twinTiles = copyTiles();
        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            swap(twinTiles, 0, 0, 0, 1);
        } else {
            swap(twinTiles, 1, 0, 1, 1);
        }
        return new Board(twinTiles);
    }

    // unit testing
    public static void main(String[] args) {
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(tiles);

        System.out.println("Board:");
        System.out.println(board);

        System.out.println("Dimension: " + board.dimension());
        System.out.println("Hamming distance: " + board.hamming());
        System.out.println("Manhattan distance: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());

        int[][] tiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board2 = new Board(tiles2);
        System.out.println("Equals: " + board.equals(board2));

        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        System.out.println("Twin:");
        System.out.println(board.twin());
    }
}