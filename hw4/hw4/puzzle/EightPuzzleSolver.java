package hw4.puzzle;

import hw4.algs4.*;

/**
* Author: Josh Hug
*/

public class EightPuzzleSolver {
    /***********************************************************************
     * Test routine for your Solver class. Uncomment and run to test
     * your basic functionality.
    **********************************************************************/
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        long begin = System.currentTimeMillis();
        Solver solver = new Solver(initial);
        long end=System.currentTimeMillis();



        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }
        System.out.println("Total time: " + (end - begin)
                + "; total enqueue: " + solver.enqueue);
    }
}
