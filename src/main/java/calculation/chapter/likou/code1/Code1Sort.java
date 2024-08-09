package calculation.chapter.likou.code1;

import java.util.Arrays;

public class Code1Sort {

    /**
     * 选择排序：1.从0到N选择一个最小数放到第0位。2.从1到N选择一个最小的数放到1上，以此类推
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int temp = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[temp] > array[j]) {
                    temp = j;
                }
            }
            swap(temp, i, array);
        }
    }

    /**
     * 冒泡
     *
     * @param array
     */
    public static void bubbling(int[] array) {
        for (int i = 0; i < array.length; i++) {
            //改进写法：j < array.length - i
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    //互换时候打个标记：如果没有互换，就直接退出
                    swap(i, j, array);
                }
            }
        }
    }

    /**
     * 插入排序
     *
     * @param array
     */
    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int cur = i;
            for (int j = cur - 1; j > 0; j--) {
                if (array[cur] < array[j]) {
                    //如果没有交换，那么直接退出
                    swap(cur, j, array);
                    cur = j;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] array = {1,4,5,6,2,7,8,1};
        insertSort(array);
        System.out.println(Arrays.toString(array));
        bubbling(array);
        System.out.println(Arrays.toString(array));
    }

    private static void swap(int a, int b, int[] array) {
        int temp = array[b];
        array[b] = array[a];
        array[a] = temp;
    }
}
