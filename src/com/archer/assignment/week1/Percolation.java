package com.archer.assignment.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Percolation implements IPercolation {

    private final int n;
    private Set<Integer> openSites;
    private List<Integer> closedSites;
    private WeightedQuickUnionUF uf;
    private final int sitesNum;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Parameter n should be > 0");
        this.n = n;
        sitesNum = n * n;
        uf = new WeightedQuickUnionUF(sitesNum);
        openSites = new HashSet<>();
        closedSites = new ArrayList<>(sitesNum);
        for (int i = 0; i < sitesNum; i++) {
            closedSites.add(i);
        }
    }

    @Override
    public void open(int row, int col) {
        open(convertCoorToIndex(row, col));
    }

    public void open(int index) {
        isIndexLegal(index);
        int[] neighbors = {/*left*/index - 1,/*top*/index - n,/*right*/index + 1,/*bottom*/index + n};
        int neighbor;
        for (int i = 0; i < 4; i++) {
            neighbor = neighbors[i];
            if (indexLegal(neighbor) && isOpen(neighbor)) {
                uf.union(index, neighbor);
            }
        }
        openSites.add(index);
        closedSites.remove((Integer) index);//防止index和Object混淆，显示转换成Object类型Integer
    }

    @Override
    public boolean isOpen(int row, int col) {
        return isOpen(convertCoorToIndex(row, col));
    }

    public boolean isOpen(int index) {
        isIndexLegal(index);
        return openSites.contains(index);
    }

    @Override
    public boolean isFull(int row, int col) {
        return isFull(convertCoorToIndex(row, col));
    }

    public boolean isFull(int index) {
        isIndexLegal(index);
        for (int i = 0; i < n; i++) {
            if (uf.connected(index, i)) return true;
        }
        return false;
    }

    @Override
    public int numberOfOpenSites() {
        return openSites.size();
    }

    @Override
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull(n, i + 1)) {
                return true;
            }
        }
        return false;
    }


    private boolean indexLegal(int index) {
        return 0 <= index && index < sitesNum;
    }

    private void isIndexLegal(int index) {
        if (!indexLegal(index)) {
            throw new IllegalArgumentException("Parameter row or col is not between 1 and n");
        }
    }

    /**
     * 将n-by-n网格的坐标转换成数组对应的索引
     * @param row 行，横坐标
     * @param col 列，纵坐标
     */
    private int convertCoorToIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }


    /**
     * open另一个closed的site
     */
    public void randomOpenSite() {
        int siteToOpen = closedSites.get(StdRandom.uniform(closedSites.size()));
        open(siteToOpen);
    }

    public double threshold() {
        return numberOfOpenSites() / (double) sitesNum;
    }
}
