package calculation.chapter.base.link;

import calculation.chapter.base.datastructure.recursion.HolllandQuickSort;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 题14：将单向链表按某值划分左边小，中间相等、右边大的形式
 * 1）把链表放入数组中，在数组上做partition（笔试用）
 * 2）分成小，中，大三部分，再把各个部分之间串起来（面试用）
 *
 * @author: xuxianbei
 * Date: 2021/10/30
 * Time: 15:48
 * Version:V1.0
 */
public class Hollland {

    public Node<Integer> execute(Node<Integer> header, Integer num) {
        HolllandQuickSort holllandQuickSort = new HolllandQuickSort();
        List<Integer> array = new ArrayList<>();
        Node<Integer> index = header;
        while (index != null) {
            array.add(index.getData());
            index = index.getNext();
        }
        int[] ints = array.stream().mapToInt(Integer::valueOf).toArray();
        ints = holllandQuickSort.execute2(ints, num);
        index = header;
        for (int anInt : ints) {
            index.setData(anInt);
            index = index.getNext();
        }
        return header;
    }

    public Node<Integer> execute2(Node<Integer> header, Integer num) {
        List<Integer> array = new ArrayList<>();
        Node<Integer> index = header;
        while (index != null) {
            array.add(index.getData());
            index = index.getNext();
        }
        Integer[] ints = holllandArrraySort(array.toArray(new Integer[array.size()]), num);

        index = header;
        for (int anInt : ints) {
            index.setData(anInt);
            index = index.getNext();
        }
        return header;
    }

    @Data
    static class NodeContext {
        private Node<Integer> head;
        private Node<Integer> end;
    }

    public Node<Integer> execute3(Node<Integer> header, Integer num) {
        NodeContext leftNode = new NodeContext();
        NodeContext sameNode = new NodeContext();
        NodeContext rightNode = new NodeContext();
        Node<Integer> index = header;
        while (index != null) {
            if (index.getData() < num) {
                fill(leftNode, index);
            } else if (index.getData() == num) {
                fill(sameNode, index);
            } else {
                fill(rightNode, index);
            }
            index = index.getNext();
        }
        leftNode.getEnd().setNext(sameNode.getHead());
        sameNode.getEnd().setNext(rightNode.getHead());
        return leftNode.getHead();
    }

    private void fill(NodeContext leftNode, Node<Integer> index) {
        if (leftNode.getHead() == null) {
            leftNode.setHead(index);
            leftNode.setEnd(index);
        } else {
            if (leftNode.getHead() == leftNode.getEnd()) {
                leftNode.setEnd(index);
                leftNode.getHead().setNext(leftNode.getEnd());
            } else {
                leftNode.getEnd().setNext(index);
                leftNode.getHead().setNext(leftNode.getEnd());
                leftNode.setEnd(index);
            }
        }
    }

    public Integer[] holllandArrraySort(Integer[] integers, int num) {
        int left = 0;
        int same = 0;
        int right = integers.length - 1;
        while (same < right) {
            if (integers[same] < num) {
                swap(integers, left++, same++);
            } else if (integers[same] > num) {
                swap(integers, same, right--);
            } else {
                same++;
            }
        }
        return integers;
    }

    private void swap(Integer[] integers, int a, int b) {
        int temp = integers[a];
        integers[a] = integers[b];
        integers[b] = temp;
    }

    public static void main(String[] args) {
        Node<Integer> header = new Node<>(0);
        Node<Integer> header1 = new Node<>(1);
        Node<Integer> header2 = new Node<>(2);
        Node<Integer> header3 = new Node<>(3);
        Node<Integer> header4 = new Node<>(2);
        Node<Integer> header5 = new Node<>(7);
        header.setNext(header1);
        header1.setNext(header2);
        header2.setNext(header3);
        header3.setNext(header4);
        header4.setNext(header5);
        Hollland hollland = new Hollland();
//        System.out.println(hollland.execute2(header, 2));
//        System.out.println(hollland.execute(header, 2));
        System.out.println(hollland.execute3(header, 2));

    }

}
