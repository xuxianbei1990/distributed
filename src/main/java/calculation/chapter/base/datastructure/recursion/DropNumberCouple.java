package calculation.chapter.base.datastructure.recursion;

import java.util.Arrays;

/**
 * 题2. 降序对：【3,1,7,0,2】降序对【3,1】【3,0】，【3,2】【1,0】，【7,0】，【7,2】
 *
 * @author: xuxianbei
 * Date: 2021/7/28
 * Time: 13:57
 * Version:V1.0
 */
public class DropNumberCouple {

    public static void main(String[] args) {
        DropNumberCouple dropNumberCouple = new DropNumberCouple();
        dropNumberCouple.execute(new int[]{3, 1, 7, 0, 2});
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

    private void merge(int[] array, int i, int mid, int length, int[] temps) {
        int left = i;
        int right = mid + 1;
        int tempIndex = 0;
        while (left <= mid && right <= length) {
            if (array[left] < array[right]) {
                temps[tempIndex++] = array[left++];
            } else {
                for (int j = left; j <= mid; j++) {
                    System.out.println(array[j] + "," + array[right]);
                }
                temps[tempIndex++] = array[right++];
            }
        }
        while (left <= mid) {
            temps[tempIndex++] = array[left++];
        }

        while (right <= length) {
            temps[tempIndex++] = array[right++];
        }

        tempIndex = 0;
        left = i;
        while (left <= length) {
            array[left++] = temps[tempIndex++];
        }
    }
}
