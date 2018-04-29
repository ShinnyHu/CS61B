package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 4;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private Set<K> keySet;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        keySet = new HashSet<>();
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private void resize(int newSize) {
        ArrayMap<K, V>[] newBuckets = new ArrayMap[newSize];
        for (int i = 0; i < newSize; i += 1) {
            newBuckets[i] = new ArrayMap<>();
        }

        ArrayMap<K, V> temp = new ArrayMap<>();
        for (K key : keySet) {
            temp.put(key, get(key));
        }

        buckets = newBuckets;
        int index = 0;
        for(K key : temp) {
            put(key, temp.get(key));
            index += 1;
        }
        size -= index;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int bucketNum = hash(key);
        return buckets[bucketNum].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if(loadFactor() >= MAX_LF) {
            resize(buckets.length * 2);
        }
        if(get(key) == null) {
            size += 1;
            keySet.add(key);
        }
        int bucketNum = hash(key);
        buckets[bucketNum].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (get(key) != null) {
            size -= 1;
            keySet.remove(key);
            return buckets[hash(key)].remove(key);
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if(get(key) == value) {
            remove(key);
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();

    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>();
        hashMap.put("hello", 5);
        hashMap.put("cat", 10);
        hashMap.put("aaa", 0);
        hashMap.put("dog", 12);

//        System.out.println("size: " + hashMap.size());
//        System.out.println("cat: " + hashMap.get("cat"));
//
//        for (String s : hashMap.keySet()) {
//            System.out.print(s + " ");
//        }
//        System.out.println();

        hashMap.put("fish", 22);
        hashMap.put("python", 40);
        hashMap.put("cat", 90);
        hashMap.put("ithaca", 10);

//        System.out.println("size: " + hashMap.size());
//        System.out.println("cat: " + hashMap.get("cat"));

        hashMap.remove("ithaca", 30);
        hashMap.remove("not exist");

        System.out.println("size: " + hashMap.size());


        System.out.println(hashMap.remove("ithaca"));

        for (String s : hashMap.keySet()) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : hashMap) {
            System.out.print(s + " ");
        }
    }
}
