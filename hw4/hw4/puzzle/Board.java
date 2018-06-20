package hw4.puzzle;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Board implements WorldState {

    private int[][] tiles;
    private int[][] goal;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        goal = new int[size()][size()];
        for(int i = 0; i < size(); i++) {
            for(int j = 0; j < size(); j++) {
                goal[i][j] = i * size() + j + 1;
            }
        }
        goal[size() - 1][size() - 1] = 0;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if((i < 0 || i >= size()) || (j < 0 || j >= size())) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return tiles.length;
    }

    /**
     * Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        Stack<WorldState> neighbor = new Stack<>();
        int[] position = find(0);
        if(position[0] > 0) {
            neighbor.add(down(position));
        }
        if(position[0] < size() - 1) {
            neighbor.add(up(position));
        }
        if(position[1] > 0) {
            neighbor.add(right(position));
        }
        if(position[1] < size() - 1) {
            neighbor.add(left(position));
        }
        return neighbor;
    }

    public Board down(int[] position) {
        int[][] newTile = new int[size()][size()];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newTile[i][j] = tileAt(i, j);
            }
        }
        newTile[position[0]][position[1]] = newTile[position[0] - 1][position[1]];
        newTile[position[0] - 1][position[1]] = 0;

        return new Board(newTile);
    }

    public Board up(int[] position) {
        int[][] newTile = new int[size()][size()];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newTile[i][j] = tileAt(i, j);
            }
        }
        newTile[position[0]][position[1]] = newTile[position[0] + 1][position[1]];
        newTile[position[0] + 1][position[1]] = 0;

        return new Board(newTile);
    }

    public Board right(int[] position) {
        int[][] newTile = new int[size()][size()];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newTile[i][j] = tileAt(i, j);
            }
        }
        newTile[position[0]][position[1]] = newTile[position[0]][position[1] - 1];
        newTile[position[0]][position[1] - 1] = 0;

        return new Board(newTile);
    }

    public Board left(int[] position) {
        int[][] newTile = new int[size()][size()];
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newTile[i][j] = tileAt(i, j);
            }
        }
        newTile[position[0]][position[1]] = newTile[position[0]][position[1] + 1];
        newTile[position[0]][position[1] + 1] = 0;

        return new Board(newTile);
    }

    public int[] find(int target) {
        for(int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if(tileAt(i, j) == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Compute the Hamming distance:
     * The number of tiles in the wrong position
     */
    public int hamming() {
        int dis = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if(tileAt(i, j) != goal[i][j] && tileAt(i, j) != 0) {
                    dis += 1;
                }

            }
        }
        return dis;
    }

    /**
     * Compute the manhattan distance:
     * sum of the vertical and horizontal distance
     * from the tiles to their goal positions
     */
    public int manhattan() {
        int dis = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if(tileAt(i, j) != 0) {
                    int x = (tileAt(i, j) - 1) / size();
                    int y = tileAt(i, j) % size() - 1;
                    if(y < 0) {
                        y += size();
                    }
                    dis += (Math.abs(x - i) + Math.abs(y - j));
//                    System.out.println("(" + x + ", " + y + ")");
                }
            }
        }
        return dis;

    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
//        System.out.println(this.toString() + " dis: " +manhattan());
        return manhattan();
    }

    /**
     * Returns true if this board's tile values
     * are the same position as y's
     */
    public boolean equals(Object y) {
        if(y == null) {
            return false;
        }
        int[][] curr = ((Board) y).tiles;
        for (int i = 0; i < size(); i++) {
            for(int j = 0; j < size(); j++) {
                if(curr[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
