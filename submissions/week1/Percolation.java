import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private boolean[] openSites;
    private int openCount;
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
        openSites = new boolean[sitesNum];
    }

    
    public void open(int row, int col) {
        open(convertCoorToIndex(row, col));
    }

    private void open(int index) {
        isIndexLegal(index);
        int[] neighbors = {/*left*/index - 1,/*top*/index - n,/*right*/index + 1,/*bottom*/index + n};
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
        for (int i = 0; i < n; i++) {
            if (uf.connected(index, i)) return true;
        }
        return false;
    }

    
    public int numberOfOpenSites() {
        return openCount;
    }

    
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
     * 将数组对应的索引转换成n-by-n网格的坐标
     */
    private int[] convertIndexToCoor(int index) {
        int[] coor = new int[2];
        coor[0] = index / n + 1;
        coor[1] = index % n;
        return coor;
    }

}
