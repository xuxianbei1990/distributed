package calculation.chapter.base.datastructure.barrel;

import org.checkerframework.checker.units.qual.C;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;

/**
 * 题10：计数排序
 * <p>
 * 数字范围1到100诺干的数，进行排序。
 *
 * @author: xuxianbei
 * Date: 2021/10/20
 * Time: 14:09
 * Version:V1.0
 */
public class CountSort {

    private int[] helps;

    public int[] sort(int[] arrays, int min, int max) {
        if (arrays == null || arrays.length == 0) {
            return arrays;
        }
        helps = new int[(max - min) + 2];
        for (int array : arrays) {
            helps[array]++;
        }
        int i = 0;
        for (int i1 = 0; i1 < helps.length; i1++) {
            if (helps[i1] != 0) {
                int temp = helps[i1];
                for (int j = 0; j < temp; j++) {
                    arrays[i++] = i1;
                }
            }
        }
        return arrays;
    }

    public static void main(String[] args) {
        CountSort countSort = new CountSort();
        int[] araay = new int[]{1, 2, 3, 4, 5, 6, 3, 2, 2, 3, 12, 34, 11, 22, 33, 100, 34, 43, 23, 21, 1, 2, 3, 9};
        System.out.println(Arrays.toString(countSort.sort(araay, 1, 100)));
        System.out.println(araay.length == countSort.sort(araay, 1, 100).length);
    }
}
