package calculation.chapter.base;

/**
 * 局部最小：定义局部最小的概念。arr长度为1时，arr[0]是局部最小。arr的长度为N(N>1)时，
 * 如果arr[0]<arr[1]，那么arr[0]是局部最小；如果arr[N-1]<arr[N-2]，那么arr[N-1]是局部最小；
 * 如果0<i<N-1，既有arr[i]<arr[i-1]又有arr[i]<arr[i+1]，那么arr[i]是局部最小。
 * 给定无序数组arr，已知arr中任意两个相邻的数都不相等，写一个函数，只需返回arr中任意一个局部最小出现的位置即可.
 *
 * @author: xuxianbei
 * Date: 2021/6/21
 * Time: 10:36
 * Version:V1.0
 */
public class PartMin {

    /**
     * 1.头部最小
     * 2.尾部最小
     * 3.中间存在最小
     */

    /**
     * 找到最小
     *
     * @return
     */
    public int findPartMin(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        if (array.length == 1) {
            return array[0];
        }
        if (array[0] < array[1]) {
            return array[0];
        }
        if (array[array.length - 2] > array[array.length - 1]) {
            return array[array.length - 1];
        }
        for (int i = 1; i < array.length; i++) {
            if (i + 1 < array.length) {
                if (array[i - 1] < array[i] && array[i] < array[i + 1]) {
                    return array[i];
                }
            }
        }
        return -1;
    }

    public int fastFindPartMin(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        if (array.length == 1) {
            return 0;
        }
        if (array[0] < array[1]) {
            return 1;
        }
        if (array[array.length - 2] > array[array.length - 1]) {
            return array.length - 1;
        }
        int left = 1;
        int right = array.length - 2;
        //游标可以代替数据拷贝
        while (left < right) {
            int min = (right - left) / 2;
            if (array[min] < array[min + 1]) {
                right = right - 1;
            } else if (array[min - 1] > array[min]){
                left = left + 1;
            } else {
                return min;
            }
        }
        return left;

    }

    public static void main(String[] args) {

    }
}
