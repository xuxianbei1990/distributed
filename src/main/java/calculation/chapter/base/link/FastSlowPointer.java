package calculation.chapter.base.link;

import java.util.ArrayList;
import java.util.List;

/**
 * 题12：快慢指针
 * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点；
 * 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点；
 * 3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个；
 * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个；
 * 分别以数组链表实现。
 *
 * @author: xuxianbei
 * Date: 2021/10/28
 * Time: 14:36
 * Version:V1.0
 */
public class FastSlowPointer {

    /**
     * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点；
     *
     * @param header
     * @return
     */
    public Node<Integer> execute(Node<Integer> header) {
        if (header == null || header.getNext() == null || header.getNext().getNext() == null) {
            return header;
        }
        Node<Integer> fast = header.getNext().getNext();
        Node<Integer> slow = header.getNext();
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }

    /**
     * 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点；
     *
     * @param header
     * @return
     */
    public Node<Integer> execute2(Node<Integer> header) {
        if (header == null || header.getNext() == null || header.getNext().getNext() == null) {
            return header;
        }
        Node<Integer> fast = header.getNext().getNext();
        Node<Integer> slow = header.getNext();
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        if (fast.getNext() != null) {
            return slow.getNext();
        }
        return slow;
    }

    /**
     * 3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个；
     *
     * @param header
     * @return
     */
    public Node<Integer> execute3(Node<Integer> header) {
        if (header == null || header.getNext() == null || header.getNext().getNext() == null) {
            return header;
        }

        Node<Integer> fast = header.getNext().getNext();
        Node<Integer> slow = header;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }

    /**
     * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个；
     *
     * @param header
     * @return
     */
    public Node<Integer> execute4(Node<Integer> header) {
        if (header == null || header.getNext() == null || header.getNext().getNext() == null) {
            return header;
        }

        Node<Integer> fast = header.getNext().getNext();
        Node<Integer> slow = header;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        if (fast.getNext() != null) {
            return slow.getNext();
        }
        return slow;
    }


    public Node<Integer> execute5(Node<Integer> header, int next) {
        List<Node<Integer>> list = new ArrayList<>();
        Node<Integer> index = header;
        while (index != null) {
            list.add(index);
            index = index.getNext();
        }
        if (list.size() % 2 == 0) {
            return list.get((list.size() - 1) / 2 + next);
        }
        return list.get((list.size() - 1) / 2);
    }

    public static void main(String[] args) {
        Node<Integer> header = new Node<>(0);
        Node<Integer> index = header;
        for (int i = 1; i < 10; i++) {
            Node<Integer> temp = new Node<>(i);
            index.setNext(temp);
            index = temp;
        }
        FastSlowPointer fastSlowPointer = new FastSlowPointer();
        System.out.println(fastSlowPointer.execute(header).equals(fastSlowPointer.execute5(header, 0)));
        System.out.println(fastSlowPointer.execute2(header).equals(fastSlowPointer.execute5(header, 1)));
        System.out.println(fastSlowPointer.execute3(header).equals(fastSlowPointer.execute5(header, -1)));
        System.out.println(fastSlowPointer.execute4(header).equals(fastSlowPointer.execute5(header, 0)));
    }
}
