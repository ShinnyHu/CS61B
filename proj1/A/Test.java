// import LinkedListDeque.java;

public class Test {
	public static void main(String[] args) {

        // Some testing:
        LinkedListDeque<Integer> list = new LinkedListDeque<Integer>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addLast(0);
        list.addLast(-1);
        list.printDeque();
        list.removeFirst();
        list.removeFirst();
        list.removeLast();
        list.printDeque();
        System.out.println((1 + 5) % 5);

        ArrayDeque<Integer> arr = new ArrayDeque<Integer>();
        arr.addFirst(1);
        arr.addFirst(2);
        arr.addFirst(3);
        arr.addFirst(4);
        arr.addFirst(5);
        arr.addLast(0);
        arr.addLast(-1);
        arr.addLast(-2);
        arr.addLast(-3);
        System.out.println(arr.get(2));
        arr.printDeque();
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        arr.removeLast();
        arr.removeLast();
        arr.printDeque();
        System.out.println(arr.get(1));
    }
}