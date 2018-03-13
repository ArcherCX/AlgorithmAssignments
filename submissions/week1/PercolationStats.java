import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] trailResults;

    public static void main(String[] args) {
        int n = 200;
        int trials = 100;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials= Integer.parseInt(args[1]);

        }
        new PercolationStats(n, trials).startSimulate();
    }


    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        trailResults = new double[trials];
    }


    
    public double mean() {
        return StdStats.mean(trailResults);
    }

    
    public double stddev() {
        return StdStats.stddev(trailResults);
    }

    
    public double confidenceLo() {
        return mean() - stddev() * 1.96 / Math.sqrt(trials);
    }

    
    public double confidenceHi() {
        return mean() + stddev() * 1.96 / Math.sqrt(trials);
    }

    private void startSimulate() {
        int sitesNum = n * n;
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                randomOpenSite(percolation);
            }
            trailResults[i] = threshold(percolation, sitesNum);
            StdOut.println("threshod "+i+" : "+trailResults[i]);
        }
        logResult();
    }

    /**
     * 获取阀值
     */
    private double threshold(Percolation perc, int sitesNum) {
        return perc.numberOfOpenSites() / (double) sitesNum;
    }

    /**
     * open另一个closed的site
     */
    private void randomOpenSite(Percolation perc) {
        int row, col;
        while (perc.isOpen((row = StdRandom.uniform(n)+1), (col = StdRandom.uniform(n)+1))){}
        perc.open(row, col);
    }

    private void logResult() {
        StdOut.println("mean                  = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = [ " + confidenceLo()+" , "+confidenceHi()+" ]");
    }
}
