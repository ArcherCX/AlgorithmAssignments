import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k;
        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        } else {
            return;
        }
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
//        for (int i = 0; i < k; i++) {
//            rq.enqueue(StdIn.readString());
//        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
