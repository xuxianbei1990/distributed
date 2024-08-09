package calculation.chapter.base.link;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 题15：一种特殊的单链表点类描述如下：
 * class Node{
 * int value;
 * Node next;
 * Node rand;
 * Node(int val){value = val;}
 * }
 * rand指针是单链表结构中新增的指针，rand可能指向链表中的任意一个节，也可能指向null。
 * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制，
 * 并返回复制的新链表的头节点。
 * 要求：时间复杂度O(N)，空间复杂度O(1)
 *
 * @author: xuxianbei
 * Date: 2021/11/1
 * Time: 17:39
 * Version:V1.0
 */
public class RandomLink {


    @Data
    static class Node {
        int value;
        Node next;
        Node rand;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }

        Node(int value) {
            this.value = value;
        }

        @Override
        protected Node clone() throws CloneNotSupportedException {
            Node node = (Node) super.clone();
            return node;
        }


        protected Node cloneNext(Node node) throws CloneNotSupportedException {
            node.next = this.next.clone();
            node.rand = this.rand.clone();
            return node;
        }
    }


    @SneakyThrows
    public Node execute(Node header) {
        Node index = header;
        while (index != null) {
            //代码问题出在这里，实际链表没法克隆
            Node help = index.clone();
            index.next = help;
            index = help.next;
        }
        Node result = header.next;
        Node index1 = header;
        Node index2 = header.next;
        while (index2 != null) {
            index1.next = index2.next;
            index2.next = index1.next.next;
            index1 = index1.next;
            index2 = index2.next;
        }
        return result;
    }

    public Node hashMapExecute(Node header) {
        if (header == null) {
            return header;
        }
        Node index = header;
        Map<Node, Node> map = new HashMap();
        while (index != null) {
            map.put(index, new Node(index.value));
            index = index.next;
        }
        Node header1 = map.get(header);
        index = header.next;
        Node cur = header;
        Node index2 = header1;
        while (index != null) {
            index2.rand = map.get(cur.getRand());
            index2.next = map.get(index);
            cur = index;
            index = index.getNext();
            index2 = index2.next;
        }
        return header1;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;

        node1.rand = node4;
        node2.rand = node1;
        node3.rand = node2;
        node4.rand = node6;
        node5.rand = node7;
        node4.rand = node2;
        node6.rand = node2;
        node7.rand = node1;

        RandomLink randomLink = new RandomLink();
        System.out.println(node1);
        System.out.println(randomLink.hashMapExecute(node1));

    }

}
