package concurrent.program.studyJdk;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author: xuxianbei
 * Date: 2020/1/2
 * Time: 16:12
 * Version:V1.0
 */
public class MyHashMap<K, V> {

    transient Node<K, V>[] table;

    transient int modCount;
    int size;

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int TREEIFY_THRESHOLD = 8;

    static final int MIN_TREEIFY_CAPACITY = 64;

    int threshold;
    float loadFactor;

    //xxb 1 1101000101100010
    //  the king 111101110111110011010000111001
    //  the 1 1100000001110000
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //取hash值与table长度的hash值进行与操作。
        if ((p = tab[i = (n - 1) & hash]) == null) {
            //第一步到这里结束  如果两个线程同时走到这里会如何？会丢失
            tab[i] = newNode(hash, key, value, null);
        } else {
            Node<K, V> e = null;
            K k;
            //如果放入的是同一个元素
            if (p.hash == hash && ((k = p.key) == key || key != null && key.equals(k)))
                e = p;
                //如果元素超过8改为红黑树。
            else if (p instanceof TreeNode) {
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            } else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1)
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //如果旧值存在判断是否需要更新旧值
            if (e != null) {
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return value;
    }

    private void treeifyBin(Node<K, V>[] tab, int hash) {
        int n, index;
        Node<K, V> e;
        //可能运气不好，都是分配到第一列
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY) {
            resize();
            //确定这个位置有值的
        } else if ((e = tab[index = (n - 1) & hash]) != null) {
            //将列表转换成treeNode
            TreeNode<K, V> hd = null, tl = null;
            do {
                TreeNode<K, V> p = replacementTreeNode(e, null);
                if (tl == null) {
                    hd = p;
                } else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null) {
                hd.treeify(tab);
            }
        }
    }

    private TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }

    private void afterNodeInsertion(boolean evict) {

    }

    private void afterNodeAccess(Node<K, V> e) {

    }

    private Node<K, V> newNode(int hash, K key, V value, Object o) {
        return null;
    }

    private Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap = 0, newThr = 0;
        //假设需要扩容
        if (oldCap > 0) {
            // 阈值和边界值扩容2倍
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1;
        } else if (oldThr > 0) {
            newCap = oldThr;
        } else {
            //第一步
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY) ? (int) ft : Integer.MAX_VALUE;
        }
        //第一步肯定是走这里，因为初始oldTab 一定是null
        threshold = newThr;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {
                    //为了GC？
                    oldTab[j] = null;
                    //判断该元素是否存在链表情况
                    //不存在直接分配新的数组上
                    if (e.next == null) {
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode) {

                    } else {
                        //存在列表情况
                        Node<K, V> loHead = null, loTail = null,
                                hiHead = null, hiTail = null,
                                next;
                        //1.8修复了多线程扩容死锁问题。
                        do {
                            next = e.next;
                            /**
                             * 扩容后，元素要么是在原位置，要么是在原位置再移动原长度的位置，且链表顺序不变。
                             * 不需要重新计算hash，只需要根据原来hash值新增的bit是1还是0分别放进两个链表lo和hi（非红黑树的情况）里，0的话索引没变，1的话索引变为原索引加原来的数组长度。
                             * 因为用的尾插法所以新数组链表不会倒置，多线程下不会出现死循环。
                             */
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    @Data
    @AllArgsConstructor
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }

    static class Entry<K, V> extends Node<K, V> {
        Entry<K, V> before, after;

        Entry(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }


    static final class TreeNode<K, V> extends Entry<K, V> {
        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;    // needed to unlink next upon deletion
        boolean red;

        TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }

        final TreeNode<K, V> root() {
            //还能这么写的？
            for (TreeNode<K, V> r = this, p; ; ) {
                if ((p = r.parent) == null) return r;
                r = p;
            }
        }

        final void treeify(Node<K, V>[] tab) {
            TreeNode<K, V> root = null;
            for (TreeNode<K, V> x = this, next; x != null; x = next) {
                next = (TreeNode<K, V>) x.next;
            }
        }


        //放入或者返回节点
        final TreeNode<K, V> putTreeVal(MyHashMap<K, V> map, Node<K, V>[] tab,
                                        int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            TreeNode<K, V> root = (parent != null) ? root() : this;
            for (TreeNode<K, V> p = root; ; ) {
                int dir, ph;
                K pk;
                //这里应该是左旋 或者 右旋
                if ((ph = p.hash) > h) {
                    dir = -1;
                } else if (ph < h) {
                    dir = 1;
                    //找到就返回
                } else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                        (kc = comparableClassFor(k)) == null) ||
                        (dir = compareComparables(kc, k, pk)) == 0) {
                    if (!searched) {
                        TreeNode<K, V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                                (q = ch.find(h, k, kc)) != null) ||
                                ((ch = p.right) != null &&
                                        (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }
            }
        }

        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                    (d = a.getClass().getName().
                            compareTo(b.getClass().getName())) == 0)
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                        -1 : 1);
            return d;
        }

        static int compareComparables(Class<?> kc, Object k, Object x) {
            return (x == null || x.getClass() != kc ? 0 :
                    ((Comparable)k).compareTo(x));
        }

        static Class<?> comparableClassFor(Object x) {
            if (x instanceof Comparable) {
                Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
                if ((c = x.getClass()) == String.class) // bypass checks
                    return c;
                if ((ts = c.getGenericInterfaces()) != null) {
                    for (int i = 0; i < ts.length; ++i) {
                        if (((t = ts[i]) instanceof ParameterizedType) &&
                                ((p = (ParameterizedType)t).getRawType() ==
                                        Comparable.class) &&
                                (as = p.getActualTypeArguments()) != null &&
                                as.length == 1 && as[0] == c) // type arg is c
                            return c;
                    }
                }
            }
            return null;
        }

        final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
            TreeNode<K, V> p = this;
            do {
                int ph, dir;
                K pk;
                TreeNode<K, V> pl = p.left, pr = p.right, q;
                if ((ph = p.hash) > h)
                    p = pl;
                else if (ph < h)
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if (pl == null)
                    p = pr;
                else if (pr == null)
                    p = pl;
                else if ((kc != null ||
                        (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0)
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.find(h, k, kc)) != null)
                    return q;
                else
                    p = pl;
            } while (p != null);
            return null;
        }
    }
}
