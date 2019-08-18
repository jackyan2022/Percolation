import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
 
public class PercolationStats {
    private final double meanVal;
    private final double stddevVal;
    private final double halfInterval;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        double[] samples = new double[trials];
        final int times = trials;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int openSite = StdRandom.uniform(n * n) + 1;
                int row, col;
                if (openSite % n == 0) {
                    row = openSite / n;
                    col = n;
                } else {
                    row = openSite / n + 1;
                    col = openSite % n;
                }
                percolation.open(row, col);
            }
            double num = n * n;
            samples[i] = percolation.numberOfOpenSites() / num;
        }
        meanVal = StdStats.mean(samples);
        stddevVal = StdStats.stddev(samples);
        halfInterval = 1.96 * stddevVal / java.lang.Math.sqrt(times);
    }
    
    public double mean() {
        return meanVal;
    }
    
    public double stddev() {
        return stddevVal;
    }
    
    public double confidenceLo() {
        return meanVal - halfInterval;
    }
    
    public double confidenceHi() {
        return meanVal + halfInterval;
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, times);
        double mean = percolationStats.mean();
        double stddev = percolationStats.stddev();
        double conLo = percolationStats.confidenceLo();
        double conHi = percolationStats.confidenceHi();
        StdOut.printf("mean%20s= %f\n", " ", mean);
        StdOut.printf("stddev%18s= %f\n", " ", stddev);
        StdOut.printf("95%% confidence interval = [%f, %f]", conLo, conHi);
    }
}
