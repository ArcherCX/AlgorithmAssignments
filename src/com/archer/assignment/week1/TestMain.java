package com.archer.assignment.week1;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.io.*;

public class TestMain {
    public static void main(String[] args) {
    }

    private static void testPercolationStats() {
        int n = 200;
        int trails = 1;
        PercolationStats ps = new PercolationStats(n, trails);
        ps.startSimulate();
    }

    /**
     * 由文件内容作为输入，进行可观测的图形化测试
     *
     * @param path 输入的文件路径
     * @deprecated 绘制方法太慢，每次都重新绘制所有点
     */
    private static void testPercolation(String path) {
        File input = new File(path);
        if (input.exists()) {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(input);
                br = new BufferedReader(fr);
                int n = Integer.parseInt(br.readLine());
                String point;
                Percolation perc = new Percolation(n);
                int lineNo = 0;
                while ((point = br.readLine()) != null) {
                    StdOut.println("line number :" + lineNo + " start -------");
                    String[] coor = point.trim().split(" ");
                    // open site (i, j) provided it's in bounds
                    final int row = Integer.parseInt(coor[0]);
                    final int col = Integer.parseInt(coor[1]);
                    if (!perc.isOpen(row, col)) {
                        StdOut.println(row + " " + col);
                    }
                    perc.open(row, col);
                    Stopwatch stopwatch = new Stopwatch();
                    StdOut.println("line number :" + lineNo + " before draw");
                    // draw n-by-n percolation system
                    PercolationVisualizer.draw(perc, n);
                    StdOut.println("line number :" + lineNo + " before show ===== " + stopwatch.elapsedTime());
                    StdDraw.show();
                    StdOut.println("line number :" + lineNo + " after show **********" + stopwatch.elapsedTime());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
