package hw4.puzzle;

import hw4.algs4.*;


import java.util.HashSet;
import java.util.Set;


public final class Solver {

    private MinPQ<Node> path = new MinPQ<>();
    private Stack<WorldState> sol = new Stack<>();
    private Set<WorldState> marked = new HashSet<>();
    private int totalmov = 0;
    public int enqueue = 0;
    public WorldState initial;


    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        int moves = 0;
        Node ini = new Node(null, initial, moves);
        this.initial = initial;
        solve(ini, moves);
        sol.push(initial);
    }

    public Node solve(Node node, int moves) {
        marked.add(node.curr());
        if(node.curr().isGoal()) {
            sol.push(node.curr());
            return node;
        }
        for (WorldState neighbor : node.curr().neighbors()) {
            if(neighbor.isGoal()) {
                totalmov += 1;
                sol.push(neighbor);
                return node;
            }
            if(!neighbor.equals(node.prev()) && !marked.contains(neighbor)) {

                Node n = new Node(node, neighbor, moves + 1);
                path.insert(n);
                enqueue += 1;

            }
        }

        // Some outputs to help debug:
//        System.out.println("\nenque: " + enqueue);
//        for (Node n : path) {
//            System.out.print(n.curr() + ",diff:" + (n.cost() - n.moves) + ",m:" + n.moves + "  ");
//        }
//        System.out.println();
//        System.out.println("moves: " + moves + ", pick: " + path.min().curr());

        Node min = path.delMin();
        Node word = solve(min, min.moves);
        if(word != null && word.curr() != initial) {
            totalmov += 1;
            sol.push(word.curr());
            return word.prev;
        }

        return null;
    }


    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return totalmov;
    }

    /**
     *  Returns a sequence of WorldStates from the initial WorldState
     *  to the solution.
     */
    public Iterable<WorldState> solution() {
        return sol;
    }
}
