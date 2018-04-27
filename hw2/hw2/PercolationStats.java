package hw2;

public class PercolationStats {

    int N;
    int T;
    int[] result;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        this.result = pf.result();
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for(int i = 0; i < result.length; i++) {
            sum += result[i];
        }
        sum /= (T * N * N);
        return sum;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        double mean = mean();
        for(int i = 0; i < result.length; i++) {
            sum += (mean - (double)result[i] / (N * N)) * (mean - (double) result[i] / (N * N));
        }
        return Math.sqrt(sum / (T - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int N = 10;
        int T = 1000;
        PercolationFactory factory = new PercolationFactory(T, N);
        PercolationStats stats = new PercolationStats(N, T, factory);
        System.out.println("mean: " + stats.mean());
        System.out.println("95% confidence interval: [" + stats.confidenceLow() + ", " + stats.confidenceHigh() + "]");
    }

}
