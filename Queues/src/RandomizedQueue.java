import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2]; // Initial capacity
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }
        if (size == queue.length) {
            resize(2 * queue.length); // Double the capacity if full
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--size]; // Swap with last item and decrement size
        queue[size] = null; // Avoid loitering
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2); // Halve the capacity if too much space
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Randomized queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private final int[] order;

        public RandomizedQueueIterator() {
            current = 0;
            order = new int[size];
            for (int i = 0; i < size; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order); // Shuffle the indices for random order
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            return queue[order[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(queue, 0, copy, 0, size);
        queue = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        System.out.println("Is rq empty? " + rq.isEmpty()); // true
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        System.out.println("rq size: " + rq.size()); // 3
        System.out.println("Sample: " + rq.sample()); // Random item
        System.out.println("Dequeue: " + rq.dequeue()); // Random item removed
        System.out.println("Is rq empty? " + rq.isEmpty()); // false
        for (String s : rq) {
            System.out.print(s + " "); // Items in random order
        }
        System.out.println();
    }
}