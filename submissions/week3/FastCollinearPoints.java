

import edu.princeton.cs.algs4.MergeX;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private double[] slopes;
    private Point[] startPoints;
    private int cur;

    /**
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        if (!checkArgsIllegal(points)) throw new IllegalArgumentException();
        Point origin;
        int length = points.length;
        lineSegments = new LineSegment[2];
        slopes = new double[2];
        startPoints = new Point[2];
        Point[] cp = new Point[length];
        StableComparator stableComparator = new StableComparator();
        System.arraycopy(points, 0, cp, 0, length);
        for (int i = 0; i < length; i++) {
            origin = points[i];
            stableComparator.setOrigin(origin);
            MergeX.sort(cp, stableComparator);
            int start = 1, end = 1;//[start,end],start，end上的Point都包含
            int edge = length - 1;
            double slopeToStart = -1;
            for (int j = 1; j < edge; j++) {
                if (slopeToStart == -1) slopeToStart = origin.slopeTo(cp[start]);
                if (slopeToStart == origin.slopeTo(cp[j + 1])) {
                    end = j + 1;
                } else {
                    executeAddLine(cp, origin, start, end);
                    start = end = j + 1;
                    slopeToStart = -1;
                }
            }
            if (start != end) {//如果最后几个点可以构成一条线，上一个循环直到结束也不会进入保存线段的流程，最后做一次保存
                executeAddLine(cp, origin, start, end);
            }
        }
    }

    private void executeAddLine(Point[] points, Point origin, int start, int end) {
        int pCount = end - start + 1;
        if (pCount >= 3) {//算上自身就至少是4个点
            Point startP = points[start], endP = points[end];
            if (origin.compareTo(startP) < 0) startP = origin;
            if (origin.compareTo(endP) > 0) endP = origin;
            addLineSegment(startP, endP);
        }
    }

    /**
     * 添加一条线段
     */
    private void addLineSegment(Point start, Point end) {
        double slope = start.slopeTo(end);
        for (int i = 0; i < slopes.length; i++) {
            if (slope == slopes[i] && start == startPoints[i]) return;
        }
        if (lineSegments.length == cur + 1) resize();
        LineSegment lineSegment = new LineSegment(start, end);
        slopes[cur] = slope;
        startPoints[cur] = start;
        lineSegments[cur++] = lineSegment;
    }

    /**
     * 重构数组大小
     */
    private void resize() {
        LineSegment[] newArr = new LineSegment[lineSegments.length << 1];
        System.arraycopy(lineSegments, 0, newArr, 0, cur);
        lineSegments = newArr;

        double[] newSlopes = new double[newArr.length];
        System.arraycopy(slopes, 0, newSlopes, 0, cur);
        slopes = newSlopes;

        Point[] newSP = new Point[newArr.length];
        System.arraycopy(startPoints, 0, newSP, 0, cur);
        startPoints = newSP;
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return cur;
    }

    /**
     * the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[cur];
        System.arraycopy(lineSegments, 0, ret, 0, cur);
        return ret;
    }

    /**
     * 检查输入的点是否为null或含有重复的点
     *
     * @param points 输入的点
     * @return 输入点是否合法
     */
    private boolean checkArgsIllegal(Point[] points) {
        if (points == null) return false;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return false;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) return false;
            }
        }
        return true;
    }

    private static class StableComparator implements Comparator<Point> {

        Comparator<Point> firstOrder;

        @Override
        public int compare(Point o1, Point o2) {
            int ret;
            ret = firstOrder.compare(o1, o2);
            if (ret == 0) return o1.compareTo(o2);//只有斜率相同时，按照y坐标排序，保证排序后的数组内处于有效线段的点的坐标是按y坐标递增存放
            return ret;
        }

        void setOrigin(Point origin) {
            firstOrder = origin.slopeOrder();
        }
    }

}
