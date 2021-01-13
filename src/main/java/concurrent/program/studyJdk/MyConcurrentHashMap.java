package concurrent.program.studyJdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

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

    static final int UNTREEIFY_THRESHOLD = 6;
    private static final int MIN_TRANSFER_STRIDE = 16;
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

    /**
     *  与hashmap计算hash基本一样，但多了一步& HASH_BITS，HASH_BITS是0x7fffffff，该步是为了消除最高位上的负符号
     *  hash的负在ConcurrentHashMap中有特殊意义表示在扩容或者是树节点
     * @param h
     * @return
     */
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
                /**
                 * i = (n - 1) & hash
                 * 取模运算 1 % 3 = 1; 4 % 3 = 1；4 % 6 = 4
                 */
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
            //当数量到达临界值时候，进行扩容
            while (s >= (long) (sc = sizeCtl) && (tab = table) != null &&
                    (n = tab.length) < MAXIMUM_CAPACITY) {
                int rs = resizeStamp(n);
                if (sc < 0) {
                    //表示存在一个线程在扩容
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                            sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                            transferIndex <= 0)
                        break;
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        transfer(tab, nt);
                    //1000 0000 0001 1100 0000 0000 0000 0010
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

    private static long TRANSFERINDEX;

    private final void transfer(Node<K, V>[] tab, Node<K, V>[] nextTab) {
        int n = tab.length, stride;
        //计算每条线程处理的桶个数，每条线程处理的桶数量一样，如果CPU为单核，则使用一条线程处理所有桶
        //每条线程至少处理16个桶，如果计算出来的结果少于16，则一条线程处理16个桶
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // subdivide range
        if (nextTab == null) {            // 初始化新数组(原数组长度的2倍)
            try {
                @SuppressWarnings("unchecked")
                Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n << 1];
                nextTab = nt;
            } catch (Throwable ex) {      // try to cope with OOME
                sizeCtl = Integer.MAX_VALUE;
                return;
            }
            nextTable = nextTab;
            //将 transferIndex 指向最右边的桶，也就是数组索引下标最大的位置
            transferIndex = n;
        }
        int nextn = nextTab.length;
        ForwardingNode<K, V> fwd = new ForwardingNode<K, V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        for (int i = 0, bound = 0; ; ) {
            Node<K, V> f;
            int fh;
            while (advance) {
                int nextIndex, nextBound;
                if (--i >= bound || finishing)
                    advance = false;
                else if ((nextIndex = transferIndex) <= 0) {
                    i = -1;
                    advance = false;
                } else if (U.compareAndSwapInt
                        (this, TRANSFERINDEX, nextIndex,
                                nextBound = (nextIndex > stride ?
                                        nextIndex - stride : 0))) {
                    bound = nextBound;
                    i = nextIndex - 1;
                    advance = false;
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
                int sc;
                //如果所有的节点都已经完成复制工作  就把nextTable赋值给table 清空临时对象nextTable
                if (finishing) {
                    nextTable = null;
                    table = nextTab;
                    //重新设置下次扩展阈值
                    sizeCtl = (n << 1) - (n >>> 1);
                    return;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                        return;
                    finishing = advance = true;
                    i = n; // recheck before commit
                }
            } else if ((f = tabAt(tab, i)) == null)
            /**
             * ForwardingNode是一种临时节点，在扩容进行中才会出现，hash值固定为-1，
             * 并且它不存储实际的数据数据。如果旧数组的一个hash桶中全部的节点都迁移到新数组中，
             * 旧数组就在这个hash桶中放置一个ForwardingNode。读操作或者迭代读时碰到ForwardingNode时，
             * 将操作转发到扩容后的新的table数组上去执行，写操作碰见它时，则尝试帮助扩容。
             */
                advance = casTabAt(tab, i, null, fwd);
            else if ((fh = f.hash) == MOVED)
                advance = true; // already processed
            else {
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        Node<K, V> ln, hn;
                        if (fh >= 0) {
                            // resize后的元素要么在原地，要么移动n位（n为原capacity）
                            int runBit = fh & n;
                            Node<K, V> lastRun = f;
                            /**
                             * 下面的逻辑大概是进行高低位分组，进行重新放置
                             * 低位直接放入，高位移动原链表长度放入
                             */
                            for (Node<K, V> p = f.next; p != null; p = p.next) {
                                int b = p.hash & n;
                                if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            if (runBit == 0) {
                                ln = lastRun;
                                hn = null;
                            } else {
                                hn = lastRun;
                                ln = null;
                            }
                            for (Node<K, V> p = f; p != lastRun; p = p.next) {
                                int ph = p.hash;
                                K pk = p.key;
                                V pv = p.val;
                                if ((ph & n) == 0)
                                    ln = new Node<K, V>(ph, pk, pv, ln);
                                else
                                    hn = new Node<K, V>(ph, pk, pv, hn);
                            }
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        } else if (f instanceof TreeBin) {
                            TreeBin<K, V> t = (TreeBin<K, V>) f;
                            TreeNode<K, V> lo = null, loTail = null;
                            TreeNode<K, V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            for (Node<K, V> e = t.first; e != null; e = e.next) {
                                int h = e.hash;
                                TreeNode<K, V> p = new TreeNode<K, V>
                                        (h, e.key, e.val, null, null);
                                if ((h & n) == 0) {
                                    if ((p.prev = loTail) == null)
                                        lo = p;
                                    else
                                        loTail.next = p;
                                    loTail = p;
                                    ++lc;
                                } else {
                                    if ((p.prev = hiTail) == null)
                                        hi = p;
                                    else
                                        hiTail.next = p;
                                    hiTail = p;
                                    ++hc;
                                }
                            }
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                                    (hc != 0) ? new TreeBin<K, V>(lo) : t;
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                                    (lc != 0) ? new TreeBin<K, V>(hi) : t;
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                    }
                }
            }
        }
    }

    static final <K, V> void setTabAt(Node<K, V>[] tab, int i, Node<K, V> v) {

    }

    static <K, V> Node<K, V> untreeify(Node<K, V> b) {
        return null;
    }

    static final int resizeStamp(int n) {
        /**
         * numberOfLeadingZeros:该方法的作用是返回无符号整型i的最高非零位前面的0的个数，包括符号位在内；
         * 比如说，12的二进制表示为 0000 0000 0000 0000 0000 0000 0000 1100
         * java的整型长度为32位。那么这个方法返回的就是28
         *
         * (1 << (RESIZE_STAMP_BITS - 1)): 1000 0000 0000 0000
         * |
         * 1000 0000 0000 0000 或
         * 0000 0000 0001 1100
         * 1000 0000 0001 1100
         */
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

    static final class ForwardingNode<K, V> extends Node<K, V> {
        ForwardingNode(Node<K, V>[] tab) {
            super(MOVED, null, null, null);

        }

        public ForwardingNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }
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

        TreeNode(int hash, K key, V val, Node<K, V> next,
                 TreeNode<K, V> parent) {
            super(hash, key, val, next);
            this.parent = parent;
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

        public TreeBin(TreeNode<K, V> lo) {
            super(-2, null, null, null);
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
//        ConcurrentMap concurrentMap = new ConcurrentHashMap();
//        for (int i = 0; i < 14; i++) {
//            concurrentMap.putIfAbsent("dd" + i, "dd");
//        }
//        System.out.println(spread("the".hashCode()));

        System.out.println(Integer.numberOfLeadingZeros(12));
    }
}
