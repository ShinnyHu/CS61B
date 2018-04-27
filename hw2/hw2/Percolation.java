package hw2;

public class Percolation {

    int N;
    private boolean[] open;
    WeightedQuickUnionUF uf;
    private int numOfOpenSites = 0;

    // create N-by-N grid, with all sites initially blocked
    // time proportional to N^2
    public Percolation(int N) {
        this.N = N;
        if(N <= 0) {
            throw new IllegalArgumentException();
        }
        open = new boolean[N * N];
        uf = new WeightedQuickUnionUF(N*N + 2);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int num = xyToNum(row, col);
        if(!valid(num)) {
            throw new IndexOutOfBoundsException();
        }
        if (open[num] == false) {
            numOfOpenSites += 1;
        }
        open[num] = true;
        int[] nearby = nearby(num);
        for (int i : nearby) {
            if(valid(i) && isOpen(i / N, i % N)) {
                uf.union(num, i);
            }
        }

        if(row == 0) {
            uf.union(num, N*N); // top dum node
        }

        if(row == N - 1) {
            uf.union(num, N*N + 1); // bottom dum node
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int num = xyToNum(row, col);
        if(!valid(num)) {
            throw new IndexOutOfBoundsException();
        }
        return open[num];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int num = xyToNum(row, col);
        if(!valid(num)) {
            throw new IndexOutOfBoundsException();
        }
        return uf.connected(num, N*N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N*N, N*N + 1);
    }

    // convert the 2-D coordinates to 1-D number
    public int xyToNum(int row, int col) {
        return col + row * N;
    }

    public int[] nearby(int num) {
        int[] nearby = new int[4];
        nearby[0] = num - 1;
        nearby[1] = num + 1;
        nearby[2] = num - N;
        nearby[3] = num + N;
        return nearby;
    }

    public boolean valid(int num) {
        if(num < N * N && num >= 0) {
            return true;
        }
        return false;
    }

}
