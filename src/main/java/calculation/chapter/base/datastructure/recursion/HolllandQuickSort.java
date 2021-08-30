package calculation.chapter.base.datastructure.recursion;

import java.util.Arrays;

/** 荷兰国旗问题 给定一个数组arr 和一个整数num ，请把小于num的数放在数组的左边，等于num的数放中间，大于num的数放在数组的右边。
 空间复杂度o(1),时间复杂度o(N)
 * @author: xuxianbei
 * Date: 2021/7/28
 * Time: 20:26
 * Version:V1.0
 */
public class HolllandQuickSort {

    public static void main(String[] args) {
        HolllandQuickSort holllandQuickSort = new HolllandQuickSort();
        System.out.println(Arrays.toString(holllandQuickSort.execute(new int[]{1, 3, 2, 4, 5, 1, 3, 7, 3}, 3)));
        System.out.println(Arrays.toString(holllandQuickSort.execute2(new int[]{1, 3, 2, 4, 5, 1, 3, 7, 3}, 3)));
    }


    public int[] execute(int[] array, int num) {
        progress(array, 0, array.length - 1, num);
        return array;
    }

    public int[] execute2(int[] array, int num) {
        threeArea(array, 0, array.length - 1, num);
        return array;
    }

    private void progress(int[] array, int i, int length, int num) {
        int index = findIndex(array, i, length, num);

    }

    private void threeArea(int[] array, int i, int lenth, int num) {
        int left = i;
        int right = lenth;
        int same = left;
        while (same <= right) {
            if (array[same] < num) {
                swap(array, left++, same++);
            } else if (array[same] > num) {
                swap(array, same, right--);
            } else {
                same++;
            }

        }
    }

    private int findIndex(int[] array, int i, int length, int num) {
        int left = i;
        int right = length;
        int same = 0;
        while (left <= right) {
            if (array[left] < num) {
                if (same > 0) {
                    swap(array, left, same);
                    same = left;
                }
                left++;
            } else if (array[left] == num) {
                if (same == 0) {
                    same = left;
                } else {
                    left++;
                }
            } else if (array[left] > num) {
                swap(array, left, right);
                right--;
            }
        }

        return left;
    }

    private void swap(int[] array, int a, int b) {
        int mid = array[b];
        array[b] = array[a];
        array[a] = mid;
    }

}
