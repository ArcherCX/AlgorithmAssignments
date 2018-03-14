package com.archer.assignment.week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats implements IPercolationStats {

    private int n;
    private int trials;
    private double[] trailResults;
    private double mean;
    private double stddev;

    public static void main(String[] args) {
        int n = 2;
        int trials = 100000;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials= Integer.parseInt(args[1]);
        }
        new PercolationStats(n, trials).logResult();
    }


    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Params n or trials should be > 0");
        this.n = n;
        this.trials = trials;
        trailResults = new double[trials];
        startSimulate();
    }


    public double mean() {
        mean = StdStats.mean(trailResults);
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(trailResults);
        return stddev;
    }

    public double confidenceLo() {
        checkRet();
        return mean - stddev * 1.96 / Math.sqrt(trials);
    }

    public double confidenceHi() {
        checkRet();
        return mean + stddev * 1.96 / Math.sqrt(trials);
    }

    private void checkRet() {
        if (mean == 0) mean();
        if (stddev == 0) stddev();
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
//            StdOut.println("threshod " + i + " : " + trailResults[i]+" -- "+ Arrays.toString(percolation.openSites));
        }
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
        row = StdRandom.uniform(1, n + 1);
        col = StdRandom.uniform(1, 1 + n);
//        StdOut.println("row : " + row + " , col : " + col);
        perc.open(row, col);
    }

    private void logResult() {
        StdOut.println("mean                  = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = [ " + confidenceLo()+" , "+confidenceHi()+" ]");
    }
}
