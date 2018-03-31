
import java.util.Arrays;

public class BruteCollinearPoints {

    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point[] as = {a, null};
        new BruteCollinearPoints(as);
        System.out.println("complete ----------");
    }
    private int cur;
    private LineSegment[] lineSegments;

    /**
     * finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (!checkArgsIllegal(points)) {
            throw new IllegalArgumentException();
        }
        cur = 0;
        lineSegments = new LineSegment[2];
        findLineSegment(points);
    }

    /**
     * 寻找含有4个点的线段
     *
     * @param points 输入点
     */
    private void findLineSegment(Point[] points) {
        int length = points.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                double slopeToJ = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < length; k++) {
                    double slopeToK = points[i].slopeTo(points[k]);
                    for (int l = k + 1; l < length; l++) {
                        double slopeToL = points[i].slopeTo(points[l]);
                        if (slopeToJ == slopeToK && slopeToJ == slopeToL) {
                            Point[] ps = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(ps);
                            addLineSegment(new LineSegment(ps[0], ps[3]));
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加一条线段
     */
    private void addLineSegment(LineSegment lineSegment) {
        if (lineSegments.length == cur + 1) resize();
        lineSegments[cur++] = lineSegment;
    }

    /**
     * 重构数组大小
     */
    private void resize() {
        LineSegment[] newArr = new LineSegment[lineSegments.length << 1];
        System.arraycopy(lineSegments, 0, newArr, 0, cur);
        lineSegments = newArr;
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
}
