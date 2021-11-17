package calculation.chapter.base.datastructure.tree;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 题5：二叉树结构定义如下：
 * Class Node{
 * V value;
 * Node left;
 * Node right;
 * Node parent;
 * }
 * 给你二叉树中的某个节点，返回该节点的后继节点
 *
 * @author: xuxianbei
 * Date: 2021/11/15
 * Time: 13:11
 * Version:V1.0
 */
public class TwoForTreeSuffix {

    static class Node<V> {
        V value;
        Node left;
        Node right;
        Node parent;

        Node(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    public Node<Integer> violenceExecute(Node<Integer> header) {
        Node<Integer> index = header;
        while (index.parent != null) {
            index = index.parent;
        }
        List<Node<Integer>> list = new ArrayList();
        midFor(index, list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(header) && i + 1 < list.size()) {
                return list.get(i + 1);
            }
        }
        return null;
    }

    private void midFor(Node<Integer> index, List<Node<Integer>> list) {
        if (index == null) {
            return;
        }
        midFor(index.left, list);
        list.add(index);
        midFor(index.right, list);
    }


    public Node<Integer> execute(Node<Integer> header) {
        if (header == null) {
            return header;
        }
        if (header.right != null) {
            Node<Integer> index = header.right;
            while (index.left != null) {
                index = index.left;
            }
            return index;
        }
        if (header.parent.left == header) {
            return header.parent;
        }
        Node<Integer> index = header;
        while (index != null) {
            if (index == index.parent.left) {
                return index.parent;
            } else {
                index = index.parent;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        TwoForTreeSuffix twoForTreeSuffix = new TwoForTreeSuffix();
        Node<Integer> header = twoForTreeSuffix.createBaseData();
        System.out.println(twoForTreeSuffix.violenceExecute(header));
        System.out.println(twoForTreeSuffix.execute(header));
    }

    private Node<Integer> createBaseData() {
        Node<Integer> node = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);
        Node<Integer> node7 = new Node<>(7);
        Node<Integer> node8 = new Node<>(8);
        Node<Integer> node9 = new Node<>(9);
        node.left = node2;
        node.right = node3;
        node2.parent = node;
        node2.left = node4;
        node2.right = node5;
        node3.parent = node;
        node3.left = node6;
        node3.right = node7;
        node4.parent = node2;
        node4.left = node8;
        node4.right = node9;
        node5.parent = node2;
        node6.parent = node3;
        node7.parent = node3;
        node8.parent = node4;
        node9.parent = node5;
        return node8;
    }
}
