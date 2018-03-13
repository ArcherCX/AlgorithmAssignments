package com.archer.assignment.week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats implements IPercolationStats {

    private int n;
    private int trials;
    private double[] trailResults;


    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        trailResults = new double[trials];
    }


    @Override
    public double mean() {
        return StdStats.mean(trailResults);
    }

    @Override
    public double stddev() {
        return StdStats.stddev(trailResults);
    }

    @Override
    public double confidenceLo() {
        return mean() - stddev() * 1.96 / Math.sqrt(trials);
    }

    @Override
    public double confidenceHi() {
        return mean() + stddev() * 1.96 / Math.sqrt(trials);
    }

    public void startSimulate() {
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.randomOpenSite();
            }
            trailResults[i] = percolation.threshold();
            StdOut.println("threshod "+i+" : "+trailResults[i]);
        }
        logResult();
    }

    private void logResult() {
        StdOut.println("mean                  = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = [ " + confidenceLo()+" , "+confidenceHi()+" ]");
    }
}
