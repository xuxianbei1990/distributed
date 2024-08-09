package collection.chapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo<K, V> {

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    transient volatile Node<K, V>[] table;

    public static void main(String[] args) {

        ConcurrentHashMap chm = new ConcurrentHashMap();

    }

    public V put(K key, V value) {
        return putVal(key, value, false);
    }

    final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());

        int binCount = 0;
        for (Node<K, V>[] tab = table; ; ) {
            Node<K, V> f;
            int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
        }
    }

    private transient volatile int sizeCtl;

    private static long SIZECTL;

    /**
     * 初始化长度。这个是多线程环境，所以要表示要保证一个线程进行扩容
     *
     * @return
     */
    private final Node<K, V>[] initTable() {
        Node<K, V>[] tab;
        int sc;

        return null;
    }


    /**
     * HASH_BITS相与，其值为0x7fffffff。它的作用主要是使hash值为正数。在ConcurrentHashMap中，Hash值为负数有特别的意义，
     * 如-1表示ForwardingNode结点，-2表示TreeBin结点。
     *
     * @param h
     * @return
     */
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    static class Node<K, V> implements Map.Entry<K, V> {

        @Override
        public K getKey() {
            return null;
        }

        @Override
        public V getValue() {
            return null;
        }

        @Override
        public V setValue(V value) {
            return null;
        }
    }
}
