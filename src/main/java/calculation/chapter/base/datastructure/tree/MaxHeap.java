package calculation.chapter.base.datastructure.tree;

import lombok.Data;

import java.util.Arrays;

/**
 * 题6：用数组构建大根堆，小根堆。
 * 题7：大根堆，拿到最大的数，取走之后，堆依旧成立，swap， heapify
 *
 * @author: xuxianbei
 * Date: 2021/8/30
 * Time: 10:40
 * Version:V1.0
 */
public class MaxHeap {

    private int size;

    private int capacity;

    private int[] table;

    public MaxHeap(int capacity) {
        table = new int[capacity];
        this.capacity = capacity;
        size = 0;
    }

    public int put(int value) {
        if (isFull()) {
            return -1;
        }
        table[size] = value;
        heapify(size);
        size++;
        return size;
    }

    private void heapify(int index) {
        if (index == 0) return;
        while (true) {
            int parent = (index - 1) / 2;
            if (table[index] > table[parent]) {
                swap(table, index, parent);
                index = parent;
            } else {
                break;
            }
            if (parent == 0) {
                break;
            }
        }
    }

    private void swap(int[] table, int index, int parent) {
        int temp = table[index];
        table[index] = table[parent];
        table[parent] = temp;
    }

    private boolean isFull() {
        return size == capacity - 1;
    }

    public int getMaxAndRemove() {
        if (isEmpty()) {
            return -1;
        }
        int max = table[0];
        removeHeapify(size);
        size--;
        if (size <= 0) {
            size = 0;
        }
        return max;
    }

    private void removeHeapify(int index) {
        table[0] = table[index];
        int parent = 0;
        while (true) {
            Node maxNode = getLeft(parent).value > getRight(parent).value ? getLeft(parent) : getRight(parent);
            if (maxNode.getValue() == -1) {
                break;
            }
            if (maxNode.getValue() > table[parent]) {
                swap(table, maxNode.getIndex(), parent);
                parent = maxNode.getIndex();
            } else {
                break;
            }
        }

    }

    @Data
    class Node {
        int index;
        int value;
    }

    private Node getRight(int parent) {
        Node node = new Node();
        node.setIndex(parent * 2 + 2);

        if (parent * 2 + 2 <= size) {
            node.setValue(table[parent * 2 + 2]);
        } else {
            node.setValue(-1);
        }
        return node;
    }

    private Node getLeft(int parent) {
        Node node = new Node();
        node.setIndex(parent * 2 + 1);

        if (parent * 2 + 1 <= size) {
            node.setValue(table[parent * 2 + 1]);
        } else {
            node.setValue(-1);
        }
        return node;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);
        maxHeap.put(2);
        maxHeap.put(9);
        maxHeap.put(12);
        maxHeap.put(5);
        maxHeap.put(3);
        maxHeap.put(1);
        System.out.print(Arrays.toString(maxHeap.table));
        maxHeap.getMaxAndRemove();
        System.out.print(Arrays.toString(maxHeap.table));
    }

}
