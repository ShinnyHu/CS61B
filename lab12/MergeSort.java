import algs4.*;
// import org.junit.Test;

// import static junit.framework.TestCase.assertEquals;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     * @author  Josh Hug
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> q = new Queue<Queue<Item>>();
        for (Item i : items) {
            Queue<Item> in = new Queue<Item>();
            in.enqueue(i);
            q.enqueue(in);
        }
        return q;
    }

    // @Test
    // public void testMakeSingleItemQueues() {
    //     Queue<Integer> q = new Queue<Integer>();
    //     q.enqueue(3);
    //     q.enqueue(5);
    //     q.enqueue(8);

    //     for (Queue<Integer> item : makeSingleItemQueues(q)){
    //         assertEquals(q.dequeue().toString() + " ", item.toString());
    //     }
    // }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {

        Queue<Item> sorted = new Queue<Item>();
        while (!(q1.isEmpty() && q2.isEmpty())){
            Item min = getMin(q1, q2);
            sorted.enqueue(min);
        }
        return sorted;

    }

    // @Test
    // public void testMergeSortedQueues() {
    //     Queue<Integer> q1 = new Queue<Integer>();
    //     q1.enqueue(3);
    //     q1.enqueue(5);

    //     Queue<Integer> q2 = new Queue<Integer>();
    //     q2.enqueue(2);
    //     q2.enqueue(4);
    //     q2.enqueue(7);

    //     Queue<Integer> answer = new Queue<Integer>();
    //     answer.enqueue(2);
    //     answer.enqueue(3);
    //     answer.enqueue(4);
    //     answer.enqueue(5);
    //     answer.enqueue(7);

    //     assertEquals(mergeSortedQueues(q1,q2).toString(), answer.toString());
    // }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> q = makeSingleItemQueues(items);
        while (q.size() > 1) {
            q.enqueue(mergeSortedQueues(q.dequeue(), q.dequeue()));
        }
        return q.dequeue();
    }

    public static void main(String args[]) {
        Queue<Integer> students = new Queue<Integer>();
        students.enqueue(3);
        students.enqueue(5);
        students.enqueue(1);
        students.enqueue(2);
        students.enqueue(7);
        students.enqueue(0);

        System.out.println("The original: " + students);
        System.out.println("The sorted array: " + MergeSort.mergeSort(students));
    }
}
