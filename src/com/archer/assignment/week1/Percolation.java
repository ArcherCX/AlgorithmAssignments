package com.archer.assignment.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation implements IPercolation {

    private final int n;
    private boolean[] openSites;
    private int openCount;
    private WeightedQuickUnionUF uf;
    private final int sitesNum;
    private final int virtualTop;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Parameter n should be > 0");
        this.n = n;
        sitesNum = n * n;
        virtualTop = sitesNum;
        uf = new WeightedQuickUnionUF(sitesNum + 1);
        openSites = new boolean[sitesNum + 1];
        //虚拟点默认为open状态
        openSites[virtualTop] = true;
    }


    public void open(int row, int col) {
        open(convertCoorToIndex(row, col));
    }

    private void open(int index) {
        if (isOpen(index)) return;
        final int[] coor = convertIndexToCoor(index);
        int row = coor[0];
        int col = coor[1];
        int[] neighbors = new int[4];
        for (int i = 0; i < 4; i++) {
            neighbors[i] = -1;
        }
        if (col != 1) {//非第一列，计算左部
            neighbors[0] = index - 1;
        }
        if (row != 1) {//非第一行，计算顶部
            neighbors[1] = index - n;
        } else {//第一行，和虚拟顶点连接
            uf.union(virtualTop, index);
        }
        if (col != n) {//非第n列，计算右部
            neighbors[2] = index + 1;
        }
        if (row != n) {//非第n列，计算底部
            neighbors[3] = index + n;
        }
        int neighbor;
        for (int i = 0; i < 4; i++) {
            neighbor = neighbors[i];
            if (indexLegal(neighbor) && isOpen(neighbor)) {
                uf.union(index, neighbor);
            }
        }
        openSites[index] = true;
        openCount++;
    }


    public boolean isOpen(int row, int col) {
        return isOpen(convertCoorToIndex(row, col));
    }

    private boolean isOpen(int index) {
        isIndexLegal(index);
        return openSites[index];
    }


    public boolean isFull(int row, int col) {
        return isFull(convertCoorToIndex(row, col));
    }

    private boolean isFull(int index) {
        isIndexLegal(index);
        return uf.connected(virtualTop, index);
    }


    public int numberOfOpenSites() {
        return openCount;
    }


    public boolean percolates() {
        for (int i = sitesNum - n; i < sitesNum; i++) {
            if (isOpen(i)){
                if(uf.connected(virtualTop, i)) return true;
            }
        }
        return false;
    }


    private boolean indexLegal(int index) {
        return 0 <= index && index < sitesNum;
    }

    private void isIndexLegal(int index) {
        if (!indexLegal(index)) {
            throw new IllegalArgumentException("Parameter index is not between 0 and " + sitesNum + " : " + index);
        }
    }

    /**
     * 将n-by-n网格的坐标转换成数组对应的索引
     *
     * @param row 行，横坐标
     * @param col 列，纵坐标
     */
    private int convertCoorToIndex(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException("Parameter row or col is not between 1 and " + n + " : [row = " + row + " , col = " + col + "]");
        return (row - 1) * n + col - 1;
    }

    /**
     * 将数组对应的索引转换成n-by-n网格的坐标
     */
    private int[] convertIndexToCoor(int index) {
        int[] coor = new int[2];
        coor[0] = index / n + 1;//row
        coor[1] = index % n + 1;//col
        return coor;
    }

}
