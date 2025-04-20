package org.darkknight;
import java.util.ArrayList;

// Separate Chaining Technique
// other techniques are double hashing and linear probing
public class HashMapImpl<K, V> {
    private class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private ArrayList<HashNode<K, V>> bucketArray;
    private int numBucket;
    private int size;

    public HashMapImpl() {
        bucketArray = new ArrayList<>();
        numBucket = 10;
        size = 0;

        for (int i = 0; i < numBucket; i++) {
            bucketArray.add(null);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % numBucket;
        return index;
    }

    // handles update as well
    public void add(K key, V val) {
        // find the head of chain for given key
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // update in case already exists
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = val;
                return;
            }
            head = head.next;
        }

        // insert key in chain
        size++;
        HashNode<K, V> newNode = new HashNode<>(key, val);
        newNode.next = head; // insert in the beginning
        bucketArray.set(bucketIndex, newNode);

        // if load goes beyond threshold, double hash table size
        if ((1.0 * size) / numBucket > 0.7) {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBucket = 2 * numBucket;
            size = 0;
            for (int i = 0; i < numBucket; i++) {
                bucketArray.add(null);
            }

            for (HashNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }

        return null;
    }

    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key))
                break;

            prev = head;
            head = head.next;
        }

        if (head == null)
            return null;

        size--;

        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);

        return head.value;
    }

    public static void main(String[] args) {
        HashMapImpl<String, Integer> map = new HashMapImpl<>();
        map.add("this", 1);
        map.add("coder", 2);
        map.add("this", 4);
        map.add("hi", 5);

        System.out.println(map.size());
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
        System.out.println(map.isEmpty());
    }
}