package calculation.chapter.base.datastructure;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 归并排序：
 * 1.先让子函数有序，2.让整体有序
 * 递归,非递归实现
 *
 * @author: xuxianbei
 * Date: 2021/6/25
 * Time: 16:51
 * Version:V1.0
 */
public class RecursionSort {


    public static void main(String[] args) {
        RecursionSort recursionSort = new RecursionSort();
        recursionSort.execute(new int[]{7, 5, 1, 2, 3, 4, 9, 6, 2, 7, 8, 1, 3, 5, 4});
    }

    public void execute(int[] array) {
        int[] temps = new int[array.length];
        process(array, 0, array.length - 1, temps);
        System.out.println(Arrays.toString(array));
    }


    private void process(int[] array, int i, int length, int[] temps) {
        if (i < length) {
            int mid = (i + length) / 2;
            process(array, i, mid, temps);
            process(array, mid + 1, length, temps);
            merge(array, i, mid, length, temps);
        }
    }

    private void merge(int[] array, int left, int mid, int right, int[] temps) {
        int p1 = left;
        int p2 = mid + 1;
        int index = 0;
        while (p1 <= mid && p2 <= right) {
            if (array[p1] < array[p2]) {
                temps[index++] = array[p1++];
            } else {
                temps[index++] = array[p2++];
            }
        }
        while (p2 <= right) {
            temps[index++] = array[p2++];
        }

        while (p1 <= mid) {
            temps[index++] = array[p1++];
        }

        index = 0;
        while (left <= right) {
            array[left++] = temps[index++];
        }
    }

}
