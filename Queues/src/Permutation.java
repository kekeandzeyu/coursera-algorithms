import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        // Extra challenge: Using RandomizedQueue (maximum size k)
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            if (count <= k) {
                rq.enqueue(s);
            } else {
                if (StdRandom.uniformDouble() < (double) k / count) {
                    rq.dequeue();
                    rq.enqueue(s);
                }
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

        /*
        // Using RandomizedQueue (maximum size n)
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
         */
    }
}
