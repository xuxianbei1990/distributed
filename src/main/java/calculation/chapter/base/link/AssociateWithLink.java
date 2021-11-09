package calculation.chapter.base.link;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 题16： 给定两个可能有环也可能无环的单链表， 头节点head1和head2.请实现一个函数，如果两个链表相交，
 * 请返回相交的第一个节点。如果不相交，返回null。
 * 要求：如果两个链表长度之和为N，时间复杂度为0(N)，空间复杂度0(1)
 * <p>
 * 1.两个都是无环
 * 2.两个都是有环
 * 3.一个有环，一个无环
 * a.如果两个无环的，相交一定尾部重合
 * b.如果两个有环， 相交那么一定是环上或者共用一个环。
 * c.一个有环，一个无环一定不相交
 *
 * @author: xuxianbei
 * Date: 2021/11/7
 * Time: 15:51
 * Version:V1.0
 */
public class AssociateWithLink {

    public Node judgeCricle(Node header, Set<Node> set) {
        Node index = header;
        while (index != null) {
            if (!set.add(index)) {
                return index;
            }
            index = index.getNext();
        }
        return null;
    }

    /**
     * 这里原理是：快慢指针, 快指针走两步，慢指针走一步，他们相交的时候。
     *
     * @param header
     * @return
     */
    public Node judgeCricleFastSlow(Node header) {
        if (header == null || header.getNext() == null || header.getNext().getNext() == null) {
            return header;
        }
        Node fast = header.getNext().getNext();
        Node slow = header.getNext();
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
            if (fast.equals(slow)) {
                break;
            }
        }
        fast = header;
        while (fast.getNext() != null) {
            fast = fast.getNext();
            slow = slow.getNext();
            if (fast.equals(slow)) {
                return fast;
            }
        }
        return null;
    }

    public Node execute(Node header1, Node header2) {
        if (header1.equals(header2)) {
            return header1;
        }

        Set<Node> set = new HashSet();
        Set<Node> set2 = new HashSet();
        Node headerIs = judgeCricle(header1, set);
        Node header2Is = judgeCricle(header2, set2);
        if (headerIs != header2Is) {
            return null;
        }
        //无环
        if (headerIs == null) {
            return judgeCricle(header2, set);
        }
        //有环
        if (headerIs == header2Is) {
            return headerIs;
        }
        return judgeCricle(header2, set);
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(1);
        Node<Integer> node1 = new Node<>(2);
        Node<Integer> node2 = new Node<>(3);
        Node<Integer> node3 = new Node<>(4);
        Node<Integer> node4 = new Node<>(5);
        node.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node2);
        AssociateWithLink associateWithLink = new AssociateWithLink();
        System.out.println(associateWithLink.judgeCricleFastSlow(node).getData());
        System.out.println(associateWithLink.judgeCricle(node, new HashSet<>()).getData());

    }
}
