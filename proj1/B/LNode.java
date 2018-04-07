public class LNode<Type> {

    public Type item;
    public LNode<Type> next;
    public LNode<Type> prev;

    public LNode(Type i, LNode<Type> l, LNode<Type> p) {
        this.item = i;
        this.next = l;
        this.prev = p;
    }

    public LNode() {
    }

}
