package practice;

/**
 * @author: xuxianbei
 * Date: 2022/10/25
 * Time: 13:42
 * Version:V1.0
 */
public class KniConcurrentHashMap {

    /**
     * 多线程扩容问题
     * 扩容之前需要打上扩容标记，保证只有一个线程参与扩容
     */

    /**
     * 多线程存的问题
     * 1.没有链表
     * 让高位参与Hash,减少Hash冲突，& HASH_BITS 该步是为了消除最高位上的负符号，hash的负在ConcurrentHashMap中有特殊意义表示在扩容或者是树节点
     * (h ^ (h >>> 16)) & HASH_BITS;
     * 通过数组长度的与运算得到位置，且判断该位置如果是NUll。则继续
     * tabAt(tab, i = (n - 1) & hash)
     * cas方法放入；
     * 2.有链表。
     * 锁住该节点。
     * 然后再判断一次，是不是该点 if (tabAt(tab, i) == f)
     * 确定不是移动点或者树节点。把该节点循环遍历加入
     * 如果超过8，则改造成红黑树
     * 3.有链表。且树节点，则放入红黑树
     * 4.节点转移
     */

    /**
     * 计数
     * 采用cas计数
     * U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)
     * 如果失败，采用创建随机数组分担压力
     *                     else if (cellsBusy == 0 &&
     *                         U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
     *                     try {
     *                         if (counterCells == as) {// Expand table unless stale
     *                             CounterCell[] rs = new CounterCell[n << 1];
     *                             for (int i = 0; i < n; ++i)
     *                                 rs[i] = as[i];
     *                             counterCells = rs;
     *                         }
     *                     } finally {
     *                         cellsBusy = 0;
     *                     }
     *                     collide = false;
     *                     continue;                   // Retry with expanded table
     *                 }
     * 如果压力还是大的话，再创建数组进行计数
     * 最后汇总统计
     */

    /**
     * 多线程取的问题
     * 正常取
     */

    /**
     * 多线程删除问题
     * 1.定位，然后把该位置置为null
     * 2.链表，定位然后链表循环找到，置为null，然后组织链表
     */
}
