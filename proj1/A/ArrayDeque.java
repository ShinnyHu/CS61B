public class ArrayDeque<T> {
    private T[] arr;
    private int head;
    private int tail;
    private int size;

    /** An initial empty-parameter constructor. */
    public ArrayDeque() {
        arr = (T[]) new Object[8];
        head = 0;   //sentFront
        tail = 1;   //sentBack
        size = 0;
    }

    /** Resize the array based on usage. */
    public void resize(String s) {
        int newSize = 0;
        if (s.equals("increase")) {
            newSize = arr.length * 2;
        } else if (s.equals("decrease")){
            newSize = arr.length / 2;
        } else {
            System.out.print("Something wrong with resizing!");
            return;
        }
        T[] newArr = (T[]) new Object[newSize];
        for(int i = 0; i < size; i++) {
            newArr[i+1] = get(i);
        }
        head = 0;
        tail = size + 1;
        arr = newArr;

    }

    /** Returns True if the Deque usage is under 25%. */
    public boolean usedTooLittle() {
        if (((double)(size + 2) / (double)arr.length) < 0.25) {
            return true;
        }
        return false;
    }


    /** Returns True if the Deque is full. */
    public boolean isFull() {
        return size == arr.length - 2;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (isFull()) {
            this.resize("increase");
        }
        int newHead = (head - 1 + arr.length) % arr.length;
        arr[head] = item;
        head = newHead;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (isFull()) {
            this.resize("increase");
        }
        int newTail = (tail + 1) % arr.length;
        arr[tail] = item;
        tail = newTail;
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
        int index = head;
        for (int i = 0; i < size; i++) {
            index += 1;
            if(index == arr.length) {
                index = 0;
            }
            System.out.print(arr[index] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int newHead = (head + 1) % arr.length;
        T item = arr[newHead];
        head = newHead;
        if(usedTooLittle()) {
            resize("decrease");
        }
        size -= 1;
        return item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
           return null;
        }
        int newTail = (tail - 1 + arr.length) % arr.length;
        T item = arr[newTail];
        tail = newTail;
        if(usedTooLittle()) {
            resize("decrease");
        }
        size -= 1;
        return item;
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
        return arr[(head + index + 1) % arr.length];
    }

}
