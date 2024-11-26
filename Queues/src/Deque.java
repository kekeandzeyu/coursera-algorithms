import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private static class Node<Item> {
        Item item;
        Node<Item> prev;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("Is deque empty? " + deque.isEmpty()); // true
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addLast("C");
        deque.addLast("D");
        System.out.println("Deque size: " + deque.size()); // 4
        System.out.println("Remove first: " + deque.removeFirst()); // B
        System.out.println("Remove last: " + deque.removeLast()); // D
        System.out.println("Is deque empty? " + deque.isEmpty()); // false
        for (String s : deque) {
            System.out.print(s + " "); // A C
        }
        System.out.println();
    }
}
