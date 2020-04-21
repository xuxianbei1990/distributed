package concurrent.program.studyJdk;


import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal 是支持泛型
 * 其实就是一个key放到thread threadlocalmap 而这个map是key是弱应用，value是强应用, entry 是存在的
 * 场景：GC的条件是，对象不可达。所以只有当threadLocal被频繁创建时候且没有remove才会造成内存泄漏, 如果是成员变量或者静态变量不会被GC
 *
 * @author: xuxianbei
 * Date: 2020/4/7
 * Time: 20:24
 * Version:V1.0
 */
public class MyThreadLocal<T> {

    private final int threadLocalHashCode = nextHashCode();


    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    public MyThreadLocal() {

    }


    public T get() {
        MyThread t = MyThread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T) e.value;
                return result;
            }
        }
        return setInitialValue();
    }

    private T setInitialValue() {
        T value = initialValue();
        MyThread t = MyThread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }

    protected T initialValue() {
        return null;
    }

    public void set(T value) {
        //每个线程下维护一个ThreadLocalMap
        MyThread t = MyThread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }

    ThreadLocalMap getMap(MyThread t) {
        return t.threadLocals;
    }

    void createMap(MyThread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    //https://blog.csdn.net/important0534/article/details/50706774
    private static final int HASH_INCREMENT = 0x61c88647;

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }


    /**
     * 就是value是强应用：key是弱引用。
     */
    static class ThreadLocalMap {

        private int size = 0;

        static class Entry extends WeakReference<MyThreadLocal<?>> {
            /**
             * The value associated with this ThreadLocal.
             */
            Object value;

            Entry(MyThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }

        private static final int INITIAL_CAPACITY = 16;

        private Entry getEntry(MyThreadLocal<?> key) {
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            if (e != null && e.get() == key)
                return e;
            else
                return getEntryAfterMiss(key, i, e);
        }

        private Entry getEntryAfterMiss(MyThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;

            while (e != null) {
                MyThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }

        /**
         * The table, resized as necessary.
         * table.length MUST always be a power of two.
         */
        private Entry[] table;

        private int threshold;

        ThreadLocalMap(MyThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }

        private void set(MyThreadLocal<?> key, Object value) {
            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len - 1);
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                MyThreadLocal<?> k = e.get();

                if (k == key) {
                    e.value = value;
                    return;
                }

                if (k == null) {
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }
        }

        private static int nextIndex(int i, int len) {
            return ((i + 1 < len) ? i + 1 : 0);
        }

        private void setThreshold(int len) {
            threshold = len * 2 / 3;
        }

        private static int prevIndex(int i, int len) {
            return ((i - 1 >= 0) ? i - 1 : len - 1);
        }

        private void replaceStaleEntry(MyThreadLocal<?> key, Object value,
                                       int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;

            // Back up to check for prior stale entry in current run.
            // We clean out whole runs at a time to avoid continual
            // incremental rehashing due to garbage collector freeing
            // up refs in bunches (i.e., whenever the collector runs).
            int slotToExpunge = staleSlot;
            for (int i = prevIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;

            // Find either the key or trailing null slot of run, whichever
            // occurs first
            for (int i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                MyThreadLocal<?> k = e.get();

                // If we find key, then we need to swap it
                // with the stale entry to maintain hash table order.
                // The newly stale slot, or any other stale slot
                // encountered above it, can then be sent to expungeStaleEntry
                // to remove or rehash all of the other entries in run.
                if (k == key) {
                    e.value = value;

                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;

                    // Start expunge at preceding stale entry if it exists
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;
                    cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // If we didn't find stale entry on backward scan, the
                // first stale entry seen while scanning for key is the
                // first still present in the run.
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }

            // If key not found, put new entry in stale slot
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);

            // If there are any other stale entries in run, expunge them
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }

        private boolean cleanSomeSlots(int i, int n) {
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ((n >>>= 1) != 0);
            return removed;
        }

        private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;

            // expunge entry at staleSlot
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;

            // Rehash until we encounter null
            Entry e;
            int i;
            for (i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                MyThreadLocal<?> k = e.get();
                if (k == null) {
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        tab[i] = null;

                        // Unlike Knuth 6.4 Algorithm R, we must scan until
                        // null because multiple entries could have been stale.
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }
    }


    public static void main(String[] args) {
        ThreadLocalMap.Entry entry = new ThreadLocalMap.Entry(new MyThreadLocal<>(), "dd");
        WeakReference weakReference = new WeakReference(new Object());
        System.gc();
        System.out.println(entry.toString() + weakReference.get());
        Object o = new Object();
        weakReference = new WeakReference(o);
        System.gc();
        System.out.println(entry.toString() + weakReference.get());

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("tt");
        System.gc();
        threadLocal.set("yy");
        System.out.println(threadLocal);
    }
}

