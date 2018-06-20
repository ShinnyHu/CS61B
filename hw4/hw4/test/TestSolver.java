package hw4.test;
import hw4.algs4.*;
import hw4.puzzle.*;

import java.util.Arrays;
import java.util.List;

/**
 * Local tester for the Solver class. You'll need to uncomment
 * in order to run the tests.
 * Author: Josh Hug
 */
public class TestSolver {
    public static class BoardPuzzleSolution {
        final String name;
        final Board board;
        final int numMoves;
        public BoardPuzzleSolution(String n, Board b, int m) {
            name = n;
            board = b;
            numMoves = m;
        }
    }

    public static class WordPuzzleSolution {
        final String start;
        final String goal;
        final int numMoves;
        final List<String> possibleSolution;
        public WordPuzzleSolution(String s, String g, int m, List<String> ps) {
            start = s;
            goal = g;
            numMoves = m;
            possibleSolution = ps;
        }
    }

    /** Takes as input a word puzzle string and returns a WordPuzzle
     *  object. For example, "kept, tent, kept-kent-tent, 13"
     *  would return a WordPuzzle with start = kept, goal = tent,
     *  numMoves = 2, and possible solution {"kept", "kent", "tent"}.
     *  The last value is not used by this class, and indicates
     *  the number of enqueues used by the reference solution to
     *  solve the puzzle. Returns null if String isn't a valid word puzzle.
     */
    public static WordPuzzleSolution stringToWordPuzzle(String wp) {
        try {
            /* skip comments and blank lines */
            if (wp.charAt(0) == '#') {
                return null;
            }
            String[] tokens = wp.split(",");
            String start = tokens[0].replaceAll("\\s+", "");
            String goal = tokens[1].replaceAll("\\s+", "");
            String solutionString = tokens[2].replaceAll("\\s+", "");
            List<String> possibleSolution = Arrays.asList(solutionString.split("-"));
            int numMoves = possibleSolution.size() - 1;
            return new WordPuzzleSolution(start, goal, numMoves, possibleSolution);
        } catch (RuntimeException e) {
            return null;
        }
    }

// Uncomment once you've written Solver.
    public void testWordPuzzles() {
        In in = new In("input/word_puzzles.txt");
        while (!in.isEmpty()) {
            WordPuzzleSolution wps = stringToWordPuzzle(in.readLine());
            if (wps == null) {
                continue;
            }
            Word w = new Word(wps.start, wps.goal);
            long begin = System.currentTimeMillis();
            Solver s = new Solver(w);
            long end=System.currentTimeMillis();
            System.out.println(wps.start + " -> " + wps.goal + "; total time: " + (end - begin) + "; total enqueue: " + s.enqueue);

        }
    }


 // Uncomment everything in this block once you've written Board.
     public static Board readBoard(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board start = new Board(tiles);
        return start;
    }


    public void test2x2BoardPuzzles() {
        for (int i = 0; i <= 6; i += 1) {
            String pnum = String.format("%02d", i);
            String puzzleName = "input/puzzle2x2-" + pnum + ".txt";
            Board b = readBoard(puzzleName);
            int numMoves = i;
            BoardPuzzleSolution bps = new BoardPuzzleSolution(puzzleName, b, numMoves);

            long begin = System.currentTimeMillis();
            Solver s = new Solver(b);
            long end=System.currentTimeMillis();
            System.out.println("Puzzle: " + puzzleName + "; total time: " + (end - begin) + "; total enqueue: " + s.enqueue);

        }
    }

    public void test3x3BoardPuzzles() {
        for (int i = 0; i <= 30; i += 1) {
            String pnum = String.format("%02d", i);
            String puzzleName = "input/puzzle3x3-" + pnum + ".txt";
            Board b = readBoard(puzzleName);
            int numMoves = i;
            BoardPuzzleSolution bps = new BoardPuzzleSolution(puzzleName, b, numMoves);
            long begin = System.currentTimeMillis();
            Solver s = new Solver(b);
            long end=System.currentTimeMillis();

            System.out.println("Puzzle: " + puzzleName + "; total time: " + (end - begin) + "; total enqueue: " + s.enqueue);

        }
    }


    public void test4x4BoardPuzzles() {
        for (int i = 0; i <= 30; i += 1) {
            String pnum = String.format("%02d", i);
            String puzzleName = "input/puzzle4x4-" + pnum + ".txt";
            Board b = readBoard(puzzleName);
            int numMoves = i;
            BoardPuzzleSolution bps = new BoardPuzzleSolution(puzzleName, b, numMoves);
            long begin = System.currentTimeMillis();
            Solver s = new Solver(b);
            long end=System.currentTimeMillis();

            System.out.println("Puzzle: " + puzzleName + "; total time: " + (end - begin) + "; total enqueue: " + s.enqueue);

        }
    }


    public void testVariousPuzzles() {
        for (int i = 0; i <= 31; i += 1) {
            String pnum = String.format("%02d", i);
            String puzzleName = "input/puzzle" + pnum + ".txt";
            Board b = readBoard(puzzleName);
            int numMoves = i;
            BoardPuzzleSolution bps = new BoardPuzzleSolution(puzzleName, b, numMoves);
            long begin = System.currentTimeMillis();
            Solver s = new Solver(b);
            long end=System.currentTimeMillis();

            System.out.println("Puzzle: " + puzzleName + "; total time: " + (end - begin) + "; total enqueue: " + s.enqueue);

        }
    }

    public static void main(String[] args) {
        TestSolver ts = new TestSolver();
        // ts.testWordPuzzles();
        // ts.test2x2BoardPuzzles();
        // ts.test3x3BoardPuzzles(); // >=27 (StackOverflowError)
        // ts.test4x4BoardPuzzles(); // =28 (StackOverflowError)
        ts.testVariousPuzzles(); // >=28 (StackOverflowError)
    }
}
