package hw4.puzzle;


public class Node implements Comparable<Node> {

    public int moves;
    private WorldState n;
    public Node prev;

    public Node(Node prev, WorldState curr, int moves) {
        this.prev = prev;
        n = curr;
        this.moves = moves;
    }

    public WorldState prev() {
        if(prev == null) {
            return null;
        }
        return prev.curr();
    }

    public WorldState curr() {
        return n;
    }

    @Override
    public int compareTo(Node o) {
        return this.n.estimatedDistanceToGoal() + this.moves -
                (o.n.estimatedDistanceToGoal() + o.moves);
    }

    public int cost() {
        return this.n.estimatedDistanceToGoal() + this.moves;
    }
}