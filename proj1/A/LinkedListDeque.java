public class LinkedListDeque<T> {

    private final LNode<T> sentFront;
    private final LNode<T> sentBack;
    private int size;

    /** An empty-parameter constructor. */
    public LinkedListDeque() {
        sentFront = new LNode<T>();
        sentBack = new LNode<T>();
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
        this.size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        LNode<T> n = new LNode<T>(item, sentFront.next, sentFront);
        sentFront.next.prev = n;
        sentFront.next = n;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        LNode<T> n = new LNode<T>(item, sentBack, sentBack.prev);
        sentBack.prev.next = n;
        sentBack.prev = n;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        LNode<T> n = sentFront.next;
        while (n != sentBack) {
            System.out.print(n.item + " ");
            n = n.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        LNode<T> n = sentFront.next;
        sentFront.next = n.next;
        n.next.prev = sentFront;
        size -= 1;
        return n.item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        LNode<T> n = sentBack.prev;
        sentBack.prev = n.prev;
        n.prev.next = sentBack;
        size -= 1;
        return n.item;
    }

    /** Gets the item at the given index,
     *  where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null.
     *  Must not alter the deque!
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        LNode<T> n = sentFront.next;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return n.item;
    }

    /** Same as get, but uses recursion */
    public T getRecursive(int index) {
        if (index == 0) {
            return sentFront.next.item;
        }
        return getRecursive(index--);
    }

}
