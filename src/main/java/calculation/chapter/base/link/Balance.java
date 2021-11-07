package calculation.chapter.base.link;


import java.util.Stack;

/**
 * 题13：给定一个单链表的头节点head，请判断该链表是否为回文结构
 * 1）栈方法简单（笔试用）
 * 2）改原链表的方法就需要注意边界了（面试用）
 *
 * @author: xuxianbei
 * Date: 2021/10/30
 * Time: 14:53
 * Version:V1.0
 */
public class Balance {

    public boolean execute(Node<String> header) {
        if (header == null) {
            return false;
        }
        Stack<Node> stringStack = new Stack();
        Node<String> index = header;
        while (index != null) {
            stringStack.push(index);
            index = index.getNext();
        }
        index = header;
        Node<String> stackIndex = stringStack.pop();
        while (index != null) {
            if (!index.equals(stackIndex)) {
                return false;
            }
            index = index.getNext();
            if (stringStack.isEmpty()) {
                break;
            }
            stackIndex = stringStack.pop();
        }
        return true;
    }

    /**
     * 链表1-》2-》3->2->1 改为1-》2-》3->null   null《-3《-2《-1
     * @param header
     * @return
     */
    public boolean execute2(Node<String> header) {
        if (header == null || header.getNext() == null) {
            return true;
        }
        if (header.getNext().getNext() == null) {
            return header.equals(header.getNext());
        }
        Node<String> mid = getMid(header);
        Node<String> rightHeader = reverse(mid);
        Node<String> index = header;
        Node<String> right = rightHeader;
        boolean result = true;
        while (index != null && right != null) {
            if (!index.equals(right)) {
                result = false;
                break;
            }
            index = index.getNext();
            right = right.getNext();
        }
        reverse(rightHeader);
        return result;
    }

    private Node<String> reverse(Node<String> mid) {
        Node<String> cur = mid;
        Node<String> next = null;
        while (cur != null) {
            Node<String> pre = cur.getNext();
            cur.setNext(next);
            next = cur;
            cur = pre;
        }
        return next;
    }

    private Node<String> getMid(Node<String> header) {
        Node<String> fast = header.getNext().getNext();
        Node<String> slow = header.getNext();
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
        }
        return slow;
    }

    public static void main(String[] args) {
        Node<String> header = new Node(0);
        Node<String> index = header;
        for (int i = 1; i < 10; i++) {
            Node<String> temp = new Node(i);
            index.setNext(temp);
            index = temp;
        }
        Balance balance = new Balance();
        System.out.println(balance.execute2(header));
        System.out.println(balance.execute(header));
        for (int i = 8; i >= 0; i--) {
            Node<String> temp = new Node(i);
            index.setNext(temp);
            index = temp;
        }
        System.out.println(balance.execute(header));
        System.out.println(balance.execute2(header));

    }
}
