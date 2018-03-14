package com.archer.assignment.week1;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.io.*;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(2,3);
        percolation.open(3,3);
        System.out.println(percolation.isFull(2,3));
    }

    /**
     * 由文件内容作为输入，进行无图形绘制的检测
     *
     * @param path 输入的文件路径
     */
    private static void testPercolationNoUI(String path) {
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
                    if (point.trim().isEmpty()) continue;
                    String[] coor = point.trim().split(" +");
                    // open site (i, j) provided it's in bounds
                    final int row = Integer.parseInt(coor[0]);
                    final int col = Integer.parseInt(coor[1]);
                    if (!perc.isOpen(row, col)) {
                        StdOut.println(row + " " + col);
                    }
                    perc.open(row, col);
                    if (perc.percolates()) {
                        StdOut.println("Now it's percolate");
                        return;
                    }
                }
                StdOut.println("This input file does not produce a percolate result");
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
                    if (point.trim().isEmpty()) continue;
//                    StdOut.println("line number :" + lineNo + " start -------");
                    String[] coor = point.trim().split(" +");
                    // open site (i, j) provided it's in bounds
                    System.out.println("point string : -" + point + "- point.trim : -" + point.trim() + "- , point.trim.isEmpty : " + point.trim().isEmpty()+" , split ret : "+ Arrays.toString(coor));
                    final int row = Integer.parseInt(coor[0]);
                    final int col = Integer.parseInt(coor[1]);
                    if (!perc.isOpen(row, col)) {
                        StdOut.println(row + " " + col);
                    }
                    perc.open(row, col);
//                    Stopwatch stopwatch = new Stopwatch();
//                    StdOut.println("line number :" + lineNo + " before draw");
                    // draw n-by-n percolation system
                    PercolationVisualizer.draw(perc, n);
//                    StdOut.println("line number :" + lineNo + " before show ===== " + stopwatch.elapsedTime());
                    StdDraw.show();
//                    StdOut.println("line number :" + lineNo + " after show **********" + stopwatch.elapsedTime());
//                    if (row == 9 && col == 1) {
//                        System.out.println("now break , open sites num = " + perc.numberOfOpenSites() + " -- 9,1 isFull ? " + perc.isFull(row, col));
//                        break;
//                    }
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
