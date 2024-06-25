package chirg.dev.cache.keystore;

import chirg.dev.cache.exceptions.*;

import java.util.*;

public class KeyStoreImpl<KEY, VAL> implements KeyStore<KEY, VAL> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int capacity;
    private int size;

    Bucket<KEY, VAL> head, tail;
    HashMap<KEY, Bucket<KEY, VAL>> nodes;

    public KeyStoreImpl() {
        this(DEFAULT_CAPACITY);
    }

    public KeyStoreImpl(int capacity) {
        this.capacity = capacity;
        this.size = 0;

        nodes = new HashMap<>(capacity, DEFAULT_LOAD_FACTOR);
    }

    private void insert(KEY key, Bucket<KEY, VAL> bucket) throws CacheOverflowException {
        if (size >= capacity) throw new CacheOverflowException();

        if (head == null) {
            head = bucket;
            tail = bucket;
        } else {
            tail.next = bucket;
            bucket.prev = tail;

            tail = bucket;
        }

        nodes.put(key, bucket);
        size++;
    }

    @Override
    public void insert(KEY key, VAL value) throws CacheOverflowException {
        if (size >= capacity) throw new CacheOverflowException();

        Bucket<KEY, VAL> newBucket = new Bucket<>(key, value);
        insert(key, newBucket);
    }

    public Bucket<KEY, VAL> removeFirst() throws KeyNotFoundException {
        if (head == null) throw new KeyNotFoundException();

        Bucket<KEY, VAL> ret = head;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;

        nodes.remove(ret.key);
        size--;
        return ret;
    }

    public Bucket<KEY, VAL> removeLast() throws KeyNotFoundException{
        if (tail == null) throw new KeyNotFoundException();

        Bucket<KEY, VAL> ret = tail;
        tail = tail.prev;
        if (tail != null)
            tail.next = null;
        else head = null;

        nodes.remove(ret.key);
        size--;
        return ret;
    }

    public Bucket<KEY, VAL> remove(KEY key) throws KeyNotFoundException {
        if (!nodes.containsKey(key)) return null;

        Bucket<KEY, VAL> ret = get(key);
        if (head.key == ret.key) return removeFirst();
        else if (tail.key == ret.key) return removeLast();

        if (ret.prev != null) ret.prev.next = ret.next;
        if (ret.next != null) ret.next.prev = ret.prev;

        ret.prev = null;
        ret.next = null;

        size--;
        nodes.remove(ret.key);
        return ret;
    }

    public void update(KEY key, VAL val) throws KeyNotFoundException {
        Bucket<KEY, VAL> buck = get(key);
        buck.value = val;
    }

    public Bucket<KEY, VAL> get(KEY key) throws KeyNotFoundException {
        if (!nodes.containsKey(key)) throw new KeyNotFoundException();
        return nodes.get(key);
    }

    public VAL getVal(KEY key) throws KeyNotFoundException {
        return get(key).value;
    }

    public void clear() {
        this.head = null;
        this.tail = null;

        nodes.clear();
        System.gc();
    }

    public void print() {
        Bucket<KEY, VAL> temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }

        System.out.println();
    }
}
