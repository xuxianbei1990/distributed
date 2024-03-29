package calculation.chapter.three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

/**
 * 选择排序算法
 * 当原始数组大部分元素无序时，选择排序性能最优。
 * <p>
 * 我家房子拆迁，明天去签合同，请一天事假
 * O（n^2）
 *
 * @author: xuxianbei
 * Date: 2019/12/16
 * Time: 9:31
 * Version:V1.0
 * <p>
 * 1、从第一个元素开始，分别与后面的元素向比较，找到最小的元素与第一个元素交换位置；
 * 2、从第二个元素开始，分别与后面的元素相比较，找到剩余元素中最小的元素，与第二个元素交换；
 * 3、重复上述步骤，直到所有的元素都排成由小到大为止。
 */
public class ChooseSort {

    private int[] array;
    private int length;

    public ChooseSort(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public void sort() {
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    public static <T> void practice(List<T> array, Comparator<T> comparator) {
        for (int i = 0; i < array.size(); i++) {
            int min = i;
            for (int j = i + 1; j < array.size(); j++) {
                if (comparator.compare(array.get(j), array.get(min)) > 0) {
                    min = j;
                }
            }
            swap(i, min, array);
        }
        System.out.println(array);
    }

    private static <T> void swap(int a, int b, List<T> array) {
        T temp = array.get(a);
        array.set(a, array.get(b));
        array.set(b, temp);
    }


    public static void main(String[] args) {
        int[] array = {100, 45, 36, 21, 17, 13, 7};
        ChooseSort chooseSort = new ChooseSort(array);
        System.out.println("排序前:");
        chooseSort.display();
        chooseSort.sort();

        System.out.println("排序后:");
        Integer[] array2 = {100, 45, 36, 21, 17, 13, 7};
        chooseSort.practice(new ArrayList<>(Arrays.asList(array2)), (a, b) -> a > b ? 1 : 0);
        chooseSort.display();
    }
}
