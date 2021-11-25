package calculation.chapter.likou;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 生成窗口最大值数组
 * <p>
 * 题 有一个整型数组arr和一个大小为w的窗口从数组的最左端滑到最后端，每次只向右移动一个位置
 * 如果数组长度为n， 窗口大小为w 则一共产生n-w+1个窗口的最大值
 * 输入： 整型数组arr。窗口w
 * 输出 n-w+1的数组res.其中认识res[i]表示每一种情况的最大值
 * 例如数组 【4,3,5,4,3,3,6,7】
 * 输出： 5,5,5,4,6,7
 *
 * @author: xuxianbei
 * Date: 2021/11/25
 * Time: 9:45
 * Version:V1.0
 */
public class MaxWindowArray {

    public int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] < arr[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);
            if (qmax.peekFirst() == i - w) {
                qmax.pollFirst();
            }
            if (i >= w - 1) {
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        MaxWindowArray maxWindowArray = new MaxWindowArray();
        System.out.println(Arrays.toString(maxWindowArray.getMaxWindow(new int[]{4, 3, 5, 4, 3, 3, 6, 7}, 3)));
        System.out.println(Arrays.toString(maxWindowArray.execute(new int[]{4, 3, 5, 4, 3, 3, 6, 7}, 3)));
    }

    public int[] execute(int[] array, int w) {
        int[] res = new int[array.length - w + 1];
        int index = 0;
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            while (!linkedList.isEmpty() && array[linkedList.peekFirst()] < array[i]) {
                linkedList.pollFirst();
            }
            linkedList.addLast(i);

            if (linkedList.peekFirst() == i - w) {
                linkedList.pollFirst();
            }

            if (i >= w - 1) {
                res[index++] = array[linkedList.peekFirst()];
            }

        }

        return res;
    }
}
