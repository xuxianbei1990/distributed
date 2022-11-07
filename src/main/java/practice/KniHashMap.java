package practice;

/**
 * @author: xuxianbei
 * Date: 2022/10/24
 * Time: 14:32
 * Version:V1.0
 */
public class KniHashMap {

    //初始化
    /**
     * 存的问题
     * 拿到一个值的hashcode 然后重新计算HashCode（减少hash冲突）,
     * return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
     * 计算HashCode的位置。
     * if ((p = tab[i = (n - 1) & hash]) == null) {
     */
    //取的问题
    /**
     * 扩容问题
     * 1.没有链表，newTab[e.hash & (newCap - 1)] = e;
     * 2.有链表 (e.hash & oldCap) == 0 进行高低链表分组，然后重新放入新数组中
     * 3.链表且是红黑树，那么也是按照 ((e.hash & bit) == 0) 进行高低链分组，进行重新放入数组，同时进行拆红黑树，或者重组红黑树处理
     *
     */
    /**
     * 删除问题
     * 1.没有链表，rehash,重新定位，置为null
     * 2.有链表，往下找，找到相等的，把下一个节点放到当前的节点的上一个节点上，置为null
     * 3.有链表且红黑树。在链表基础上，加上红黑树删除节点，判断是否需要拆树或者平衡红黑树
     */
}
