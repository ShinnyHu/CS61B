package hw2;

import java.util.Random;

public class PercolationFactory {

    private int[] count;
    private Random random;

    public PercolationFactory(int T, int N) {
        count = new int[T];
        random = new Random();
        for (int i = 0; i < T; i++) {
            count[i] = simulation(N);
        }
    }

    public Percolation make(int N) {
        return new Percolation(N);
    }

    public int simulation(int N) {
        Percolation p = make(N);
        while(!p.percolates()) {
            p.open(random.nextInt(N), random.nextInt(N));
        }
        return p.numberOfOpenSites();
    }

    public int[] result() {
        return count;
    }
}
