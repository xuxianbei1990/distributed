package concurrent.program.studyJdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: xuxianbei
 * Date: 2020/1/2
 * Time: 15:30
 * Version:V1.0
 */
public class MyConcurrentHashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 4567168400242806161L;

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
    //二进制：111111111111111 1111111111111111  31位2进制

    static final int MOVED = -1; // hash for forwarding nodes
    transient volatile Node<K, V>[] table;

    private transient volatile int sizeCtl;

    private transient volatile CounterCell[] counterCells;

    private transient volatile long baseCount;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private transient volatile int cellsBusy;
    /**
     * The next table to use; non-null only while resizing.
     */
    private transient volatile Node<K, V>[] nextTable;
    private static int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;
    private static final int MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
    private transient volatile int transferIndex;

    @Data
    @AllArgsConstructor
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K, V> next;

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

    /**
     * put 可能会失败
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        return putVal(key, value, false);
    }

    //如果是"xxb".hashCode() 1 1101000101100010
    //  the king 111101110111110011010000111 001
    //  the 1 1100000001110000
    // 11101000101100010 ^ 1 & 111111111111111 1111111111111111
    // 11101000101100011
    static final int spread(int h) {
        //右移16位 或 低位 和 得到一个正数。
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    /**
     * The default initial table capacity.  Must be a power of 2
     * (i.e., at least 1) and at most MAXIMUM_CAPACITY.
     */
    private static final int DEFAULT_CAPACITY = 16;

    static final int TREEIFY_THRESHOLD = 8;

    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * 不安全方法，因为java 不允许访问Unsafe实例。
     *
     * @return
     * @throws Exception 作废：官网不允许unsafe被访问
     */


    private static final sun.misc.Unsafe U;
    private static final long SIZECTL;
    private static final int ASHIFT;
    private static final long ABASE;
    private static final long BASECOUNT;
    private static long CELLVALUE;
    private static long CELLSBUSY;


    @Deprecated
    private static Unsafe getUnsafe() throws Exception {
        Class<?> unsafeClass = Unsafe.class;
        for (Field f : unsafeClass.getDeclaredFields()) {
            if ("theUnsafe".equals(f.getName())) {
                f.setAccessible(true);
                return (Unsafe) f.get(null);
            }
        }
        throw new IllegalAccessException("no declared field: theUnsafe");
    }


    static {
        try {
            U = getUnsafe();
            Class<?> k = MyConcurrentHashMap.class;
            SIZECTL = U.objectFieldOffset
                    (k.getDeclaredField("sizeCtl"));
            Class<?> ak = Node[].class;
            ABASE = U.arrayBaseOffset(ak);
            BASECOUNT = U.objectFieldOffset
                    (k.getDeclaredField("baseCount"));
//            CELLVALUE = U.objectFieldOffset
//                    (k.getDeclaredField("value"));
            int scale = U.arrayIndexScale(ak);
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K, V>[] tab = table; ; ) {
            Node<K, V> f;
            int n, i, fh;
            if (tab == null || (n = tab.length) == 0) {
                tab = initTable();
                //并发线程下取出Node<K, V>
            } else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                //通过cas放入
                if (casTabAt(tab, i, null, new Node<K, V>(hash, key, value, null)))
                    break;
                //刚放入的hash不会为-1
            } else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                synchronized (f) {
                    //多线程下双重判定
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (Node<K, V> e = f; ; ++binCount) {
                                //循环链表查找
                                K ek;
                                if (e.hash == hash && ((ek = e.key) == key ||
                                        (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K, V> pred = e;
                                //找不到新增一个节点
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K, V>(hash, key,
                                            value, null);
                                    break;
                                }
                            }
                            //红黑树
                        } else if (f instanceof TreeBin) {
                            Node<K, V> p;
                            binCount = 2;

                        }
                    }
                }
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        //构建红黑树
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }

    private final void addCount(long x, int check) {
        CounterCell[] as;
        long b, s = 0;
        //第一次进来这里肯定是null
        //本对象  待更新属性  预期值 0  更新值 1
        if ((as = counterCells) != null || !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
            CounterCell a;
            long v;
            int m;
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||

                    (a = as[MyThreadLocalRandom.getProbe() & m]) == null ||
                    !(uncontended =
                            U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
                fullAddCount(x, uncontended);
                return;
            }
            if (check <= 1)
                return;
            s = sumCount();
        }
        if (check >= 0) {
            Node<K, V>[] tab, nt;
            int n, sc;
            while (s >= (long) (sc = sizeCtl) && (tab = table) != null &&
                    (n = tab.length) < MAXIMUM_CAPACITY) {
                int rs = resizeStamp(n);
                if (sc < 0) {
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                            sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                            transferIndex <= 0)
                        break;
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        transfer(tab, nt);
                } else if (U.compareAndSwapInt(this, SIZECTL, sc,
                        (rs << RESIZE_STAMP_SHIFT) + 2))
                    transfer(tab, null);
                s = sumCount();
            }

        }

    }

    private final void fullAddCount(long x, boolean wasUncontended) {
        int h;
        if ((h = MyThreadLocalRandom.getProbe()) == 0) {
            MyThreadLocalRandom.localInit();      // force initialization
            h = MyThreadLocalRandom.getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (; ; ) {
            CounterCell[] as;
            CounterCell a;
            int n;
            long v;
            if ((as = counterCells) != null && (n = as.length) > 0) {
                if ((a = as[(n - 1) & h]) == null) {
                    if (cellsBusy == 0) {            // Try to attach new Cell
                        CounterCell r = new CounterCell(x); // Optimistic create
                        if (cellsBusy == 0 &&
                                U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                            boolean created = false;
                            try {               // Recheck under lock
                                CounterCell[] rs;
                                int m, j;
                                if ((rs = counterCells) != null &&
                                        (m = rs.length) > 0 &&
                                        rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created)
                                break;
                            continue;           // Slot is now non-empty
                        }
                    }
                    collide = false;
                } else if (!wasUncontended)       // CAS already known to fail
                    wasUncontended = true;      // Continue after rehash
                else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))
                    break;
                else if (counterCells != as || n >= NCPU)
                    collide = false;            // At max size or stale
                else if (!collide)
                    collide = true;
                else if (cellsBusy == 0 &&
                        U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                    try {
                        if (counterCells == as) {// Expand table unless stale
                            CounterCell[] rs = new CounterCell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            counterCells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = MyThreadLocalRandom.advanceProbe(h);
            } else if (cellsBusy == 0 && counterCells == as &&
                    U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                boolean init = false;
                try {                           // Initialize table
                    if (counterCells == as) {
                        CounterCell[] rs = new CounterCell[2];
                        rs[h & 1] = new CounterCell(x);
                        counterCells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            } else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))
                break;                          // Fall back on using base
        }
    }

    final long sumCount() {
        CounterCell[] as = counterCells;
        CounterCell a;
        long sum = baseCount;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }

    private final void transfer(Node<K, V>[] tab, Node<K, V>[] nextTab) {

    }

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }


    @sun.misc.Contended
    static final class CounterCell {
        volatile long value;

        CounterCell(long x) {
            value = x;
        }
    }


    private final void treeifyBin(Node<K, V>[] tab, int index) {

    }

    static final class TreeNode<K, V> extends Node<K, V> {
        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;    // needed to unlink next upon deletion
        boolean red;

        public TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }
    }


    static final class TreeBin<K, V> extends Node<K, V> {
        TreeNode<K, V> root;
        volatile TreeNode<K, V> first;
        volatile MyThread waiter;
        volatile int lockState;
        // values for lockState
        static final int WRITER = 1; // set while holding write lock
        static final int WAITER = 2; // set when waiting for write lock
        static final int READER = 4; // increment value for setting read lock

        public TreeBin(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }
    }

    private Node<K, V>[] helpTransfer(Node<K, V>[] tab, Node<K, V> f) {
        return new Node[0];
    }

    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i,
                                         Node<K, V> c, Node<K, V> v) {
        return U.compareAndSwapObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }

    private Node<K, V> tabAt(Node<K, V>[] tab, int i) {
        return (Node<K, V>) U.getObjectVolatile(tab, ((long) i << ASHIFT) + ABASE);
    }

    private Node<K, V>[] initTable() {
        Node<K, V>[] tab;
        int sc;
        while ((tab = table) == null || tab.length == 0) {
            //如果有线程在扩容，那么就让出cpu
            if ((sc = sizeCtl) < 0)
                Thread.yield();
                //CAS 对象，对象需要CAS的值，期望值，更新值
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                //这里为什么要try finally ?
                //因为是多线程环境，当前线程执行这个操作不一定成功，所以需要try  finally
                // 如果当前线程执行初始化容量失败，那么由其他线程完成这个动作。这是并发环境
                try {
                    if ((tab = table) == null || tab.length == 0) {
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        Node<K, V>[] nt = (Node<K, V>[]) new Node[n];
                        table = tab = nt;
                        //这个算出来是12  而 12/16 = 0.75 这个值是负载因子
                        sc = n - (n >>> 2);
                    }
                } finally {
                    sizeCtl = sc;
                }
                break;
            }
        }
        return tab;
    }

    public static void main(String[] args) {
        //1 1101000101100010
//        1101000101100010
        int n, sc;
        long s = 0;
//        System.out.println(s >= (long) (sc = 12) && (new Object()) != null &&
//                (n = 16) < MAXIMUM_CAPACITY);
        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        concurrentMap.putIfAbsent("dd", "dd");
        System.out.println(spread("the".hashCode()));

        System.out.println(DEFAULT_CAPACITY - (DEFAULT_CAPACITY >>> 2));
    }
}
