package synthesizer;
import synthesizer.AbstractBoundedQueue;

import java.util.Iterator;
import java.lang.RuntimeException;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int capacity;
    private int fillCount;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        fillCount += 1;
        rb[last] = x;
        last = updateIndex(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T r = rb[first];
        first = updateIndex(first);
        fillCount -= 1;
        return r;
    }

    /** Update the index when coming to the end of array. */
    public int updateIndex(int i) {
        if (i == capacity - 1) {
            return 0;
        }
        return i + 1;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            return null;
        }
        return rb[first];
    }


    /** Implements the method iterator. */
    @Override
    public Iterator<T> iterator() {
        return new gsIterator();
    }

    private class gsIterator implements Iterator<T> {
        private int pos;
        private int count;

        public gsIterator() {
            pos = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            if (count == fillCount) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            T r = rb[pos];
            pos = updateIndex(pos);
            count += 1;
            return r;
        }
    }
}
