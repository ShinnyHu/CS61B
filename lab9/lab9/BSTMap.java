package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if(p == null) {
            return null;
        } else if(p.key.compareTo(key) == 0) {
            return p.value;
        }

        if(p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        } else if(p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        }

        return null;

    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if(p == null) {
            return new Node(key, value);
        }
        if (p.key.compareTo(key) == 0) {
            Node n = new Node(key, value);
            n.right = p.right;
            n.left = p.left;
            return n;
        }
        if(p.key.compareTo(key) > 0) {
            p.left = putHelper(key, value, p.left);
        } else if(p.key.compareTo(key) < 0) {
            p.right = putHelper(key, value, p.right);
        }

        return p;

    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if(get(key) == null) {
            size += 1;
        }
        root = putHelper(key, value, root);


    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }



    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<Node> nodeSet = new HashSet<>();
        getKeySetHelper(root, nodeSet);

        Set<K> keySet = new HashSet<>();
        for (Node n: nodeSet) {
            if(n != null) {
                keySet.add(n.key);
            }
        }
        return keySet;
    }

    public Node getKeySetHelper(Node p, Set<Node> set) {
        if(p == null) {
            return null;
        }
        set.add(getKeySetHelper(p.left, set));
        set.add(p);
        set.add(getKeySetHelper(p.right, set));
        return null;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node n = removeHelper(key, root);
        if(n == null) {
            return null;
        }
        Node parent = parent(n);
        if(parent == null) {
            root = leftMostRightSubTree(root);
        } else {
            if(n.left == null && n.right == null) {
                if(parent.right == n) {
                    parent.right = null;
                } else {
                    parent.left = null;
                }
            } else if(n.left == null) {
                if(parent.right == n) {
                    parent.right = n.right;
                } else {
                    parent.left = n.right;
                }
            } else if(n.right == null) {
                if(parent.right == n) {
                    parent.right = n.left;
                } else {
                    parent.left = n.left;
                }
            } else { // two children
                if(parent.right == n) {
                    parent.right = leftMostRightSubTree(n);
                } else {
                    parent.left = leftMostRightSubTree(n);
                }
            }
        }
        size -= 1;
        keySet().remove(key);
        return get(key);
    }

    public Node removeHelper(K key, Node p) {
        if(p == null) {
            return null;
        }

        if(p.key.compareTo(key) == 0) {
            return p;
        } else if(p.key.compareTo(key) > 0) {
            return removeHelper(key, p.left);
        } else if(p.key.compareTo(key) < 0) {
            return removeHelper(key, p.right);
        }

        return null;
    }

    public Node parent(Node p) {
        Node parent = root;
        if(parent == null || parent == p) {
            return null;
        }
        while(parent.left != p && parent.right != p) {
            if(parent.key.compareTo(p.key) > 0) {
                parent = parent.left;
            } else {
                parent = parent.right;
            }
        }
        return parent;
    }

    public Node leftMostRightSubTree(Node root) {
        Node rightSubTree = root.right;
        while(rightSubTree.left != null) {
            rightSubTree = rightSubTree.left;
        }
        if(parent(rightSubTree) == root) {
            rightSubTree.left = root.left;
        } else {
            parent(rightSubTree).left = rightSubTree.right;
            rightSubTree.right = root.right;
            rightSubTree.left = root.left;
        }
        return rightSubTree;
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V v = get(key);
        if(v == value) {
            remove(key);
            return v;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("aaa", 0);
        bstmap.put("dog", 12);
        bstmap.put("fish", 22);
        bstmap.put("python", 40);
        bstmap.put("zebra", 90);
        bstmap.put("ithaca", 10);

        for (String s : bstmap.keySet()) {
            System.out.print(s + " ");
        }
        System.out.println(bstmap.size);
        bstmap.remove("hello");
        System.out.println();
        for (String s : bstmap.keySet()) {
            System.out.print(s + " ");
        }
        System.out.println(bstmap.size);

        for (String s : bstmap) {
            System.out.print(s + " ");
        }


    }
}
