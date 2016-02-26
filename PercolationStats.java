import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double mean;
    private double std;
    private double upperCon;
    private double lowerCon;
    private double[] fraction;


    public PercolationStats(int N, int T) {
        // perform T times expriments on an N-by-N grid
        if (N < 1 || T < 1) {
            throw 
            new java.lang.IllegalArgumentException("N, T must be bigger than 0");
        }

        fraction = new double[T];
        for (int t = 0; t < T; t++) {
            Percolation percolation = new Percolation(N);
            for (int c = 0; c < N * N; c++) {
                int openSite = (int) (StdRandom.uniform() * N * N);
                int i = openSite / N + 1, j = openSite % N + 1;
                while (percolation.isOpen(i, j)) {
                    openSite = (int) (StdRandom.uniform() * N * N);
                    i = openSite / N + 1;
                    j = openSite % N + 1;
                }
                percolation.open(i, j);
                if (percolation.percolates()) {
                    fraction[t] = ((double) c) / (N * N);
                    break;
                }

            }
        }
        mean = StdStats.mean(fraction);
        std = StdStats.stddev(fraction);
        lowerCon = mean - 1.96 * std / Math.sqrt(T + 0.0);
        upperCon = mean + 1.96 * std / Math.sqrt(T + 0.0);


    }
    public double mean() {
        return mean;
    }
    public double stddev() {
        return std;
    }

    public double confidenceLo() {
        return lowerCon;
    }
    public double confidenceHi() {
        return upperCon;
    }


    public static void main(String[] args) {   // test client (described below)
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), 
            Integer.parseInt(args[1]));
        System.out.println("mean                   =" + ps.mean());
        System.out.println("stddev                 =" + ps.stddev());
        System.out.println("95% confidence interval=" + 
            ps.confidenceLo() + ", " + ps.confidenceHi());


    }
}