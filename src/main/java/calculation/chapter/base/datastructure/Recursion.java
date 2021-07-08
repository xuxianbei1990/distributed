package calculation.chapter.base.datastructure;

/**
 * 题1：求数组arr[L..R]的最大值，递归实现。
 *
 * @author: xuxianbei
 * Date: 2021/6/25
 * Time: 16:38
 * Version:V1.0
 */
public class Recursion {

    public int maxValue(int[] array) {
        return process(array, 0, array.length - 1);
    }

    private int process(int[] array, int i, int length) {
        if (i == length) {
            return array[i];
        }
        int mid = (i + length) / 2;
        int left = process(array, i, mid);
        int right = process(array, mid + 1, length);
        return Math.max(left, right);
    }

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        System.out.println(recursion.maxValue(new int[]{2, 3, 5, 7, 1, 8, 9, 10, 2}));
    }
}
