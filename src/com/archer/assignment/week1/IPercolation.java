package com.archer.assignment.week1;

public interface IPercolation {

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col);

    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col);

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col);

    /**
     * number of open sites
     */
    public int numberOfOpenSites();

    /**
     * does the system percolate
     */
    public boolean percolates();

}
