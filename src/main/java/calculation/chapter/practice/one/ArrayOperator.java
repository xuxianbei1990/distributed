package calculation.chapter.practice.one;

import calculation.chapter.base.link.Node;

/**
 * 数组
 *
 * @author: xuxianbei
 * Date: 2022/2/16
 * Time: 11:19
 * Version:V1.0
 */
public class ArrayOperator {

    /**
     * 冒泡
     * 1改进：如果已经有序了，没法生成任何交换，那么即整体有序，跳出循环
     * 2改进：在1改进的基础上，记录最后一次交换的位置，第二个循环使用这个位置交换，详细见BubbleSort
     *
     * @param arrays
     */
    public void maoPao(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            for (int i1 = i + 1; i1 < arrays.length; i1++) {
                if (arrays[i] < arrays[i1]) {
                    swap(arrays, i, i1);
                }
            }
        }
    }


    /**
     * 选择
     *
     * @param arrays
     */
    public void xuanze(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            int min = i;
            for (int i1 = i + 1; i1 < arrays.length; i1++) {
                if (arrays[min] > arrays[i]) {
                    min = i;
                }
            }
            if (min != i) {
                swap(arrays, min, i);
            }
        }
    }

    /**
     * 插入排序
     */
    public void charu(int[] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            int temp = arrays[i];
            int j = i;
            while (j >= 0 && arrays[j] > temp) {
                arrays[j + 1] = arrays[j];
                j--;
            }
            arrays[j + 1] = temp;
        }
    }

    /**
     * 归并排序
     *
     * @param arrays
     */
    public void guiBing(int[] arrays) {
        int[] temps = new int[arrays.length];
        doGuiBing(arrays, 0, arrays.length, temps);
    }

    public void doGuiBing(int[] arrays, int left, int right, int[] temps) {
        if (left < right) {
            int mid = (right - left) / 2;
            doGuiBing(arrays, left, mid, temps);
            doGuiBing(arrays, mid + 1, right, temps);
            mergy(arrays, left, mid, right, temps);
        }
    }

    private void mergy(int[] arrays, int left, int mid, int right, int[] temps) {
        int p1 = left;
        int p2 = mid + 1;
        int p = 0;
        while (p1 <= mid && p2 <= right) {
            if (arrays[p1] < arrays[p2]) {
                temps[p++] = arrays[p1++];
            } else {
                temps[p++] = arrays[p2++];
            }
        }
        while (p1 <= mid) {
            temps[p++] = arrays[p1++];
        }
        while (p2 <= right) {
            temps[p++] = arrays[p2++];
        }
        p = left;
        p1 = 0;
        while (p <= right) {
            arrays[p++] = temps[p1++];
        }
    }

    public static Node reverse(Node head) {
        Node prev = null;
        Node next;
        Node current = head;
        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        Node<Integer> node  = new Node<>(0);
        Node<Integer> node1  = new Node<>(1);
        Node<Integer> node2  = new Node<>(2);
        Node<Integer> node3  = new Node<>(3);
        Node<Integer> node4  = new Node<>(4);
        node.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        System.out.println(node);
        System.out.println(reverse(node));
    }


    private void swap(int[] arrays, int array, int array1) {
        int b = arrays[array1];
        arrays[array1] = arrays[array];
        arrays[array] = b;
    }

}
