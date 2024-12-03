import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;

    /**
     * Create a board from an n-by-n array of tiles.
     *
     * @param tiles n-by-n array of tiles
     */
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], n);
        }
    }

    /**
     * String representation of the board.
     *
     * @return String representation of the board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
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
                    // vertical distance + horizontal distance
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
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }

        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    /**
     * Iterable of all neighboring board positions. Here, a neighbor board is a
     * board that can be obtained by exchanging 0 and one of its neighboring
     * tiles.
     *
     * @return Iterable of all neighboring board positions
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int blankRow = -1, blankCol = -1;

        // 1. Find the blank tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }

        // 2. Swap the blank tile with its neighbors
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int exchangeRow = blankRow + dir[0];
            int exchangeCol = blankCol + dir[1];
            if (exchangeRow >= 0 && exchangeRow < n && exchangeCol >= 0 && exchangeCol < n) {
                int[][] newTiles = copyTiles();
                swap(newTiles, blankRow, blankCol, exchangeRow, exchangeCol);
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
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++)
            newTiles[i] = Arrays.copyOf(tiles[i], n);
        return newTiles;
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
        int[][] newTiles = copyTiles();
        if (newTiles[0][0] != 0 && newTiles[0][1] != 0) {
            swap(newTiles, 0, 0, 0, 1);
        } else {
            swap(newTiles, 1, 0, 1, 1);
        }
        return new Board(newTiles);
    }

    // unit testing
    public static void main(String[] args) {
        int[][] initial = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };

        Board board = new Board(initial);
        System.out.println(board);
        System.out.println("Dimension: " + board.dimension());
        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is Goal?: " + board.isGoal());

        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        System.out.println("Twin:");
        System.out.println(board.twin());

        int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goal);
        System.out.println("Goal board is goal?: " + goalBoard.isGoal());
    }
}