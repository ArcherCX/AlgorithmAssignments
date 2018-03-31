
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k;
        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        } else {
            return;
        }
//      水塘抽样算法，从未知大小的集合中抽取K个随机样本，https://en.wikipedia.org/wiki/Reservoir_sampling
        int idx = 0;
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            rq.enqueue(StdIn.readString());
            idx++;//保证foreach结束后idx为数组含有的内容边界，方便下一个while取随机数直接+1即可取到该边界
        }
        while (k > 0 && !StdIn.isEmpty()) {
            String item = StdIn.readString();
            int sample = StdRandom.uniform(++idx);//随机获取[0,idx]间的随机数，所以需要++idx保证idx包含在随机范围内
            if (sample < k) {
                rq.dequeue();
                rq.enqueue(item);
            }
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
